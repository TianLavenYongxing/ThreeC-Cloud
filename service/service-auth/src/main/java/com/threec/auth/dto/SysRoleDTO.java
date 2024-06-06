package com.threec.auth.dto;

import com.threec.common.mybatis.dto.BashDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 系统角色表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since 2024-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统角色表")
public class SysRoleDTO extends BashDTO {
    @ApiModelProperty(value = "角色名")
    private String roleName;
}