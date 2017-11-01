package com.lawu.autotest.client;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.lawu.autotest.client.config.api.HttpRequestConfig;
import com.lawu.autotest.client.util.AnnotationScanUtil;

/**
 * @author Leach
 * @date 2017/10/23
 */
@RestController
@RequestMapping(value = "autoTest/")
public class AutoTestingController {

    /**
     * 获取扫描的接口数据
     *
     * @param packageName
     * @param requestIpAddress
     * @return
     */
    @RequestMapping(value = "scanInterfaceInfo", method = RequestMethod.GET)
    public String scanInterfaceInfo(@RequestParam String packageName, @RequestParam String requestIpAddress) {
        List<HttpRequestConfig> requestConfigs = AnnotationScanUtil.getScanInterfaceInfo(packageName, requestIpAddress);
        if (requestConfigs != null && !requestConfigs.isEmpty()) {
            return JSONArray.toJSONString(requestConfigs);
        }
        return "";
    }
}
