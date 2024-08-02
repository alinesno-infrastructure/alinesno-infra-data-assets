package com.alinesno.infra.data.fastapi.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据库连接校验结果
 */
@Data
public class CheckDbConnectResult implements Serializable {
	
    private String msg;
    private String code;
    private boolean accepted;

    public static CheckDbConnectResult accept(String code) {
        return new CheckDbConnectResult("数据库连接校验成功", code, true);
    }

    public static CheckDbConnectResult reject(String code) {
        return new CheckDbConnectResult("数据库连接校验失败", code, false);
    }


    public CheckDbConnectResult(String msg, String code, boolean accepted) {
        this.msg = msg;
        this.code = code;
        this.accepted = accepted;
    }

}
