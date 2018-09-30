package com.tensquare.user.controller;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.prism.shader.Mask_TextureSuper_AlphaTest_Loader;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private JwtUtil jwtUtil;

	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 用户注册
	 * 1.获取短信验证码
	 * 2.校验短信验证码，通过后进行验证
	 */

	/**
	 * 发送手机验证码
	 * 1.随机生成验证码
	 * 2.将验证码存储到redis中
	 * 3.将验证码发送给用户
	 * 4.将验证码在控制台输出（测试用）
	 */
	@RequestMapping(value = "sendsms/{mobile}",method = RequestMethod.POST)
	public Result sendSms(@PathVariable String mobile) throws UnsupportedEncodingException {
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送验证码成功");
	}

	/**
	 * 用户注册
	 */
	@RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
	public Result add(@PathVariable String code,@RequestBody User user){
		userService.register(code,user);
		return new Result(true,StatusCode.OK,"注册成功");
	}

	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody User user){
		user = userService.login(user);
		if(user==null){
			return new Result(false,StatusCode.LOGINERROR,"登录失败");
		}
		String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
		Map map = new HashMap();
		map.put("token",token);
		map.put("name",user.getNickname());
		return new Result(true,StatusCode.OK,"登录成功",map);
	}

	/**
	 * 添加喜欢用户/添加不喜欢用户
	 * @param userid
	 * @param friendid
	 * @return
	 */
	@RequestMapping(value = "/addFriend/{userid}/{friendid}/{count}",method = RequestMethod.PUT)
	public void addFriend(@PathVariable String userid,@PathVariable String friendid,@PathVariable int count){
		userService.addFriend(count,userid,friendid);
	}

	
}
