package com.threec.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.threec.auth.dao.SysUserDao;
import com.threec.auth.dto.SysUserDTO;
import com.threec.auth.entity.SysUserEntity;
import com.threec.auth.service.SysUserService;
import com.threec.common.mybatis.utils.ConvertUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.threec.common.mybatis.service.Impl.BaseServiceImpl;
import com.threec.common.mybatis.page.PageData;
import com.threec.common.mybatis.constant.Constant;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since  2024-04-25
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Override
    public PageData<SysUserDTO> page(Map<String, Object> params) {
        IPage<SysUserEntity> page = baseDao.selectPage(
                getPage(params, Constant.CREATE_DATE, false),
                getWrapper(params)
        );

        return getPageData(page, SysUserDTO.class);
    }

    @Override
    public List<SysUserDTO> list(Map<String, Object> params) {
        List<SysUserEntity> entityList = baseDao.selectList(getWrapper(params));

        return ConvertUtils.sourceToTarget(entityList, SysUserDTO.class);
    }

    private QueryWrapper<SysUserEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        //wrapper.eq(Constant.DEL_FLAG, DelFlagEnum.NORMAL.value());

        return wrapper;
    }

    @Override
    public SysUserDTO get(String id) {
        SysUserEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, SysUserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserDTO dto) {
        SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);
        entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
        insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserDTO dto) {
        SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String[] ids) {
        //逻辑删除
        //logicDelete(ids, SysUserEntity.class);

        //物理删除
        baseDao.deleteBatchIds(Arrays.asList(ids));
    }

}