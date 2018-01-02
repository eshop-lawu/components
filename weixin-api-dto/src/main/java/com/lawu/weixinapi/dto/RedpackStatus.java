package com.lawu.weixinapi.dto;

/**
 * @author Leach
 * @date 2018/1/2
 */
public enum RedpackStatus {
    SENDING, // 发放中
    SENT, // 已发放待领取
    FAILED, // 发放失败
    RECEIVED, // 已领取
    RFUND_ING, // 退款中
    REFUND, // 已退款
}
