package com.threec.auth;

import com.threec.auth.security.JwtService;
import com.threec.auth.security.auth.AuthenticationUser;
import com.threec.auth.security.constant.AuthConstant;
import com.threec.auth.security.dto.JWTValidationResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class AuthenticationServiceTest {

    @Resource
    JwtService jwtService;

    @Test
    public void test1() {
        AuthenticationUser authenticationUser = new AuthenticationUser();
        authenticationUser.setUsername("username");
        String jwt = jwtService.generateToken(authenticationUser);
        System.out.println(jwt);
        JWTValidationResult jwtResult = jwtService.validateToken(jwt);
        System.out.println(jwtResult);
    }

    @Test
    public void test2() {
        System.out.println(AuthConstant.BEARER.length());
    }
}
