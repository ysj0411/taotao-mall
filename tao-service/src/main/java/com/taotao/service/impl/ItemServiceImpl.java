package com.taotao.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.comon.pojo.EasyUIGridResult;
import com.taotao.comon.utils.IDUtils;
import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.comon.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.po.*;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Override
    public TbItem getItemById(Long itemId){
        TbItemExample example=new TbItemExample();
        //创建查询条件
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);

        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
//判断tbItem是否为空
        TbItem tbItem=null;
        if(tbItems!=null&&tbItems.size()>0){
            tbItem= tbItems.get(0);
        }
        return tbItem;
    }


    public String getItemParamHtml(Long itemId) {

        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (null == list || list.isEmpty()) {
            return "";
        }
        //取出参数信息
        TbItemParamItem itemParamItem = list.get(0);
        String paramData = itemParamItem.getParamData();
        //把json数据转换成java对象
        List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
        //将参数信息转换成html
        StringBuffer sb = new StringBuffer();
        //sb.append("<div>");
        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
        sb.append("    <tbody>\n");
        for (Map map : paramList) {
            sb.append("        <tr>\n");
            sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
            sb.append("        </tr>\n");
            List<Map> params = (List<Map>) map.get("params");
            for (Map map2 : params) {
                sb.append("        <tr>\n");
                sb.append("            <td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
                sb.append("            <td>" + map2.get("v") + "</td>\n");
                sb.append("        </tr>\n");
            }
        }
        sb.append("    </tbody>\n");
        sb.append("</table>");
        //sb.append("</div>");
        return sb.toString();
    }
    @Override
    public TaotaoResult createItem(TbItem item, String desc,String itemParam) {
        //生成商品id
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte)1);
        //创建时间和更新时间
        Date date=new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //插入商品表
        tbItemMapper.insert(item);
        //商品描述
        TbItemDesc itemDesc =new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //插入商品描述数据
        tbItemDescMapper.insert(itemDesc);
        //添加商品规格参数处理
        TbItemParamItem itemParamItem=new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);

        //插入数据
        tbItemParamItemMapper.insert(itemParamItem);
        return TaotaoResult.ok();
    }

    @Override
   public EasyUIGridResult getItemList(int page,int rows){
        PageHelper.startPage(page, rows);
        TbItemExample example=new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);

        PageInfo<TbItem> pageInfo=new PageInfo<>(list);
        EasyUIGridResult result=new EasyUIGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }


    public TbItemMapper getItemMapper() {
        return tbItemMapper;
    }
}
