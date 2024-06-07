package com.threec.auth.security;

import com.alibaba.fastjson2.JSON;
import com.threec.auth.dao.SysUserDao;
import com.threec.auth.entity.SysUserEntity;
import com.threec.auth.security.enums.ResultEnums;
import com.threec.common.mybatis.utils.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final SysUserDao sysUserDao;
    @Value("${application.security.b-crypt-password-encoder.strength}")
    private int strength;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            SysUserEntity user = sysUserDao.findByUsername(username);
            if (ObjectUtils.isEmpty(user)) {
                throw new UsernameNotFoundException(ResultEnums.ERROR_USERNAME_OR_PASSWORD.getMsg());
            }
            return new User(user.getUsername(), user.getPassword(), user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(strength);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            // todo 逻辑 ThreeCLoginFailureHandler一样实现 authException逻辑
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            Result<Object> localizedMessage = new Result<>().error(HttpServletResponse.SC_UNAUTHORIZED, ResultEnums.AUTHENTICATION_FAILED.getMsg());
            outputStream.write(JSON.toJSONString(localizedMessage).getBytes());
            outputStream.flush();
            outputStream.close();
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // todo 逻辑 ThreeCLoginFailureHandler一样实现 accessDeniedException逻辑
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            Result<Object> localizedMessage = new Result<>().error(HttpServletResponse.SC_FORBIDDEN, ResultEnums.ACCESS_DENIED.getMsg());
            outputStream.write(JSON.toJSONString(localizedMessage).getBytes());
            outputStream.flush();
            outputStream.close();
        };
    }


}

