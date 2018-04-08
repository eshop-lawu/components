package com.lawu.framework.web.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 审核API的注解,只有添加了这个注解的方法才会在API文档上面显示
 * 
 * 
 * 
 * @author Sunny
 * @date 2017/3/31
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Audit {
	
	String date();
	
	String reviewer();
}
