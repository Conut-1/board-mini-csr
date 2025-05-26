package org.kosa.board.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.kosa.board.common.enums.Gender;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberNo;

	@Column(length = 20, unique = true)
	private String id;

	@Column(length = 20)
	private String password;

	@Column(length = 20)
	private String name;

	@Column(length = 100)
	private String email;

	private LocalDate birthDate;

	@Enumerated(EnumType.STRING)
	@Column(length = 2, nullable = false)
	private Gender gender;
	// TODO: 나중에 @Convert를 사용해서 length를 1로 줄이는 게 가능한지 해보기. 지금은 JPA가 Char(1)로 컬럼을 설정해서 casting에서 문제가 발생하고 있음.

	@Column(length = 13)
	private String 	phoneNumber;

	@Column(length = 5)
	private String 	postCode;

	@Column(length = 300)
	private String 	address;

	@Column(length = 300)
	private String 	detailAddress;

	@CreatedDate
	private LocalDateTime createDate;

	private LocalDateTime deleteDate;

	private boolean locked;

	private int 	loginFailure;

	private boolean adminAuth;

	public void changeName(String name) {
		this.name = name;
	}

	public void changeEmail(String email) {
		this.email = email;
	}

	public void changeBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void changeGender(Gender gender) {
		this.gender = gender;
	}

	public void changePhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void changePostCode(String postCode) {
		this.postCode = postCode;
	}

	public void changeAddress(String address) {
		this.address = address;
	}

	public void changeDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
}
