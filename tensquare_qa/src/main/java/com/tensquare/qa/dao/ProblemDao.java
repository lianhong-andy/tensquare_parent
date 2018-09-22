package com.tensquare.qa.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**
     * 查找最新回答列表
     */
    @Query(value = "SELECT * FROM tb_problem JOIN tb_pl WHERE tb_problem.id = tb_pl.problemid AND tb_pl.labelid = ? ORDER BY tb_problem.replytime DESC",nativeQuery = true)
    List<Problem> findNewListByLabelId(Integer label, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem JOIN tb_pl WHERE tb_problem.id = tb_pl.problemid AND tb_pl.labelid = '1' ORDER BY tb_problem.reply DESC",nativeQuery = true)
    List<Problem> findHotListByLabelid(int label, PageRequest pageRequest);

    @Query(value = "SELECT * FROM tb_problem JOIN tb_pl WHERE tb_problem.id = tb_pl.problemid AND tb_pl.labelid = '1' AND tb_problem.reply = '0' ORDER BY tb_problem.reply DESC",nativeQuery = true)
    List<Problem> findWaitList(int label, PageRequest pageRequest);

}
