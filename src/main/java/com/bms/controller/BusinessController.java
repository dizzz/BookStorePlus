package com.bms.controller;

//import com.bms.auth.UserValidator;
import com.bms.model.*;
import com.bms.service.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
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
//    private List<Book>bookList;
//    private List<CartItem>cartItemList;
//    private List<Category>categoryList;
//    private Order order;
//    private Integer userId;
//    private Book thisBook;//detail页面的书
    @RequestMapping(value={"/","home"})
    public ModelAndView  home(){
        ModelAndView  modelAndView = new ModelAndView();

        modelAndView.addObject("popularbooks",bookService.queryBookOrderByClicks(1,5));
        modelAndView.addObject("newbooks",bookService.queryBookOrderByPublishDate(1,5));
//        modelAndView.addObject("popularbooks",bookService.queryBookInOrder(1,5,"clicks"));

//        modelAndView.addObject("newbooks",bookService.queryBookInOrder(1,5,"publishDate"));

        modelAndView.addObject("tags",bookService.queryAllCategories());
        modelAndView.setViewName("home");
        return modelAndView;
    }
    @RequestMapping("main")
    public String main(HttpServletRequest request,Model model, Integer pageNum, String key, Integer tag){
        if(pageNum==null) pageNum=1;
        List<Book>bookList;
        if(key != null && key.length()!=0){
            bookList=bookService.queryBookByKey(key,pageNum,10  );
        }else if(tag!=null){
            bookList=bookService.queryByTag(tag,pageNum,10);
        }else{
            bookList=bookService.query(pageNum,10);

        }
        model.addAttribute("page",new PageInfo<Book>(bookList));
        model.addAttribute("books",bookList);
        model.addAttribute("tags",bookService.queryAllCategories());
        User user = userService.queryUserByLoginId(request.getRemoteUser());
        if(user !=null)
            model.addAttribute("role",user.getUserRole());

        return  "main";
    }
    @RequestMapping("/changeorder")
    public ModelAndView changeorder(HttpServletRequest request,String order){
        ModelAndView modelAndView = new ModelAndView();
        List<Book>bookList;
        System.out.println(order);
        if("publishDate".equals(order)){
            bookList = bookService.queryBookOrderByPublishDate(1,10);
        }else if("clicks".equals(order)){
            bookList = bookService.queryBookOrderByClicks(1,10);

        }else if("sell".equals(order)){
            bookList = bookService.queryBookOrderBySell(1,10);
        }else{
            bookList=bookService.query(1,10);
        }
        modelAndView.addObject("page",new PageInfo<Book>(bookList));
        modelAndView.addObject("books",bookList);
        modelAndView.addObject("tags",bookService.queryAllCategories());
        User user = userService.queryUserByLoginId(request.getRemoteUser());
        if(user !=null)
            modelAndView.addObject("role",user.getUserRole());
        modelAndView.setViewName("main");
        return modelAndView;
    }
    ////Book////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("bookdetail")
    public ModelAndView bookdetail(Integer bookId){
        bookService.addClicks(bookId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bookdetail");
//      如果当前booklist里有这本书，就直接用
        modelAndView.addObject("ratings",bookService.queryBookRatingByBookId(bookId));
        modelAndView.addObject("book",bookService.queryBookById(bookId));
        return modelAndView;
    }

    ////CartItem////////////////////////////////////////////////////////////////////////////////////////////
        @RequestMapping("addtocart")
    public ModelAndView addtocart(Model model,Integer bookId,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Integer userId = userService.queryUserByLoginId(request.getRemoteUser()).getId();
        cartService.addItem(new CartItem(userId,bookService.queryBookById(bookId),1));
        modelAndView.setViewName("redirect:/bookdetail?bookId="+bookId);
        return modelAndView;
    }
    @RequestMapping("/cart")
    public ModelAndView cart(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Integer userId = userService.queryUserByLoginId(request.getRemoteUser()).getId();
        List<CartItem>cartItemList=cartService.queryCount(userId);
        int totalCnt = 0;
        double totalPrice = 0;
        for (int i = 0; i < cartItemList.size(); i++) {
            Integer cnt = cartItemList.get(i).getQuantity();
            Double price = cartItemList.get(i).getUnitPrice();
            totalCnt += cnt;
            totalPrice += price * cnt;
        }
        modelAndView.addObject("books",bookService.queryRecomdBook(userId));
        modelAndView.addObject("items",cartItemList);
        modelAndView.addObject("totalCnt",totalCnt);
        modelAndView.addObject("totalPrice",totalPrice);
        modelAndView.setViewName("cart");
        return modelAndView;
    }

    @RequestMapping("delcartitem")
    public String delcartitem(HttpServletRequest request,Integer[] bookId){
        Integer userId = userService.queryUserByLoginId(request.getRemoteUser()).getId();
        for(int i = 0;i<bookId.length;i++)
            cartService.delCartItemByUserIdAndBookId(userId,bookId[i]);
        return "redirect:/cart";
    }
//    @RequestMapping("updatecart")
//    public String updatecart(HttpServletRequest request, HttpServletResponse response,Integer bookId, String type){
//        userId = (userId==null?userService.queryUserByLoginId(request.getRemoteUser()).getId():userId);
//        cartService.adjustCnt(userId,bookId,"up".equals(type));
//        return "redirect:/cart";
//    }
    @RequestMapping("confirm")
    public ModelAndView confirm(HttpServletRequest request,Integer[] bookId){
        ModelAndView modelAndView = new ModelAndView();
        Integer userId = userService.queryUserByLoginId(request.getRemoteUser()).getId();
        Order order = new Order(userId);
        String URL = request.getHeader("Referer");
        if(URL.indexOf("cart") != -1) {
            //从购物车购买
            List<CartItem> cartItemList = cartService.queryCount(userId);

            if (cartItemList != null && bookId != null) {
                for (int i = 0; i < cartItemList.size(); i++) {
                    for (int j = 0; j < bookId.length; j++) {
                        if (cartItemList.get(i).getBookId().intValue() == bookId[j].intValue()) {
                            order.add((cartItemList.get(i)));
                            break;
                        }
                    }
                }
            }
        }

//
//        }else if (URL.indexOf("bookdetail") != -1){
//            //从detail页购买
//            order.add(new CartItem(thisBook));
//        }else{
//            if(bookList != null){
//                for(int i = 0;i<bookList.size();i++){
//                    if(bookList.get(i).getId().intValue() == bookId[0]){
//                        //从主页购买
//                        order.add(new CartItem(bookList.get(i)));
//                        break;
//                    }
//                }
//
//            }
//            //否则
//        }
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
    public String addorder(HttpServletRequest request,Integer bookId[],Integer quantity[]){
        if(bookId == null || quantity == null)
            return "redirect:/main";
        Integer userId = userService.queryUserByLoginId(request.getRemoteUser()).getId();
        Order order = new Order(userId);
        for(int i = 0;i<bookId.length;i++){
            order.add(new CartItem(userId,bookService.queryBookById(bookId[i]),quantity[i]));
        }
        orderService.addOrder(order);
        for(int i = 0;i<order.getItems().size();i++){
            cartService.delCartItemByUserIdAndBookId(userId,order.get(i).getBookId());
        }
        return "redirect:/main";
    }
    @RequestMapping("order")
    public ModelAndView order(HttpServletRequest request) {
        Integer userId = userService.queryUserByLoginId(request.getRemoteUser()).getId();

        ModelAndView modelAndView = new ModelAndView();
        List<Order>orders = orderService.queryOrderByUserId(userId);
        for(int i = 0;i<orders.size();i++){
            Order order = orders.get(i);
            order.setItems(orderService.queryOrderItemByOrderId(order.getId()));
            System.out.println(order.get(0).getUnitPrice());
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
        Integer userId = userService.queryUserByLoginId(request.getRemoteUser()).getId();
        ModelAndView modelAndView = new ModelAndView();
        bookService.addBookRating(new BookRating(userId,bookId,rating,comment));
//        modelAndView.addObject("bookId",bookId);
        modelAndView.setViewName("redirect:/bookdetail?bookId="+bookId);
        return modelAndView;
    }
}

//
//购物车到cofirm
//confirm到add