package com.xueqiu.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author:ggq
 * @Date:2018/12/3
 * @Description:安全配置
 */

@ConfigurationProperties(prefix = "securityConfig")
public class SecuritySettings {

        @Getter
        private String logoutSuccessUrl = "/logout";//登出成功页

        @Getter
        private String permitAll = "/api";//允许访问

        @Getter
        private String deniedPage = "/deny";//拒绝访问连接

        @Getter
        private String urlRoles;//链接地址和角色权限配置

}
