package com.taotao.service;

import com.taotao.comon.pojo.EasyUIGridResult;
import com.taotao.comon.pojo.TaotaoResult;

public interface ItemParamService {
    TaotaoResult getItemParamByCid(Long cid);
    TaotaoResult insertItemParam(Long cid,String paramData);
    EasyUIGridResult getItemParamList(int page, int rows);
}
