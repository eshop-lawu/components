package com.lawu.shiro;

import com.lawu.shiro.model.ShiroUser;

/**
 * @author Leach
 * @date 2017/4/18
 */
public interface AuthService {

    ShiroUser find(String account);

    ShiroUser find(String account, String password);
}
