package com.taotao.service;

import com.taotao.comon.pojo.PictureResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface PictureService {

    PictureResult uploadPic(MultipartFile picFile);
}
