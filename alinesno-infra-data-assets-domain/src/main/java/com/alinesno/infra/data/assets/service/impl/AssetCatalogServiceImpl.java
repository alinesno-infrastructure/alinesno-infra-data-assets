package com.alinesno.infra.data.assets.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.data.assets.api.TreeSelectDto;
import com.alinesno.infra.data.assets.entity.AssetCatalogEntity;
import com.alinesno.infra.data.assets.mapper.AssetCatalogMapper;
import com.alinesno.infra.data.assets.service.IAssetCatalogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public List<AssetCatalogEntity> selectCatalogList(AssetCatalogEntity promptCatalog) {

        List<AssetCatalogEntity> list = list() ;

        if(list == null || list.isEmpty()){

            list = new ArrayList<>() ;

            // 默认有一个选项是父类
            AssetCatalogEntity parent = new AssetCatalogEntity() ;
            parent.setName("父类对象");
            parent.setId(0L);

            list.add(parent) ;
        }

        return list ;

    }

    @Override
    public void insertCatalog(AssetCatalogEntity entity) {

        AssetCatalogEntity info = this.getById(entity.getParentId());
        if(info != null){
            entity.setAncestors(info.getAncestors() + "," + entity.getParentId());
        }

        this.save(entity) ;
    }

    @Override
    public List<TreeSelectDto> selectCatalogTreeList() {

        List<AssetCatalogEntity> deptTrees = buildDeptTree(list());
        return deptTrees.stream().map(TreeSelectDto::new).collect(Collectors.toList());
    }

    @Override
    public List<AssetCatalogEntity> topCatalog(int count) {

        LambdaQueryWrapper<AssetCatalogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(AssetCatalogEntity::getOrderNum);
        queryWrapper.last("limit " + count);

        return list(queryWrapper) ;
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
            if (hasChild(list, tChild))
            {
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
