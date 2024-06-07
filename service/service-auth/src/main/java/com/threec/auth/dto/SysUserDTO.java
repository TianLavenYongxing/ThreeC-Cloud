package com.threec.auth.dto;

import com.threec.common.mybatis.dto.BashDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 系统用户表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since  2024-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统用户表")
public class SysUserDTO extends BashDTO implements UserDetails {

	@NotBlank
	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@NotBlank
	@ApiModelProperty(value = "密码")
	private String password;

	@Email
	@ApiModelProperty(value = "邮箱")
	private String email;

	@NotBlank
	@ApiModelProperty(value = "手机号")
	private String phoneNumber;

	@NotNull
	@ApiModelProperty(value = "0男,1女,2跨性别,3未知")
	private Integer sex;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "启用（0未启用，1启用）")
	private Boolean enabled;

	@ApiModelProperty(value = "帐户未过期（0过期，1未过期）")
	private Boolean accountNonExpired;

	@ApiModelProperty(value = "凭证未过期(0过期，1未过期)")
	private Boolean credentialsNonExpired;

	@ApiModelProperty(value = "帐户未锁定(0锁定，1未锁定)")
	private Boolean accountNonLocked;

	@ApiModelProperty(value = "用户角色")
	private List<String> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}