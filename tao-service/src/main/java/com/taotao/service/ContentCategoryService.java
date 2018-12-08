package com.taotao.service;

import com.taotao.comon.pojo.EasyUITreeNode;
import com.taotao.comon.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCatList(Long parentId);
     TaotaoResult insertCategory(Long parentId,String name);
}

