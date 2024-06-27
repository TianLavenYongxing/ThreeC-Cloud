package com.threec.auth.service;

import com.threec.auth.dto.SysUserDTO;
import com.threec.auth.entity.SysUserEntity;
import com.threec.common.mybatis.page.PageData;
import com.threec.common.mybatis.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 系统用户表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since 2024-04-25
 */
public interface SysUserService extends BaseService<SysUserEntity> {

    PageData<SysUserDTO> page(Map<String, Object> params);

    List<SysUserDTO> list(Map<String, Object> params);

    SysUserDTO get(String id);

    void save(SysUserDTO dto);

    void update(SysUserDTO dto);

    void delete(String[] ids);
}