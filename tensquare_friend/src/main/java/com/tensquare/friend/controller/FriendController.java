package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserClient userClient;

    /**
     * 首先要判断用户是否登录(配合拦截器)，如果用户没有登录的话，是不允许添加好友的，提示用户先登录
     * 根据type的值判断要添加喜欢还是添加不喜欢
     * type=1:喜欢好友，前端传要添加为好友的用户id过来（friendid),首先要判断是否已经添加过该好友为喜欢好友了，不能重复添加；
     * 然后往Friend里面添加一条记录，islike状态修改为0，然后更新user表里面的fanscount,followcount
     * 最后还要判断是否相互喜欢，如果是的话，将islike状态修改为1
     * type=2:不喜欢好友，首先要进行判断，不能重复添加不喜欢；
     * 然后往不喜欢列表里面添加一条记录，最后更新user表里面的fanscount,followcount
     * /like/{friendid}/{type}
     * @param friendid
     * @param type
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
        //1.判断用户是否登录
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims==null){
            //用户未登录
            return new Result(false, StatusCode.LOGINERROR,"没有权限");
        }
        //获取userid
        String userid = claims.getId();
        if(type!=null&&type.equals("1")){
            //添加喜欢
            //查询是否重复添加
            int flag = friendService.findFriendByUseridAndFriendid(userid,friendid);
            if(flag==1){
                //重复添加
                return new Result(false,StatusCode.REPERROR,"您已添加过该好友了");
            }
            if(flag==0){
                //未添加过，可以添加，往Friend表里添加一条记录
                friendService.addFriend(userid,friendid);
                //user表中Friendid对应的用户fanscount+1,userId对应的用户followcount+1
                userClient.addFriend(userid,friendid,1);
                //查询被喜欢方是否已经喜欢过该用户
                int flag1 = friendService.findFriendByUseridAndFriendid(friendid, userid);
                if(flag1==1){
                    //双向喜欢，更改状态
                    friendService.updateIslike("1",userid,friendid);
                    friendService.updateIslike("1",friendid,userid);
                }
                return new Result(true,StatusCode.OK,"添加成功");
            }
        }else if(type!=null&&type.equals("2")){
            //添加不喜欢
            //先判断是否重复添加
            int noFriendFlag = friendService.findNoFriendUseridAndFriendid(userid, friendid);
            if(noFriendFlag==1){
                //重复添加
                return new Result(false,StatusCode.REPERROR,"不能重复添加");
            }else if(noFriendFlag==0){
                //允许添加
                friendService.addNoFriend(userid,friendid);
                return new Result(true,StatusCode.OK,"添加成功");
            }
        }else{
            //参数异常
            return new Result(false,StatusCode.ERROR,"参数异常");
        }
        return null;
    }


    /**
     * 根据好友id删除好友业务分析：
     * 先判断是否登录
     * 如果登录有权限删除，否则没有权限
     * 根据friendid从数据库中查找对应好友
     * 判断好友是否存在，如果不存在，不能删除
     * 如果存在，进行删除操作
     * 将好友从friend中删除，给不喜欢好友添加一条记录
     * 将friendid—>userid对应的喜欢记录的islike状态改为0
     * @param friendid
     * @return
     */
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result removeFriend(@PathVariable String friendid){
        //1.判断用户是否登录
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims==null){
            //用户未登录
            return new Result(false, StatusCode.LOGINERROR,"没有权限");
        }
        //获取userid
        String userid = claims.getId();
        int flag = friendService.findFriendByUseridAndFriendid(userid, friendid);
        if(flag==0){
            //不存在好友记录，不可以删除
            return new Result(false,StatusCode.REPERROR,"没有权限");
        }else if(flag==1){
            //存在好友记录，可以删除
            friendService.deleteFriendByUseridAndFriendId(userid,friendid);
            //往好友表里面添加一条记录
            friendService.addNoFriend(userid,friendid);
            //更新相应的粉丝数和关注数
            userClient.addFriend(userid,friendid,-1);
            //将friendid->userid对应的islike修改为“0”,不管是否存在
            //user表中Friendid对应的用户fanscount-1,userId对应的用户followcount-1
            friendService.updateIslike("0",friendid,userid);
            return new Result(true,StatusCode.OK,"删除成功");
        }
        return null;
    }

}
