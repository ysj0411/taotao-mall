package com.taotao.controller;

import com.taotao.comon.pojo.EasyUIGridResult;
import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * 商品规格参数模板管理Controller
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public TaotaoResult getItemCatByCid(@PathVariable Long cid){
        TaotaoResult result= itemParamService.getItemParamByCid(cid);
        return result;
    }
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable Long cid,String paramData){
        TaotaoResult result = itemParamService.insertItemParam(cid, paramData);
        return result;
    }
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIGridResult getItemParamList(Integer page, Integer rows){
        EasyUIGridResult result = itemParamService.getItemParamList(page, rows);
        return result;
    }


}
