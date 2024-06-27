package com.threec.common.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.io.Serializable;
import java.util.Collection;

public interface BaseService<T> {

    boolean insert(T entity);

    boolean insertBatch(Collection<T> entityList);

    boolean insertBatch(Collection<T> entityList, int batchSize);

    boolean logicDelete(Long id, Class<T> entity);

    boolean logicDelete(Long[] ids, Class<T> entity);

    boolean deleteById(Serializable id);

    boolean deleteBatchIds(Collection<? extends Serializable> idList);

    boolean updateById(T entity);

    boolean update(T entity, Wrapper<T> updateWrapper);

    boolean updateBatchById(Collection<T> entityList);

    boolean updateBatchById(Collection<T> entityList, int batchSize);

    T selectById(Serializable id);

}
