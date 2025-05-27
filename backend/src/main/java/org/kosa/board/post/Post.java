package org.kosa.board.post;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postNo;

    @Column(length = 20)
	private String password;

    @Column(length = 150)
	private String title;

    @Lob
	private String content;

	private int views;

	@CreatedDate
	private LocalDateTime createDate;

	private LocalDateTime deleteDate;

	public void changeTitle(String title) {
		this.title = title;
	}

	public void changeContent(String content) {
		this.content = content;
	}
}
