package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * 注册和登录页面
 */
@Controller
public class PageController {
    //登录
    @RequestMapping("/page/login")
    public String showLogin(String redirectURL, Model model){
        //把参数传递给jsp
        model.addAttribute("redirect",redirectURL);

        return "login";
    }
    //注册
    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }


}

