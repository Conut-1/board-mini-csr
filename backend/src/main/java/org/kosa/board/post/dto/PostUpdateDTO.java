package org.kosa.board.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostUpdateDTO {
	@NotBlank(message = "제목은 필수 항목입니다.")
	private String title;

	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	private String password;

	@NotBlank(message = "내용은 필수 항목입니다.")
	private String content;
}
