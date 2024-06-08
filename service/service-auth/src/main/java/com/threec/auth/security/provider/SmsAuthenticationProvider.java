package com.threec.auth.security.provider;

import com.threec.auth.security.token.SmsAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Class SmsAuthenticationProvider.
 * <p>
 * 一个从userDetailsService检索用户详细信息的AuthentiationDriver实现。
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 8/6/24
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String code = authenticationToken.getCode();
        // todo redis之类通过手机号查询验证码后 比较
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        SmsAuthenticationToken smsAuthenticationToken = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        smsAuthenticationToken.setDetails(smsAuthenticationToken.getDetails());
        return smsAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
