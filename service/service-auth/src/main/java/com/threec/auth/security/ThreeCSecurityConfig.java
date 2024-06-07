package com.threec.auth.security;

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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ThreeCSecurityConfig {

    private static final String[] WHITE_LIST_URL = {"/api/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final ThreeCAuthorizationHandler threeCAuthorizationHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);         // 关闭csrf攻击防御
        http.cors(Customizer.withDefaults());               // 跨域问题
        http.authorizeHttpRequests(authz -> authz.requestMatchers(WHITE_LIST_URL).permitAll()   // api/下的不用认证
                .anyRequest().access(threeCAuthorizationHandler)               //开启授权保护  anyRequest() 对所有请求开启授权保护  authenticated() 已认证的请求会被自动授权
        );
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 无状态配置：通过
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
