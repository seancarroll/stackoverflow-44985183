package com.seancarroll.twoJdbcConnections;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FirstRowMapper implements RowMapper<First> {
    
    public static final FirstRowMapper ROW_MAPPER = new FirstRowMapper();
    
    @Override
    public First mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new First(rs.getString("name"));
    }
}
