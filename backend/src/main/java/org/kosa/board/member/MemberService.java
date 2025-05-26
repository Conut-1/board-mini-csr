package org.kosa.board.member;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
	private final MemberRepository memberRepository;

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
}
