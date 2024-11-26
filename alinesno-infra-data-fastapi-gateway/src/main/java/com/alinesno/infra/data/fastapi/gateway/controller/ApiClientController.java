package com.alinesno.infra.data.fastapi.gateway.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.common.extend.datasource.annotation.DataPermissionSave;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.fastapi.api.ApiClientDto;
import com.alinesno.infra.data.fastapi.entity.ApiClientEntity;
import com.alinesno.infra.data.fastapi.service.IApiClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

/**
 * 处理与ApiClientEntity相关的请求的Controller。
 * 继承自BaseController类并实现IApiClientService接口。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/fastapi/ApiClient")
public class ApiClientController extends BaseController<ApiClientEntity, IApiClientService> {

    @Autowired
    private IApiClientService service;


    /**
     * 获取ApiClientEntity的DataTables数据。
     *
     * @param request HttpServletRequest对象。
     * @param model Model对象。
     * @param page DatatablesPageBean对象。
     * @return 包含DataTables数据的TableDataInfo对象。
     */
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign(), page);
    }

    @GetMapping("/protected")
    public String protectedResource(HttpServletRequest request) {
        service.validateClientToken(request);
        return "This is a protected resource.";
    }

    @DataPermissionSave
    @PostMapping("/saveClient")
    public AjaxResult saveClient(@Valid @RequestBody ApiClientDto client) {
        ApiClientEntity clientEntity = new ApiClientEntity();
        BeanUtils.copyProperties(client, clientEntity);

        // 将字符串形式的过期时间转换为 Date 类型
        Date expiryTime = parseExpiryTime(client.getExpiryTimeStr());
        clientEntity.setExpiryTime(expiryTime);

        service.saveClientToken(clientEntity);

        return AjaxResult.success();
    }

    private Date parseExpiryTime(String expiryTimeStr) {
        int days = Integer.parseInt(expiryTimeStr.replace("d", ""));
        long currentTimeMillis = System.currentTimeMillis();
        long expiryTimeMillis = currentTimeMillis + ((long) days * 24 * 60 * 60 * 1000); // 计算过期时间
        return new Date(expiryTimeMillis);
    }


    @Override
    public IApiClientService getFeign() {
        return this.service;
    }
}