package com.taotao.service;

import com.taotao.comon.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
   List<EasyUITreeNode> getItemCatList(long parentId);


}
