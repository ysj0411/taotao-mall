package com.taotao.portal.service;

import com.taotao.po.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    TbUser getUserByToken(HttpServletRequest request,HttpServletResponse response);
}
