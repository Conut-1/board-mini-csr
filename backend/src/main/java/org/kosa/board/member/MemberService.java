package org.kosa.board.member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.kosa.board.common.enums.Gender;
import org.kosa.board.member.dto.MemberRegisterDTO;
import org.kosa.board.member.dto.MemberUpdateDTO;
import org.kosa.board.page.PageResponseVO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
	private final MemberRepository memberRepository;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public PageResponseVO<Member> list(int pageNo, int size) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(pageNo, size, Sort.by(sorts));
		Page<Member> page = this.memberRepository.findAll(pageable);
		return new PageResponseVO<>(page, 10);
	}

	public Member get(String id) {
		Optional<Member> member = this.memberRepository.findById(id);
		if (member.isPresent()) {
			return member.get();
		}
		return null;
	}

	public void create(MemberRegisterDTO memberRegister) {
		try {
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
		} catch (DataIntegrityViolationException exception) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "아이디가 중복입니다.");
		}
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

    public boolean isExist(String memberId) {
		Member member = this.get(memberId);
		return member != null;
    }

	public void withdraw(String id) {
		Member member = this.get(id);
		member.changeDeleteDate(LocalDateTime.now());
		this.memberRepository.save(member);
	}
}
