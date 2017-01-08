package com.xcloud.ocfix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class UserUtils {

    @Autowired
    private UserDao userDao;

    public List<UserVO> findUsers() {
        return userDao.findAll();
    }

}
