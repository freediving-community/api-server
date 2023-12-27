package com.freediving.adapter.out.persistence;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.freediving.community.adapter.out.persistence.ArticleRepository;
import com.freediving.community.adapter.out.persistence.CommentRepository;
import com.freediving.community.application.domain.Article;
import com.freediving.community.config.JpaConfig;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JPARepositoryTest {

	private final ArticleRepository articleRepository;
	private final CommentRepository commentRepository;

	public JPARepositoryTest(@Autowired ArticleRepository articleRepository,
		@Autowired CommentRepository commentRepository) {

		this.articleRepository = articleRepository;
		this.commentRepository = commentRepository;
	}

	@DisplayName("Select 테스트")
	@Test
	void selectTest() {

		//given
		Article save = articleRepository.save(Article.of("1", "1321", "제목1", "내용1"));
		//when
		List<Article> articleList = articleRepository.findAll();
		//then
		Assertions.assertThat(articleList)
			.isNotNull()
			.hasSize(1);
	}

}
