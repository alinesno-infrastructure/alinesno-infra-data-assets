package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.data.fastapi.api.TreeSelectDto;
import com.alinesno.infra.data.fastapi.entity.ApiGroupEntity;
import com.alinesno.infra.data.fastapi.mapper.ApiGroupMapper;
import com.alinesno.infra.data.fastapi.service.IApiGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApiGroupServiceImpl extends IBaseServiceImpl<ApiGroupEntity, ApiGroupMapper> implements IApiGroupService {

    @Override
    public List<ApiGroupEntity> selectCatalogList(ApiGroupEntity promptCatalog) {

        List<ApiGroupEntity> list = list() ;

        if(list == null || list.isEmpty()){

            list = new ArrayList<>() ;

            // 默认有一个选项是父类
            ApiGroupEntity parent = new ApiGroupEntity() ;
            parent.setName("父类对象");
            parent.setId(0L);

            list.add(parent) ;
        }

        return list ;

    }

    @Override
    public void insertCatalog(ApiGroupEntity entity) {

        ApiGroupEntity info = this.getById(entity.getParentId());
        if(info != null){
            entity.setAncestors(info.getAncestors() + "," + entity.getParentId());
        }

        this.save(entity) ;
    }

    @Override
    public List<TreeSelectDto> selectCatalogTreeList() {

        List<ApiGroupEntity> deptTrees = buildDeptTree(list());
        return deptTrees.stream().map(TreeSelectDto::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param prompts 部门列表
     * @return 树结构列表
     */
    public List<ApiGroupEntity> buildDeptTree(List<ApiGroupEntity> prompts) {

        List<ApiGroupEntity> returnList = new ArrayList<>();
        List<Long> tempList = prompts.stream().map(ApiGroupEntity::getId).toList();

        for (ApiGroupEntity dept : prompts) {
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
    private void recursionFn(List<ApiGroupEntity> list, ApiGroupEntity t) {
        // 得到子节点列表
        List<ApiGroupEntity> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ApiGroupEntity tChild : childList) {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ApiGroupEntity> getChildList(List<ApiGroupEntity> list, ApiGroupEntity t) {
        List<ApiGroupEntity> tlist = new ArrayList<>();
        for (ApiGroupEntity n : list) {
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ApiGroupEntity> list, ApiGroupEntity t) {
        return !getChildList(list, t).isEmpty();
    }

}
