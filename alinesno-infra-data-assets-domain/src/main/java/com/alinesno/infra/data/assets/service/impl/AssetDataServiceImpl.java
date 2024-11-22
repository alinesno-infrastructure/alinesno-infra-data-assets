package com.alinesno.infra.data.assets.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.data.assets.api.AssetsTableDataInfo;
import com.alinesno.infra.data.assets.entity.AssetDataEntity;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;
import com.alinesno.infra.data.assets.mapper.AssetDataMapper;
import com.alinesno.infra.data.assets.service.IAssetDataService;
import com.alinesno.infra.data.assets.service.IManifestService;
import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
public class AssetDataServiceImpl extends IBaseServiceImpl<AssetDataEntity, AssetDataMapper> implements IAssetDataService {

    @Autowired
    private IManifestService manifestService ;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DS("postgresql")
    @Override
    public AssetsTableDataInfo findManifestData(DatatablesPageBean page, String manifestId, List<ManifestFieldEntity> fieldList) {

        String tableName = "table_" + manifestId;

        // 构造SQL查询语句的SELECT部分
        StringBuilder selectColumns = new StringBuilder();
        for (int i = 0; i < fieldList.size(); i++) {
            ManifestFieldEntity field = fieldList.get(i);
            selectColumns.append("\"").append(field.getFieldName()).append("\""); // 使用双引号包围字段名
            if (i < fieldList.size() - 1) {
                selectColumns.append(", ");
            }
        }

        // 计算分页所需的偏移量
        int offset = (page.getPageNum() - 1) * page.getPageSize();

        // 构造SQL查询语句
        String sql = "SELECT " + selectColumns.toString() +
                " FROM \"" + tableName + "\"" + // 使用双引号包围表名
                " LIMIT ? OFFSET ?";

        // 执行查询
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, page.getPageSize(), offset);

        // 计算总记录数
        String countSql = "SELECT COUNT(*) FROM \"" + tableName + "\""; // 使用双引号包围表名
        Integer totalRecords = jdbcTemplate.queryForObject(countSql, Integer.class);

        // 构建返回对象
        AssetsTableDataInfo tableDataInfo = new AssetsTableDataInfo();
        tableDataInfo.setCode(0);
        tableDataInfo.setMsg("");

        assert totalRecords != null;
        tableDataInfo.setTotal(totalRecords.longValue());

        tableDataInfo.setRows(result);

        List<AssetsTableDataInfo.Field> fieldDtoList = fieldList.stream().map(AssetsTableDataInfo.Field::new).toList();
        tableDataInfo.setFields(fieldDtoList); // 设置字段列表信息

        return tableDataInfo;
    }
}
