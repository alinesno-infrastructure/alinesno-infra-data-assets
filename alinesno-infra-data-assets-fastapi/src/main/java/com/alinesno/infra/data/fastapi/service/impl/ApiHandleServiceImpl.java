package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.data.assets.entity.DataSourceConfigEntity;
import com.alinesno.infra.data.assets.service.IDataSourceConfigService;
import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import com.alinesno.infra.data.fastapi.mapper.ApiConfigMapper;
import com.alinesno.infra.data.fastapi.service.IApiClientService;
import com.alinesno.infra.data.fastapi.service.IApiHandleService;
import com.alinesno.infra.data.fastapi.utils.ParamFilterUtils;
import com.alinesno.infra.data.fastapi.utils.TemplateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApiHandleServiceImpl implements IApiHandleService {

    // 在类中新增默认值
    private static final int DEFAULT_MAX_ROWS = 100;

    @Autowired
    private IDataSourceConfigService dataSourceConfigService;

    @Autowired
    private IApiClientService apiClientService;

    @Autowired
    private ApiConfigMapper apiConfigMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 数据源缓存（按 datasourceId）
    private final ConcurrentHashMap<String, DataSource> dsCache = new ConcurrentHashMap<>();

    private DataSource buildDataSource(DataSourceConfigEntity cfg) {
        // 尽量复用
        return dsCache.computeIfAbsent(cfg.getId().toString(), k -> {
            HikariConfig hc = new HikariConfig();
            hc.setJdbcUrl(cfg.getUrl());
            hc.setUsername(cfg.getUsername());
            hc.setPassword(cfg.getPassword());
            if (cfg.getDriverClassName() != null) {
                hc.setDriverClassName(cfg.getDriverClassName());
            }
            // 可配置更多参数
            hc.setMaximumPoolSize(10);
            return new HikariDataSource(hc);
        });
    }

    private Map<String, Object> bindRequestParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        // 1. query params
        request.getParameterMap().forEach((k, v) -> {
            if (v != null && v.length == 1) params.put(k, v[0]);
            else params.put(k, v);
        });
        // 2. body as JSON
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String body = sb.toString();
            if (!body.isBlank()) {
                try {
                    Map<String, Object> map = objectMapper.readValue(body, Map.class);
                    if (map != null) params.putAll(map);
                } catch (Exception ex) {
                    // 如果 body 不是 json，则放个 rawBody
                    params.put("_rawBody", body);
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return params;
    }

    @Override
    public Object handle(ApiConfigEntity apiConfig, HttpServletRequest request, HttpServletResponse response, Long orgId) {
        if (apiConfig == null) {
            return AjaxResult.error("API not found.");
        }

        // 绑定并过滤参数（只保留定义里的参数）
        Map<String, Object> params = bindRequestParams(request);
        params = ParamFilterUtils.filterByDefinition(apiConfig.getJsonParam(), params);

        String executeType = Optional.ofNullable(apiConfig.getExecuteType()).orElse("sql").toLowerCase();
        Object result;

        try {
            // Build template variables
            Map<String, Object> templateVars = new HashMap<>(params);
            templateVars.put("orgId", orgId);
            // headers
            Map<String, String> headers = new HashMap<>();
            Collections.list(request.getHeaderNames()).forEach(h -> headers.put(h, request.getHeader(h)));
            templateVars.put("headers", headers);

            if ("groovy".equals(executeType)) {
                // 对 groovy 脚本做占位符替换
                String scriptTemplate = Optional.ofNullable(apiConfig.getGroovyScript()).orElse("");
                String finalScript = TemplateUtils.process(scriptTemplate, "groovy_" + apiConfig.getId(), templateVars);
                result = executeGroovyWithScript(apiConfig, finalScript, params, request, response, templateVars);
            } else {
                String sqlTemplate = Optional.ofNullable(apiConfig.getGroovyScript()).orElse(apiConfig.getJsonParam());
                String finalSql = TemplateUtils.process(sqlTemplate, "sql_" + apiConfig.getId(), templateVars);
                result = executeSqlWithFinalText(apiConfig, finalSql, params);
            }

            // 增加调用次数
            apiConfig.setUseCount(Optional.ofNullable(apiConfig.getUseCount()).orElse(0) + 1);
            apiConfigMapper.updateById(apiConfig);
        } catch (Exception e) {
            throw new RuntimeException("execute error: " + e.getMessage(), e);
        }
        return result;
    }

    // 辅助方法：根据 apiConfig 获取 DataSource（使用已有 dsCache 与 buildDataSource）
    private DataSource getDataSourceByApiConfig(ApiConfigEntity apiConfig) {
        DataSourceConfigEntity dsCfg = dataSourceConfigService.getById(apiConfig.getDatasourceId()) ;
        return buildDataSource(dsCfg);
    }

    private static class StopIterationException extends RuntimeException {}

    // 修改后的 executeSqlWithFinalText（核心部分）
    private Object executeSqlWithFinalText(ApiConfigEntity apiConfig, String finalSql, Map<String, Object> params) {
        if (finalSql == null || finalSql.isBlank()) {
            throw new RuntimeException("SQL is empty");
        }

        DataSource ds = getDataSourceByApiConfig(apiConfig);
        if (ds == null) {
            throw new RuntimeException("datasource not found");
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        NamedParameterJdbcTemplate named = new NamedParameterJdbcTemplate(ds);

        String trimmed = finalSql.trim().toLowerCase(Locale.ROOT);

        boolean hasNamedParams = false;
        try {
            Pattern p = Pattern.compile(":[a-zA-Z0-9_]+");
            Matcher m = p.matcher(finalSql);
            hasNamedParams = m.find();
        } catch (Exception ignored) {}

        try {
            if (trimmed.startsWith("select") || trimmed.startsWith("show") ||
                    trimmed.startsWith("describe") || trimmed.startsWith("explain")) {

                // 如果调用方传了分页参数，优先使用
                Integer page = null, size = null;
                if (params != null) {
                    Object pObj = params.get("page");
                    Object sObj = params.get("size");
                    if (pObj instanceof Number) page = ((Number) pObj).intValue();
                    if (sObj instanceof Number) size = ((Number) sObj).intValue();
                    else if (sObj != null) {
                        try { size = Integer.parseInt(sObj.toString()); } catch (Exception ignored) {}
                    }
                }

                // 如果 apiConfig 支持 maxRows 配置，优先使用；否则用默认
                int maxRows = DEFAULT_MAX_ROWS;

                // 如果传了 page/size，可以直接做分页（简单示例仅对常见 DB 有效：MySQL/Postgres）
                if (size != null && size > 0) {
                    // 当使用分页时，不用再做 maxRows 截断（或者可以上限为 maxRows）
                    int actualSize = Math.min(size, maxRows);
                    int offset = (page != null && page > 0) ? (page - 1) * actualSize : 0;
                    // 简单地给 SQL 加上 LIMIT/OFFSET（注意：不同 DB 需要不同写法）
                    finalSql = finalSql + " LIMIT " + actualSize + " OFFSET " + offset;
                    // 此时执行并返回完整结果（行数受 actualSize 限制）
                    if (hasNamedParams && !params.isEmpty()) {
                        return named.queryForList(finalSql, params);
                    } else {
                        return jdbcTemplate.queryForList(finalSql);
                    }
                }

                // 否则使用流式读取并限制最大返回行数（防止内存暴涨）
                List<Map<String,Object>> rows = new ArrayList<>();
                final boolean[] truncated = { false };

                org.springframework.jdbc.core.RowCallbackHandler rch = rs -> {
                    Map<String,Object> row = new LinkedHashMap<>();
                    java.sql.ResultSetMetaData md = rs.getMetaData();
                    int colCount = md.getColumnCount();
                    for (int i = 1; i <= colCount; i++) {
                        row.put(md.getColumnLabel(i), rs.getObject(i));
                    }
                    rows.add(row);
                    if (rows.size() > maxRows) {
                        truncated[0] = true;
                        throw new StopIterationException();
                    }
                };

                try {
                    if (hasNamedParams && params != null && !params.isEmpty()) {
                        named.query(finalSql, new org.springframework.jdbc.core.namedparam.MapSqlParameterSource(params), rch);
                    } else {
                        jdbcTemplate.query(finalSql, rch);
                    }
                } catch (StopIterationException sie) {
                    // 这是预期的中断，忽略
                }

                Map<String,Object> resp = new HashMap<>();
                resp.put("rows", rows);
                resp.put("truncated", truncated[0]);
                resp.put("maxRows", maxRows);
                resp.put("returned", rows.size());
                return resp;

            } else {
                // 更新类语句（insert/update/delete）
                int updated;
                if (hasNamedParams && params != null && !params.isEmpty()) {
                    updated = named.update(finalSql, params);
                } else {
                    updated = jdbcTemplate.update(finalSql);
                }
                Map<String, Object> r = new HashMap<>();
                r.put("updated", updated);
                return r;
            }
        } catch (StopIterationException sie) {
            // 如果意外抛出，包装处理（一般不会到这里）
            Map<String,Object> r = new HashMap<>();
            r.put("rows", Collections.emptyList());
            r.put("truncated", true);
            r.put("maxRows", DEFAULT_MAX_ROWS);
            return r;
        } catch (Exception e) {
            throw new RuntimeException("SQL execute error: " + e.getMessage(), e);
        }
    }

    /**
     * 执行 Groovy 脚本（finalScript 已过模板渲染）
     * @param apiConfig 原始配置
     * @param finalScript 渲染后的 Groovy 脚本
     * @param params 参数映射（脚本中也可通过 params 访问）
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param templateVars 模板变量集合（注入为 vars）
     * @return 脚本执行结果
     */
    private Object executeGroovyWithScript(ApiConfigEntity apiConfig,
                                           String finalScript,
                                           Map<String, Object> params,
                                           HttpServletRequest request,
                                           HttpServletResponse response,
                                           Map<String, Object> templateVars) {
        if (finalScript == null) {
            throw new RuntimeException("groovy script empty");
        }

        Binding binding = new Binding();
        binding.setVariable("params", params != null ? params : new HashMap<>());
        binding.setVariable("vars", templateVars != null ? templateVars : new HashMap<>()); // 模板变量
        binding.setVariable("request", request);
        binding.setVariable("response", response);
        binding.setVariable("apiConfig", apiConfig);

        // 尝试注入 jdbcTemplate/namedJdbc
        try {
            DataSource ds = getDataSourceByApiConfig(apiConfig);
            if (ds != null) {
                binding.setVariable("jdbcTemplate", new JdbcTemplate(ds));
                binding.setVariable("namedJdbc", new NamedParameterJdbcTemplate(ds));
            }
        } catch (Exception ignored) {}

        // 可按需要自定义 ClassLoader / 安全策略
        GroovyShell shell = new GroovyShell(binding);
        try {
            return shell.evaluate(finalScript);
        } catch (Exception e) {
            throw new RuntimeException("Groovy execute error: " + e.getMessage(), e);
        }
    }

}