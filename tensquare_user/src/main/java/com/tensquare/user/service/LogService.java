package com.tensquare.user.service;

import com.tensquare.user.dao.LogDao;
import com.tensquare.user.pojo.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    private LogDao logDao;
    public void save(Log log) {
        logDao.save(log);
    }
}
