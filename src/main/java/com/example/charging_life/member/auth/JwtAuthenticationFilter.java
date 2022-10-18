package com.example.charging_life.member.auth;

import com.example.charging_life.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {
    private TokenService tokenService;

    // tokenService 주입
    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // request 로 들어오는 jwt token 의 유효성을 검증하는 filter 를 filterChain 에 등록
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // 리퀘스트를 서블렛 리퀘스트로 변환하여 헤더에서 해당 이름 값 가져오기
        String accessToken = ((HttpServletRequest) request).getHeader("Authorization");
        if(accessToken!=null&&tokenService.TokenCheck(accessToken)){
            // accessToken 유효할 시 getAuthentication 으로 인증 정보 조회 및 토큰 생성
            Authentication auth = tokenService.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        // 해당 필터는 WebSecurityConfig 수준에서 UsernamePasswordAuthenticationFilter
        // 이전 단계에 추가 되어 invoke 당하게 된다. (서블릿 필터 단계에서 ㅇㅇ)
        filterChain.doFilter(request,response);
    }
}
