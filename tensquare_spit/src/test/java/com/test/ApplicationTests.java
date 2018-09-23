package com.test;

import com.tensquare.spit.SpitApplication;
import com.tensquare.spit.dao.CollectionDao;
import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Collection;
import com.tensquare.spit.pojo.Spit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpitApplication.class)
public class ApplicationTests {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private CollectionDao collectionDao;
    @Autowired
    private IdWorker idWorker;

    @Test
    public void contextLoads() {
//        mongoTemplate.createCollection(Collection.class);

//        List<Spit> all = spitDao.findAll();
//        Collection collection = new Collection();
//        collection.set_id(idWorker.nextId()+"");
//        collection.setCreateTime(new Date());
//        collection.setSpits(all);
//        collectionDao.save(collection);
        List<Collection> all = collectionDao.findAll();
        for (Collection collection : all) {
            List<Spit> spits = collection.getSpits();
            for (Spit spit : spits) {
                System.out.println(spit);
            }
        }
    }

}
