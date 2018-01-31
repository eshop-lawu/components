package com.lawu.push.constants;

/**
 * @author zhangyong
 * @date 2018/1/31.
 */
public enum UserTypeEnum {

    /**
     * 会员
     */
    MEMBER("M"),

    /**
     * 商家
     */
    MERCHANT("B");


    private String prefix;

    UserTypeEnum(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}