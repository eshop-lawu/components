package com.lawu.weixinapi.controller;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.weixinapi.base.Result;
import com.lawu.weixinapi.converter.AccessTokenConverter;
import com.lawu.weixinapi.converter.WeixinUserConverter;
import com.lawu.weixinapi.dto.AccessTokenDTO;
import com.lawu.weixinapi.dto.WeixinUserDTO;
import com.lawu.weixinapi.wx.WechatMpProperties;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Leach
 * @date 2017/12/29
 */
@RestController
@RequestMapping(value = "/auth/")
public class AuthController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatMpProperties properties;

    @RequestMapping(value = "redirect")
    public Object redirect(String appKey, String redirectURL, String scope, @RequestParam(required = false) String state) {
        try {
            String wxRedirectURI = properties.getRedirectDomain() + "/wx/redirect?url=" + URLEncoder.encode(redirectURL, "utf-8");
            String authorizationUrl = wxMpService.oauth2buildAuthorizationUrl(wxRedirectURI, scope, state);
            return createMav("redirect:" + authorizationUrl, null);
        } catch (Exception e) {

            return successGet(e.getMessage());
        }
    }

    @RequestMapping(value = "getAccessToken", method = RequestMethod.GET)
    public Result<AccessTokenDTO> getAccessToken(String appKey, String code) {
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

            return successGet(AccessTokenConverter.convert(wxMpOAuth2AccessToken));
        } catch (WxErrorException e) {
            logger.error("Fail to get accessToken by code", e);
            return failServerError(e.getMessage());
        }
    }

    @RequestMapping(value = "getAuthUserInfo", method = RequestMethod.GET)
    public Result<WeixinUserDTO> getAuthUserInfo(String appKey, String code) {
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");

            return successGet(WeixinUserConverter.convert(wxMpUser));
        } catch (WxErrorException e) {
            logger.error("Fail to get user info by code", e);
            return failServerError(e.getMessage());
        }
    }

    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public Result<WeixinUserDTO> getUserInfo(String appKey, String code) {
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMpOAuth2AccessToken.getOpenId(),"zh_CN");

            return successGet(WeixinUserConverter.convert(wxMpUser));
        } catch (WxErrorException e) {
            logger.error("Fail to get user info by openid", e);
            return failServerError(e.getMessage());
        }
    }
}
