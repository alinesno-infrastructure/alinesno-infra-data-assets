package com.alinesno.infra.data.assets.metrics.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.data.assets.metrics.dto.MetricTreeSelectDto;
import com.alinesno.infra.data.assets.metrics.entity.DataMetricTypeEntity;
import com.alinesno.infra.data.assets.metrics.mapper.DataMetricTypeMapper;
import com.alinesno.infra.data.assets.metrics.service.IDataMetricTypeService;
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
public class DataMetricTypeServiceImpl extends IBaseServiceImpl<DataMetricTypeEntity, DataMetricTypeMapper> implements IDataMetricTypeService {

    @Override
    public List<DataMetricTypeEntity> selectCatalogList(DataMetricTypeEntity promptCatalog) {

        List<DataMetricTypeEntity> list = list() ;

        if(list == null || list.isEmpty()){

            list = new ArrayList<>() ;

            // 默认有一个选项是父类
            DataMetricTypeEntity parent = new DataMetricTypeEntity() ;
            parent.setName("父类对象");
            parent.setId(0L);

            list.add(parent) ;
        }

        return list ;

    }

    @Override
    public void insertCatalog(DataMetricTypeEntity entity) {

        DataMetricTypeEntity info = this.getById(entity.getParentId());
        if(info != null){
            entity.setAncestors(info.getAncestors() + "," + entity.getParentId());
        }

        this.save(entity) ;
    }

    @Override
    public List<MetricTreeSelectDto> selectCatalogTreeList() {

        List<DataMetricTypeEntity> deptTrees = buildDeptTree(list());
        return deptTrees.stream().map(MetricTreeSelectDto::new).collect(Collectors.toList());
    }

    @Override
    public List<DataMetricTypeEntity> topCatalog(int count) {

        LambdaQueryWrapper<DataMetricTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(DataMetricTypeEntity::getOrderNum);
        queryWrapper.last("limit " + count);

        return list(queryWrapper) ;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param prompts 部门列表
     * @return 树结构列表
     */
    public List<DataMetricTypeEntity> buildDeptTree(List<DataMetricTypeEntity> prompts) {

        List<DataMetricTypeEntity> returnList = new ArrayList<>();
        List<Long> tempList = prompts.stream().map(DataMetricTypeEntity::getId).toList();

        for (DataMetricTypeEntity dept : prompts) {
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
    private void recursionFn(List<DataMetricTypeEntity> list, DataMetricTypeEntity t) {
        // 得到子节点列表
        List<DataMetricTypeEntity> childList = getChildList(list, t);
        t.setChildren(childList);
        for (DataMetricTypeEntity tChild : childList) {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<DataMetricTypeEntity> getChildList(List<DataMetricTypeEntity> list, DataMetricTypeEntity t) {
        List<DataMetricTypeEntity> tlist = new ArrayList<>();
        for (DataMetricTypeEntity n : list) {
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<DataMetricTypeEntity> list, DataMetricTypeEntity t) {
        return !getChildList(list, t).isEmpty();
    }
}
