package com.tensquare.user.dao;

import com.tensquare.user.pojo.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogDao extends JpaRepository<Log,Long>,JpaSpecificationExecutor<Log> {
}
