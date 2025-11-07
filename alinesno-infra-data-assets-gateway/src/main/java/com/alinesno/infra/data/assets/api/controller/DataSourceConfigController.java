package com.alinesno.infra.data.assets.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionQuery;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionScope;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.assets.api.DataSourceConfigDto;
import com.alinesno.infra.data.assets.entity.DataSourceConfigEntity;
import com.alinesno.infra.data.assets.service.IDataSourceConfigService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/assets/datasourceConfig")
public class DataSourceConfigController extends BaseController<DataSourceConfigEntity, IDataSourceConfigService> {

    @Autowired
    private IDataSourceConfigService service;

    /**
     * 获取BusinessLogEntity的DataTables数据。
     *
     * @param request HttpServletRequest对象。
     * @param model Model对象。
     * @param page DatatablesPageBean对象。
     * @return 包含DataTables数据的TableDataInfo对象。
     */
    @DataPermissionScope
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));

        return this.toPage(model, this.getFeign(), page);
    }

    /**
     * 获取当前数据源配置信息
     * @return
     */
    @DataPermissionQuery
    @GetMapping("/getCurrentDatasourceConfig")
    public AjaxResult getCurrentDatasourceConfig(PermissionQuery permissionQuery){
        DataSourceConfigEntity dataSourceConfig = service.getCurrentDatasourceConfig(permissionQuery);
        return AjaxResult.success(dataSourceConfig);
    }

    /**
     * 保存数据源配置
     */
    @DataPermissionSave
    @PostMapping("/saveDatasourceConfig")
    public AjaxResult saveDatasourceConfig(@RequestBody DataSourceConfigDto dto){

        log.debug("dto = {}", dto.toString());

        DataSourceConfigEntity entity = dto.toEntity() ;
        service.save(entity) ;

        return AjaxResult.success() ;
    }

    /**
     * 检测数据源连接是否可用
     *
     * @param url 数据库连接地址 (jdbc 格式)
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param type     数据源类型 (mysql/postgresql/clickhouse)
     * @return 返回连接结果的 JSON
     */
    @GetMapping("/probe")
    public AjaxResult probeDatasource(
            @RequestParam String url,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String type) {

        AjaxResult result ;
        String driver;

        // 根据类型选择驱动
        switch (type.toLowerCase()) {
            case "mysql" -> driver = "com.mysql.cj.jdbc.Driver";
            case "postgresql" -> driver = "org.postgresql.Driver";
            case "clickhouse" -> driver = "ru.yandex.clickhouse.ClickHouseDriver";
            default -> {
                return AjaxResult.error("不支持的数据库类型");
            }
        }

        log.info("检测数据源连接: url={}, username={}, type={}", url, username, type);


        try {
            Class.forName(driver);
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
               result = AjaxResult.success("连接成功");
            }
        } catch (Exception e) {
            log.error("数据源连接失败: {}", e.getMessage(), e);
            result = AjaxResult.error("数据源连接失败: " + e.getMessage());
        }

        return result;
    }

    @Override
    public IDataSourceConfigService getFeign() {
        return this.service;
    }
}
