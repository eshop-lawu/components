package com.lawu.framework.web.interceptor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lawu.framework.web.doc.annotation.Audit;
import com.lawu.framework.web.exception.ParamSignException;
import com.lawu.framework.web.util.SignUtil;

/**
 * @author Leach
 * @date 2018/3/21
 */
public class ParamSignInterceptor extends HandlerInterceptorAdapter {

    private Map<String, String> appSecretKeys;

    public void setAppSecretKeys(Map<String, String> appSecretKeys) {
        this.appSecretKeys = appSecretKeys;
    }

    public Map<String, String> getAppSecretKeys() {
        return appSecretKeys;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(Audit.class) != null) {

            Map<String, String[]> parameterMap = request.getParameterMap();
            SortedMap<String, String> sortedMap = new TreeMap<>();
            for (Map.Entry<String, String[]> param:
                 parameterMap.entrySet()) {
                if (param.getValue() != null && param.getValue().length > 0) {
                    sortedMap.put(param.getKey(), param.getValue()[0]);
                }
            }

            if (!sortedMap.isEmpty()) {
                String appKey = sortedMap.get("appKey");
                if (appKey != null ) {
                    if (!SignUtil.checkSign(sortedMap, appSecretKeys.get(appKey))) {
                        throw new ParamSignException();
                    }
                }
            }
            return true;
        }
        return true;
    }
}
