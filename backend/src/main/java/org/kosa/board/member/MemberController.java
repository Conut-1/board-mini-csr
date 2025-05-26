package org.kosa.board.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.kosa.board.member.dto.MemberRegisterDTO;
import org.kosa.board.member.dto.MemberUpdateDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
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

	@GetMapping("/me")
	public Map<String, Object> me(@AuthenticationPrincipal UserDetails user) {
		Map<String, Object> map = new HashMap<>();
		if (user == null) {
			map.put("id", "");
			return map;
		}
		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		map.put("id", user.getUsername());
		map.put("roles", roles);
		return map;
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
}
