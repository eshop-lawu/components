package com.lawu.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author zhangyong
 * @date 2017/4/21.
 */
public class UserUtil {
    /**
     * 获取用户account
     * @return
     */
    public static String getCurrentUserAccount() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipals() == null) {
            return "";
        }
        return subject.getPrincipals().toString();
    }
}
