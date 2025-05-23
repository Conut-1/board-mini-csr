package org.kosa.board.member;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/list")
	public String list(
			Model model,
//			HttpServletResponse response,
//			HttpSession session,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size
			// , String searchValue
			) throws IOException {
		// TODO: 인자를 하나로 합치기?
//		boolean isAdmin = (Boolean)session.getAttribute("isAdmin");
//		if (!isAdmin) {
//			response.setHeader("Content-Type", "text/html;charset=UTF-8");
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//		}
		// size = size == null ? 10 : size;
		model.addAttribute("paging", memberService.list(page, size
			// , searchValue
		));
		return "member/list";
	}
}
