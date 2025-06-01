package org.kosa.board.member;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.kosa.board.auth.CustomUserDetails;
import org.kosa.board.member.dto.MemberRegisterDTO;
import org.kosa.board.member.dto.MemberUpdateDTO;
import org.kosa.board.member.summary.MemberDetailSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> list(
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size,
		@RequestParam(value = "searchValue", required = false) String searchValue
	) {
		// TODO: 인자를 하나로 합치기?
		Map<String, Object> map = new HashMap<>();
		map.put("paging", memberService.list(page, size, searchValue));
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/me")
	public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal CustomUserDetails user) {
		if (user == null) {
			return ResponseEntity.ok(null);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("id", user.getId());
		map.put("roles", user.getRoles());
		return ResponseEntity.ok(map);
	}

	@GetMapping("/detail/{id}")
	public Map<String, Object> detail(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<>();
		MemberDetailSummary member = memberService.detail(id);
		map.put("member", member);
		return map;
	}

	@PostMapping("/register")
	public Map<String, Object> register(@RequestBody @Valid MemberRegisterDTO member) {
		Map<String, Object> map = new HashMap<>();
		memberService.create(member);
		map.put("status", "ok");
		return map;
	}

	@PostMapping("/update")
	public Map<String, Object> update(@RequestBody @Valid MemberUpdateDTO member, Principal principal) {
		Map<String, Object> map = new HashMap<String, Object>();
		memberService.update(principal.getName(), member);
		map.put("status", "ok");
		return map;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/unlock/{id}")
	public Map<String, Object> unlock(@PathVariable("id") String id) {
		memberService.unlock(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "ok");
		return map;
	}

	@GetMapping("/isExistMemberId")
	public Map<String, Object> isExist(@RequestParam("memberId") String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isExist", memberService.isExist(memberId));
		return map;
	}

	@PostMapping("/withdraw")
	public Map<String, Object> withdraw(HttpSession session, Principal principal) {
		Map<String, Object> map = new HashMap<String, Object>();
		memberService.withdraw(principal.getName());
		session.invalidate();
		map.put("status", "ok");
		return map;
	}
}
