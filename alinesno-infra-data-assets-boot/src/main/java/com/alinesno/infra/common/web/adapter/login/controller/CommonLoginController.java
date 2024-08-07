package com.alinesno.infra.common.web.adapter.login.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.dto.LoginBodyDto;
import com.alinesno.infra.common.web.adapter.dto.menus.Menu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class CommonLoginController {

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBodyDto loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = UUID.randomUUID().toString() ;
        ajax.put(TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {

        Map<String, Object> data = new HashMap<>();
        // 将数据填充到data中...
        data.put("permissions", new String[]{"*:*:*"});

        Map<String, Object> user = new HashMap<>();
        user.put("createBy", "admin");
        user.put("createTime", "2023-04-23 16:11:38");
        user.put("updateBy", null);
        user.put("updateTime", null);
        user.put("remark", "管理员");
        user.put("userId", 1);
        user.put("deptId", 103);
        user.put("userName", "admin");
        user.put("nickName", "AIP技术团队");
        user.put("email", "aip-team@163.com");
        user.put("phonenumber", "15888888888");
        user.put("sex", "1");
        user.put("avatar", "");
        user.put("password", "");
        user.put("status", "0");
        user.put("delFlag", "0");
        user.put("loginIp", "");
        user.put("loginDate", "2023-09-21T16:54:12.000+08:00");

        Map<String, Object> dept = new HashMap<>();
        dept.put("createBy", null);
        dept.put("createTime", null);
        dept.put("updateBy", null);
        dept.put("updateTime", null);
        dept.put("remark", null);
        dept.put("deptId", 103);
        dept.put("parentId", 101);
        dept.put("ancestors", "0,100,101");
        dept.put("deptName", "研发部门");
        dept.put("orderNum", 1);
        dept.put("leader", "AIP技术团队");
        dept.put("phone", null);
        dept.put("email", null);
        dept.put("status", "0");
        dept.put("delFlag", null);
        dept.put("parentName", null);
        dept.put("children", new Object[]{});

        user.put("dept", dept);

        Map<String, Object> role = new HashMap<>();
        role.put("createBy", null);
        role.put("createTime", null);
        role.put("updateBy", null);
        role.put("updateTime", null);
        role.put("remark", null);
        role.put("roleId", 1);
        role.put("roleName", "超级管理员");
        role.put("roleKey", "admin");
        role.put("roleSort", 1);
        role.put("dataScope", "1");
        role.put("menuCheckStrictly", false);
        role.put("deptCheckStrictly", false);
        role.put("status", "0");
        role.put("delFlag", null);
        role.put("flag", false);
        role.put("menuIds", null);
        role.put("deptIds", null);
        role.put("permissions", null);
        role.put("admin", true);

        user.put("roles", new Object[]{role});

        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", user.get("roles"));
        ajax.put("permissions", data.get("permissions"));

        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {

        Menu dashboardMenu = new Menu("Dashboard", "/dashboard", false, "noRedirect", "Layout", true, new Menu.Meta("仪盘表", "dashboard", false, null), List.of(
                new Menu("Dashboard", "index", false, false , "dashboard", new Menu.Meta("概览", "monitor", false, null))
        ));

        Menu assetMenu = new Menu("Asset", "/asset", false, "noRedirect", "Layout", true, new Menu.Meta("资产管理", "druid", false, null), List.of(
                new Menu("Manifest", "data/asset/manifest/index", false, false , "data/asset/manifest/index", new Menu.Meta("资产清单", "druid", false, null)),
                new Menu("Asset", "data/asset/assets/index", false, false , "data/asset/assets/index", new Menu.Meta("资产管理", "pdf", false, null)),
                new Menu("Type", "data/asset/type/index", false, false , "data/asset/type/index", new Menu.Meta("分类管理", "redis", false, null)),
                new Menu("Label", "data/asset/label/index", false, false , "data/asset/label/index", new Menu.Meta("标签", "link", false, null)),
                new Menu("Security", "data/asset/security/index", false, false , "data/asset/security/index", new Menu.Meta("安全策略", "monitor", false, null))
        ));

        Menu assetBloodMenu = new Menu("Service", "/service", false, "noRedirect", "Layout", true, new Menu.Meta("数据服务", "logininfor", false, null), List.of(
                new Menu("ApiCatalog", "data/fastapi/group/index", false, false , "data/fastapi/group/index", new Menu.Meta("服务目录", "logininfor", false, null)),
                new Menu("Client", "data/fastapi/apiclient/index", false, false , "data/fastapi/apiclient/index", new Menu.Meta("客户端", "peoples", false, null)),
                new Menu("Api", "data/fastapi/api/index", false, false , "data/fastapi/api/index", new Menu.Meta("API开发", "link", false, null))
        ));

        Menu assetLifeMenu = new Menu("Life", "/lift", false, "noRedirect", "Layout", true, new Menu.Meta("生命周期", "excel", false, null), List.of(
                new Menu("Data", "data/lift/data/index", false, false , "data/lift/data/index", new Menu.Meta("数据生命", "redis", false, null)),
                new Menu("Config", "data/lift/config/index", false, false , "data/asset/type/index", new Menu.Meta("周期配置", "eye", false, null))
        ));

        Menu assetOpsMenu = new Menu("Operate", "/operate", false, "noRedirect", "Layout", true, new Menu.Meta("数据运营", "dict", false, null), List.of(
                new Menu("Analyse", "data/operate/analyse/index", false, false , "data/operate/analyse/index", new Menu.Meta("计量统计", "druid", false, null)),
                new Menu("Appraise", "data/operate/appraise/index", false, false , "data/operate/appraise/index", new Menu.Meta("满意度评价", "peoples", false, null)),
                new Menu("Apply", "data/operate/apply/index", false, false , "data/operate/apply/index", new Menu.Meta("审批管理", "dict", false, null))
        ));

        Menu systemMenu = new Menu("Function", "/function", false, "noRedirect", "Layout", true, new Menu.Meta("系统管理", "system", false, null), List.of(
                new Menu("Power", "data/function/power/index", false, false , "data/asset/power/index", new Menu.Meta("权限管理", "clipboard", false, null)),
                new Menu("Backup", "data/function/backup/index", false, false , "data/asset/backup/index", new Menu.Meta("备份与恢复", "user", false, null)),
                new Menu("Monitor", "data/function/monitor/index", false, false , "data/asset/monitor/index", new Menu.Meta("使用分析", "tree-table", false, null))
                ));

        List<Menu> menus = List.of(dashboardMenu , assetMenu ,assetBloodMenu , assetOpsMenu , assetLifeMenu , systemMenu) ;
        String jsonString = JSON.toJSONString(menus, SerializerFeature.WriteMapNullValue);

        return AjaxResult.success(JSONArray.parseArray(jsonString)) ;
    }

}
