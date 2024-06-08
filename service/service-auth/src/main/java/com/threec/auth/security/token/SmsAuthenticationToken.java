package com.threec.auth.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Class SmsAuthenticationToken.
 * <p>
 * 手机验证码Token
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 8/6/24
 */
public class SmsAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private String code;

    public SmsAuthenticationToken(Object principal, String code) {
        super(null);
        this.principal = principal;
        this.code = code;
        setAuthenticated(false);
    }

    public SmsAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        super.setAuthenticated(false);
    }

}


