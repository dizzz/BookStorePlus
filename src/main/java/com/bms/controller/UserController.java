package com.bms.controller;

import com.bms.UserValidator;
import com.bms.model.*;
import com.bms.service.*;

import com.github.pagehelper.PageInfo;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private BookService bookService;
    @RequestMapping(value = {"/","main"})
    public String main(Model model,Integer pageNum,String key,Integer tag){
        List<Book>list;
        if(pageNum==null) pageNum=1;
        if(key != null && key.length()!=0){
            list=bookService.quaryBookByKey(key,pageNum,10  );
        }else if(tag!=null){
            list=bookService.quaryByTag(tag,pageNum,10);
        }else{
            list=bookService.quary(pageNum,10);

        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).changeDec();
        }
        model.addAttribute("page",new PageInfo<Book>(list));
        model.addAttribute("books",list);
        model.addAttribute("tags",bookService.quaryAllCategories());

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

//    @RequestMapping(value = "/test")
//    public String test(Model model) {
//        List<Book>list=bookService.quaryAll();
//        for (int i = 0; i < list.size(); i++) {
//            Book book =  list.get(i);
////            System.out.println(book.getDescription());
//            String tmp  = book.getDescription().replaceAll("<br>", "");
//            tmp = tmp.replaceAll("<p>","");
//            tmp = tmp.replaceAll("</p>","");
//            bookService.updateDescription(tmp,book.getId());
//            System.out.println(i);
//        }
//        return "/";
//    }

}
