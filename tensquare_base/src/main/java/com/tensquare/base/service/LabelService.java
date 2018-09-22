package com.tensquare.base.service;

import com.tensquare.base.dao.BaseDao;
import com.tensquare.base.pojo.Label;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 增加一个标签
     * @param label
     */
    public void addLabel(Label label) {
        label.setId(idWorker.nextId()+"");
        baseDao.save(label);
    }

    /**
     * 查询所有的标签
     * @return
     */
    public List<Label> findAll() {
        return baseDao.findAll();
    }

    /**
     * 主键查找
     * @param labelId
     * @return
     */
    public Label findOne(String labelId) {
        return baseDao.findById(labelId).get();
    }

    /**
     * 修改标签
     * @param label
     */
    public void update(Label label) {
        baseDao.save(label);
    }

    /**
     * 根据对象删除
     * @param label
     */
    public void delete(Label label) {
        baseDao.delete(label);

    }

    /**
     * 主键删除
     * @param labelId
     */
    public void deleteById(String labelId) {
        baseDao.deleteById(labelId);
    }

    /**
     * 条件查找
     * 根据标签名称以及是否推荐来查找
     * @param label
     * @return
     */
    public List<Label> searchByCondition(Label label) {
        return baseDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //标签名按照模糊查询
                if (label.getLabelname() != null && !label.getLabelname().trim().equals("")) {
                    Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(labelname);
                }
                //是否推荐
                if (label.getRecommend() != null && !label.getRecommend().trim().equals("")) {
                    Predicate recommend = cb.equal(root.get("recommend").as(String.class), "1");
                    list.add(recommend);
                }
                Predicate[] predicates = new Predicate[list.size()];
                list.toArray(predicates);
                return cb.and(predicates);
            }
        });
    }

    /**
     * SpringDataJPA的分页查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> findByPage(Label label, int page, int size) {
        return baseDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !label.getLabelname().equals("")) {
                    Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(labelname);
                }
                if (label.getRecommend() != null && !label.getRecommend().equals("")) {
                    Predicate recommend = cb.equal(root.get("recommend").as(String.class), "1");
                    list.add(recommend);
                }
                Predicate[] predicates = new Predicate[list.size()];
                list.toArray(predicates);
                return cb.and(predicates);
            }
        }, PageRequest.of(page-1, size));//此处减一，因为索引是从0开始的
    }
}
