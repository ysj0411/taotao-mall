package com.taotao.portal.service;

import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.Impl.CartServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {
    TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);
    List<CartItem> getCartItems(HttpServletRequest request);
    TaotaoResult updateCartItem(long itemId,Integer num,HttpServletRequest request, HttpServletResponse response);
    TaotaoResult deleteCartItem(long itemId,HttpServletRequest request, HttpServletResponse response);

}
