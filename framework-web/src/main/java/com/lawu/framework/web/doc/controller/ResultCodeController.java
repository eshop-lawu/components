package com.lawu.framework.web.doc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lawu.framework.web.BaseResultCode;
import com.lawu.framework.web.doc.dto.ResultCodeDTO;

/**
 * 业务返回码
 *
 * @author yangqh
 * @date 2018/01/10
 */
@Controller
public class ResultCodeController {

    @ResponseBody
    @RequestMapping(value = "resultCode")
    public List<ResultCodeDTO> api() {
        List<ResultCodeDTO> rtn = new ArrayList<>();
        Map<Integer, String> messageMap = BaseResultCode.messageMap;
        for (Integer key : messageMap.keySet()) {
            String value = messageMap.get(key);
            ResultCodeDTO dto = new ResultCodeDTO();
            dto.setCode(key.toString());
            dto.setDesc(value);
            rtn.add(dto);
        }
        return rtn;
    }



}
