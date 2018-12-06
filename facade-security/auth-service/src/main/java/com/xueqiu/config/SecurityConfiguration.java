package com.xueqiu.config;

import com.xueqiu.interceptor.CustomAccessDecisionManager;
import com.xueqiu.interceptor.CustomFilterSecurityInterceptor;
import com.xueqiu.interceptor.CustomSecurityMetadataSource;
import com.xueqiu.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * @Author:ggq
 * @Date:2018/12/3
 * @Description:认证服务器安全策略配置
 */
@Configuration
@Order(Integer.MIN_VALUE)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private SecuritySettings settings;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").permitAll()//登录地址允许所有
//                .loginProcessingUrl("/authservice/login/form")
                .failureUrl("/login-error").and()//登录失败页
                .authorizeRequests().antMatchers("/images/**","/checkcode","/scripts","/index/**","/css/**").permitAll() //不需要认证就可以访问
//                .antMatchers(settings.getPermitAll().split(",")).permitAll()
                .anyRequest().authenticated()
//                .and().csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().exceptionHandling().accessDeniedPage("/deny")
                .and().rememberMe().tokenValiditySeconds(15*60).tokenRepository(tokenRepository());
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        //remember me
        auth.eraseCredentials(false);
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public CustomSecurityMetadataSource customSecurityMetadataSource(){
//        return new CustomSecurityMetadataSource(settings.getUrlRoles());
//    }
//
//    @Bean
//    public CustomAccessDecisionManager customAccessDecisionManager(){
//        return new CustomAccessDecisionManager();
//    }
//    @Bean
//    public CustomFilterSecurityInterceptor customFilter() throws Exception {
//        CustomFilterSecurityInterceptor customFilter = new CustomFilterSecurityInterceptor();
//        customFilter.setSecurityMetadataSource(customSecurityMetadataSource());
//        customFilter.setAuthenticationManager(authenticationManager());
//        return customFilter;
//    }
}
