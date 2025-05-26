package org.kosa.board.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberUpdateDTO {
	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$",
		message = "비밀번호는 영문자, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다."
	)
	private String password;

	@Size(max = 20, message = "이름은 20자 이하로 입력하세요.")
	private String name;

	@Email(message = "올바른 이메일 형식이어야 합니다.")
	private String email;

	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "생년월일은 yyyy-MM-dd 형식이어야 합니다.")
	private String birthDate;

	@Pattern(regexp = "^[MF]$", message = "성별은 M 또는 F만 입력 가능합니다.")
	private String gender;

	@Pattern(regexp = "^((01[016789])|(0\\d{1,2}))-?\\d{3,4}-?\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
	private String phoneNumber;

	@Pattern(regexp = "^\\d{5}$", message = "우편번호 형식이 올바르지 않습니다.")
	private String postCode;

	private String address;

	private String detailAddress;
}
