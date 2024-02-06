package com.freediving.communityservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.ArticleWritePort;
import com.freediving.communityservice.application.port.out.BoardReadPort;
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

	@Override
	public Article getArticle(ArticleReadCommand articleReadCommand) {
		return articleReadPort.readArticle(articleReadCommand);
	}

	//Query
	@Override
	public ArticleContentWithComment getArticleWithComment(ArticleReadCommand articleReadCommand) {
		ArticleContentWithComment foundContent = articleReadPort.readArticleWithComment(articleReadCommand);
		//TODO 글 작성자에게만 보여지는 값 추가시 사용 Long articleOwner = articleContentWithComment.getArticle().getCreatedBy();

		UserProvider requestUser = articleReadCommand.getUserProvider();

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
}
