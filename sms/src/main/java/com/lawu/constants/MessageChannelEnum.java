package com.lawu.constants;

/**
 * @author zhangyong
 * @date 2017/12/12.
 */
public enum MessageChannelEnum {
    CHANNEL_ALIYUN((byte) 0x01, "阿里大鱼"),
    CHANNEL_UCPAAS((byte) 0x02, "云之讯");
    private Byte val;
    private String name;

    MessageChannelEnum(Byte val, String name) {
        this.val = val;
        this.name = name;
    }

    public static MessageChannelEnum getEnum(Byte val) {
        MessageChannelEnum[] values = MessageChannelEnum.values();
        for (MessageChannelEnum object : values) {
            if (object.getVal().byteValue() == val) {
                return object;
            }
        }
        return null;
    }

    public Byte getVal() {
        return val;
    }

    public String getName() {
        return name;
    }
}
