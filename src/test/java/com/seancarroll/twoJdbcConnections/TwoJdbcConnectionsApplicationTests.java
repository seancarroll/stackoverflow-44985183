package com.seancarroll.twoJdbcConnections;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwoJdbcConnectionsApplicationTests {

    @Autowired
    @Qualifier("firstJdbcTemplate")
    private JdbcTemplate firstJdbcTemplate;
    
    @Autowired
    @Qualifier("secondJdbcTemplate")
    private JdbcTemplate secondJdbcTemplate;
    
	@Test
	public void canQueryFromTwoDataSources() {
	    
	    List<First> f = firstJdbcTemplate.query("SELECT * FROM first", FirstRowMapper.ROW_MAPPER);
	    
	    assertEquals(1, f.size());
	    assertEquals("sean", f.get(0).getName());
	    
        List<Second> s = secondJdbcTemplate.query("SELECT * FROM second", SecondRowMapper.ROW_MAPPER);
        
        assertEquals(1, s.size());
        assertEquals("bob", s.get(0).getName());
	}
	
	@Test
	@Transactional(transactionManager = "FirstDataSourceTransactionManager")
	@Rollback
	public void canUpdateFirst() {
	    int firstUpdates = firstJdbcTemplate.update("UPDATE first set name = 'shawn' WHERE name = 'sean'");	    
	    assertEquals(1, firstUpdates);
	}
	
	@Test
    @Transactional(transactionManager = "SecondDataSourceTransactionManager")
    @Rollback
    public void canUpdateSecond() {
        int secondUpdates = secondJdbcTemplate.update("UPDATE second set name = 'robert' WHERE name = 'bob'");
        assertEquals(1, secondUpdates);
        
    }

}
