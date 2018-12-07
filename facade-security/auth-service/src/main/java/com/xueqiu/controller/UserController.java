package com.xueqiu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ggq
 * @Date:2018/12/5
 * @Description:
 */
@Controller
public class UserController {

    @RequestMapping("/login")
    public String login(){
        return "login1";
    }
}
