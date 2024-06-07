package com.threec.auth.dao;

import com.threec.auth.entity.SysUserEntity;
import com.threec.auth.security.dto.AuthUrlMethodDTO;
import com.threec.common.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since  2024-06-01
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {

    SysUserEntity findByUsername(String username);

    List<AuthUrlMethodDTO> authByUsername(@Param("username")String username,@Param("method") String method);
	
}