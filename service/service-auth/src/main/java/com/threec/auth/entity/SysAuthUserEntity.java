package com.threec.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.threec.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @author Laven tianlaven@gmail.com
 * @since  2024-06-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@TableName("sys_auth_user")
public class SysAuthUserEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Integer userId;
    /**
     * 
     */
	private String username;
    /**
     * 
     */
	private String password;
    /**
     * 
     */
	private Boolean enabled;
    /**
     * 
     */
	private Boolean accountNonExpired;
    /**
     * 
     */
	private Boolean credentialsNonExpired;
    /**
     * 
     */
	private Boolean accountNonLocked;
    /**
     * 
     */
	private List<String> roles;
    /**
     * 
     */
	private String ip;
    /**
     * 
     */
	private Date loginTime;
}