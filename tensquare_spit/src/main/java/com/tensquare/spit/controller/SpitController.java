package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * （1）基本增删改查API
     * （2）根据上级ID查询吐槽列表
     * （3）吐槽点赞
     * （4）发布吐槽
     */

    /**
     * 查询吐槽列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Spit> list = spitService.findAll();
        if(list!=null&&list.size()>0){
            return new Result(true,StatusCode.OK,"查询成功",list);
        }else{
            return new Result(true,StatusCode.OK,"没有相关记录");
        }
    }

    /**
     * 查询userid为1013的记录
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        Spit spit =  spitService.findById(spitId);
        if(spit!=null){
            return new Result(true,StatusCode.OK,"查询成功",spit);
        }else{
            return new Result(true,StatusCode.OK,"没有相关记录");
        }
    }

    /**
     * 增加一条吐槽记录
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"增加成功",spit);
    }

    /**
     * 删除一条吐槽记录
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 修改吐槽
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable String spitId,@RequestBody Spit spit){
        spitService.updateById(spitId,spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    //    @RequestMapping("/thumbup/{spitId}")
    /**
     * 根据条件查询spit列表
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findByCondition(@RequestBody Spit spit){
        List<Spit> list = spitService.findByCondition(spit);
        if(spit!=null){
            return new Result(true,StatusCode.OK,"查询成功",spit);
        }else{
            return new Result(true,StatusCode.OK,"没有相关记录");
        }
    }
    /**
     * 根据条件查询spit列表,分页
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findPageByCondition(@RequestBody Spit spit,@PathVariable int page,@PathVariable int size){
        Page<Spit> list = spitService.findPageByCondition(spit,page,size);
        if(spit!=null){
            return new Result(true,StatusCode.OK,"查询成功",spit);
        }else{
            return new Result(true,StatusCode.OK,"没有相关记录");
        }
    }

    /**
     * 根据上级ID查询吐槽数据（分页）
     *
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentId(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageList = spitService.findByParentid(parentid,page,size);
        PageResult pageResult = new PageResult(pageList.getTotalElements(), pageList.getContent());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    /**
     * 吐槽点赞
     * thumbup/{spitId}
     */
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String spitId){
        //模拟查找用户名
        String userId = "1013";
        if(redisTemplate.opsForValue().get("thumbup_"+userId+"_"+spitId)!=null){
            //不为空说明该用户已经点赞过了
            return new Result(true,StatusCode.REPERROR,"不能重复点赞");
        }
        spitService.updateThumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userId+"_"+spitId,"1");
        return new Result(true,StatusCode.OK,"点赞成功");
    }

}
