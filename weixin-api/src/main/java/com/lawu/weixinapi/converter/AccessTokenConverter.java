package com.lawu.weixinapi.converter;

import com.lawu.weixinapi.dto.AccessTokenDTO;

import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

/**
 * @author Leach
 * @date 2018/1/1
 */
public class AccessTokenConverter {

    public static AccessTokenDTO convert(WxMpOAuth2AccessToken wxMpOAuth2AccessToken) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        if (wxMpOAuth2AccessToken != null) {
            accessTokenDTO.setAccessToken(wxMpOAuth2AccessToken.getAccessToken());
            accessTokenDTO.setExpiresIn(wxMpOAuth2AccessToken.getExpiresIn());
            accessTokenDTO.setRefreshToken(wxMpOAuth2AccessToken.getRefreshToken());
            accessTokenDTO.setOpenId(wxMpOAuth2AccessToken.getOpenId());
            accessTokenDTO.setScope(wxMpOAuth2AccessToken.getScope());
        }
        return accessTokenDTO;
    }
}
