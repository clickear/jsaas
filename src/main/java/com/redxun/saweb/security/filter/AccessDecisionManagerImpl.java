/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redxun.saweb.security.filter;

import com.redxun.sys.core.entity.SysAccount;
import java.util.Collection;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class AccessDecisionManagerImpl implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if (authentication == null) {
            throw new AccessDeniedException("没有登录系统");
        }
        Collection<GrantedAuthority> auths = (Collection<GrantedAuthority>) authentication.getAuthorities();

        Object principal = authentication.getPrincipal();
        if (principal == null) {
            throw new AccessDeniedException("登录对象为空");
        }
        
        SysAccount user = (SysAccount) principal;
        if(user!=null){
            return;
        }
        // 获取当前用户的角色。
        throw new AccessDeniedException("对不起,你没有访问该页面的权限!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
