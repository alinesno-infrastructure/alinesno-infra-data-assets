package com.alinesno.infra.data.fastapi.plugins;

import com.alinesno.infra.data.fastapi.plugins.filter.FilterParent;
import com.alinesno.infra.data.fastapi.plugins.plugin.PluginParent;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 插件加载工具,在工程初始化时初始化插件和过滤器
 */
@Data
public class PluginLoader {

    // 插件集成类
    private static final Map<String , PluginParent> pluginMap = new HashMap<>() ;

    // 过滤器集成类
    private static final Map<String , FilterParent> filterMap = new HashMap<>() ;

}
