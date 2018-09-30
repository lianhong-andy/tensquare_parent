package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
    /**
     * 根据手机号查找用户
     */
    User findByMobile(String mobile);

    /**
     * 用户的关注数+count
     * @param userid
     */
    @Modifying//修改操作要使用Modifying注解
    @Query(value = "UPDATE tb_user SET followcount = followcount+? WHERE id=?;",nativeQuery = true)
    void updateFollowById(int count,String userid);

    /**
     * 用户粉丝加+count
     * @param count
     * @param friendid
     */
    @Modifying//修改操作要使用Modifying注解
    @Query(value = "UPDATE tb_user SET fanscount = fanscount+? WHERE id=?;",nativeQuery = true)
    void updateFansById(int count, String friendid);
}
