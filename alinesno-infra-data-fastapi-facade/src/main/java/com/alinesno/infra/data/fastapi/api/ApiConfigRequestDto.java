package com.alinesno.infra.data.fastapi.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * API配置请求数据传输对象
 * 用于封装API配置的相关信息，以便于在接口中传递和处理
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiConfigRequestDto {

    /**
     * 脚本内容
     * 描述API的执行脚本，用于定义API的行为
     */
    private String script;

    /**
     * API标识符
     * 唯一标识一个API，用于在系统中区分不同的API
     */
    private String apiId;

    /**
     * 类型
     * 描述API的类型，用于分类管理API
     */
    private String type;

    /**
     * 参数
     * API请求所需的参数，以字符串形式存储，具体结构和意义取决于API的实现
     */
    private String params;

}
