package com.bms;

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

    @RequestMapping(value={"/","/welcome"})
    public String welcome(Model model){
        return "welcome";
    }
    @RequestMapping("/show")
    public String show(Model model){
        model.addAttribute("users",userService.quaryAll());
        model.addAttribute("userRoles", userRoleService.quaryAll());
        //return "show";
        return "show";
    }
    @RequestMapping("/home")
    public String home(Model model){
        return "home";
    }
    @RequestMapping("/hello")
    public String hello(Model model){
        return "hello";
    }
    @RequestMapping(value = "/index")
    public String index (ModelMap model){
        return  "index";
    }
    @RequestMapping(value = "/login",method= RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration");
        return modelAndView;
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute("user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        userValidator.validate(user, bindingResult);
        System.out.println("fuck" + user.getUsername() + " " + user.getPasswordComfirm());
        if(bindingResult.hasErrors()) {
            System.out.print("sb1");
            modelAndView.setViewName("registration");
        }else {
            System.out.print("sb");
            userService.save(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            userRoleService.autologin(user.getUsername(), user.getPasswordComfirm());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }
}
