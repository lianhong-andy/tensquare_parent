package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 添加记录
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addArtilce(@RequestBody Article article){
        articleService.addArtilce(article);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @RequestMapping(value = "/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result findByCondition(@PathVariable String keywords,@PathVariable int page,@PathVariable int size){
        Page<Article> pages = articleService.findByCondition(keywords, page, size);
        return new Result(true,StatusCode.OK,"查询成功",
                new PageResult<Article>(pages.getTotalElements(),pages.getContent()));

    }
}
