package com.lawu.authorization.manager;

import com.lawu.authorization.constants.TokenClearType;

/**
 * @author Leach
 * @date 2017/3/19
 */
public interface TokenCacheService {

    void setTokenOneToOne(String account, String token, Integer expireSeconds);

    void setTokenOneToMany(String account, String token, Integer expireSeconds);

    String getAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser);

    void delRelationshipByAccount(String account, Integer expireSeconds, TokenClearType tokenClearType);

    void delRelationshipByToken(String token, Boolean singleTokenWithUser, Integer expireSeconds);

    TokenClearType getTokenClearType(String token);

}
