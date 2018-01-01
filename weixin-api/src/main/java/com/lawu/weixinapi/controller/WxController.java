package com.lawu.weixinapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Leach
 * @date 2017/12/29
 */
@RestController()
@RequestMapping(value = "wx/")
public class WxController {

    @RequestMapping(value = "redirect")
    public ModelAndView redirect(String url, String code, String state) {
        String urlWithCodeState = url + (url.indexOf('?') > -1 ? "&" : "?");
        urlWithCodeState += "code=" + code + "&state=" + state;
        return new ModelAndView("redirect:" + urlWithCodeState);
    }

}
