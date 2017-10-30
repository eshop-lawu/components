package com.lawu.autotest.tool.result;

/**
 * @author meishuquan
 * @date 2017/10/25
 */
public class TestResult {
    private String url;
    private String method;
    private String result;
    private String msg;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
