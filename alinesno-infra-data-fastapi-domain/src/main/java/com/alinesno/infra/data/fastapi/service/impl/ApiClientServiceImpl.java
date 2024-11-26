package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.data.fastapi.entity.ApiClientEntity;
import com.alinesno.infra.data.fastapi.mapper.ApiClientMapper;
import com.alinesno.infra.data.fastapi.service.IApiClientService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ApiClientServiceImpl extends IBaseServiceImpl<ApiClientEntity, ApiClientMapper> implements IApiClientService {

    @Override
    public void saveClientToken(ApiClientEntity client) {
        // 保存到数据库
        baseMapper.insert(client);
        log.info("Client token saved successfully for client: {}", client.getClientName());
    }


    @Override
    public void validateClientToken(HttpServletRequest request) {
        // 从请求头中获取Token
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("Token is missing or invalid.");
        }

        // 去掉可能的 "Bearer " 前缀
        token = token.replace("Bearer ", "");

        // 从数据库中查询客户端信息
        ApiClientEntity client = getOne(new QueryWrapper<ApiClientEntity>().eq("secret", token));

        if (client == null) {
            throw new IllegalArgumentException("Invalid client token.");
        }

        // 检查Token是否过期
        Date now = new Date();
        if (client.getExpiryTime().before(now)) {
            throw new IllegalArgumentException("Client token has expired.");
        }

        log.info("Client token is valid for client: {}", client.getClientName());
    }

}
