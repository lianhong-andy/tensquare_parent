package com.tensquare.gathering.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.tensquare.gathering.dao.GatheringDao;
import com.tensquare.gathering.pojo.Gathering;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class GatheringService {

	@Autowired
	private GatheringDao gatheringDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Gathering> findAll() {
		return gatheringDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Gathering> findSearch(Map whereMap, int page, int size) {
		Specification<Gathering> specification = gatheringDao.createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return gatheringDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Gathering> findSearch(Map whereMap) {
		Specification<Gathering> specification = gatheringDao.createSpecification(whereMap);
		return gatheringDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	@Cacheable(value = "gathering",key = "#id")
	public Gathering findById(String id) {
		return gatheringDao.findById(id).get();
	}

	/**
	 * 增加
	 * 增加之前清除缓存
	 * @param gathering
	 */
	@CacheEvict(value = "gathering",key = "#gathering.id")//清除缓存
	public void add(Gathering gathering) {
		gathering.setId( idWorker.nextId()+"" );
		gatheringDao.save(gathering);
	}

	/**
	 * 修改
	 * 修改之前删除缓存
	 * @param gathering
	 */
	@CacheEvict(value = "gathering",key = "#gathering.id")//清除缓存
	public void update(Gathering gathering) {
		gatheringDao.save(gathering);
	}

	/**
	 * 删除
	 * 删除之前删除缓存
	 * @param id
	 */
	@CacheEvict(value = "gathering",key = "#gathering.id")//清除缓存
	public void deleteById(String id) {
		gatheringDao.deleteById(id);
	}

}
