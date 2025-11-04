package com.alinesno.infra.data.fastapi.service;

import com.alinesno.infra.data.fastapi.entity.ApiConfigEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IApiHandleService {

    /**
     * 处理业务请求
     * @return
     */
    Object handle(ApiConfigEntity apiConfig, HttpServletRequest request, HttpServletResponse response);

}
