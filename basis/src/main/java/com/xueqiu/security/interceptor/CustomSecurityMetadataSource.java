package com.xueqiu.security.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * @Author:ggq
 * @Date:2018/11/30
 * @Description:权限配置资源管理器
 */
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

    private static final Logger logger = LoggerFactory.getLogger(CustomSecurityMetadataSource.class);

    private Map<String,Collection<ConfigAttribute>> resourceMap = null;

    private PathMatcher pathMatcher = new AntPathMatcher();

    private String urlRoles;

    public CustomSecurityMetadataSource(String urlRoles) {
        super();
        this.urlRoles = urlRoles;
        resourceMap =loadResourceMathAuthority();
    }

    private Map<String,Collection<ConfigAttribute>> loadResourceMathAuthority(){
        Map<String,Collection<ConfigAttribute>> map = new HashMap<String,Collection<ConfigAttribute>>();

        if (urlRoles != null && !urlRoles.isEmpty()){
            String[] resouces = urlRoles.split(";");
            for (String resource : resouces){
                String[] urls = resource.split("=");
                String[] roles = urls[1].split(",");

                Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
                for (String role : roles){
                    ConfigAttribute config = new SecurityConfig(role.trim());
                    list.add(config);
                }
            }
        }else {
            logger.error("'securityconfig.urlroles' must be set");
        }
        return map;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();
        logger.debug("request url is "+url);

        if (resourceMap == null){
            loadResourceMathAuthority();
        }

        Iterator<String> iterator = resourceMap.keySet().iterator();
        while (iterator.hasNext()){
            String resURL = iterator.next();
            if(pathMatcher.match(resURL,url)){
                return resourceMap.get(resURL);
            }
        }
        return resourceMap.get(url);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
