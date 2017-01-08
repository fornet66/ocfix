package com.xcloud.ocfix;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<UserVO> {

    @Override
    public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserVO user = new UserVO();
        user.setUid(rs.getString("uid"));
        user.setStorage(rs.getInt("numeric_id"));
        return user;
    }

}
