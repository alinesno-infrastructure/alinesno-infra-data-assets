package com.alinesno.infra.data.assets.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alinesno.infra.common.facade.mapper.entity.InfraBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.annotation.TableComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能名： 资产收藏
 * 数据表：  asset_collection
 * 表备注： 资产收藏表
 * @author luoxiaodong
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("asset_collection")
@TableComment("资产收藏表")
public class AssetCollectionEntity extends InfraBaseEntity {
    // fields
    /**
    * 用户标识
    */
    @ColumnComment("用户标识")
    @Excel(name="用户标识")
    @ColumnType(length = 11)
    @TableField("user_id")
    private Long userId;
    /**
    * 收集资源
    */
    @ColumnComment("收集资源")
    @Excel(name="收集资源")
    @ColumnType(length = 255)
    @TableField("collected_resource")
    private String collectedResource;
    /**
    * 排序
    */
    @ColumnComment("排序")
    @Excel(name="排序")
    @ColumnType(length = 11)
    @TableField("sorting_order")
    private Long sortingOrder;
}
