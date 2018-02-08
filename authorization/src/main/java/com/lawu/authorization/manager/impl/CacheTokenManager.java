package com.lawu.authorization.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.authorization.constants.TokenClearType;
import com.lawu.authorization.manager.TokenCacheService;

/**
 * 使用缓存存储Token
 * @author ScienJus
 * @author Leach
 * @date 2015/10/26.
 */
public class CacheTokenManager extends AbstractTokenManager {

    @Autowired
    private TokenCacheService tokenCacheService;

    @Override
    protected void delSingleRelationshipByKey(String account, TokenClearType tokenClearType) {
        tokenCacheService.delRelationshipByAccount(account, tokenExpireSeconds, tokenClearType);
    }

    @Override
    public void delRelationshipByToken(String token) {
        tokenCacheService.delRelationshipByToken(token, singleTokenWithUser, tokenExpireSeconds);
    }

    @Override
    public TokenClearType getTokenClearType(String token) {
        return tokenCacheService.getTokenClearType(token);
    }

    @Override
    protected void createSingleRelationship(String account, String token) {
        tokenCacheService.setTokenOneToOne(account, token, tokenExpireSeconds);
    }

    @Override
    protected void createMultipleRelationship(String account, String token) {

        tokenCacheService.setTokenOneToMany(account, token, tokenExpireSeconds);
    }

    @Override
    protected String getAccountByToken(String token, boolean flushExpireAfterOperation) {
        return tokenCacheService.getAccount(token, flushExpireAfterOperation, tokenExpireSeconds, singleTokenWithUser);
    }

}
