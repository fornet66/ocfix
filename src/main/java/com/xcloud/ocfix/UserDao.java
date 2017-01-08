package com.xcloud.ocfix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao implements IBaseDao<UserVO> {

    private Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insert(UserVO t) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int deleteById(UserVO t) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateById(UserVO t) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public UserVO findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UserVO> findAll() {
        String sql = "select uid, numeric_id from oc_users a, oc_storages b where concat('home::',a.uid) = b.id";
        try {
            return jdbcTemplate.query(sql, new Object[] {}, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.error("not find record in oc_filecache");
            return null;
        }
    }

    @Override
    public List<UserVO> findByCondition(UserVO cond) {
        // TODO Auto-generated method stub
        return null;
    }

}
