package com.taotao.controller;

import com.taotao.comon.pojo.PictureResult;
import com.taotao.comon.utils.JsonUtils;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

//图片上传Controller
@Controller
public class PictureController {
    @Autowired
    PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        PictureResult result=pictureService.uploadPic(uploadFile);
        //需要把这个java对象手工转换成json数据
        String json=JsonUtils.objectToJson(result);
        return json;

    }


}
