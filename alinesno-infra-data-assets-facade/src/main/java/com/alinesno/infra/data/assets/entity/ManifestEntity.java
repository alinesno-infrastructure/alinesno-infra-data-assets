package com.alinesno.infra.data.assets.entity;

import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据清单
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("assets_manifest")
public class ManifestEntity extends InfraBaseEntity {

}
