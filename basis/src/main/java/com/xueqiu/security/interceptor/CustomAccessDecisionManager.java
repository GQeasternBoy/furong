package com.xueqiu.security.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * @Author:ggq
 * @Date:2018/11/30
 * @Description:权限管理决断器
 */
public class CustomAccessDecisionManager implements AccessDecisionManager{

    private Logger logger = LoggerFactory.getLogger(CustomAccessDecisionManager.class);

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null){
            return;
        }

        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()){
            ConfigAttribute configAttribute = iterator.next();
            String needRole = configAttribute.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()){
                if (needRole.equals(ga.getAuthority())){
                    return;
                }
            }
            logger.info("need role is "+needRole);
        }
        throw new AccessDeniedException("Cannot Access");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
