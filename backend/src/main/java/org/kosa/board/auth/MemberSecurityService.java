package org.kosa.board.auth;

import org.kosa.board.member.Member;
import org.kosa.board.member.MemberService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            Member member = this.memberService.get(id);
            return new CustomUserDetails(member);
        } catch (ResponseStatusException e) {
            throw new UsernameNotFoundException("아이디나 비밀번호가 틀립니다");
        }
    }
}
