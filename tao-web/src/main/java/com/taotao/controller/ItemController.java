package com.taotao.controller;

import com.taotao.comon.pojo.EasyUIGridResult;
import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.comon.utils.HttpClientUtil;
import com.taotao.po.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;
    @Value("${REDIS_ITEM_KEY}")
    private  String REDIS_ITEM_KEY;
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    private TbItem getItemById(@PathVariable Long itemId){
        TbItem item = itemService.getItemById(itemId);

        return item;
    }
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIGridResult getItemList(Integer page,Integer rows){
        EasyUIGridResult result = itemService.getItemList(page, rows);
         return result;
    }
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createItem(TbItem item,String desc,String itemParams){
        TaotaoResult result = itemService.createItem(item, desc,itemParams);

        return result;
    }

    @RequestMapping("/page/item/{itemId}")
    public String showItemParam(@PathVariable Long itemId, Model model){
        String html = itemService.getItemParamHtml(itemId);

        model.addAttribute("myhtml",html);
        return "itemparam";
    }
}
