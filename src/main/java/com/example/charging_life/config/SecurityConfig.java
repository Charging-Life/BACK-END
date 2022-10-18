package com.example.charging_life.config;

import com.example.charging_life.member.auth.CustomAccessDeniedHandler;
import com.example.charging_life.member.auth.CustomAuthenticationEntryPoint;
import com.example.charging_life.member.auth.JwtAuthenticationFilter;
import com.example.charging_life.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenService tokenService;

    @Bean
    @Override
    // 직접 Bean 으로 등록된 이 SecurityConfig 내부의 auth Manager Bean 을 가져오는 용도.
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // 기존에 사용하던 BCryptPasswordEncoder 단방향 인코딩을 지원했는데
        // 다양한 알고리즘에 대한 복호화를 단방향으로 지원해주는 것으로 대충 생각하자.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                // rest api 이므로 기본설정 안함. 기본설정은 비인증 시 로그인 폼 화면으로 리다이렉트 된다.
                .disable()
                .csrf()
                .disable()
                // 세션 기반의 인증을 사용하지 않기 때문에 csrf 공격은 무효하다.
                .cors()
                .and()
                .formLogin()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 그래서 세션이 꺼지는 거군

                .and()
                .authorizeRequests()
                // 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/member/new", "/member/login","/member/reissue","/exception/**").permitAll()
                // 로그인이나 회원가입은 다 허용
                .antMatchers(HttpMethod.GET,  "/swagger-ui.html/**","/helloworld/**").permitAll()
                // 위 경로로 시작하는 get 요청 리소스는 누구나 접근 가능
//                .anyRequest().hasAnyAuthority(Auth.USER.getRole(),Auth.ADMIN.getRole())
                // 와우 hasRole 은 string 으로 ROLE_ 이 앞에 알아서 붙음;;
                // 그래서 hasAuthority 로 바꿈..
                // 그 외 나머지 요청은 모두 인증된 회원만 접근 가능

                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                // 접근 권한 없을 때를 처리할 핸들러

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                // apiException 은 Spring DispatcherServlet 까지 도달하지 않기에 이걸로 라우팅 옮기고 호출.
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenService),
                        UsernamePasswordAuthenticationFilter.class);
        // spring security 에서 관리하는 인증 필터 단계에 jwt 토큰 필터 삽입
    }
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
