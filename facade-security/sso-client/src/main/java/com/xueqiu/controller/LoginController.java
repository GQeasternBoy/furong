package com.xueqiu.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:ggq
 * @Date:2018/12/4
 * @Description:用户登录控制器
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "redirect:/#/";
    }
}
