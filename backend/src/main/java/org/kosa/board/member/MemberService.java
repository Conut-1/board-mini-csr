package org.kosa.board.member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.kosa.board.common.enums.Gender;
import org.kosa.board.member.dto.MemberRegisterDTO;
import org.kosa.board.page.PageResponseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public PageResponseVO<Member> list(int pageNo, int size) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(pageNo, size, Sort.by(sorts));
		Page<Member> page = this.memberRepository.findAll(pageable);
		return new PageResponseVO<>(page, 10);
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
