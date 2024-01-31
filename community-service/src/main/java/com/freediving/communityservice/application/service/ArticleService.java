package com.freediving.communityservice.application.service;

import org.springframework.stereotype.Service;

import com.freediving.communityservice.adapter.out.dto.article.ArticleContent;
import com.freediving.communityservice.adapter.out.dto.article.ArticleContentWithComment;
import com.freediving.communityservice.adapter.out.persistence.article.ArticlePersistenceMapper;
import com.freediving.communityservice.application.port.in.ArticleReadCommand;
import com.freediving.communityservice.application.port.in.ArticleUseCase;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;
import com.freediving.communityservice.application.port.out.ArticleReadPort;
import com.freediving.communityservice.application.port.out.ArticleWritePort;
import com.freediving.communityservice.domain.Article;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService implements ArticleUseCase {

	private final ArticleWritePort articleWritePort;
	private final ArticleReadPort articleReadPort;
	private final ArticlePersistenceMapper articleMapper;

	//Query
	@Override
	public ArticleContent getArticle(ArticleReadCommand articleReadCommand) {
		return articleReadPort.readArticle(articleReadCommand);
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
