package com.bms.controller;

import com.bms.model.*;
import com.bms.service.BookService;
import com.bms.service.CartService;
import com.bms.service.UserService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@RestController
public class AjaxController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private BookService bookService;
    @RequestMapping("updatecart")
    public AjaxResponse postCustomer(@RequestBody CartRequest cartRequest,HttpServletRequest request) {
        Integer userId = userService.queryUserByLoginId(request.getRemoteUser()).getId();

        cartService.adjustCnt(userId,cartRequest.getBookId(),cartRequest.isUp());
        AjaxResponse response = new AjaxResponse("done",cartRequest.isUp()?"up":"down");
        return response;
    }
    @RequestMapping("maincontrol")
    public AjaxResponse maincontrol(@RequestBody MainPageAjaxRequest ajaxRequest, HttpServletRequest request) {
        Integer userId = userService.queryUserByLoginId(request.getRemoteUser()).getId();
        Integer bookId = ajaxRequest.getBookId();
        if("add".equals(ajaxRequest.getType())){
            cartService.addItem(new CartItem(userId,bookService.queryBookById(bookId),1));
            return new AjaxResponse("add","done");
        }else if("buy".equals(ajaxRequest.getType())){
            return new AjaxResponse("buy",bookId);
        }else if("detail".equals(ajaxRequest.getType())){
            return new AjaxResponse("detail",bookId);
        }
        return new AjaxResponse("error","");
    }

}
