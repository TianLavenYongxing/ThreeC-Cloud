package com.threec.auth.security.constant;

/**
 * Class AuthConstant.
 * 认证常量
 *
 * @author laven
 * @version 1.0
 * @since 8/6/24
 */
public class AuthConstant {
    public static final String ALL_API_PERM = "API::ALL";
    public static final String[] WHITE_LIST_URL = {"/api/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html"};

    public static final String API_AUTH = "api/auth";

    public static final String AUTHORIZATION = "Authorization";

    public static final String BEARER =  "Bearer ";
}
