package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("tensquare-user")
public interface UserClient {
    @RequestMapping(value = "/user/addFriend/{userid}/{friendid}/{count}",method = RequestMethod.PUT)
    public void addFriend(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("count") int count);
}
