package com.alinesno.infra.data.assets.service;


import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.data.assets.api.TableMetrics;
import com.alinesno.infra.data.assets.entity.ManifestEntity;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 资产清单管理
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
public interface IManifestService extends IBaseService<ManifestEntity> {

    /**
     * 保存dataList数据到Postgresql数据资产库中
     *
     * @param dataList
     * @param fieldList
     * @param tableName
     */
    TableMetrics saveToDatahouse(List<Map<String, String>> dataList, List<ManifestFieldEntity> fieldList, String tableName);

    /**
     * 保存csv文件到Postgresql数据资产库
     * @param file
     * @param fieldList
     * @param tableName
     * @return
     */
    TableMetrics saveToDatahouse(MultipartFile file, List<ManifestFieldEntity> fieldList, String tableName);

    /**
     * 保存csv文件到Postgresql数据资产库
     * @param file
     * @param fieldList
     * @param tableName
     * @return
     */
    TableMetrics saveCvsToDatahouse(MultipartFile file, List<ManifestFieldEntity> fieldList, String tableName);
}
