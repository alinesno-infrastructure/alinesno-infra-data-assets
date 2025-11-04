package com.alinesno.infra.data.assets.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.assets.api.TableMetrics;
import com.alinesno.infra.data.assets.entity.ManifestEntity;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;
import com.alinesno.infra.data.assets.mapper.ManifestMapper;
import com.alinesno.infra.data.assets.service.IManifestService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资产收藏Service业务层处理
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@Service
public class ManifestServiceImpl extends IBaseServiceImpl<ManifestEntity, ManifestMapper> implements IManifestService {

    @Autowired
    private JdbcTemplate jdbcTemplate ;

    /**
     * 保存dataList数据到Postgresql数据资产库中
     *
     * @param dataList 需要保存到json数据
     * @param fieldList 数据库的字段类型和列表
     * @param tableName 资产表名称
     * @return 表的指标信息
     */
    @DS("postgresql")
    @Transactional
    @Override
    public TableMetrics saveToDatahouse(List<Map<String, Object>> dataList, List<ManifestFieldEntity> fieldList, String tableName) {
        if (dataList == null || dataList.isEmpty() || fieldList == null || fieldList.isEmpty() || tableName == null || tableName.isEmpty()) {
            log.warn("输入参数无效。无法继续保存数据。");
            return null;
        }

        // 获取原有数据量
        long existingDataCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Long.class);

        // 构建插入语句的基础部分
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (int i = 0; i < fieldList.size(); i++) {
            ManifestFieldEntity field = fieldList.get(i);
            columns.append(field.getFieldName());
            placeholders.append("?");

            if (i < fieldList.size() - 1) {
                columns.append(", ");
                placeholders.append(", ");
            }
        }

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns.toString(), placeholders.toString());

        // 批量插入数据
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @SneakyThrows
            @Override
            public void setValues(PreparedStatement ps, int i) {
                Map<String, Object> dataMap = dataList.get(i);
                for (int j = 0; j < fieldList.size(); j++) {
                    ManifestFieldEntity field = fieldList.get(j);

                    if(dataMap.get(field.getFieldName()) instanceof java.sql.Timestamp) {
                        ps.setTimestamp(j + 1, (java.sql.Timestamp) dataMap.get(field.getFieldName()));
                    }else if(dataMap.get(field.getFieldName()) instanceof java.sql.Date) {
                        ps.setDate(j + 1, (java.sql.Date) dataMap.get(field.getFieldName()));
                    }else {
                        ps.setObject(j + 1, dataMap.get(field.getFieldName()));
                    }

                }
            }

            @Override
            public int getBatchSize() {
                return dataList.size();
            }
        });

        // 获取新增加的数据量
        long newDataCount = dataList.size();

        // 获取当前总的数据量
        long totalDataCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Long.class);

        // 返回表的指标信息
        TableMetrics metrics = new TableMetrics();
        metrics.setExistingDataCount(existingDataCount);
        metrics.setNewDataCount(newDataCount);
        metrics.setTotalDataCount(totalDataCount);

        log.info("数据已成功保存到表: {}", tableName);
        return metrics;
    }

    @DS("postgresql")
    @Transactional
    @Override
    public TableMetrics saveToDatahouse(MultipartFile file,List<ManifestFieldEntity> fieldList, String model) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // 读取整个文件内容
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }

            // 尝试解析JSON数组
            JSONArray jsonArray = null;
            try {
                jsonArray = JSON.parseArray(contentBuilder.toString());
            } catch (JSONException e) {
                log.error("解析JSON数组时发生错误: {}", e.getMessage(), e);
                throw new RuntimeException("解析JSON数组时发生错误", e);
            }

            // 获取原有数据量
            long existingDataCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + model, Long.class);

            // 构建插入语句的基础部分
            StringBuilder columns = new StringBuilder();
            StringBuilder placeholders = new StringBuilder();

            for (int i = 0; i < fieldList.size(); i++) {
                ManifestFieldEntity field = fieldList.get(i);
                columns.append(field.getFieldName());
                placeholders.append("?");

                if (i < fieldList.size() - 1) {
                    columns.append(", ");
                    placeholders.append(", ");
                }
            }

            String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", model, columns.toString(), placeholders.toString());

            // 批量插入数据
            List<JSONObject> batch = new ArrayList<>();
            for (Object obj : jsonArray) {
                if (obj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) obj;
                    try {
                        batch.add(jsonObject);

                        if (batch.size() >= 1000) {
                            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                                @SneakyThrows
                                @Override
                                public void setValues(PreparedStatement ps, int i) {
                                    JSONObject jsonObject = batch.get(i);
                                    for (int j = 0; j < fieldList.size(); j++) {
                                        ManifestFieldEntity field = fieldList.get(j);
                                        ps.setString(j + 1, jsonObject.getString(field.getFieldName()));
                                    }
                                }

                                @Override
                                public int getBatchSize() {
                                    return batch.size();
                                }
                            });
                            log.info("已处理 {} 条数据", batch.size());
                            batch.clear();
                        }
                    } catch (Exception e) {
                        log.error("处理JSON对象时发生错误: {}", e.getMessage(), e);
                    }
                } else {
                    log.warn("跳过非JSON对象: {}", obj);
                }
            }

            // 处理剩余的数据
            if (!batch.isEmpty()) {
                log.info("处理剩余 {} 条数据", batch.size());
                try {
                    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                        @SneakyThrows
                        @Override
                        public void setValues(PreparedStatement ps, int i) {
                            JSONObject jsonObject = batch.get(i);
                            for (int j = 0; j < fieldList.size(); j++) {
                                ManifestFieldEntity field = fieldList.get(j);
                                ps.setString(j + 1, jsonObject.getString(field.getFieldName()));
                            }
                        }

                        @Override
                        public int getBatchSize() {
                            return batch.size();
                        }
                    });
                } catch (Exception e) {
                    log.error("处理剩余JSON对象时发生错误: {}", e.getMessage(), e);
                }
            }

            // 获取当前总的数据量
            long totalDataCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + model, Long.class);

            // 获取新增加的数据量
            long newDataCount = totalDataCount - existingDataCount;


            // 返回表的指标信息
            TableMetrics metrics = new TableMetrics();
            metrics.setExistingDataCount(existingDataCount);
            metrics.setNewDataCount(newDataCount);
            metrics.setTotalDataCount(totalDataCount);

            log.info("数据已成功保存到表: {}", model);
            return metrics;
        } catch (IOException e) {
            log.error("读取文件时发生错误: {}", e.getMessage(), e);
            throw new RuntimeException("读取文件时发生错误", e);
        }
    }

    @Override
    public TableMetrics saveCvsToDatahouse(MultipartFile file, List<ManifestFieldEntity> fieldList, String model) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReader(reader)) {

            // 获取原有数据量
            long existingDataCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + model, Long.class);

            // 读取CSV文件的第一行作为字段名
            String[] headers = csvReader.readNext();
            if (headers == null) {
                throw new IllegalArgumentException("CSV文件为空或没有字段头");
            }

            // 构建插入语句的基础部分
            StringBuilder columns = new StringBuilder();
            StringBuilder placeholders = new StringBuilder();

            for (int i = 0; i < fieldList.size(); i++) {
                ManifestFieldEntity field = fieldList.get(i);
                columns.append(field.getFieldName());
                placeholders.append("?");

                if (i < fieldList.size() - 1) {
                    columns.append(", ");
                    placeholders.append(", ");
                }
            }

            String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", model, columns.toString(), placeholders.toString());

            // 批量插入数据
            List<String[]> batch = new ArrayList<>();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                try {
                    batch.add(line);

                    if (batch.size() >= 1000) {
                        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                            @SneakyThrows
                            @Override
                            public void setValues(PreparedStatement ps, int i) {
                                String[] row = batch.get(i);
                                for (int j = 0; j < fieldList.size(); j++) {
                                    ps.setString(j + 1, row[j]);
                                }
                            }

                            @Override
                            public int getBatchSize() {
                                return batch.size();
                            }
                        });
                        log.info("已处理 {} 条数据", batch.size());
                        batch.clear();
                    }
                } catch (Exception e) {
                    log.error("处理CSV行时发生错误: {}", e.getMessage(), e);
                }
            }

            // 处理剩余的数据
            if (!batch.isEmpty()) {
                log.info("处理剩余 {} 条数据", batch.size());
                try {
                    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                        @SneakyThrows
                        @Override
                        public void setValues(PreparedStatement ps, int i) {
                            String[] row = batch.get(i);
                            for (int j = 0; j < fieldList.size(); j++) {
                                ps.setString(j + 1, row[j]);
                            }
                        }

                        @Override
                        public int getBatchSize() {
                            return batch.size();
                        }
                    });
                } catch (Exception e) {
                    log.error("处理剩余CSV行时发生错误: {}", e.getMessage(), e);
                }
            }

            // 获取当前总的数据量
            long totalDataCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + model, Long.class);

            // 获取新增加的数据量
            long newDataCount = totalDataCount - existingDataCount;

            // 返回表的指标信息
            TableMetrics metrics = new TableMetrics();
            metrics.setExistingDataCount(existingDataCount);
            metrics.setNewDataCount(newDataCount);
            metrics.setTotalDataCount(totalDataCount);

            log.info("数据已成功保存到表: {}", model);
            return metrics;
        } catch (IOException | CsvValidationException e) {
            log.error("读取文件时发生错误: {}", e.getMessage(), e);
            throw new RuntimeException("读取文件时发生错误", e);
        }
    }

}
