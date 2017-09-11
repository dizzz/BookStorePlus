package com.bms.controller;

import com.bms.model.AjaxResponse;
import com.bms.model.CartRequest;
import com.bms.service.CartService;
import com.bms.service.UserService;
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
public class ReController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
//    @RequestMapping("updatecart")
//    public AjaxResponse postCustomer(@RequestBody CartRequest cartRequest,HttpServletRequest request) {
//        Integer userId = userService.quaryWithUserName(request.getRemoteUser()).getId();
//        cartService.adjustCnt(userId,cartRequest.getBookId(),cartRequest.isUp());
//        AjaxResponse response = new AjaxResponse("done",cartRequest.isUp()?"up":"down");
//        return response;
//    }
}
