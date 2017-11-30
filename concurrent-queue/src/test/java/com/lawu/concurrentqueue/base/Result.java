package com.lawu.concurrentqueue.base;

/**
 * @author Leach
 * @date 2017/11/29
 */
public class Result<T> {

    private int ret;

    private String msg;

    private String debug;

    private T model;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
