package com.lawu.push.service;

/**
 * @author zhangyong
 * @date 2018/1/31.
 */
public interface GtPushService {


    /**
     * @param cid      个推CID
     * @param title    推送标题
     * @param contents 推送内容 json格式
     * @param prefix   用户前缀 M 用户 B 商家
     * @return OK 成功
     */
    String pushMessageToSingle(String cid, String title, String contents, String prefix);

    /**
     * @param title    推送标题
     * @param contents 推送内容 json格式
     * @param prefix   用户前缀 M 用户 B 商家
     * @return OK 成功
     */
    String pushMessageToAll(String title, String contents, String prefix);

}
