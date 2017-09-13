package com.bms.controller;

import com.bms.UserValidator;
import com.bms.model.*;
import com.bms.service.*;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.awt.Symbol;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BusinessController {
    @Autowired
    private UserService userService;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private BookService bookService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    private List<Book>bookList;
    private List<CartItem>cartItemList;
    private List<Category>categoryList;
    List<OrderItem>orderitems;
    private Integer userId;

    private Book thisBook;//detail页面的书
    @RequestMapping(value = {"/","main"})
    public String main(Model model, Integer pageNum, String key, Integer tag){
        if(pageNum==null) pageNum=1;
        if(key != null && key.length()!=0){
            bookList=bookService.quaryBookByKey(key,pageNum,10  );
        }else if(tag!=null){
            bookList=bookService.quaryByTag(tag,pageNum,10);
        }else{
            bookList=bookService.quary(pageNum,10);

        }
        if(categoryList  == null){
            categoryList = bookService.quaryAllCategories();
        }
        model.addAttribute("page",new PageInfo<Book>(bookList));
        model.addAttribute("books",bookList);
        model.addAttribute("tags",categoryList);

        return  "main";
    }
    ////Book////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("bookdetail")
    public ModelAndView bookdetail(Integer bookId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bookdetail");
//      如果当前booklist里有这本书，就直接用
        if(bookList != null){
            for (int i = 0;i<bookList.size();i++){
                if(bookList.get(i).getId().intValue()  == bookId.intValue()){
                    thisBook = bookList.get(i);
                    modelAndView.addObject("book",thisBook);
                    return modelAndView;
                }
            }}
        thisBook = bookService.quaryBookById(bookId);
        modelAndView.addObject("book",thisBook);
        return modelAndView;
    }
    ////CartItem////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("addtocart")
    public ModelAndView addtocart(Integer bookId,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        userId = (userId==null?userService.quaryWithUserName(request.getRemoteUser()).getId():userId);
        CartItem cartItem = new CartItem(userId,bookId,1);
        cartService.addItem(cartItem);
        modelAndView.setViewName("redirect:/main");
        return modelAndView;
    }
    @RequestMapping("/cart")
    public ModelAndView cart(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        userId = (userId==null?userService.quaryWithUserName(request.getRemoteUser()).getId():userId);
        cartItemList=cartService.quaryCount(userId);
        int totalCnt = 0;
        double totalPrice = 0;
        for (int i = 0; i < cartItemList.size(); i++) {
            totalCnt += cartItemList.get(i).getBookCnt();
            cartItemList.get(i).setTotalPrice();
            totalPrice += cartItemList.get(i).getTotalPrice();
        }
        modelAndView.addObject("items",cartItemList);
        modelAndView.addObject("totalCnt",totalCnt);
        modelAndView.addObject("totalPrice",totalPrice);
        modelAndView.setViewName("cart");
        return modelAndView;
    }

    @RequestMapping("delcartitem")
    public String delcartitem(HttpServletRequest request,Integer[] bookId){
        userId = (userId==null?userService.quaryWithUserName(request.getRemoteUser()).getId():userId);
        for(int i = 0;i<bookId.length;i++)
            cartService.delCartItemByUserIdAndBookId(userId,bookId[i]);
        return "redirect:/cart";
    }
    @RequestMapping("updatecart")
    public String updatecart(HttpServletRequest request, HttpServletResponse response,Integer bookId, String type){
        userId = (userId==null?userService.quaryWithUserName(request.getRemoteUser()).getId():userId);
        cartService.adjustCnt(userId,bookId,"up".equals(type));
        return "redirect:/cart";
    }
    @RequestMapping("confirm")
    public ModelAndView confirm(HttpServletRequest request,Integer[] bookId){
        ModelAndView modelAndView = new ModelAndView();
        userId = (userId==null?userService.quaryWithUserName(request.getRemoteUser()).getId():userId);
        String URL = request.getHeader("Referer");
        orderitems = new ArrayList<OrderItem>();
        if(URL.indexOf("cart") != -1){
            //从购物车购买
            if(cartItemList != null && bookId != null) {
                for (int i = 0; i < cartItemList.size(); i++) {
                    for (int j = 0; j < bookId.length; j++) {
                        if (cartItemList.get(i).getBookId().intValue() == bookId[j].intValue()) {
                            orderitems.add(new OrderItem(cartItemList.get(i)));
                            break;
                        }
                    }
                }
            }
        }else if (URL.indexOf("bookdetail") != -1){
            //从detail页购买
            orderitems.add(new OrderItem(userId,1,thisBook));
        }else{
            if(bookList != null){
                for(int i = 0;i<bookList.size();i++){
                    if(bookList.get(i).getId().intValue() == bookId[0]){
                        //从主页购买
//                    orderService.addOrder(new OrderItem(userId,bookId[0],book.getTitle(),book.getISBN(),1,book.getPrice(),book.getPrice()));
                        orderitems.add(new OrderItem(userId,1,bookList.get(i)));
                        break;
                    }
                }

            }
            //否则
        }
        if(orderitems.size() == 0 && bookId!=null) {
            for(int i =0;i<bookId.length;i++)
            orderitems.add(new OrderItem(userId, 1, bookService.quaryBookById(bookId[i])));
        }
        double totalPrice = 0;
        for (int i = 0; i < orderitems.size(); i++) {
            totalPrice += orderitems.get(i).getTotalPrice();
        }
        modelAndView.addObject("totalPrice",totalPrice);
        modelAndView.addObject("orderitems",orderitems);
        modelAndView.setViewName("confirm");
//        return "redirect:/cart";
        return modelAndView;
    }
    @RequestMapping("addorder")
    public String addorder(){
//        if(orderitems == null)
            return "redirect:/main";

    }

}
