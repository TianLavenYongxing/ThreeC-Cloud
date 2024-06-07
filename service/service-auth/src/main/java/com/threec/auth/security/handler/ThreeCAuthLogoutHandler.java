package com.threec.auth.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * Class ThreeCAuthLogoutHandler.
 * 登出
 *
 * @author laven
 * @version 1.0
 * @since 8/6/24
 */

@Component
public class ThreeCAuthLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        // todo redis中获取jwt 如果不为空那么设置为超期 后保存 最后 SecurityContextHolder.clearContext();
    }
}
