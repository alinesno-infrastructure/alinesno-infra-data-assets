package com.alinesno.infra.data.assets.collector.aspect;

import java.lang.annotation.*;

/**
 * 推送数据接收权限
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PushAccess {
}
