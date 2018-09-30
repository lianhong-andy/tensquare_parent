package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {

    /**
     * 根据联合主键查找好友记录
     * @param userid
     * @param friendid
     * @return
     */
    public Friend findFriendByUseridAndFriendid(String userid,String friendid);

    /**
     * 根据联合主键更新islike的状态
     * @param userid
     * @param friendid
     */
    @Modifying//修改操作要使用Modifying注解
    @Query(value = "UPDATE tb_friend SET islike = ? WHERE userid = ? AND friendid = ?" ,nativeQuery = true)
    public void updateIslikeByUseridAndFAndFriendid(String islike,String userid,String friendid);

    /**
     * 通过联合主键删除喜欢好友记录
     * @param userid
     * @param friendid
     */
    @Modifying
    @Query(value = "DELETE FROM tb_friend WHERE userid=? AND friendid=?",nativeQuery = true)
    void deleteFriendByUseridAndFriendId(String userid, String friendid);
}
