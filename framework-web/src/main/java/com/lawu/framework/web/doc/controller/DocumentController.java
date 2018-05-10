package com.lawu.framework.web.doc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.lawu.framework.web.BaseController;
import com.lawu.framework.web.doc.annotation.Audit;
import com.lawu.framework.web.doc.dto.ApiDocumentDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * API生成
 * 
 * @author Sunny
 * @date 2017/03/31
 */
@Controller
public class DocumentController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    /**
     * 显示审核通过接口列表
     * 
     * @param isAudit
     * @return
     * @author Sunny
     */
    @ResponseBody
    @RequestMapping(value = "document")
    public List<ApiDocumentDTO> api(@RequestParam Boolean isAudit) {
        return getVoList(isAudit);
    }

    /**
     * 返回接口VoList
     * 
     * @param isAudit
     *            是否审核
     * @return
     * @author Sunny
     */
    private List<ApiDocumentDTO> getVoList(Boolean isAudit) {
        List<ApiDocumentDTO> rtn = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            /*
             * 不从spring中获取注解。 用反射的方式获取注解
             */
            Api api = null;
            Audit audit = null;
            ApiOperation apiOperation = null;
            Deprecated deprecated = null;

            RequestMappingInfo info = null;
            HandlerMethod method = null;

            // 通过RequestMappingHandlerMapping获取接口的路径
            WebApplicationContext wc = getWebApplicationContext();
            RequestMappingHandlerMapping rmhp = wc.getBean(RequestMappingHandlerMapping.class);
            Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = rmhp.getHandlerMethods();

            for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {

                method = entry.getValue();
                apiOperation = method.getMethodAnnotation(ApiOperation.class);
                audit = method.getMethodAnnotation(Audit.class);
                deprecated = method.getMethodAnnotation(Deprecated.class);

                boolean isShow = ((audit != null && isAudit) || (!isAudit && audit == null)) && apiOperation != null;
                if (isShow) {
                    info = entry.getKey();
                    api = method.getBeanType().getAnnotation(Api.class);

                    // 组装VO对象
                    ApiDocumentDTO vo = new ApiDocumentDTO();
                    if (api != null && api.tags() != null) {
                        vo.setApiName(Arrays.toString(api.tags()));
                    } else {
                        // 如果Api注解为空,或者tags为空，设置ApiName为空串
                        vo.setApiName("");
                    }

                    if (audit != null) {
                        vo.setDate(df.parse(audit.date()));
                        vo.setReviewer(audit.reviewer());
                    }
                    vo.setPath(info.getPatternsCondition().toString());
                    if (apiOperation != null) {
                        vo.setName(apiOperation.value());
                        vo.setNotes(apiOperation.notes());
                        vo.setHttpMethod(apiOperation.httpMethod());
                    }
                    vo.setIsDeprecated(deprecated == null ? false : true);
                    rtn.add(vo);
                }
            }
        } catch (Exception e) {
            logger.error("查询接口数据异常", e);
        }

        // 按照日期和接口排序
        Collections.sort(rtn, (o1, o2) -> {
            // 先按照时间排序
            int compare = 0;
            if (o2.getDate() != null && o1.getDate() != null) {
                compare = o2.getDate().compareTo(o1.getDate());
            }

            // 如果时间相等的话再按照接口排序
            if (compare == 0) {
                return o2.getApiName().compareTo(o1.getApiName());
            }

            return compare;
        });

        return rtn;
    }

    private WebApplicationContext getWebApplicationContext() {
        return WebApplicationContextUtils
                .getRequiredWebApplicationContext(getRequest().getSession().getServletContext());
    }

}
