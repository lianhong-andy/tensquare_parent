package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional//开启事务
public class FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;
    /**
     * 根据联合主键查询好友记录
     * @param userid
     * @param friendid
     * @return
     */
    public int findFriendByUseridAndFriendid(String userid, String friendid) {
        Friend friend = friendDao.findFriendByUseridAndFriendid(userid,friendid);
        if(friend==null){
            //没有记录，代表未添加过该好友
            return 0;
        }
        return 1;//1代表添加过该好友
    }

    /**
     * 添加好友，往Friend表里面添加一条记录
     * @param userid
     * @param friendid
     */
    public void addFriend(String userid, String friendid) {
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setIslike("0");//“0”代表单向喜欢
        friend.setFriendid(friendid);
        friendDao.save(friend);
    }

    /**
     * 更新islike的状态
     * @param islike
     * @param userid
     * @param friendid
     */
    public void updateIslike(String islike, String userid, String friendid) {
        friendDao.updateIslikeByUseridAndFAndFriendid(islike,userid,friendid);
    }

    /**
     * 根据联合主键查找不喜欢用户记录
     * @param userid
     * @param friendid
     */
    public int findNoFriendUseridAndFriendid(String userid, String friendid) {
        NoFriend noFriend = noFriendDao.findNoFriendByUseridAndFriendid(userid, friendid);
        if(noFriend==null){
            return 0;//没有记录，可以添加
        }
        return 1;//有记录，不能再添加
    }

    /**
     * 添加不喜欢用户
     * @param userid
     * @param friendid
     */
    public void addNoFriend(String userid, String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }

    /**
     * 删除好友
     * @param userid
     * @param friendid
     */
    public void deleteFriendByUseridAndFriendId(String userid, String friendid) {
        friendDao.deleteFriendByUseridAndFriendId(userid,friendid);
    }
}
