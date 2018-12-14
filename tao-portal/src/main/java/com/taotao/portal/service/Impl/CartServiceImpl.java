package com.taotao.portal.service.Impl;

import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.comon.utils.CookieUtils;
import com.taotao.comon.utils.JsonUtils;
import com.taotao.po.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 购物车实现
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ItemService itemService;
    @Value("${COOKIE_EXPIRE}")
    private Integer COOKIE_EXPIRE;

    @Override
    public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取商品列表
        List<CartItem> itemList = getCartItemList(request);
        for(CartItem cartItem: itemList){
            if(cartItem.getId()==itemId){
                //删除商品
                itemList.remove(cartItem);
                break;
            }
        }
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(itemList), COOKIE_EXPIRE,true);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateCartItem(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        //从购物车取商品列表
        List<CartItem> itemList = getCartItemList(request);
        //根据商品ID查询商品
        for(CartItem cartItem:itemList){
            if(cartItem.getId()==itemId){
                //更新数量
                cartItem.setNum(num);
                break;
            }
        }
        //写入cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(itemList), COOKIE_EXPIRE,true);
        return TaotaoResult.ok();
    }

    @Override
    public List<CartItem> getCartItems(HttpServletRequest request) {
        List<CartItem> list = getCartItemList(request);

        return list;
    }

    @Override
    public TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {

        List<CartItem> itemList = getCartItemList(request);
        boolean havaFlg=false;
        for(CartItem cartItem:itemList){
            //如果商品存在数量相加
            if(cartItem.getId().longValue()==itemId){
                cartItem.setNum(cartItem.getNum()+num);
                havaFlg=true;
            break;
            }
        }
        if(!havaFlg){
            TbItem item=itemService.getItemById(itemId);
            //转换成CartItem
            CartItem cartItem=new CartItem();
            cartItem.setId(itemId);
            cartItem.setNum(num);
            cartItem.setPrice(item.getPrice());
            cartItem.setTitle(item.getTitle());
            if(StringUtils.isNotBlank(item.getImage())){
                String image=item.getImage();
                String[] strings=image.split(",");
                cartItem.setImages(strings[0]);
            }
//添加到购物车商品列表
            itemList.add(cartItem);
        }
//        把购物车商品列表加入到cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(itemList), COOKIE_EXPIRE,true);

        return TaotaoResult.ok();
    }

    /**取购物车商品列表
     *
     * @param request
     * @return
     */
    private List<CartItem> getCartItemList(HttpServletRequest request){
        try{
            //从cookie中取商品列表
            String json = CookieUtils.getCookieValue(request, "TT_CART", true);
            //把json转换成java对象
            List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);

            return  list==null?new ArrayList<CartItem>():list;
        }catch (Exception e){
           return new ArrayList<CartItem>();



        }



    }





}
