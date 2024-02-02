package com.freediving.communityservice.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.adapter.out.persistence.article.ArticlePersistenceMapper;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.ArticleWritePort;
import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService implements ArticleUseCase {

	private final ArticleWritePort articleWritePort;
	private final ArticleReadPort articleReadPort;

	//Query
	@Override
	public ArticleContentWithComment getArticle(Long requestUserId, ArticleReadCommand articleReadCommand) {
		ArticleContentWithComment foundContent = articleReadPort.readArticle(articleReadCommand);
		//TODO 글 작성자에게만 보여지는 값 추가시 사용 Long articleOwner = articleContentWithComment.getArticle().getCreatedBy();

		List<Comment> allComments = foundContent.getComments();
		List<Comment> filteredComments = allComments.stream()
			.map(c -> {
				if(c.isVisible() || c.getCreatedBy().equals(requestUserId)) {
					return c;
				} else {
					return Comment.builder()
						.commentId(c.getCommentId())
						.articleId(c.getArticleId())
						.parentId(c.getParentId())
						.content("")
						.visible(c.isVisible())
						.createdAt(c.getCreatedAt())
						.createdBy(0L)
						.modifiedAt(c.getModifiedAt())
						.modifiedBy(0L)
						.build();
				}
			}).collect(Collectors.toList());

		return new ArticleContentWithComment(foundContent.getArticle(), filteredComments);
	}

	@Override
	public ArticleContentWithComment getArticleWithComment(Long boardId, Long articleId) {
		return null;
	}

	//Command
	@Override
	public Long writeArticle(ArticleWriteCommand articleWriteCommand) {
		//TODO 한 사용자는 같은 게시판에 00초 내로 글 추가가 제한됨. 프론트에서 먼저 체크 등.
		// articleReadPort.getXXXByAuthorId()

		Article savedArticle = articleWritePort.writeArticle(articleWriteCommand);
		//TODO articleWriteCommand Hashtag 저장

		return savedArticle.getId();
	}
}
