package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;



/**
 * 吐槽数据访问层
 */
public interface CollectionDao extends MongoRepository<Collection,String> {
}
