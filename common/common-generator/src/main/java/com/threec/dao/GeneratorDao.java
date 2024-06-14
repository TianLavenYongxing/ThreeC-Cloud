package com.threec.dao;

import java.util.List;
import java.util.Map;

/**
 * 数据库接口
 *
 * @author Laven
 * @since 2022-05-06
 */
public interface GeneratorDao {
    List<Map<String, Object>> queryList(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
