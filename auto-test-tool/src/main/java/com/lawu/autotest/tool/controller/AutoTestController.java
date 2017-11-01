package com.lawu.autotest.tool.controller;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lawu.autotest.client.config.api.HttpRequestConfig;
import com.lawu.autotest.tool.handle.api.GetHttpRequestHandle;
import com.lawu.autotest.tool.handle.api.HttpRequestHandle;
import com.lawu.autotest.tool.handle.api.HttpRequestHandleFactory;
import com.lawu.autotest.tool.result.CheckResult;
import com.lawu.autotest.tool.result.ResultCode;
import com.lawu.autotest.tool.result.TestResult;

/**
 * @author meishuquan
 * @date 2017/10/25
 */
@RestController
public class AutoTestController {

    private Logger logger = LoggerFactory.getLogger(AutoTestController.class);

    @Value(value = "${auto.test.request.ip}")
    private String autoTestRequestIp;

    @Value(value = "${auto.test.server.name}")
    private String autoTestServerName;

    @Value(value = "${auto.test.base.package.name}")
    private String autoTestBasePackageName;

    @Autowired
    private GetHttpRequestHandle getHttpRequestHandle;

    @Autowired
    private HttpRequestHandleFactory httpRequestHandleFactory;

    /**
     * 获取扫描服务列表
     *
     * @return
     */
    @RequestMapping(value = "getScanServer", method = RequestMethod.GET)
    public List<String> getScanServer() {
        String[] serverNameArr = autoTestServerName.split(",");

        List<String> list = new ArrayList<>();
        for (String serverName : serverNameArr) {
            list.add(serverName);
        }
        return list;
    }

    /**
     * 扫描接口
     *
     * @param serverName
     * @return
     */
    @RequestMapping(value = "scanInterfaceInfo", method = RequestMethod.GET)
    public Map<String, Object> scanInterfaceInfo(String serverName) {
        Map<String, Object> map = new HashMap<>();
        String scanPackageName = autoTestBasePackageName + serverName;
        String fileName = serverName.replace(".", "_");

        String requestIpAddress = "";
        String[] requestIpArr = autoTestRequestIp.split(",");
        String[] serverNameArr = autoTestServerName.split(",");
        for (int i = 0; i < serverNameArr.length; i++) {
            if (serverNameArr[i].equals(serverName)) {
                requestIpAddress = requestIpArr[i];
                break;
            }
        }

        CheckResult checkResult = getHttpRequestHandle.getScanInterfaceInfo(scanPackageName, requestIpAddress);
        if (checkResult.getResponseCode() == null || checkResult.getResponseCode() != ResultCode.SC_OK) {
            map.put("success", false);
            map.put("msg", "扫描接口数据异常");
            return map;
        }
        if (StringUtils.isEmpty(checkResult.getResponseMsg())) {
            map.put("success", false);
            map.put("msg", "没有扫描到接口数据");
            return map;
        }

        String path = this.getClass().getResource("/").getPath();
        String filePath = path + fileName + ".json";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            byte bytes[] = checkResult.getResponseMsg().getBytes();
            out.write(bytes);
            out.flush();
            map.put("success", true);
        } catch (IOException e) {
            map.put("success", false);
            map.put("msg", "保存接口数据异常");
            logger.error("Write File IOException------{}", e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("Close FileOutputStream IOException------{}", e.getMessage());
                }
            }
        }
        return map;
    }

    /**
     * 测试接口
     *
     * @param serverName
     * @return
     */
    @RequestMapping(value = "autoTestInterface", method = RequestMethod.GET)
    public Map<String, Object> autoTestInterface(String serverName) {
        Map<String, Object> map = new HashMap<>();
        String fileName = serverName.replace(".", "_");
        Resource resource = new ClassPathResource(fileName + ".json");
        StringBuffer sb = new StringBuffer();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(resource.getFile()), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim() + "\r\n");
            }
        } catch (IOException e) {
            map.put("success", false);
            map.put("msg", "读取接口数据异常");
            logger.error("Read File IOException------{}", e.getMessage());
            return map;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("Close BufferedReader IOException------{}", e.getMessage());
                }
            }
        }

        if (StringUtils.isEmpty(sb.toString())) {
            map.put("success", false);
            map.put("msg", "没有读取到接口数据");
            return map;
        }

        List<TestResult> list = new ArrayList<>();
        List<TestResult> successList = new ArrayList<>();
        List<TestResult> failList = new ArrayList<>();
        TestResult testResult;
        List<HttpRequestConfig> requestConfigs = JSON.parseArray(sb.toString(), HttpRequestConfig.class);
        for (HttpRequestConfig requestConfig : requestConfigs) {
            HttpRequestHandle handle = httpRequestHandleFactory.getHandle(requestConfig.getMethod());
            CheckResult checkResult = handle.execute(requestConfig);

            testResult = new TestResult();
            testResult.setUrl(requestConfig.getUrl());
            testResult.setMethod(requestConfig.getMethod());
            if (checkResult.getResponseCode() != null && (checkResult.getResponseCode() == ResultCode.SC_OK || checkResult.getResponseCode() == ResultCode.SC_CREATED || checkResult.getResponseCode() == ResultCode.SC_ACCEPTED || checkResult.getResponseCode() == ResultCode.SC_NO_CONTENT)) {
                testResult.setResult("success");
                testResult.setMsg("");
                successList.add(testResult);
            } else {
                testResult.setResult("fail");
                testResult.setMsg(checkResult.getResponseMsg());
                failList.add(testResult);
            }
            list.add(testResult);
        }

        map.put("success", true);
        map.put("list", list);
        map.put("successList", successList);
        map.put("failList", failList);
        return map;
    }
}
