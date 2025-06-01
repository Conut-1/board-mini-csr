package org.kosa.board.member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.kosa.board.common.enums.Gender;
import org.kosa.board.member.dto.MemberRegisterDTO;
import org.kosa.board.member.dto.MemberUpdateDTO;
import org.kosa.board.member.summary.MemberDetailSummary;
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

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public PageResponseVO<Member> list(int pageNo, int size, String searchValue) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(pageNo, size, Sort.by(sorts));
		Page<Member> page = searchValue == null
			? this.memberRepository.findAll(pageable)
			: this.memberRepository.findByIdContaining(searchValue, pageable);
		return new PageResponseVO<>(page, 10);
	}

	public Member get(String id) {
		return this.memberRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public MemberDetailSummary detail(String id) {
		return this.memberRepository.findSummaryById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
		try {
			this.get(memberId);
			return true;
		} catch (ResponseStatusException e) {
			return false;
		}
    }

	public void withdraw(String id) {
		Member member = this.get(id);
		member.changeDeleteDate(LocalDateTime.now());
		this.memberRepository.save(member);
	}

	public void handleLoginSuccess(String id) {
		Member member = this.get(id);
		member.changeLoginFailure(0);
		this.memberRepository.save(member);
	}

	public void handleLoginFailure(String id) {
		try {
			Member member = this.get(id);
			int loginFailure = member.getLoginFailure();
			member.changeLoginFailure(loginFailure + 1);
			if (loginFailure == 4) {
				member.changeLocked(true);
			}
			this.memberRepository.save(member);
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀립니다");
		}
	}
}
