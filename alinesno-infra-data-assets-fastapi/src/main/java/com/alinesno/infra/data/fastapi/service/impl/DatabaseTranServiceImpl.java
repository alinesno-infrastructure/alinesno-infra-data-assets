package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.data.fastapi.service.IDatabaseTranService;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DatabaseTranServiceImpl  implements IDatabaseTranService {

    private Map<String, DataSource> dataSourceMap;

    public DatabaseTranServiceImpl() {
        dataSourceMap = new HashMap<>();
    }

    public Object transform(String dbName, String sql, Map<String, Object> paramMap) {
        DataSource dataSource = dataSourceMap.get(dbName);
        if (dataSource == null) {
            throw new IllegalArgumentException("Invalid dbName: " + dbName);
        }

        SqlSessionFactory sqlSessionFactory = createSqlSessionFactory(dataSource);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return executeSql(sqlSession, sql, paramMap);
        }
    }

    @SneakyThrows
    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    private Object executeSql(SqlSession sqlSession, String sql, Map<String, Object> paramMap) {
        if (isSelectQuery(sql)) {
            return executeSelectQuery(sqlSession, sql, paramMap);
        } else {
            return executeUpdateQuery(sqlSession, sql, paramMap);
        }
    }

    private boolean isSelectQuery(String sql) {
        String trimmedSql = sql.trim().toLowerCase();
        return trimmedSql.startsWith("select") || trimmedSql.startsWith("with");
    }

    private List<Map<String, Object>> executePagingQuery(SqlSession sqlSession, String sql, Map<String, Object> paramMap, int pageNum, int pageSize) {
        int startRow = (pageNum - 1) * pageSize;
        paramMap.put("startRow", startRow);
        paramMap.put("pageSize", pageSize);
        return sqlSession.selectList(sql, paramMap);
    }

    private Object executeSelectQuery(SqlSession sqlSession, String sql, Map<String, Object> paramMap) {
        if (isPagingQuery(sql)) {
            int pageNum = (int) paramMap.getOrDefault("pageNum", 1);
            int pageSize = (int) paramMap.getOrDefault("pageSize", 10);
            return executePagingQuery(sqlSession, sql, paramMap, pageNum, pageSize);
        } else {
            return executeSimpleQuery(sqlSession, sql, paramMap);
        }
    }

    private boolean isPagingQuery(String sql) {
        String trimmedSql = sql.trim().toLowerCase();
        return trimmedSql.contains("limit") || trimmedSql.contains("offset");
    }

    private Object executeSimpleQuery(SqlSession sqlSession, String sql, Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = sqlSession.selectList(sql, paramMap);
        if (resultList.isEmpty()) {
            return null;
        }
        if (resultList.size() == 1) {
            return resultList.get(0);
        }
        return resultList;
    }

    private int executeUpdateQuery(SqlSession sqlSession, String sql, Map<String, Object> paramMap) {
        return sqlSession.update(sql, paramMap);
    }

    public void addDataSource(String dbName, DataSource dataSource) {
        dataSourceMap.put(dbName, dataSource);
    }

    public void removeDataSource(String dbName) {
        dataSourceMap.remove(dbName);
    }
}
