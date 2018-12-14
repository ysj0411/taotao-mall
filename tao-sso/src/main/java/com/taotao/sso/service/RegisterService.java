package com.taotao.sso.service;


import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.po.TbUser;

public interface RegisterService {
    TaotaoResult checkData(String param,int type);
    TaotaoResult register(TbUser user);
}
