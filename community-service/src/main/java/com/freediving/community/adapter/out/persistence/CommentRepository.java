package com.freediving.community.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freediving.community.application.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
