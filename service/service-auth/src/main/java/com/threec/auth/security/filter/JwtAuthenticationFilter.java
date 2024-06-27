package com.threec.auth.security.filter;

import com.threec.auth.security.JwtService;
import com.threec.auth.security.constant.AuthConstant;
import com.threec.auth.security.dto.JWTValidationResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtService jwtService;
    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains(AuthConstant.API_AUTH)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader(AuthConstant.AUTHORIZATION);
        final String jwt;
        if (authHeader == null || !authHeader.startsWith(AuthConstant.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(AuthConstant.BEARER.length());
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            JWTValidationResult jwtResult = jwtService.validateToken(jwt);
            if (!jwtResult.isValid()) {
                throw new JwtException(jwtResult.getErrorMessage());
            }
            Claims claims = jwtResult.getClaims();
            String username = claims.getSubject();
            if (username != null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
