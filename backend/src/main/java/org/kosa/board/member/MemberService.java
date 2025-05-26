package org.kosa.board.member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.kosa.board.common.enums.Gender;
import org.kosa.board.member.dto.MemberRegisterDTO;
import org.kosa.board.member.dto.MemberUpdateDTO;
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

	public Member get(String id) {
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

	public void update(String id, MemberUpdateDTO memberUpdate) {
		// TODO: 스프링 시큐리티에서 로그인된 사용자 가져오기
		// TODO: OSIV를 꺼서 메소드에 @Transactional을 적용하기
		Member member = this.get(id);
		member.changeName(memberUpdate.getName());
		member.changeEmail(memberUpdate.getEmail());
		member.changeBirthDate(LocalDate.parse(memberUpdate.getBirthDate(), formatter));
		member.changeGender(Gender.valueOf(memberUpdate.getGender()));
		member.changePhoneNumber(memberUpdate.getPhoneNumber());
		member.changePostCode(memberUpdate.getPostCode());
		member.changeAddress(memberUpdate.getAddress());
		member.changeDetailAddress(memberUpdate.getDetailAddress());
		this.memberRepository.save(member);
	}

    public void unlock(String id) {
		Member member = this.get(id);
		member.changeLocked(false);
		this.memberRepository.save(member);
    }
}
