package com.bms.controller;
import com.bms.model.Book;
import com.bms.model.User;
import com.bms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private BookService bookService;
    @RequestMapping("/show")
    public String show(Model model){
        model.addAttribute("users",userService.quaryAll());
        model.addAttribute("userRoles", userRoleService.quaryAll());
        return "admin/show";
    }
    @RequestMapping("/usermanage")
    public String usermanage(Model model){
        model.addAttribute("users",userService.quaryAll());
        model.addAttribute("userRoles", userRoleService.quaryAll());
        return "admin/usermanage";
    }
    @RequestMapping("/deluser")
    public String deluser(HttpServletRequest request){
        userService.deleteByName(request.getParameter("id"));
        return "redirect:/admin/usermanage";
    }
    @RequestMapping("/userdetail")
    public ModelAndView userdetail(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",userService.quaryWithUserName(request.getParameter("id")));
        modelAndView.setViewName("admin/userdetail");
        return modelAndView;
    }
    @RequestMapping("/bookmanage")
    public String bookmanage(Model model){
        model.addAttribute("books",bookService.quaryAll());
        return "admin/bookmanage";
    }
    @RequestMapping(value = "/addbook",method = RequestMethod.GET)
    public String addbook(Model model){
        model.addAttribute("book",new Book());
        return "admin/addbook";
    }
    @RequestMapping(value = "/addbook",method = RequestMethod.POST)
    public String addbook(Book book){
        System.out.println(book.getPublishDate());
        bookService.add(book);
        return "admin/bookmanage";
    }
}
//TODO model函数统一
