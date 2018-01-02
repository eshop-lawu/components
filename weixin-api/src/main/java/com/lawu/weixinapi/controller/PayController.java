package com.lawu.weixinapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.wxpay.bean.request.WxPaySendRedpackRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRedpackQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPaySendRedpackResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.lawu.weixinapi.base.Result;
import com.lawu.weixinapi.converter.RedpackConverter;
import com.lawu.weixinapi.dto.RedpackResultDTO;
import com.lawu.weixinapi.param.RedpackParam;

/**
 * @author Leach
 * @date 2018/1/2
 */
@RestController
@RequestMapping(value = "/pay/")
public class PayController extends BaseController {

    @Autowired
    private WxPayService wxPayService;

    /**
     * 发送微信红包给个人用户
     * <pre>
     * 文档详见:
     * 发送普通红包 https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_4&index=3
     *  接口地址：https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack
     * 发送裂变红包 https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_5&index=4
     *  接口地址：https://api.mch.weixin.qq.com/mmpaymkttransfers/sendgroupredpack
     * </pre>
     *
     * @param redpackParam 请求对象
     */
    @PostMapping("sendRedpack")
    public Result<RedpackResultDTO> sendRedpack(@RequestBody RedpackParam redpackParam) throws WxPayException {
        WxPaySendRedpackRequest wxPaySendRedpackRequest = RedpackConverter.convert(redpackParam);
        WxPaySendRedpackResult wxPaySendRedpackResult = wxPayService.sendRedpack(wxPaySendRedpackRequest);
        return successCreated(RedpackConverter.convert(wxPaySendRedpackResult));
    }

    /**
     * <pre>
     *   查询红包记录
     *   用于商户对已发放的红包进行查询红包的具体信息，可支持普通红包和裂变包。
     *   请求Url	https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo
     *   是否需要证书	是（证书及使用说明详见商户证书）
     *   请求方式	POST
     * </pre>
     *
     * @param mchBillNo 商户发放红包的商户订单号，比如10000098201411111234567890
     */
    @GetMapping("/queryRedpack/{mchBillNo}")
    public Result<RedpackResultDTO> queryRedpack(@PathVariable String mchBillNo) throws WxPayException {
        WxPayRedpackQueryResult wxPayRedpackQueryResult = wxPayService.queryRedpack(mchBillNo);
        return successGet(RedpackConverter.convert(wxPayRedpackQueryResult));
    }

}
