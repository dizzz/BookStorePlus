package com.bms.controller;

import com.bms.UserValidator;
import com.bms.model.*;
import com.bms.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = {"/","main"})
    public String main(){
        return  "main";
    }


    @RequestMapping(value = "/login",method= RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }
    //如果不事先扔一个user到页面里，会找不到该属性

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration");
        return modelAndView;
    }
    //自动捕获相应的类型

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute("user") User user, BindingResult bindingResult,HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
//TODO            错误信息
        }else {
            user.setRegisterIp(request);
            user.setRegisterTime();
            userService.save(user);
            userDetailsService.autologin(user.getLoginId(), user.getPwdComfirm());
            modelAndView.setViewName("main");
        }
        return modelAndView;
    }
    @RequestMapping("show")
    public String show(Model model){
        model.addAttribute("users",userService.quaryAll());
        return "show";
    }



}
