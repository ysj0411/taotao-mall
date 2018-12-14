package com.taotao.sso.service;

import com.taotao.comon.pojo.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    TaotaoResult login(String username, String passeord, HttpServletRequest request, HttpServletResponse response);
    TaotaoResult getUserByToken(String token);
}
