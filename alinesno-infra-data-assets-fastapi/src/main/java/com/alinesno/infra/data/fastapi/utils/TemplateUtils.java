package com.alinesno.infra.data.fastapi.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

public class TemplateUtils {
    private static final Configuration CFG;
    static {
        CFG = new Configuration(Configuration.VERSION_2_3_31);
        CFG.setDefaultEncoding("UTF-8");
    }

    public static String process(String templateContent, String templateName, Map<String, Object> variables) {
        try {
            StringTemplateLoader loader = new StringTemplateLoader();
            loader.putTemplate(templateName, templateContent);
            CFG.setTemplateLoader(loader);
            Template t = CFG.getTemplate(templateName);
            StringWriter out = new StringWriter();
            t.process(variables, out);
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("Template processing error: " + e.getMessage(), e);
        }
    }
}