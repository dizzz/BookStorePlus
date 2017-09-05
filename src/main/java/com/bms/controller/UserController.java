package com.bms.controller;

import com.bms.UserValidator;
import com.bms.model.*;
import com.bms.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

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
    public ModelAndView registration(@ModelAttribute("user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
//TODO            错误信息
        }else {
            userService.save(user);
            userRoleService.autologin(user.getLoginId(), user.getPwdComfirm());
            modelAndView.setViewName("main");
        }
        return modelAndView;
    }
}
