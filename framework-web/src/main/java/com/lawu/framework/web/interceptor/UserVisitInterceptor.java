package com.lawu.framework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lawu.authorization.util.UserUtil;
import com.lawu.framework.web.constants.VisitConstants;
import com.lawu.framework.web.event.UserVisitEventPublish;
import com.lawu.framework.web.exception.HeaderParamException;

/**
 * 用户访问拦截器
 *
 * @author Leach
 * @date 2016/6/30
 */
public class UserVisitInterceptor extends HandlerInterceptorAdapter {

    @Autowired(required = false)
    private UserVisitEventPublish userVisitEventPublish;

    /**
     * 是否验证头部信息参数
     */
    private Boolean validate = true;
    
    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String imei = request.getHeader(VisitConstants.HEADER_IMEI);
        String platform = request.getHeader(VisitConstants.HEADER_PLATFORM);
        String platformVer = request.getHeader(VisitConstants.HEADER_PLATFORM_VERSION);
        String appVer = request.getHeader(VisitConstants.HEADER_APP_VERSION);
        String cityId = request.getHeader(VisitConstants.HEADER_LOCATION_PATH);
        String channel = request.getHeader(VisitConstants.HEADER_CHANNEL);
        if (getValidate() && (StringUtils.isEmpty(platform) || StringUtils.isEmpty(platformVer) || StringUtils.isEmpty(appVer) || StringUtils.isEmpty(channel))) {
            throw new HeaderParamException();
        }

        // 将header中的值存放到request中
        request.setAttribute(VisitConstants.REQUEST_IMEI, imei);
        request.setAttribute(VisitConstants.REQUEST_PLATFORM, platform);
        request.setAttribute(VisitConstants.REQUEST_PLATFORM_VERSION, platformVer);
        request.setAttribute(VisitConstants.REQUEST_APP_VERSION, appVer);
        request.setAttribute(VisitConstants.REQUEST_LOCATION_PATH, cityId);
        request.setAttribute(VisitConstants.REQUEST_CHANNEL, channel);

        String currentUserNum = UserUtil.getCurrentUserNum(request);
        Long userId = UserUtil.getCurrentUserId(request);
        if (StringUtils.isNotBlank(currentUserNum)) {
            if (userVisitEventPublish != null) {
                userVisitEventPublish.publishUserVisitEvent(currentUserNum, userId);
            }
        }
        return true;
    }
}
