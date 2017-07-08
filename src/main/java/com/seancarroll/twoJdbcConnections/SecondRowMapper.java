package com.seancarroll.twoJdbcConnections;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SecondRowMapper implements RowMapper<Second> {
    
    public static final SecondRowMapper ROW_MAPPER = new SecondRowMapper();
    
    @Override
    public Second mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Second(rs.getString("name"));
    }
}