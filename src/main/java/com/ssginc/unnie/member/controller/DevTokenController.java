package com.ssginc.unnie.member.controller;

import com.ssginc.unnie.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
//@Profile("local") // local 환경에서만 활성화
public class DevTokenController {

    private final JwtUtil jwtUtil;

    /**
     * 개발용 JWT 토큰 발급
     * DB, 비밀번호, 로그인 전부 무시
     */
    @PostMapping("/token")
    public Map<String, String> issueDevToken(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "ROLE_USER") String role,
            @RequestParam(defaultValue = "개발자") String nickname
    ) {
        String accessToken = jwtUtil.generateToken(memberId, role, nickname);
        log.info("개발용 토큰 발급 성공");

        return Map.of(
                "accessToken", accessToken,
                "tokenType", "Bearer"
        );
    }
}
