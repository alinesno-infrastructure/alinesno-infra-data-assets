package com.alinesno.infra.data.fastapi.service;

import java.util.Map;

/**
 * 数据转换服务
 */
public interface IDatabaseTranService {

    /**
     * sql转换成结果返回
     * @param dbName 数据源名称
     * @param sql 操作的sql
     * @param paramMap sql参数
     * @return
     */
    public Object transform(String dbName , String sql , Map<String , Object> paramMap) ;

}
