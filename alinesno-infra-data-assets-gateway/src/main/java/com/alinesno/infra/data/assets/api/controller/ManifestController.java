package com.alinesno.infra.data.assets.api.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.login.account.CurrentAccountJwt;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.assets.api.AssetTagUpdateDto;
import com.alinesno.infra.data.assets.api.DataSourceConfigQueryDto;
import com.alinesno.infra.data.assets.api.ManifestDto;
import com.alinesno.infra.data.assets.entity.DataSourceConfigEntity;
import com.alinesno.infra.data.assets.entity.ManifestEntity;
import com.alinesno.infra.data.assets.service.IDataSourceConfigService;
import com.alinesno.infra.data.assets.service.IManifestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 资产清单控制器。
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/assets/manifest")
public class ManifestController extends BaseController<ManifestEntity, IManifestService> {

    @Autowired
    private IManifestService service;

    @Autowired
    private IDataSourceConfigService dataSourceConfigService ;

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

        TableDataInfo tableDataInfo = this.toPage(model, this.getFeign(), page);
        log.debug("tableDataInfo = {}", ToStringBuilder.reflectionToString(tableDataInfo));

        // 确保返回的是 ManifestEntity 列表
        List<ManifestEntity> manifestEntities = new ArrayList<>();
        for (Object row : tableDataInfo.getRows()) {
            if (row instanceof ManifestEntity) {
                manifestEntities.add((ManifestEntity) row);
            }
        }

        if (manifestEntities.isEmpty()) {
            tableDataInfo.setRows(new ArrayList<>());
            return tableDataInfo;
        }

        // 一次性获取所有 dataSourceConfigId
        List<Long> configIds = manifestEntities.stream()
                .map(ManifestEntity::getDataSourceConfigId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        // 批量查询对应的数据源配置
        List<DataSourceConfigEntity> configEntities = dataSourceConfigService.listByIds(configIds);

        // 将 configEntities 转为 Map，方便快速查找
        Map<Long, DataSourceConfigEntity> configMap = configEntities.stream()
                .collect(Collectors.toMap(DataSourceConfigEntity::getId, c -> c));

        // 组装 ManifestDto 列表
        List<ManifestDto> manifestDtos = manifestEntities.stream().map(entity -> {
            ManifestDto dto = new ManifestDto();
            BeanUtils.copyProperties(entity, dto);

            // 如果关联的数据源配置存在，则复制属性
            DataSourceConfigEntity cfg = configMap.get(entity.getDataSourceConfigId());
            if (cfg != null) {
                DataSourceConfigQueryDto cfgDto = new DataSourceConfigQueryDto();
                BeanUtils.copyProperties(cfg, cfgDto);
                dto.setDataSourceConfig(cfgDto);
            }

            return dto;
        }).toList();

        tableDataInfo.setRows(manifestDtos);
        return tableDataInfo;
    }

    @DataPermissionSave
    @Override
    public AjaxResult save(Model model, @RequestBody ManifestEntity entity) throws Exception {
        log.debug("CurrentAccountJwt.get() = {}" , CurrentAccountJwt.get().getOrgId());
        return super.save(model, entity);
    }

    /**
     * 更新标签信息 updateManifestLabel
     * @return
     */
    @DataPermissionSave
    @PutMapping("/updateManifestLabel")
    public AjaxResult updateManifestLabel(@RequestBody AssetTagUpdateDto dto) {

        log.debug("dto = {}" , ToStringBuilder.reflectionToString(dto));

        return AjaxResult.success() ;
    }

    @Override
    public IManifestService getFeign() {
        return this.service;
    }
}
