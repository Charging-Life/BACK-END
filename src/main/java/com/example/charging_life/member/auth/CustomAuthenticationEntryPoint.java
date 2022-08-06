package com.example.charging_life.member.auth;

import com.example.charging_life.member.entity.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
// 중요한 것, ExceptionAdvice 로 지정한 ApiException 은 해당 레벨에서 호출해도 의미 없다.
// ApiException 은 ControllerAdvice, 즉, Spring 이 처리할 수 있는 레벨까지 가야 하는데
// SpringSecurity 는 spring dispatcher servlet 이전에 처리되기 때문에...
// 해당 상황의 exception 이 Spring 의 DispatcherServlet 까지 도달하지 않게 되는 것이다.
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        //throw new ApiException(ExceptionEnum.TokenMalformed);
        log.error(authException.getMessage(), Auth.USER.getRole());
        // 고로,, ApiPoint 를 지정하여 사용하는 걸로!
        response.sendRedirect("/exception/entrypoint");
    }
}
