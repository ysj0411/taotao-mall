package com.taotao.portal.service.Impl;

import com.taotao.comon.pojo.TaotaoResult;
import com.taotao.comon.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 搜索服务
 *
 */
@Service
public class SearchServiceImol implements SearchService {
   @Value("${SEARCH_BASE_URL}")
   private String SEARCH_BASE_URL;
    @Override
    public SearchResult search(String keyword, int page, int rows) {
        //调用服务查询服务列表
        Map<String,String> param=new HashMap<>();
        param.put("keyword",keyword);
        param.put("page",page+"");
        param.put("rows",rows+"");

        String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
        //转换成java对象
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
        //取返回的结果
        SearchResult searchResult = (SearchResult) taotaoResult.getData();

        return searchResult;
    }
}
