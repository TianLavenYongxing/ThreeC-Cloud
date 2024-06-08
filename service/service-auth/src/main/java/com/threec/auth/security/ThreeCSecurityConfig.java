package com.threec.auth.security;

import com.threec.auth.security.constant.AuthConstant;
import com.threec.auth.security.filter.JwtAuthenticationFilter;
import com.threec.auth.security.handler.ThreeCAuthorizationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ThreeCSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final ThreeCAuthorizationHandler threeCAuthorizationHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);         // 关闭csrf攻击防御
        http.cors(Customizer.withDefaults());               // 跨域问题
        http.authorizeHttpRequests(authz -> authz.requestMatchers(AuthConstant.WHITE_LIST_URL).permitAll().anyRequest().access(threeCAuthorizationHandler)  //开启授权保护  anyRequest() 对所有请求开启授权保护  access()自定义授权
        );
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 无状态配置：通过
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(e -> {
            e.accessDeniedHandler(accessDeniedHandler);
            e.authenticationEntryPoint(authenticationEntryPoint);
        });
        http.logout(logout -> logout.logoutUrl(AuthConstant.AUTH_LOGOUT).addLogoutHandler(logoutHandler).logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
        return http.build();
    }

}
