package com.sju.roomreservationbackend.common.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final CORSConfig corsConfig = new CORSConfig();
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;
    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 토큰 방식 로그인을 사용하기 위해 CSRF 사용안함
        http.addFilter(corsConfig.corsFilter())
                .csrf().disable();

        // JWT 예외 처리
        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

        // 세션 방식 로그인 차단
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable()
                .httpBasic().disable();

        // 접근 권한 관리
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, JWTFilterRules.getExcludesByHttpMethod("GET")).permitAll()
                .antMatchers(HttpMethod.POST, JWTFilterRules.getExcludesByHttpMethod("POST")).permitAll()
                .antMatchers(HttpMethod.PUT, JWTFilterRules.getExcludesByHttpMethod("PUT")).permitAll()
                .antMatchers(HttpMethod.DELETE, JWTFilterRules.getExcludesByHttpMethod("DELETE")).permitAll()
                .anyRequest().authenticated();

        // JWT 적용
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

