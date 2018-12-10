package com.taotao.search.dao.Impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * solr查询dao
 */
@Service
public class SearchDaoImpl implements SearchDao {
    @Autowired
    SolrServer solrServer;
    @Override
    public SearchResult search(SolrQuery query) throws Exception {
        QueryResponse response = solrServer.query(query);
        List<Item> items=new ArrayList<>();
        //取查询结果列表
        SolrDocumentList solrDocumentList = response.getResults();
        for(SolrDocument solrDocument:solrDocumentList){
           //创建一个SearchItem对象
            Item item = new Item();
            item.setCategory_name((String)solrDocument.get("item_category_name"));
             item.setId((String) solrDocument.get("id"));
             item.setImage((String)solrDocument.get("item_image"));
             item.setPrice((Long)solrDocument.get("item_price"));
             item.setSell_point((String)solrDocument.get("item_sell_point"));

             //高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle;
            if(list!=null&&list.size()>0){
                //取高亮后的结果
                itemTitle = list.get(0);
            }else {
                itemTitle=(String)solrDocument.get("item_tile");
            }
            item.setTitle(itemTitle);
            items.add(item);
        }
        SearchResult result=new SearchResult();
        result.setItemList(items);
//        查询结果总数量
        result.setRecordCount(solrDocumentList.getNumFound());

        return result;
    }
}
