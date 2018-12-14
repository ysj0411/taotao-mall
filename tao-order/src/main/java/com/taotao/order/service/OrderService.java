package com.taotao.order.service;

import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;

public interface OrderService {
    TaotaoResult createOrder(OrderInfo orderInfo);
}
