package com.taotao.portal.service.Impl;

import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.comon.utils.HttpClientUtil;
import com.taotao.comon.utils.JsonUtils;
import com.taotao.po.TbItem;
import com.taotao.po.TbItemDesc;
import com.taotao.po.TbItemParamItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * 查询商品信息
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_ITEM_BASE_URL}")
    private String REST_ITEM_BASE_URL;
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_ITEM_DESC_URL}")
    private String REST_ITEM_DESC_URL;
    @Value("${REST_ITEM_PARAM_URL}")
    private String REST_ITEM_PARAM_URL;
    //根据商品id查询商品信息

    @Override
    public String getItemDescById(Long itemId) {
        //根据商品id调用tao-rest获得数据
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
        //转换成java对象
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
        //取商品描述信息
        TbItemDesc itemDesc=(TbItemDesc)taotaoResult.getData();
        String desc = itemDesc.getItemDesc();
        return desc;

    }

    @Override
    public String getItemParamById(Long itemId) {
        //根据商品id获得对应的规格参数
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
        //取规格参数
        TbItemParamItem itemParamItem=(TbItemParamItem) taotaoResult.getData();
        String paramJson = itemParamItem.getParamData();
         //把规格参数的json转换成java对象
        List<Map> paramList = JsonUtils.jsonToList(paramJson, Map.class);


        //将参数信息转换成html
        StringBuffer sb = new StringBuffer();
        //sb.append("<div>");
        sb.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
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
    public TbItem getItemById(Long itemId) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemId);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, PortalItem.class);
        //取商品对象
        TbItem item=(TbItem) taotaoResult.getData();
        return item;
    }


}
