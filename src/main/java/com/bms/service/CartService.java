package com.bms.service;

import com.bms.mapper.CartMapper;
import com.bms.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    public void addItem(Cart cart){
        for(int i = 0;i<cart.getBookNum();i++)
        cartMapper.addCartItem(cart.getCreateTime(),cart.getBookId(),cart.getUserId());
    }

}
