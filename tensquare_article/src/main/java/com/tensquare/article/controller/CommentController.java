package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 1.新增评论
     * 2.根据id查找评论列表
     * 3.根据id删除评论
     */

    /**
     * 1.新增评论
     * @param comment
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 2.根据id查找评论列表
     */

}
