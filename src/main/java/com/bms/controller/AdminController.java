
package com.bms.controller;
import com.bms.model.Book;
import com.bms.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
//    @Autowired
//    private UserRoleService userRoleService;
    @Autowired
    private BookService bookService;

    @RequestMapping(value = {"/","home"})
    public String home(){
        return "admin/home";
    }
    @RequestMapping("/show")
    public String show(Model model){
        model.addAttribute("users",userService.quaryAll());
//        model.addAttribute("userRoles", userRoleService.quaryAll());
        return "admin/show";
    }
    @RequestMapping("/usermanage")
    public String usermanage(Model model){
        model.addAttribute("users",userService.quaryAll());
        return "admin/usermanage";
    }
    @RequestMapping("/deluser")
    public String deluser(HttpServletRequest request){
        userService.deleteById(request.getParameter("id"));
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
    public String bookmanage(Model model,Integer pageNum,String key){
        if(pageNum==null) pageNum=1;
        List<Book> list;
        if(key != null && key.length()!=0){
            list=bookService.quaryBookByKey(key,pageNum,10  );
        }else {
            list = bookService.quary(pageNum, 20);
        }
        model.addAttribute("page",new PageInfo<Book>(list));
        model.addAttribute("books",list);
        return "admin/bookmanage";
    }
    @RequestMapping(value = "/addbook",method = RequestMethod.GET)
    public String addbook(Model model){
        model.addAttribute("book",new Book());
        return "admin/addbook";
    }
//    TODO
//    不能添加已有图书，添加成功提示
    @RequestMapping(value = "/addbook",method = RequestMethod.POST)
    public String addbook(Book book,Model model){
        bookService.addBook(book);
        return "redirect:/admin/bookmanage";
    }
    @RequestMapping(value = "/delbook")
    public String delbook(HttpServletRequest request){
        bookService.delBook(request.getParameter("id"));
        return "redirect:/admin/bookmanage";
    }
}
//TODO model函数统一
