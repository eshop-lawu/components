package com.lawu.autotest.tool.result;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author meishuquan
 * @date 2017/10/25
 */
public class CheckResult {

    private Integer responseCode;

    private String responseMsg;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("responseCode", responseCode)
                .append("responseMsg", responseMsg)
                .toString();
    }
}
