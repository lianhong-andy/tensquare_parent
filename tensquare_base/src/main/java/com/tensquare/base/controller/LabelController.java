package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
@CrossOrigin//处理跨域请求
public class LabelController {
    @Autowired
    private LabelService labelService;

    /**
     * 增加标签
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addLabel(@RequestBody Label label){
        System.out.println(label);
         labelService.addLabel(label);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 查询所有标签
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Label> list =  labelService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    /**
     * 主键查找
     */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findOne(@PathVariable String labelId){
        Label label = labelService.findOne(labelId);
        return new Result(true, StatusCode.OK,"查找成功",label);
    }

    /**
     * 修改标签
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId,@RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK,"修改成功");
    }
    /**
     * 删除标签
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Result delete(@RequestBody Label label){
        labelService.delete(label);
        return new Result(true, StatusCode.OK,"删除成功");
    }
    /**
     * 删除标签
     */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        int i = 1/0;
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK,"删除成功");
    }


    /**
     * 条件查询
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result searchByCondition(@RequestBody Label label){
        List<Label> list = labelService.searchByCondition(label);
        if(list!=null&&list.size()>0) {
            return new Result(true, StatusCode.OK,"查询成功", list);
        }else{
            return new Result(true, StatusCode.OK,"没有相关记录", list);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findByPage(@RequestBody Label label,@PathVariable int page,@PathVariable int size){
        Page<Label> pages = labelService.findByPage(label,page,size);
        if(pages!=null&&pages.getTotalElements()>0){
            return new Result(true, StatusCode.OK,"查找成功"
                    ,new PageResult(pages.getTotalElements(),pages.getContent()));
        }else{
            return new Result(true, StatusCode.OK,"没有改记录"
                    ,new PageResult(pages.getTotalElements(),pages.getContent()));
        }
    }






}

