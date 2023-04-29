package com.lomiguk.springapp.repository.rowMapper;

import com.lomiguk.springapp.model.priofile.Profile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginProfileRowMapper implements RowMapper<Profile> {
    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Profile.builder()
                .id(rs.getLong("id"))
                .login(rs.getString("login"))
                .admin(rs.getBoolean("isAdmin")).build();
    }
}
