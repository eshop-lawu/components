package com.lawu.autotest.client.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.lawu.autotest.client.AutoTesting;
import com.lawu.autotest.client.config.api.HttpHeaderConfig;
import com.lawu.autotest.client.config.api.HttpParamConfig;
import com.lawu.autotest.client.config.api.HttpRequestConfig;

/**
 * @author meishuquan
 * @date 2017/10/25
 */
public class AnnotationScanUtil {

    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private AnnotationScanUtil() {
    }

    /**
     * 获取扫描的接口信息
     *
     * @param packageName
     * @param requestIpAddress
     * @return
     */
    public static List<HttpRequestConfig> getScanInterfaceInfo(String packageName, String requestIpAddress) {
        List<HttpRequestConfig> requestConfigs = new ArrayList<>();

        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(RestController.class);

        RequestMapping requestMapping;
        AutoTesting autoTesting;
        String basePath = "";
        String requestPath;
        String requestMethod;
        List<HttpParamConfig> paramConfigs;

        for (Class classes : classesList) {
            //获取类的请求路径
            Annotation[] annotations = classes.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequestMapping) {
                    String[] pathArr = ((RequestMapping) annotation).value();
                    basePath = StringUtils.join(pathArr);
                    if (StringUtils.isNotEmpty(basePath) && !basePath.endsWith("/")) {
                        basePath += "/";
                    }
                }
            }

            //获取该类下面的所有方法
            Method[] methods = classes.getDeclaredMethods();
            for (Method method : methods) {
                //获取添加了AutoTesting注解的方法
                autoTesting = method.getAnnotation(AutoTesting.class);
                if (autoTesting != null) {
                    //获取方法的请求路径
                    requestMapping = method.getAnnotation(RequestMapping.class);
                    String[] pathArr = requestMapping.value();
                    requestPath = StringUtils.join(pathArr).replaceAll("\\{[^}]*\\}", "100");

                    //获取方法的请求方式
                    RequestMethod[] methodArr = requestMapping.method();
                    requestMethod = StringUtils.join(methodArr);

                    //获取方法的请求参数
                    String[] parameterNameArr = parameterNameDiscoverer.getParameterNames(method);
                    Parameter[] parameters = method.getParameters();
                    paramConfigs = new ArrayList<>();
                    for (int i = 0; i < parameters.length; i++) {
                        paramConfigs.addAll(getRequestParam(parameterNameArr[i], parameters[i]));
                    }

                    HttpRequestConfig requestConfig = new HttpRequestConfig();
                    requestConfig.setUrl(requestIpAddress + basePath + requestPath);
                    requestConfig.setMethod(requestMethod);
                    requestConfig.setHeaders(getHeaderParams());
                    requestConfig.setParams(paramConfigs);
                    requestConfigs.add(requestConfig);
                }
            }
        }
        return requestConfigs;
    }

    /**
     * 组装请求参数
     *
     * @param parameterName
     * @param parameter
     * @return
     */
    private static List<HttpParamConfig> getRequestParam(String parameterName, Parameter parameter) {
        List<HttpParamConfig> paramList = new ArrayList<>();
        HttpParamConfig paramConfig = new HttpParamConfig();
        paramConfig.setName(parameterName);
        boolean isDefaultObject = isDefaultObject(parameter.getType(), paramConfig, parameter.getAnnotations());
        if (isDefaultObject) {
            paramList.add(paramConfig);
        } else {
            paramConfig.setName(null);
            List<HttpParamConfig> paramConfigs = getObjectAttribute(parameter.getType());
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof ModelAttribute) {
                    paramList.addAll(paramConfigs);
                } else if (annotation instanceof RequestBody) {
                    StringBuilder sb = new StringBuilder("{");
                    for (HttpParamConfig paramConfig1 : paramConfigs) {
                        sb.append("\"");
                        sb.append(paramConfig1.getName());
                        sb.append("\"");
                        sb.append(":");
                        sb.append("\"");
                        sb.append(paramConfig1.getValue());
                        sb.append("\"");
                        sb.append(",");
                    }
                    String jsonStr = sb.substring(0, sb.length() - 1) + "}";
                    paramConfig.setValue(jsonStr);
                    paramList.add(paramConfig);
                }
            }
        }
        return paramList;
    }

    /**
     * 是否是Java内置对象
     *
     * @param clazz
     * @param paramConfig
     * @param annotations
     * @return
     */
    private static Boolean isDefaultObject(Class clazz, HttpParamConfig paramConfig, Annotation[] annotations) {
        if (clazz.isEnum()) {
            Field[] fields = clazz.getDeclaredFields();
            paramConfig.setValue(fields[0].getName());
            return true;
        }
        if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
            paramConfig.setValue("1");
            return true;
        }
        if (clazz.equals(Short.class) || clazz.equals(short.class)) {
            paramConfig.setValue("1");
            return true;
        }
        if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            paramConfig.setValue("100");
            return true;
        }
        if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            paramConfig.setValue("100");
            return true;
        }
        if (clazz.equals(Float.class) || clazz.equals(float.class)) {
            paramConfig.setValue("100.0");
            return true;
        }
        if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            paramConfig.setValue("100.0");
            return true;
        }
        if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            paramConfig.setValue("false");
            return true;
        }
        if (clazz.equals(Character.class) || clazz.equals(char.class)) {
            paramConfig.setValue("100");
            return true;
        }
        if (clazz.equals(String.class)) {
            paramConfig.setValue("100");
            return true;
        }
        if (clazz.equals(Date.class)) {
            paramConfig.setValue("2010-01-01");
            return true;
        }
        if (clazz.equals(BigDecimal.class)) {
            paramConfig.setValue("100");
            return true;
        }
        if (clazz.equals(List.class)) {
            if (annotations != null) {
                paramConfig.setValue("100");

                for (Annotation annotation : annotations) {
                    if (annotation instanceof RequestBody) {
                        List<String> list = new ArrayList<>();
                        list.add("100");
                        String jsonStr = JSONArray.toJSONString(list);
                        paramConfig.setName(null);
                        paramConfig.setValue(jsonStr);
                    }
                }
            }
            return true;
        }

        return false;
    }

    /**
     * 自定义对象属性
     *
     * @param clazz
     * @return
     */
    private static List<HttpParamConfig> getObjectAttribute(Class clazz) {
        List<HttpParamConfig> paramConfigs = new ArrayList<>();
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clazz.getDeclaredFields();
        try {
            HttpParamConfig paramConfig;
            for (Field field : fields) {
                paramConfig = new HttpParamConfig();
                paramConfig.setName(field.getName());
                isDefaultObject(Class.forName(field.getGenericType().getTypeName()), paramConfig, null);
                paramConfigs.add(paramConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramConfigs;
    }

    /**
     * 请求头部参数
     *
     * @return
     */
    private static List<HttpHeaderConfig> getHeaderParams() {
        List<HttpHeaderConfig> headerConfigs = new ArrayList<>();
        HttpHeaderConfig headerConfig;

        headerConfig = new HttpHeaderConfig();
        headerConfig.setName("platform");
        headerConfig.setValue("5");
        headerConfigs.add(headerConfig);

        headerConfig = new HttpHeaderConfig();
        headerConfig.setName("platformVersion");
        headerConfig.setValue("java8");
        headerConfigs.add(headerConfig);

        headerConfig = new HttpHeaderConfig();
        headerConfig.setName("appVersion");
        headerConfig.setValue("1.0");
        headerConfigs.add(headerConfig);

        headerConfig = new HttpHeaderConfig();
        headerConfig.setName("locationPath");
        headerConfig.setValue("0");
        headerConfigs.add(headerConfig);

        headerConfig = new HttpHeaderConfig();
        headerConfig.setName("channel");
        headerConfig.setValue("dev");
        headerConfigs.add(headerConfig);

        return headerConfigs;
    }

}
