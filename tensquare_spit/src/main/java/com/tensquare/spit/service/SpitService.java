package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 查询吐槽列表
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();

    }

    /**
     * 根据吐槽id查找吐槽记录
     * @param spitId
     * @return
     */
    public Spit findById(String spitId) {
        return spitDao.findById(spitId).get();
    }

    /**
     * 增加一条吐槽记录
     * 如果是吐槽，创建一个吐槽记录
     * 如果是评论，创建一条评论记录，对应吐槽评论数量+1
     * @param spit
     */
    public void add(Spit spit) {
        //设置主键值
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());
        spit.setState("0");
        spit.setVisits(0);
        spit.setComment(0);
        if(spit.getParentid()!=null&&!spit.getParentid().trim().equals("")){
            //没有父id，说明是评论，有则是吐槽本身
            //创建一条评论记录，对应吐槽评论数量+1
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,Spit.class);
        }
        //保存评论/吐槽
        spitDao.save(spit);
    }

    /**
     * 根据id删除一条吐槽记录
     * @param spitId
     */
    public void deleteById(String spitId) {
        spitDao.deleteById(spitId);
    }

    /**
     * 根据id修改吐槽记录
     * @param spitId
     */
    public void updateById(String spitId,Spit spit) {
        spit.set_id(spitId);
        spitDao.save(spit);
    }

    /**
     * 条件查询,
     * @param spit
     * @return
     */
    public List<Spit> findByCondition(Spit spit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("visits").gt(spit.getVisits()));
        query.addCriteria(Criteria.where("content").is("我爱Kelly"));
        return mongoTemplate.find(query,Spit.class);
    }

    /**
     * 条件查询,
     * @param spit
     * @return
     */
    public Page<Spit> findPageByCondition(Spit spit,int page,int size) {
        PageRequest pageable = PageRequest.of(page-1,size);
        return spitDao.findByNickname(spit.getNickname(),pageable);
    }

    /**
     * 根据上级id查询吐槽数据
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentid(String parentid, int page, int size) {
        PageRequest pageable = PageRequest.of(page-1, size);
        return spitDao.findByParentid(parentid,pageable);
    }

    /**
     * 吐槽点赞
     * @param spitId
     */
    public void updateThumbup(String spitId) {
        //先查询
//        Spit spit = spitDao.findById(spitId).get();
//        spit.setThumbup(spit.getThumbup()+1);
//        spitDao.save(spit);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,Spit.class);
    }
}
