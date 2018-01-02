package com.lawu.weixinapi.param;

/**
 * @author Leach
 * @date 2018/1/2
 */
public class RedpackParam {

    private String openid;

    /**
     * 商户订单号
     */
    private String mchBillno;

    /**
     * 商户名称
     */
    private String sendName;
    /**
     * 付款金额，单位分
     */
    private int totalAmount;
    /**
     * 红包发放总人数
     */
    private int totalNum;
    /**
     * 红包祝福语
     */
    private String wishing;
    /**
     * 活动名称
     */
    private String actName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 场景id
     */
    private String sceneId;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMchBillno() {
        return mchBillno;
    }

    public void setMchBillno(String mchBillno) {
        this.mchBillno = mchBillno;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }
}
