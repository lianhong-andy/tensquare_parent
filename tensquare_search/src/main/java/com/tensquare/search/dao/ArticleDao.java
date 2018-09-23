package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleDao extends ElasticsearchRepository<Article,String> {
    /**
     * 根据关键词进行搜索并将结果进行分页
     */
    public Page<Article> findByTitleOrContent(String title, String content, Pageable pageable);
}
