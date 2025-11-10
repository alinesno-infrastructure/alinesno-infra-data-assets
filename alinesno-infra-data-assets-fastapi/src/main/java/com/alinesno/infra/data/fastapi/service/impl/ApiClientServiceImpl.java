package com.alinesno.infra.data.fastapi.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.data.fastapi.entity.ApiClientEntity;
import com.alinesno.infra.data.fastapi.mapper.ApiClientMapper;
import com.alinesno.infra.data.fastapi.service.IApiClientService;
import com.alinesno.infra.data.fastapi.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public ApiClientEntity validateClientToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("令牌不存在或无效");
        }
        token = token.replace("Bearer ", "").trim();

        ApiClientEntity client = getOne(new QueryWrapper<ApiClientEntity>().eq("secret", token));
        if (client == null) {
            throw new IllegalArgumentException("客户端令牌无效");
        }

        Date now = new Date();
        if (client.getExpiryTime() == null || client.getExpiryTime().before(now)) {
            throw new IllegalArgumentException("客户端令牌已过期");
        }

        // 从 token 中提取 orgId（若不可解析则返回 null）
        Long orgId = TokenUtils.extractOrgIdFromToken(token);
        log.info("客户端令牌验证通过，客户端：{}，机构ID：{}", client.getClientName(), orgId);

        return client;
    }

    /**
     * 获取测试用的客户端令牌，并返回一个可用的Token用于做测试
     * @param orgId
     * @return
     */
    @Override
    public ApiClientEntity getTokenForTest(Long orgId) {

        LambdaQueryWrapper<ApiClientEntity> query = new LambdaQueryWrapper<ApiClientEntity>()
                .eq(ApiClientEntity::getOrgId , orgId) ;

        return getOne(query);
    }

}
