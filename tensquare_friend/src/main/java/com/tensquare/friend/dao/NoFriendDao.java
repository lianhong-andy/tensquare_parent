package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoFriendDao extends JpaRepository<NoFriend,String> {
    /**
     * 添加非好友
     * @param userid
     * @param friendid
     * @return
     */
    public NoFriend findNoFriendByUseridAndFriendid(String userid,String friendid);
}
