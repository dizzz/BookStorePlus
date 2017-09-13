package com.bms.service;

import com.bms.mapper.CartMapper;
import com.bms.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    public void addItem(CartItem cartItem){
        for(int i = 0; i< cartItem.getQuantity(); i++)
            cartMapper.addCartItem(cartItem.getCreateTime(), cartItem.getBookId(), cartItem.getUserId());
    }
    public List<CartItem> quaryCount(Integer userId){
        return cartMapper.quaryCount(userId);
    }
    public void addtoCartByUsername(String username,Integer bookid){


    }
    public void adjustCnt(Integer userId,Integer bookId,boolean up){
        int cnt = cartMapper.queryByUserIdAndBookId(userId,bookId).getQuantity() + (up?1:-1);
        cartMapper.delCartItemByUserIdAndBookId(userId,bookId);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i = 0;i<cnt;i++)
            cartMapper.addCartItem(sdf.format(d), bookId, userId);
    }
    public void adjustCnt(Integer userId,Integer bookId,boolean up,Integer cnt){
        cartMapper.delCartItemByUserIdAndBookId(userId,bookId);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i = 0;i<cnt;i++)
            cartMapper.addCartItem(sdf.format(d), bookId, userId);
    }
    public void delCartItemByUserIdAndBookId(Integer userId,Integer bookId){
        cartMapper.delCartItemByUserIdAndBookId(userId,bookId);
    }
}
