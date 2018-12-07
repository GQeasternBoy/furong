package com.xueqiu.config;

import com.xueqiu.interceptor.CustomAccessDecisionManager;
import com.xueqiu.interceptor.CustomFilterSecurityInterceptor;
import com.xueqiu.interceptor.CustomSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:ggq
 * @Date:2018/12/4
 * @Description:客户端安全策略配置
 */
@Configuration
//@EnableConfigurationProperties(SecuritySettings.class)
public class SecurityConfiguration /*extends WebSecurityConfigurerAdapter*/{

//    @Autowired
//    private SecuritySettings settings;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and().csrf().disable();
//    }
//
//    @Bean
//    public CsrfSecurityRequestMatcher csrfSecurityRequestMatcher(){
//        CsrfSecurityRequestMatcher csrfSecurityRequestMatcher = new CsrfSecurityRequestMatcher();
//        List<String> list = new ArrayList<>();
//        list.add("/rest/");
//        csrfSecurityRequestMatcher.setExecludeUrls(list);
//        return csrfSecurityRequestMatcher;
//    }


//    @Bean
//    public CustomSecurityMetadataSource customSecurityMetadataSource(){
//        return new CustomSecurityMetadataSource(settings.getUrlRoles());
//    }

//    @Bean
//    public CustomAccessDecisionManager customAccessDecisionManager(){
//        return new CustomAccessDecisionManager();
//    }
//
//    @Bean
//    public CustomFilterSecurityInterceptor customFilter() throws Exception {
//        CustomFilterSecurityInterceptor customFilter = new CustomFilterSecurityInterceptor();
//        customFilter.setSecurityMetadataSource(customSecurityMetadataSource());
//        customFilter.setAuthenticationManager(authenticationManager());
//        return customFilter;
//    }
//
    /*@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/

}
