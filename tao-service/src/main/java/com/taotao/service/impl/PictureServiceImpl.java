package com.taotao.service.impl;

import com.taotao.comon.pojo.PictureResult;
import com.taotao.comon.utils.FastDFSClient;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//图片上传service
@Service
public class PictureServiceImpl implements PictureService {


        @Value("${IMAGE_SERVER_BASE_URL}")
        private String IMAGE_SERVER_BASE_URL;
    @Override
    public PictureResult uploadPic(MultipartFile picFile) {

        PictureResult result=new PictureResult();
        //判断图片是否为空
        if(picFile.isEmpty()){
            result.setError(1);
            result.setMessage("图片为空");
            return result;

        }
        try {
            //取文件扩展名
            String originalFilename = picFile.getOriginalFilename();
            //取扩展名不要"."
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            FastDFSClient client=new FastDFSClient("E:\\works1\\tao-web\\src\\main\\resources\\properties\\client.conf");
            String url = client.uploadFile(picFile.getBytes(), extName);
            url =IMAGE_SERVER_BASE_URL+url;
            result.setError(0);
            result.setUrl(url);
        }catch (Exception e){
            e.printStackTrace();
            result.setError(1);
            result.setMessage("图片上传失败");
        }


        return result;
    }



}
