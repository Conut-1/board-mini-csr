package org.kosa.board.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/list")
	public Map<String, Object> list() throws IOException {
		Map<String, Object> map = new HashMap<>();
		map.put("memberList", memberService.list());
		return map;
	}

	@GetMapping("/detail/{id}")
	public Map<String, Object> detail(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<>();
		Member member = memberService.detail(id);
		if (member == null) {
			map.put("status", "error");
			return map;
		}
		map.put("member", member);
		return map;
	}
}
