package com.taotao.service;

import com.taotao.comon.pojo.EasyUIGridResult;
import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.po.TbItem;

public interface ItemService {
    TbItem getItemById(Long itemId);
    EasyUIGridResult getItemList(int page,int rows);
    TaotaoResult createItem(TbItem item,String desc,String itemParam);
    String getItemParamHtml(Long itemId);
}
