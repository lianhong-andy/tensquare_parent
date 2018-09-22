package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;

@Service
public class CommentService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;
    /**
     * 增加文章
     * @param comment
     * @return
     */
    public void addComment(Comment comment) {
        comment.set_id(idWorker+"");
        comment.setPublishdate(new Date());
        commentDao.save(comment);
    }
}
