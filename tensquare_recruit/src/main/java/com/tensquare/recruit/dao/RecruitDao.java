package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    /**
     * 查找推荐职位列表
     * @return
     */
    List<Recruit> findByStateEquals(String state);

    /**
     * 查找最新职位列表，只要前六条记录
     */
    List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
