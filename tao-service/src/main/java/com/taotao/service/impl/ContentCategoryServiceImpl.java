package com.taotao.service.impl;

import com.taotao.comon.pojo.EasyUITreeNode;
import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;

import com.taotao.po.TbContentCategory;
import com.taotao.po.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理
 *
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public TaotaoResult insertCategory(Long parentId, String name) {
        //创建一个pojo对象
        TbContentCategory tbContentCategory=new TbContentCategory();

        tbContentCategory.setName(name);
        tbContentCategory.setParentId(parentId);
        //正常（1）删除（2）
        tbContentCategory.setStatus(1);
        tbContentCategory.setIsParent(false);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排序，取值范围：大于零的整数
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        // 插入数据
        contentCategoryMapper.insert(tbContentCategory);
        //取返回的主键
        Long id = tbContentCategory.getId();
        //判断父节点的isparent属性
        //查询父节点
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parentNode.getIsParent()){
            parentNode.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentNode);

        }
        //返回主键
        return TaotaoResult.ok(id);
    }

    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> resultList=new ArrayList<>();
        for(TbContentCategory tbContentCategory:list){
            EasyUITreeNode node=new EasyUITreeNode();
            //添加到列表‘
           node.setId(tbContentCategory.getId());
           node.setText(tbContentCategory.getName());
           node.setState(tbContentCategory.getIsParent()?"closed":"open");

           resultList.add(node);

        }
        return resultList;
    }
}
