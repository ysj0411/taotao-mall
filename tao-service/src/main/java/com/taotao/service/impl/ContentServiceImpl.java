package com.taotao.service.impl;

import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.po.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 内容管理
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    TbContentMapper contentMapper;
    @Override
    public TaotaoResult insertContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入数据
        contentMapper.insert(content);
        return TaotaoResult.ok();
    }
}
