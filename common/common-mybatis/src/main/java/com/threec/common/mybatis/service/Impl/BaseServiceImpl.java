package com.threec.common.mybatis.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.threec.common.mybatis.page.PageData;
import com.threec.common.mybatis.vo.DictVO;
import com.threec.common.mybatis.service.BaseService;
import com.threec.common.mybatis.utils.ConvertUtils;
import com.threec.common.mybatis.utils.EntityUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    @Autowired
    protected M baseDao;

    public BaseServiceImpl() {
    }

    protected IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        long curPage = 1L;
        long limit = 10L;
        if (params.get("page") != null) {
            curPage = Long.parseLong((String) params.get("page"));
        }

        if (params.get("limit") != null) {
            limit = Long.parseLong((String) params.get("limit"));
        }

        Page<T> page = new Page(curPage, limit);
        params.put("page", page);
        String orderField = (String) params.get("orderField");
        String order = (String) params.get("order");
        List<OrderItem> orders = new ArrayList<>();

        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if ("asc".equalsIgnoreCase(order)) {
                orders.add(OrderItem.asc(orderField));
            } else {
                orders.add(OrderItem.desc(orderField));
            }
            page.setOrders(orders);
            return page;
        } else if (StringUtils.isEmpty(defaultOrderField)) {
            return page;
        } else {
            if (isAsc) {
                orders.add(OrderItem.asc(defaultOrderField));
            } else {
                orders.add(OrderItem.desc(defaultOrderField));
            }
            page.setOrders(orders);

            return page;
        }
    }

    protected <T> PageData<T> getPageData(List<?> list, long total, Class<T> target, String... fieldNames) {
        List<T> targetList = ConvertUtils.sourceToTarget(list, target, fieldNames);
        return new PageData(targetList, total);
    }

    protected <T> PageData<T> getPageData(IPage page, Class<T> target, String... fieldNames) {
        return this.getPageData(page.getRecords(), page.getTotal(), target, fieldNames);
    }

    protected List<DictVO> getDictVOList(Boolean isI18N, Collection sourceList, String codeFieldName, String nameFiledName) {
        if (sourceList == null) {
            return null;
        } else {
            List<DictVO> targetList = new ArrayList(sourceList.size());

            DictVO dictVO;
            for (Iterator var6 = sourceList.iterator(); var6.hasNext(); targetList.add(dictVO)) {
                Object source = var6.next();
                dictVO = new DictVO();
                dictVO.setId(Long.valueOf(ConvertUtils.getFieldValueByFieldName("id", source)));
                dictVO.setCode(ConvertUtils.getFieldValueByFieldName(codeFieldName, source));
                if (Boolean.TRUE.equals(isI18N)) {
                    dictVO.setName(ConvertUtils.getFieldValueByFieldName(nameFiledName + ConvertUtils.getLanguage(), source));
                } else {
                    dictVO.setName(ConvertUtils.getFieldValueByFieldName(nameFiledName, source));
                }
            }

            return targetList;
        }
    }

    protected List<DictVO> getDictVOList(Boolean isI18N, Collection sourceList, String codeFieldName, String nameFiledName, String paramFiledName) {
        if (sourceList == null) {
            return null;
        } else {
            List<DictVO> targetList = new ArrayList(sourceList.size());
            Iterator var7 = sourceList.iterator();

            while (var7.hasNext()) {
                Object source = var7.next();
                DictVO dictVO = new DictVO();
                dictVO.setId(Long.valueOf(ConvertUtils.getFieldValueByFieldName("id", source)));
                dictVO.setCode(ConvertUtils.getFieldValueByFieldName(codeFieldName, source));
                if (Boolean.TRUE.equals(isI18N)) {
                    dictVO.setName(ConvertUtils.getFieldValueByFieldName(nameFiledName + ConvertUtils.getLanguage(), source));
                } else {
                    dictVO.setName(ConvertUtils.getFieldValueByFieldName(nameFiledName, source));
                }

                dictVO.setParam(ConvertUtils.getFieldValueByFieldName(paramFiledName, source));
                targetList.add(dictVO);
            }

            return targetList;
        }
    }

    protected Map<String, Object> paramsToLike(Map<String, Object> params, String... likes) {
        String[] var3 = likes;
        int var4 = likes.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String like = var3[var5];
            String val = (String) params.get(like);
            if (StringUtils.isNotEmpty(val)) {
                params.put(like, "%" + val + "%");
            } else {
                params.put(like, null);
            }
        }

        return params;
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseServiceImpl.class, 1);
    }

    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(this.currentModelClass()));
    }

    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(this.currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    public boolean insert(T entity) {
        return SqlHelper.retBool(this.baseDao.insert(entity));
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean insertBatch(Collection<T> entityList) {
        return this.insertBatch(entityList, 100);
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean insertBatch(Collection<T> entityList, int batchSize) {
        SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(this.currentModelClass());
        int i = 0;
        String sqlStatement = this.sqlStatement(SqlMethod.INSERT_ONE);

        try {
            for (Iterator var6 = entityList.iterator(); var6.hasNext(); ++i) {
                T anEntityList = (T) var6.next();
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }

            batchSqlSession.flushStatements();
        } finally {
            this.closeSqlSession(batchSqlSession);
        }

        return true;
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean logicDelete(Long[] ids, Class<T> entity) {
        List<T> entityList = EntityUtils.deletedBy(ids, entity);
        return this.updateBatchById(entityList);
    }

    public boolean logicDelete(Long id, Class<T> entity) {
        T entityList = EntityUtils.deletedBy(id, entity);
        return this.updateById(entityList);
    }

    public boolean deleteById(Serializable id) {
        return SqlHelper.retBool(this.baseDao.deleteById(id));
    }

    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        return SqlHelper.retBool(this.baseDao.deleteBatchIds(idList));
    }

    public boolean updateById(T entity) {
        return SqlHelper.retBool(this.baseDao.updateById(entity));
    }

    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return SqlHelper.retBool(this.baseDao.update(entity, updateWrapper));
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean updateBatchById(Collection<T> entityList) {
        return this.updateBatchById(entityList, 30);
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        } else {
            SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(this.currentModelClass());
            int i = 0;
            String sqlStatement = this.sqlStatement(SqlMethod.UPDATE_BY_ID);

            try {
                for (Iterator var6 = entityList.iterator(); var6.hasNext(); ++i) {
                    T anEntityList = (T) var6.next();
                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap();
                    param.put("et", anEntityList);
                    batchSqlSession.update(sqlStatement, param);
                    if (i >= 1 && i % batchSize == 0) {
                        batchSqlSession.flushStatements();
                    }
                }
                batchSqlSession.flushStatements();
                return true;
            } finally {
                this.closeSqlSession(batchSqlSession);
            }
        }
    }

    public T selectById(Serializable id) {
        return this.baseDao.selectById(id);
    }

}
