package com.freediving.communityservice.application.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleRemoveCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleDeletePort;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.ArticleWritePort;
import com.freediving.communityservice.application.port.out.BoardReadPort;
import com.freediving.communityservice.application.port.out.CommentDeletePort;
import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Board;
import com.freediving.communityservice.domain.Comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService implements ArticleUseCase {

	private final BoardReadPort boardReadPort;
	private final ArticleWritePort articleWritePort;
	private final ArticleReadPort articleReadPort;
	private final ArticleDeletePort articleDeletePort;
	private final CommentDeletePort commentDeletePort;

	//Query
	@Override
	public Article getArticle(ArticleReadCommand command) {
		return articleReadPort.readArticle(command.getBoardId(), command.getArticleId(), command.isShowAll());
	}

	@Override
	public ArticleContentWithComment getArticleWithComment(ArticleReadCommand command) {

		if (command.isWithoutComment()) {
			Article onlyArticle = articleReadPort.readArticle(command.getBoardId(), command.getArticleId(),
				command.isShowAll());
			return new ArticleContentWithComment(onlyArticle, null);
		}

		ArticleContentWithComment foundContent = articleReadPort.readArticleWithComment(command);
		//TODO 글 작성자에게만 보여지는 값 추가시 사용 Long articleOwner = articleContentWithComment.getArticle().getCreatedBy();

		UserProvider requestUser = command.getUserProvider();

		//TODO 관리자는 allComments 가 필요
		List<Comment> allComments = foundContent.getComments();
		List<Comment> filteredComments = Comment.getVisibleComments(requestUser.getRequestUserId(), allComments);

		return new ArticleContentWithComment(foundContent.getArticle(), filteredComments);
	}

	//Command
	@Override
	public Long writeArticle(ArticleWriteCommand articleWriteCommand) {
		//TODO 한 사용자는 같은 게시판에 00초 내로 글 추가가 제한됨. 프론트에서 먼저 체크 등.
		// articleReadPort.getXXXByAuthorId()
		Board board = boardReadPort.findById(articleWriteCommand.getBoardId());
		board.checkPermission(articleWriteCommand);
		Article savedArticle = articleWritePort.writeArticle(articleWriteCommand);
		//TODO articleWriteCommand Hashtag 저장

		return savedArticle.getId();
	}

	@Override
	public Long deleteArticle(ArticleRemoveCommand command) {
		// TODO Admin 등 처리?
		boolean isShowAll = false;

		Article article = articleReadPort.readArticle(command.getBoardId(), command.getArticleId(), isShowAll);
		if (Objects.isNull(article)) {
			throw new IllegalArgumentException("해당하는 게시글이 없습니다.");
		}
		commentDeletePort.deleteComments(article.getId());

		return articleDeletePort.removeArticle(command);
	}
}
