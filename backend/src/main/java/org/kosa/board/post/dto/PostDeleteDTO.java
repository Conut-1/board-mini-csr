package org.kosa.board.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDeleteDTO {
	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	private String password;
}
