package com.lawu.mybatis.plugin;

import java.lang.reflect.Field;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

/**
 * 修正Mybatis每次重新generator，mapper.xml文件都不断追加的问题而用的插件
 * 
 * @author jiangxinjun
 * @createDate 2018年1月9日
 * @updateDate 2018年1月9日
 */
public class OverIsMergeablePlugin extends PluginAdapter {
    
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}