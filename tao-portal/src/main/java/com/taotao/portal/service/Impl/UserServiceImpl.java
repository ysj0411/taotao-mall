package com.taotao.portal.service.Impl;

import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.comon.utils.CookieUtils;
import com.taotao.comon.utils.HttpClientUtil;
import com.taotao.po.TbUser;
import com.taotao.portal.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户管理
 */
@Service
public class UserServiceImpl  implements UserService {
    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;
    @Value("${SSO_USER_TOKEN_SERVICE}")
    private String SSO_USER_TOKEN_SERVICE;
    @Override
    public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        //判断token是否有值
        if(StringUtils.isBlank(token)){
            return  null;
        }
        String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN_SERVICE + token);
        //把json转换成java对象
        TaotaoResult result = TaotaoResult.format(json);
        if(result.getStatus()!=200){
            return null;
        }
        //正常返回取用户对象
        result = TaotaoResult.formatToPojo(json, TbUser.class);

        try {
            TbUser user = (TbUser) result.getData();
            return user;
        }catch (Exception e){
            e.printStackTrace();

            return null;
        }

    }
}
