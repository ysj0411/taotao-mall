package com.taotao.sso.controller;

import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.comon.utils.ExceptionUtil;
import com.taotao.sso.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 用户登录
 */
@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response){

        try {
            TaotaoResult result=loginService.login(username,password,request,response);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    @RequestMapping("user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        try {
            TaotaoResult result=loginService.getUserByToken(token);

            if(StringUtils.isNotBlank(callback)){
                MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;

            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

}
