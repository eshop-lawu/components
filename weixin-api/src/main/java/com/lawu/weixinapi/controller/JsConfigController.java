package com.lawu.weixinapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.weixinapi.base.Result;
import com.lawu.weixinapi.base.ResultCode;
import com.lawu.weixinapi.dto.JsConfigDTO;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

/**
 * @author Leach
 * @date 2018/1/30
 */
@RestController()
@RequestMapping(value = "jsconfig/")
public class JsConfigController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(JsConfigController.class);

    @Autowired
    private WxMpService wxMpService;

    @CrossOrigin({"http://test.lovelawu.com:12680", "http://pre.lovelawu.com"})
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Result<JsConfigDTO> get(String url) {

        if (StringUtils.isEmpty(url)) {
            return successGet(ResultCode.FAIL, "url不能为空 ");
        }

        WxJsapiSignature wxJsapiSignature;
        try {
            wxJsapiSignature = wxMpService.createJsapiSignature(url);
        } catch (WxErrorException e) {
            logger.error("Fail to create jsapi signature", e);
            return successGet(ResultCode.FAIL, "js授权失败: " + e.getMessage());
        }

        JsConfigDTO jsConfig = new JsConfigDTO();
        jsConfig.setAppId(wxJsapiSignature.getAppId());
        jsConfig.setTimestamp(wxJsapiSignature.getTimestamp());
        jsConfig.setNonceStr(wxJsapiSignature.getNonceStr());
        jsConfig.setSignature(wxJsapiSignature.getSignature());

        return successGet(jsConfig);

    }
}
