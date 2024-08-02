package com.alinesno.infra.data.assets.sample;

/**
 * 初始化示例应用数据
 */
public interface ISimpleService {

    /**
     * 示例资产分类
     */
    void catalog();

    /**
     * 示例资产数据
     */
    void data();

    /**
     * 示例接入数据
     */
    void access();

    /**
     * 示例数据安全策略
     */
    void dataSecurity();

    /**
     * 示例数据生命周期
     */
    void dataLifeCycle();
}
