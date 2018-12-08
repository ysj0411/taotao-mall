package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.comon.pojo.EasyUIGridResult;
import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.po.TbItem;

import com.taotao.po.TbItemParam;
import com.taotao.po.TbItemParamExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品规格参数模板管理service
 *
 */
@Service
public class ItemParamService implements com.taotao.service.ItemParamService {
    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public TaotaoResult getItemParamByCid(Long cid) {
        //根据cid查询规格参数模板
        TbItemParamExample example=new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        //执行查询返回list
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if(list!=null&&list.size()>0){
            TbItemParam itemParam=list.get(0);
            return TaotaoResult.ok(itemParam);
        }
        return TaotaoResult.ok();

    }

    @Override
    public TaotaoResult insertItemParam(Long cid, String paramData) {
        TbItemParam itemParam=new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        tbItemParamMapper.insert(itemParam);
        //插入记录
        tbItemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

    @Override
    public EasyUIGridResult getItemParamList(int page, int rows) {


        PageHelper.startPage(page, rows);
        TbItemParamExample example=new TbItemParamExample();
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);

        PageInfo<TbItemParam> pageInfo=new PageInfo<>(list);
        EasyUIGridResult result=new EasyUIGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }
}
