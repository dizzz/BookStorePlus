package com.bms.controller;

//import com.bms.UserValidator;
import com.bms.UserValidator;
import com.bms.model.User;
import com.bms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Syscontroller {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private BookService bookService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;


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
    public ModelAndView registration(@ModelAttribute("user") User user, BindingResult bindingResult, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        }else {
            user.setRegisterIp(request);
            user.setRegisterTime();
            userService.addUser(user);
            userDetailsService.autologin(user.getLoginId(), user.getPwdComfirm());
            modelAndView.setViewName("redirect:/main");
            //注册成功页面 、
        }
        return modelAndView;
    }
    @RequestMapping("show")
    public String show(Model model){
        model.addAttribute("users",userService.queryAll());
        return "show";
    }
}
