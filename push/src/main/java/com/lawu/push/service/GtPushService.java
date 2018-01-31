package com.lawu.push.service;

/**
 * @author zhangyong
 * @date 2018/1/31.
 */
public interface GtPushService {


    /**
     * 推送单个用户
     *
     * @param cid      个推CID
     * @param title    推送标题
     * @param contents 推送内容 json格式
     * @return OK 成功
     */
    String pushMessageToSingle(String cid, String title, String contents);

    /**
     * 推送所有用户
     *
     * @param title    推送标题
     * @param contents 推送内容 json格式
     * @return OK 成功
     */
    String pushMessageToAll(String title, String contents);

}
