package com.freediving.community.application.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "board", indexes = {
	@Index(columnList = "title")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String boardName;

	private String description;

	private Integer displayOrder;

	public void setId(Long id) {
		this.id = id;
	}

}
