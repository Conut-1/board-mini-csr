package org.kosa.board.auth;

import org.kosa.board.auth.dto.LoginDTO;
import org.kosa.board.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;

    public void login(HttpSession session, LoginDTO login) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getId(), login.getPassword())
            );
            this.memberService.handleLoginSuccess(login.getId());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        } catch (BadCredentialsException e) {
            this.memberService.handleLoginFailure(login.getId());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀립니다");
        } catch (LockedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "아이디가 잠겼습니다");
        }
    }
}
