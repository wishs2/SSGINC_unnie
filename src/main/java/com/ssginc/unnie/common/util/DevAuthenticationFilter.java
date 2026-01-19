package com.ssginc.unnie.common.util;

import com.ssginc.unnie.common.config.MemberPrincipal;
import com.ssginc.unnie.member.vo.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 개발 환경(local)에서만 사용하는 강제 인증 필터
 * - UI 기능 테스트를 위해 로그인/JWT 과정을 생략
 * - 운영 환경에서는 절대 활성화되지 않음
 */

@Component
@Profile("local")
public class DevAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            Member member = new Member();
            member.setMemberId(2);
            member.setMemberRole("ROLE_USER");
            member.setMemberNickname("개발자");

            MemberPrincipal principal = new MemberPrincipal(member);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
