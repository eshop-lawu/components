package com.lawu.authorization.util;

import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;

import com.lawu.authorization.interceptor.AuthorizationInterceptor;
import com.lawu.authorization.manager.impl.AbstractTokenManager;

/**
 * @author Leach
 * @date 2017/3/15
 */
public class UserUtil {

    private static Claims getClaimsJwsBody(String token) {
        if (token != null && token.length() > 0) {
            Jws<Claims> claimsJws = null;
            try {

                claimsJws = Jwts.parser().setSigningKey(AbstractTokenManager.TOKEN_KEY).parseClaimsJws(token);
                // OK, we can trust this JWT

            } catch (SignatureException | MalformedJwtException | ExpiredJwtException e) {
                // don't trust the JWT!
                return null;
            }
            if (claimsJws != null) {
                return claimsJws.getBody();
            }
        }
        return null;
    }

    /**
     * 获取当前登录用户ID
     *
     * @param request
     * @return
     */
    public static Long getCurrentUserId(HttpServletRequest request) {
        Object userId = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_USER_ID);
        if (userId == null) {
            return 0L;
        }

        return Long.valueOf(userId.toString());
    }

    /**
     * 根据token获取当前登录用户ID（仅供无法通过header传递token情况下使用）
     *
     * @param token
     * @return
     */
    public static String getCurrentUserIdByToken(String token) {
        Claims body = getClaimsJwsBody(token);
        return body == null ? null : body.getAudience();
    }

    /**
     * 获取当前登录用户编号
     *
     * @param request
     * @return
     */
    public static String getCurrentUserNum(HttpServletRequest request) {
        Object userNum = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_USER_NUM);
        return userNum == null ? null : userNum.toString();
    }

    /**
     * 根据token获取当前登录用户编号（仅供无法通过header传递token情况下使用）
     *
     * @param token
     * @return
     */
    public static String getCurrentUserNumByToken(String token) {
        Claims body = getClaimsJwsBody(token);
        return body == null ? null : body.getId();
    }

    /**
     * 获取当前登录用户账号
     *
     * @param request
     * @return
     */
    public static String getCurrentAccount(HttpServletRequest request) {
        Object account = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_ACCOUNT);
        return account == null ? null : account.toString();
    }

    /**
     * 根据token获取当前登录用户账号（仅供无法通过header传递token情况下使用）
     *
     * @param token
     * @return
     */
    public static String getCurrentAccountByToken(String token) {
        Claims body = getClaimsJwsBody(token);
        return body == null ? null : body.getSubject();
    }
}
