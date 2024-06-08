package com.threec.auth.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threec.auth.dao.SysUserDao;
import com.threec.auth.dto.SysUserDTO;
import com.threec.auth.entity.SysUserEntity;
import com.threec.auth.security.JwtService;
import com.threec.auth.security.constant.AuthConstant;
import com.threec.auth.security.token.SmsAuthenticationToken;
import com.threec.common.mybatis.utils.ConvertUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SysUserDao sysUserDao;

    public AuthenticationResponse register(AuthenticationUser authUser) {
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        SysUserEntity userEntity = ConvertUtils.sourceToTarget(authUser, SysUserEntity.class);
        sysUserDao.insert(userEntity);
        String jwtToken = jwtService.generateToken(authUser);
        String refreshToken = jwtService.generateRefreshToken(authUser);
        saveUserToken(authUser, jwtToken);
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SysUserEntity user = sysUserDao.findByUsername(request.getUsername());
        return getAuthenticationResponse(user, ConvertUtils.sourceToTarget(request, SysUserDTO.class));
    }

    public AuthenticationResponse smsAuthenticate(SmsAuthenticationRequest request) {
        authenticationManager.authenticate(new SmsAuthenticationToken(request.getPhone(), request.getCode()));
        SysUserEntity user = sysUserDao.findByPhone(request.getPhone());
        return getAuthenticationResponse(user, ConvertUtils.sourceToTarget(request, SysUserDTO.class));
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith(AuthConstant.BEARER)) {
            return;
        }
        refreshToken = authHeader.substring(AuthConstant.BEARER.length());
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            SysUserEntity userEntity = sysUserDao.findByUsername(username);
            AuthenticationUser authUser = ConvertUtils.sourceToTarget(userEntity, AuthenticationUser.class);
            if (jwtService.isTokenValid(refreshToken, authUser)) {
                String accessToken = jwtService.generateToken(authUser);
                revokeAllUserTokens(authUser);
                saveUserToken(authUser, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private AuthenticationResponse getAuthenticationResponse(SysUserEntity user, SysUserDTO request) {
        AuthenticationUser authUser = ConvertUtils.sourceToTarget(user, AuthenticationUser.class);
        SysUserDTO userDTO = request;
        String jwtToken = jwtService.generateToken(userDTO);
        String refreshToken = jwtService.generateRefreshToken(userDTO);
        revokeAllUserTokens(authUser);
        saveUserToken(authUser, jwtToken);
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    private void revokeAllUserTokens(AuthenticationUser user) {
        // todo redis 查询根据用户名查询令牌 后验证 为空直接返回 不为空设置过期后保存
    }

    private void saveUserToken(AuthenticationUser user, String jwtToken) {
        // todo redis写入 根据userId 为key写入
    }
}
