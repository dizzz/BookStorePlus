package com.bms.controller;

//import com.bms.auth.UserValidator;
import com.bms.model.*;
import com.bms.service.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class BusinessController {
    @Autowired
    private UserService userService;
    @Autowired
    private MyUserDetailsService userDetailsService;

//    @Autowired
//    private UserValidator userValidator;
    @Autowired
    private BookService bookService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    private List<Book>bookList;
    private List<CartItem>cartItemList;
    private List<Category>categoryList;
    private Order order;
    private Integer userId;

    private Book thisBook;//detail页面的书
    @RequestMapping(value={"/","home"})
    public ModelAndView  home(){
        ModelAndView  modelAndView = new ModelAndView();

        bookList=bookService.query(1,5);
        categoryList = bookService.queryAllCategories();
        modelAndView.addObject("popularbooks",bookService.queryBookOrderByClicks(1,5));
        modelAndView.addObject("newbooks",bookService.queryBookOrderByPublishDate(1,5));
        modelAndView.addObject("tags",categoryList);
        modelAndView.setViewName("home");
        return modelAndView;
    }
    @RequestMapping("main")
    public String main(HttpServletRequest request,Model model, Integer pageNum, String key, Integer tag){
        if(pageNum==null) pageNum=1;
        if(key != null && key.length()!=0){
            bookList=bookService.queryBookByKey(key,pageNum,10  );
        }else if(tag!=null){
            bookList=bookService.queryByTag(tag,pageNum,10);
        }else{
            bookList=bookService.query(pageNum,10);

        }
        if(categoryList  == null){
            categoryList = bookService.queryAllCategories();
        }
        model.addAttribute("page",new PageInfo<Book>(bookList));
        model.addAttribute("books",bookList);
        model.addAttribute("tags",categoryList);
        User user = userService.queryUserByLoginId(request.getRemoteUser());
        if(user !=null)
            model.addAttribute("role",user.getUserRole());

        return  "main";
    }
    ////Book////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("bookdetail")
    public ModelAndView bookdetail(Integer bookId){
        bookService.addClicks(bookId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bookdetail");
//      如果当前booklist里有这本书，就直接用
        if(bookList != null){
            for (int i = 0;i<bookList.size();i++){
                if(bookList.get(i).getId().intValue()  == bookId.intValue()){
                    thisBook = bookList.get(i);
                    modelAndView.addObject("book",thisBook);
                    modelAndView.addObject("ratings",bookService.queryBookRatingByBookId(thisBook.getId()));
                    return modelAndView;
                }
            }}
        thisBook = bookService.queryBookById(bookId);
        modelAndView.addObject("ratings",bookService.queryBookRatingByBookId(bookId));
        modelAndView.addObject("book",thisBook);
        return modelAndView;
    }

    //TODO
//    购物车不能加

    ////CartItem////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("addtocart")
    public ModelAndView addtocart(Integer bookId,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        userId = (userId==null?userService.queryUserByLoginId(request.getRemoteUser()).getId():userId);
        cartService.addItem(new CartItem(userId,bookService.queryBookById(bookId),1));
        modelAndView.setViewName("redirect:/main");
        return modelAndView;
    }
    @RequestMapping("/cart")
    public ModelAndView cart(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        userId = (userId==null?userService.queryUserByLoginId(request.getRemoteUser()).getId():userId);
        cartItemList=cartService.queryCount(userId);
        int totalCnt = 0;
        double totalPrice = 0;
        for (int i = 0; i < cartItemList.size(); i++) {
            totalCnt += cartItemList.get(i).getQuantity();
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
        userId = (userId==null?userService.queryUserByLoginId(request.getRemoteUser()).getId():userId);
        for(int i = 0;i<bookId.length;i++)
            cartService.delCartItemByUserIdAndBookId(userId,bookId[i]);
        return "redirect:/cart";
    }
    @RequestMapping("updatecart")
    public String updatecart(HttpServletRequest request, HttpServletResponse response,Integer bookId, String type){
        userId = (userId==null?userService.queryUserByLoginId(request.getRemoteUser()).getId():userId);
        cartService.adjustCnt(userId,bookId,"up".equals(type));
        return "redirect:/cart";
    }
    @RequestMapping("confirm")
    public ModelAndView confirm(HttpServletRequest request,Integer[] bookId){
        ModelAndView modelAndView = new ModelAndView();
        userId = (userId==null?userService.queryUserByLoginId(request.getRemoteUser()).getId():userId);
        order = new Order(userId);
        String URL = request.getHeader("Referer");
        if(URL.indexOf("cart") != -1){
            //从购物车购买
            if(cartItemList != null && bookId != null) {
                for (int i = 0; i < cartItemList.size(); i++) {
                    for (int j = 0; j < bookId.length; j++) {
                        if (cartItemList.get(i).getBookId().intValue() == bookId[j].intValue()) {
                            order.add(cartItemList.get(i));
                            break;
                        }
                    }
                }
            }
        }else if (URL.indexOf("bookdetail") != -1){
            //从detail页购买
            order.getItems().add(new CartItem(thisBook));
        }else{
            if(bookList != null){
                for(int i = 0;i<bookList.size();i++){
                    if(bookList.get(i).getId().intValue() == bookId[0]){
                        //从主页购买
                        order.add(new CartItem(bookList.get(i)));
                        break;
                    }
                }

            }
            //否则
        }
        if(order.getItems().size() == 0 && bookId!=null) {
            for(int i =0;i<bookId.length;i++)
                order.add(new CartItem(bookService.queryBookById(bookId[i])));
        }
        modelAndView.addObject("totalPrice",order.getTotalPrice());
        modelAndView.addObject("items",order.getItems());
        modelAndView.setViewName("confirm");
//        return "redirect:/cart";
        return modelAndView;
    }
    @RequestMapping("addorder")
    public String addorder(){
        if(order == null || userId == null)
            return "redirect:/main";
        orderService.addOrder(order);
        for(int i = 0;i<order.getItems().size();i++){
            cartService.delCartItemByUserIdAndBookId(userId,order.get(i).getBookId());
        }
        return "redirect:/main";
    }
    @RequestMapping("order")
    public ModelAndView order(HttpServletRequest request) {
        userId = (userId==null?userService.queryUserByLoginId(request.getRemoteUser()).getId():userId);
        ModelAndView modelAndView = new ModelAndView();
        List<Order>orders = orderService.queryOrderByUserId(userId);
        for(int i = 0;i<orders.size();i++){
            Order order = orders.get(i);
            order.setItems(orderService.queryOrderItemByOrderId(order.getId()));
        }
        modelAndView.addObject("orders",orders);
        return modelAndView;
    }
    @RequestMapping(value = "ratebook",method = RequestMethod.GET)
    public ModelAndView ratebook(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("book",bookService.queryBookById(id));
        modelAndView.setViewName("ratebook");
        return modelAndView;
    }
    @RequestMapping(value = "ratebook",method = RequestMethod.POST)
    public ModelAndView doratebook(HttpServletRequest request,Integer bookId,Integer rating,String comment){
        ModelAndView modelAndView = new ModelAndView();
        userId = (userId==null?userService.queryUserByLoginId(request.getRemoteUser()).getId():userId);
        bookService.addBookRating(new BookRating(userId,bookId,rating,comment));
//        modelAndView.addObject("bookId",bookId);
        modelAndView.setViewName("redirect:/bookdetail?bookId="+bookId);
        return modelAndView;
    }
}
//bookDetail bookId重复
//TODO