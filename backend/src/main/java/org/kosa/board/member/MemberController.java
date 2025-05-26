package org.kosa.board.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
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
}
