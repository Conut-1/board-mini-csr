package org.kosa.board.member;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.kosa.board.auth.CustomUserDetails;
import org.kosa.board.member.dto.MemberRegisterDTO;
import org.kosa.board.member.dto.MemberUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public Map<String, Object> list() throws IOException {
		Map<String, Object> map = new HashMap<>();
		map.put("memberList", memberService.list());
		return map;
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
		Member member = memberService.get(id);
		if (member == null) {
			map.put("status", "error");
			return map;
		}
		map.put("member", member);
		return map;
	}
	
	@PostMapping("/register")
	public Map<String, Object> register(@RequestBody @Valid MemberRegisterDTO member, BindingResult bindingResult) {
		Map<String, Object> map = new HashMap<>();
		if (bindingResult.hasErrors()) {
			List<String> messages = bindingResult.getFieldErrors().stream().map((err) -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.toList()); 
			map.put("status", "error");
			map.put("message", messages);
			return map;
		}
		memberService.create(member);
		map.put("status", "ok");
		return map;
	}

	@PostMapping("/update/{id}")
	public Map<String, Object> update(@PathVariable("id") String id, @RequestBody @Valid MemberUpdateDTO member, BindingResult bindingResult) {
		// TODO: id에 대해서 검증 가능? 8 ~ 20자
		Map<String, Object> map = new HashMap<String, Object>();
		if (bindingResult.hasErrors()) {
			List<String> messages = bindingResult.getFieldErrors().stream().map((err) -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.toList());
			map.put("status", "error");
			map.put("message", messages);
			return map;
		}

		memberService.update(id, member);
		map.put("status", "ok");
		return map;
	}

	@PostMapping("/unlock/{id}")
	public Map<String, Object> unlock(@PathVariable("id") String id) throws IOException {
		memberService.unlock(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "ok");
		return map;
	}

	@GetMapping("/isExistMemberId")
	public Map<String, Object> isExist(String memberId) {
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
