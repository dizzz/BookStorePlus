
package com.bms.controller;
import com.bms.model.*;
import com.bms.service.*;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Or;
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
    @Autowired
    private BookService bookService;
    @Autowired
    private OrderService orderService;
    @RequestMapping(value = {"/","home"})
    public String home(){
        return "admin/home";
    }
    @RequestMapping("/show")
    public String show(Model model){
        model.addAttribute("users",userService.quaryAll());
        return "admin/show";
    }
    @RequestMapping("/usermanage")
    public String usermanage(Model model){
        model.addAttribute("users",userService.quaryAll());
        return "admin/user/usermanage";
    }
    @RequestMapping("/deluser")
    public String deluser(HttpServletRequest request){
        userService.deleteById(request.getParameter("id"));
        return "redirect:/admin/usermanage";
    }
    @RequestMapping("/userdetail")
    public ModelAndView userdetail(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",userService.quaryById(id));
        modelAndView.setViewName("admin/user/userdetail");
        return modelAndView;
    }
    ///BOOK////////////////////////////////////////////////////////////////////////////
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
        return "admin/book/bookmanage";
    }
    @RequestMapping(value = "/addbook",method = RequestMethod.GET)
    public String addbook(Model model){
        model.addAttribute("tags",bookService.quaryAllCategories());
        model.addAttribute("publishers",bookService.quaryAllPublishers());
        model.addAttribute("book",new Book());
        return "admin/book/addbook";
    }
    //    TODO
//    不能添加已有图书，添加成功提示
    @RequestMapping(value = "/addbook",method = RequestMethod.POST)
    public String addbook(Book book,Model model){
        bookService.addBook(book);
        return "redirect:/admin/bookmanage";
    }
    @RequestMapping(value = "/delbook")
    public String delbook(Integer id){
        bookService.delBook(id);
        return "redirect:/admin/bookmanage";
    }
    @RequestMapping(value = "/modifybook",method = RequestMethod.GET)
    public ModelAndView modifybook(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tags",bookService.quaryAllCategories());
        modelAndView.addObject("publishers",bookService.quaryAllPublishers());
        modelAndView.addObject("book",bookService.quaryBookById(id));
        modelAndView.setViewName("/admin/book/modifybook");
        return modelAndView;
    }
    @RequestMapping(value = "/modifybook",method = RequestMethod.POST)
    public String modifybook(Book book){
        bookService.updateBook(book);
        return "redirect:/admin/bookmanage";
    }
    @RequestMapping("rating")
    public ModelAndView rating(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ratings",bookService.quaryBookRatingByBookId(id));
        modelAndView.setViewName("/admin/book/rating");
        return modelAndView;
    }
    @RequestMapping("delrating")
    public String delrating(Integer ratingId,Integer bookId){
        bookService.delBookRatingById(ratingId);
        return "redirect:/admin/rating?id="+bookId;
    }
    ///Category//////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("categorymanage")
    public ModelAndView categorymanage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tags",bookService.quaryAllCategories());
        modelAndView.setViewName("/admin/category/categorymanage");
        return modelAndView;
    }
    @RequestMapping(value = "/addcategory",method = RequestMethod.GET)
    public String addcategory(Model model){
        model.addAttribute("category",new Category());
        return "admin/category/addcategory";
    }
    @RequestMapping(value = "/addcategory",method = RequestMethod.POST)
    public String addcategory(Category category,Model model){
        bookService.addCategory(category);
        return "redirect:/admin/categorymanage";
    }
    @RequestMapping(value = "/delcategory")
    public String delcategory(Integer id){
        bookService.delCategory(id);
        return "redirect:/admin/categorymanage";
    }
    @RequestMapping(value = "/modifycategory",method = RequestMethod.GET)
    public ModelAndView modifycategory(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("category",bookService.quaryCategoryById(id));
        modelAndView.setViewName("/admin/category/modifycategory");
        return modelAndView;
    }
    @RequestMapping(value = "/modifycategory",method = RequestMethod.POST)
    public String modifycategory(Category category){
        bookService.updateCategory(category);
        return "redirect:/admin/categorymanage";
    }
    ///Publisher//////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping("publishermanage")
    public ModelAndView publishermanage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("publishers",bookService.quaryAllPublishers());
        modelAndView.setViewName("/admin/publisher/publishermanage");
        return modelAndView;
    }
    @RequestMapping(value = "/addpublisher",method = RequestMethod.GET)
    public String addpublisher(Model model){
        model.addAttribute("publisher",new Publisher());
        return "admin/publisher/addpublisher";
    }
    @RequestMapping(value = "/addpublisher",method = RequestMethod.POST)
    public String addpublisher(Publisher publisher,Model model){
        bookService.addPublisher(publisher);
        return "redirect:/admin/publishermanage";
    }
    @RequestMapping(value = "/delpublisher")
    public String delpublisher(Integer id){
        bookService.delPublisher(id);
        return "redirect:/admin/publishermanage";
    }
    @RequestMapping(value = "/modifypublisher",method = RequestMethod.GET)
    public ModelAndView modifypublisher(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("publisher",bookService.quaryPublisherById(id));
        modelAndView.setViewName("/admin/publisher/modifypublisher");
        return modelAndView;
    }
    @RequestMapping(value = "/modifypublisher",method = RequestMethod.POST)
    public String modifypublisher(Publisher publisher){
        bookService.updatePublisher(publisher);
        return "redirect:/admin/publishermanage";
    }
    ///Order//////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("ordermanage")
    public ModelAndView ordermanage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orders",orderService.quaryAllOrders());
        modelAndView.setViewName("/admin/order/ordermanage");
        return modelAndView;
    }
    @RequestMapping("orderdetail")
    public ModelAndView orderdetail(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order",orderService.quaryOrderById(id));
        modelAndView.addObject("items",orderService.quaryOrderItemByOrderId(id));
        modelAndView.setViewName("/admin/order/orderdetail");
        return modelAndView;
    }
}
//TODO model函数统一
//TODO类别和出版社
