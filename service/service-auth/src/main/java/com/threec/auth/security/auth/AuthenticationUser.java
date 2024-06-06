package com.threec.auth.security.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AuthenticationUser implements UserDetails {

    @ApiModelProperty(value = "用户ID")
    private int userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "启用（0未启用，1启用）")
    private Integer enabled;

    @ApiModelProperty(value = "帐户未过期（0过期，1未过期）")
    private Integer accountNonExpired;

    @ApiModelProperty(value = "凭证未过期(0过期，1未过期)")
    private Integer credentialsNonExpired;

    @ApiModelProperty(value = "帐户未锁定(0锁定，1未锁定)")
    private Integer accountNonLocked;

    @ApiModelProperty(value = "用户角色")
    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired != 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked != 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired !=0;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled != 0;
    }
}
