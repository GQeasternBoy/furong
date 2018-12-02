package com.xueqiu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 郭根权 on 2018/12/1.
 * 认证授权控制器
 */
@Controller
public class AuthAccessController {

    @RequestMapping("/")
    public String root(){
        return "redirect:index";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex(){
        return "user/index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "login";
    }

    @RequestMapping("/401")
    public String accessDenied(){
        return "401";
    }
}
