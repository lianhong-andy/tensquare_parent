package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class ArticleService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ArticleDao articleDao;

    /**
     * 添加文章
     * @param article
     */
    public void addArtilce(Article article){
        article.setId(idWorker.nextId()+"");
        articleDao.save(article);
    }

    /**
     * 根据关键词进行搜索，将结果进行分页展示
     */
    public Page<Article> findByCondition(String keywords,int page,int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return articleDao.findByTitleOrContent(keywords,keywords,pageable);
    }
}
