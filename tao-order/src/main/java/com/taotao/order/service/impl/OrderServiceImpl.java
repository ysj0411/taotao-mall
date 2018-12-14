package com.taotao.order.service.impl;

import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.component.JedisClient;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.po.TbOrder;
import com.taotao.po.TbOrderItem;
import com.taotao.po.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

/**
 *
 * 生成顶单服务
 */
@Controller
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_ORDER_GEN_KEY}")
    private String REDIS_ORDER_GEN_KEY;
    @Value("${ORDER_ID_BEGIN}")
    private String ORDER_ID_BEGIN;
    @Value("${REDIS_ORDER_DETAIL_GEN_KEY}")
    private String REDIS_ORDER_DETAIL_GEN_KEY;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        //生成订单号
//        取订单号
        String id = jedisClient.get(REDIS_ORDER_GEN_KEY);
        if(StringUtils.isBlank(id)){
            //如果订单号生成key不存在设置初始值
            jedisClient.set(REDIS_ORDER_GEN_KEY,ORDER_ID_BEGIN);
        }
        Long orderId = jedisClient.incr(REDIS_ORDER_GEN_KEY);
        //补全字段
        orderInfo.setOrderId(orderId.toString());
        //1未付款2已付款3未发货4已发货5交易成功6交易关闭

        orderInfo.setStatus(1);
        Date date = new Date();
        orderInfo.setCreateTime(date);
        orderInfo.setUpdateTime(date);

        //插入订单
        orderMapper.insert(orderInfo);
         //插入订单明细

        //补全字段
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for(TbOrderItem orderItem:orderItems){
            Long detailId= jedisClient.incr(REDIS_ORDER_DETAIL_GEN_KEY);
            orderItem.setId(detailId.toString());
            //订单号
            orderItem.setOrderId(orderId.toString());
            //插入数据
            orderItemMapper.insert(orderItem);


        }

        //三、插入物流表
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        //补全数据

        orderShipping.setOrderId(orderId.toString());
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        //插入数据
        orderShippingMapper.insert(orderShipping);
        //返回TaotaoResult包装订单号
        return TaotaoResult.ok(orderId);
    }
}
