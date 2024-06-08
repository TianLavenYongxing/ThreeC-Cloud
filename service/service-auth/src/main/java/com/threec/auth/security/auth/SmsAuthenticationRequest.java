package com.threec.auth.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class SmsAuthenticationRequest.
 * <p>
 * 短信认证结果
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 8/6/24
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsAuthenticationRequest {
    private String code;
    private String phone;
}
