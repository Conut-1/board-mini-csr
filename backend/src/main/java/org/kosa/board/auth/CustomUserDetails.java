package org.kosa.board.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kosa.board.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    private final String id;
    private final String password;
    private final List<GrantedAuthority> authorities = new ArrayList<>();

    public CustomUserDetails(Member member) {
        this.id = member.getId();
        this.password = member.getPassword();
        if (member.isAdminAuth()) {
            this.authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        }
        this.authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    public String getId() {
        return this.id;
    }

    public List<String> getRoles() {
        return this.authorities.stream().map(GrantedAuthority::getAuthority).toList();
    }
}
