package com.taotao.portal.controller;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 订单处理
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
     private CartService cartService;

    @RequestMapping("/order-cart")
    public String showOrderCart(Model model, HttpServletRequest request){
        //取购物车商品列表
        List<CartItem>list = cartService.getCartItems(request);
        model.addAttribute("cartList",list);
        return "order-cart";

    }

}
