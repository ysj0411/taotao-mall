package com.taotao.controller;


import com.taotao.comon.pojo.EasyUITreeNode;
import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatgoryController {
    @Autowired
    ContentCategoryService contentCategoryService;
    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
        return  list;
    }
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult contentNode(Long parentId,String name){
        TaotaoResult result=contentCategoryService.insertCategory(parentId,name);
        return result;
    }
}
