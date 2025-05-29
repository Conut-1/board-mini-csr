package org.kosa.board.auth;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.kosa.board.auth.dto.LoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Map<String, Object> login(HttpSession session, @RequestBody @Valid LoginDTO login) {
        Map<String, Object> map = new HashMap<>();
        this.authService.login(session, login);
        map.put("message", "login success");
        return map;
    }
}
