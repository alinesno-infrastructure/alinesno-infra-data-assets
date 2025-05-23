package com.alinesno.infra.data.assets.collector.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alinesno.infra.common.core.utils.DateUtils;
import com.alinesno.infra.data.assets.api.PushDataUserInfoDto;
import com.alinesno.infra.data.assets.api.TableFieldRequestDto;
import com.alinesno.infra.data.assets.api.TableMetrics;
import com.alinesno.infra.data.assets.collector.service.DataAssetService;
import com.alinesno.infra.data.assets.constants.AssetDataConstants;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;
import com.alinesno.infra.data.assets.service.IManifestFieldService;
import com.alinesno.infra.data.assets.service.IManifestService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DataAssetServiceImpl implements DataAssetService {

    @Autowired
    private IManifestService manifestService;

    @Autowired
    private IManifestFieldService manifestFieldService;

    @Override
    public TableMetrics handleCsvFile(MultipartFile file, String model, PushDataUserInfoDto userInfoDto) {
        // 实现CSV文件处理逻辑
        // 可以使用OpenCSV等库来解析CSV文件
        log.debug("Handling CSV file for model: " + model);

        // 插入到列数据中
        List<ManifestFieldEntity> fieldList = getManifestFieldEntities(model);
        String tableName = getSafeTableName(model);

        // 保存到数据资产库中
        return manifestService.saveCvsToDatahouse(file, fieldList, tableName);
    }


    private String getSafeTableName(String model) {
        // 假设表名只允许字母、数字和下划线
        return "table_" + model.replaceAll("[^a-zA-Z0-9_]", "");
    }

    @Override
    public TableMetrics handleJsonFile(MultipartFile file, String model, PushDataUserInfoDto userInfoDto) {
        // 可以使用Jackson或Gson等库来解析JSON文件
        log.debug("Handling JSON file for model: " + model);

        List<ManifestFieldEntity> fieldList = getManifestFieldEntities(model);
        String tableName = getSafeTableName(model);

        // 保存到数据资产库中
        return manifestService.saveToDatahouse(file, fieldList, tableName);
    }

    private List<ManifestFieldEntity> getManifestFieldEntities(String model) {
        // 插入到列数据中
        LambdaQueryWrapper<ManifestFieldEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ManifestFieldEntity::getManifestId, model);
        return manifestFieldService.list(queryWrapper);
    }

    @Override
    public TableMetrics handleListData(List<Map<String, Object>> dataList, String model, PushDataUserInfoDto userInfoDto) {
        // 实现List数据处理逻辑
        log.debug("Handling list data for model: " + model);

        // 获取默认字段
        List<TableFieldRequestDto> defaultFields = AssetDataConstants.DEFAULT_FIELDS;

        // 检查并添加默认字段
        for (Map<String, Object> data : dataList) {
            for (TableFieldRequestDto defaultField : defaultFields) {
                if (!data.containsKey(defaultField.getName())) {
                    switch (defaultField.getName()) {
                        case "id":
                            data.put(defaultField.getName(), IdUtil.getSnowflakeNextIdStr());
                            break;
                        case "add_time":
                            data.put(defaultField.getName(), new java.sql.Timestamp(System.currentTimeMillis()));
                            break;
                        case "operator_id":
                            data.put(defaultField.getName(), userInfoDto.getUserId());
                            break;
                        case "org_id":
                            data.put(defaultField.getName(), userInfoDto.getOrgId());
                            break;
                        case "department_id":
                            data.put(defaultField.getName(), 0);
                            break;
                    }
                }
            }
        }

        // 插入到列数据中
        List<ManifestFieldEntity> fieldList = getManifestFieldEntities(model);
        String tableName = getSafeTableName(model);

        // 保存到数据资产库中
        return manifestService.saveToDatahouse(dataList, fieldList, tableName);

    }
}