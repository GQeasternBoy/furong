package com.xueqiu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * @Author:ggq
 * @Date:2018/12/5
 * @Description:
 */
@SpringBootApplication
@EnableOAuth2Sso
public class SSOClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(SSOClientApplication.class,args);
    }
}
