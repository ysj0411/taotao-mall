package com.taotao.search.service.Impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 查询
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;
    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        //查询条件
        SolrQuery query =new SolrQuery();
        query.setQuery(queryString);
        //设置分页条件
        query.setStart((page-1)*rows);
        //设置默认搜索域
        query.set("df","item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
        query.setHighlightSimplePost("</font>");
        //执行查询
        SearchResult searchResult = searchDao.search(query);
        //计算总页数
        Long recordCount = searchResult.getRecordCount();
        int pageCount=(int)(recordCount/rows);
        if(recordCount%rows>0){
            pageCount++;
        }
        searchResult.setPageCount(pageCount);
        searchResult.setCurpage(page);


        return searchResult;
    }
}
