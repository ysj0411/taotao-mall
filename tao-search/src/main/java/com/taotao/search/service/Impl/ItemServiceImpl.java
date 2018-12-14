package com.taotao.search.service.Impl;

import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importItemToIndex() throws Exception {
        //查询商品列表
        List<Item> itemList = itemMapper.getItemList();
        //将商品列表导入solr
        for (Item item : itemList) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            //将文档写入索引库
            solrServer.add(document);
        }
        //提交修改
        solrServer.commit();
        return TaotaoResult.ok();
}

}