package org.kosa.board.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDTO {
	@NotBlank(message = "아이디는 필수 항목입니다.")
    private String id;

	@NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;
}
