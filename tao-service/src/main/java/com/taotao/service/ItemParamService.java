package com.taotao.service;

import com.taotao.comon.pojo.TaotaoResult;

public interface ItemParamService {
    TaotaoResult getItemParamByCid(Long cid);
    TaotaoResult insertItemParam(Long cid,String paramData);
}
