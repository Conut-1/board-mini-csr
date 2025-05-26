package org.kosa.board.member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.kosa.board.common.enums.Gender;
import org.kosa.board.member.dto.MemberRegisterDTO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
	private final MemberRepository memberRepository;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public List<Member> list() {
		return this.memberRepository.findAll();
	}

	public Member detail(String id) {
		Optional<Member> member = this.memberRepository.findById(id);
		if (member.isPresent()) {
			return member.get();
		}
		return null;
	}

	public void create(MemberRegisterDTO memberRegister) {
		Member member = Member.builder()
				.id(memberRegister.getId())
				.password(memberRegister.getPassword())
				.name(memberRegister.getName())
				.email(memberRegister.getEmail())
				.birthDate(LocalDate.parse(memberRegister.getBirthDate(), formatter))
				.gender(Gender.valueOf(memberRegister.getGender()))
				.phoneNumber(memberRegister.getPhoneNumber())
				.postCode(memberRegister.getPostCode())
				.address(memberRegister.getAddress())
				.detailAddress(memberRegister.getDetailAddress())
				.build();
		this.memberRepository.save(member);
	}
}
