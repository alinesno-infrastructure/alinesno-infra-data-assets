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
import com.alinesno.infra.data.assets.api.utils.JdbcDriverUtils;
import com.alinesno.infra.data.assets.entity.DataSourceConfigEntity;
import com.alinesno.infra.data.assets.job.IDataAssetSyncJob;
import com.alinesno.infra.data.assets.service.IDataSourceConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private IDataAssetSyncJob dataAssetSyncJob ;

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

        // 1. 查询原始数据（实体列表）
        TableDataInfo tableDataInfo = this.toPage(model, this.getFeign(), page);

        // 2. 安全转换为脱敏DTO列表（消除unchecked cast警告）
        List<DataSourceConfigDto> dtoList = Optional.ofNullable(tableDataInfo.getRows())
                .orElse(Collections.emptyList()) // 空值处理
                .stream()
                // 过滤并转换为DataSourceConfigEntity类型（类型校验）
                .filter(item -> item instanceof DataSourceConfigEntity)
                .map(item -> (DataSourceConfigEntity) item)
                // 转换为脱敏DTO
                .map(DataSourceConfigDto::fromEntity)
                .toList();

        // 3. 替换rows为脱敏后的DTO
        tableDataInfo.setRows(dtoList);
        return tableDataInfo;
    }

    /**
     * 列出所有的数据源listAll
     */
    @DataPermissionQuery
    @GetMapping("/listAll")
    public AjaxResult listAll(PermissionQuery query){

        LambdaQueryWrapper<DataSourceConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.setEntityClass(DataSourceConfigEntity.class) ;
        query.toWrapper(wrapper) ;

        List<DataSourceConfigEntity> list = service.list(wrapper);

        // 转换成DTO返回
        List<DataSourceConfigDto> dtoList = list.stream()
                .map(DataSourceConfigDto::fromEntity)
                .toList();

        return AjaxResult.success(dtoList) ;
    }

    /**
     * 获取当前数据源配置信息
     * @return
     */
    @DataPermissionQuery
    @GetMapping("/getCurrentDatasourceConfig")
    public AjaxResult getCurrentDatasourceConfig(PermissionQuery permissionQuery){
        DataSourceConfigEntity dataSourceConfig = service.getCurrentDatasourceConfig(permissionQuery);
        // 转换成DTO
        DataSourceConfigDto dto = DataSourceConfigDto.fromEntity(dataSourceConfig);
        return AjaxResult.success(dto);
    }

    /**
     * 保存数据源配置
     */
    @DataPermissionSave
    @PostMapping("/insertDatasourceConfig")
    public AjaxResult insertDatasourceConfig(@RequestBody @Valid DataSourceConfigDto dto){

        log.debug("dto = {}", dto.toString());

        // 根据类型选择驱动
        String driver = JdbcDriverUtils.getDriverByType(dto.getType());

        DataSourceConfigEntity entity = dto.toEntity(driver) ;
        service.saveOrUpdate(entity) ;

        return AjaxResult.success("操作成功." , entity.getId()) ;
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
        try {
            driver = JdbcDriverUtils.getDriverByType(type);
        } catch (IllegalArgumentException e) {
            return AjaxResult.error(e.getMessage());
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

    /**
     * 立即同步指定数据源
     */
    @PostMapping("/syncNow/{id}")
    public AjaxResult syncNow(@PathVariable("id") Long id) {
        try {
            // 根据id获取配置
            DataSourceConfigEntity config = service.getById(id);
            if (config == null) {
                return AjaxResult.error("数据源不存在");
            }

            // 调用同步逻辑
            dataAssetSyncJob.syncDataSource(config);

            return AjaxResult.success("已触发数据源同步");
        } catch (Exception e) {
            log.error("执行立即同步失败", e);
            return AjaxResult.error("同步失败：" + e.getMessage());
        }
    }

    @Override
    public IDataSourceConfigService getFeign() {
        return this.service;
    }
}
