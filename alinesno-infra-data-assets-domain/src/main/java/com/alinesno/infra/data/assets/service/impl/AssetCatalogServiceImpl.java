package com.alinesno.infra.data.assets.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.common.facade.datascope.PermissionQuery;
import com.alinesno.infra.data.assets.api.TreeSelectDto;
import com.alinesno.infra.data.assets.entity.AssetCatalogEntity;
import com.alinesno.infra.data.assets.entity.ManifestEntity;
import com.alinesno.infra.data.assets.mapper.AssetCatalogMapper;
import com.alinesno.infra.data.assets.service.IAssetCatalogService;
import com.alinesno.infra.data.assets.service.IManifestService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资产编目Service业务层处理
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@Service
public class AssetCatalogServiceImpl extends IBaseServiceImpl<AssetCatalogEntity, AssetCatalogMapper> implements IAssetCatalogService {

    @Autowired
    private IManifestService manifestService;

    @Override
    public List<AssetCatalogEntity> selectCatalogList(AssetCatalogEntity promptCatalog, PermissionQuery query) {

        LambdaQueryWrapper<AssetCatalogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntityClass(AssetCatalogEntity.class) ;
        query.toWrapper(queryWrapper);

        List<AssetCatalogEntity> list = list(queryWrapper);

        if (list == null || list.isEmpty()) {

            list = new ArrayList<>();

            // 默认有一个选项是父类
            AssetCatalogEntity parent = new AssetCatalogEntity();
            parent.setName("父类对象");
            parent.setId(0L);

            list.add(parent);
        }

        return list;

    }

    @Override
    public void insertCatalog(AssetCatalogEntity entity) {

        AssetCatalogEntity info = this.getById(entity.getParentId());
        if (info != null) {
            entity.setAncestors(info.getAncestors() + "," + entity.getParentId());
        }

        this.save(entity);
    }

    @Override
    public List<TreeSelectDto> selectCatalogTreeList(PermissionQuery query) {

        LambdaQueryWrapper<AssetCatalogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntityClass(AssetCatalogEntity.class) ;
        query.toWrapper(queryWrapper);

        List<AssetCatalogEntity> deptTrees = buildDeptTree(list(queryWrapper));
        return deptTrees.stream().map(TreeSelectDto::new).collect(Collectors.toList());
    }

    @Override
    public List<AssetCatalogEntity> topCatalog(int count) {

        LambdaQueryWrapper<AssetCatalogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(AssetCatalogEntity::getOrderNum);
        queryWrapper.last("limit " + count);

        return list(queryWrapper);
    }

    @Override
    public List<TreeSelectDto> catalogManifestTreeSelect(PermissionQuery query) {
        List<TreeSelectDto> dtoList = selectCatalogTreeList(query);

        // 递归处理每个节点及其子节点
        addManifestToTree(dtoList);

        return dtoList;
    }

    /**
     * 递归方法，为树中的每个叶子节点添加其下的清单
     *
     * @param nodes 当前层级的节点列表
     */
    private void addManifestToTree(List<TreeSelectDto> nodes) {
        for (TreeSelectDto node : nodes) {
            if (node.getChildren().isEmpty()) {
                // 叶子节点，查询其下的清单
                LambdaQueryWrapper<ManifestEntity> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ManifestEntity::getDataDomain, node.getId());
                List<ManifestEntity> manifests = manifestService.list(queryWrapper);
                if (manifests != null && !manifests.isEmpty()) {
                    // 将ManifestEntity转换成TreeSelectDto类
                    List<TreeSelectDto> manifestDtos = manifests.stream()
                            .map(manifest -> {
                                TreeSelectDto tree = new TreeSelectDto();
                                tree.setId(manifest.getId());
                                tree.setLabel(manifest.getTableName());
                                return tree;
                            })
                            .toList();
                    node.setChildren(manifestDtos);
                }
            } else {
                // 非叶子节点，递归处理子节点
                addManifestToTree(node.getChildren());
            }
        }
    }

    /**
     * 构建前端所需要树结构
     *
     * @param prompts 部门列表
     * @return 树结构列表
     */
    public List<AssetCatalogEntity> buildDeptTree(List<AssetCatalogEntity> prompts) {

        List<AssetCatalogEntity> returnList = new ArrayList<>();
        List<Long> tempList = prompts.stream().map(AssetCatalogEntity::getId).toList();

        for (AssetCatalogEntity dept : prompts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(prompts, dept);
                returnList.add(dept);
            }
        }

        if (returnList.isEmpty()) {
            returnList = prompts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<AssetCatalogEntity> list, AssetCatalogEntity t) {
        // 得到子节点列表
        List<AssetCatalogEntity> childList = getChildList(list, t);
        t.setChildren(childList);
        for (AssetCatalogEntity tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<AssetCatalogEntity> getChildList(List<AssetCatalogEntity> list, AssetCatalogEntity t) {
        List<AssetCatalogEntity> tlist = new ArrayList<>();
        for (AssetCatalogEntity n : list) {
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<AssetCatalogEntity> list, AssetCatalogEntity t) {
        return !getChildList(list, t).isEmpty();
    }
}
