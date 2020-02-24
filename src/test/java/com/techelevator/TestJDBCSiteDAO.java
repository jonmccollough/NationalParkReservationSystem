package com.techelevator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.JDBCSiteDAO;

public class TestJDBCSiteDAO extends DAOIntegrationTest {
	
	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO dao;
	private int dummyCampID;
	String sqlInsertDummySite1;
	String sqlInsertDummySite2;
	JdbcTemplate jdbcTemplate;


	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/parks");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		dao = new JDBCSiteDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sqlInsertDummyPark = "INSERT INTO park VALUES (DEFAULT, 'TESTPARKNAME', 'TESTPARKLOC', '2222-01-01', 1, 1, 'TESTPARKDESC')";
		jdbcTemplate.update(sqlInsertDummyPark);
		String sqlInsertDummyCamp = "INSERT INTO campground VALUES (DEFAULT, (SELECT park_id FROM park WHERE name = 'TESTPARKNAME'), 'TESTCAMPNAME', '13', '14', 10)";
		jdbcTemplate.update(sqlInsertDummyCamp);
		sqlInsertDummySite1 = "INSERT INTO site VALUES (DEFAULT, (SELECT campground_id FROM campground WHERE name = 'TESTCAMPNAME'), 1, 5, false, 10, false)";
		sqlInsertDummySite2 = "INSERT INTO site VALUES (DEFAULT, (SELECT campground_id FROM campground WHERE name = 'TESTCAMPNAME'), 2, 5, false, 10, false)";

		
		String sqlGetCampgroundID = "SELECT campground_id FROM campground WHERE name = 'TESTCAMPNAME'";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampgroundID);
		results.next();
		Long dummyCampIDLg = results.getLong("campground_id");
		dummyCampID = dummyCampIDLg.intValue();
		
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void test_cannot_make_res_in_past() {
		jdbcTemplate.update(sqlInsertDummySite1);
		jdbcTemplate.update(sqlInsertDummySite2);
		List <Site> testList = dao.viewAvailSitesByDate(dummyCampID, LocalDate.of(2019, 12, 12), LocalDate.of(2019, 12, 24));
		assertEquals(0,testList.size());
		
	}

	@Test
	public void test_cannot_check_out_before_check_in() {
		jdbcTemplate.update(sqlInsertDummySite1);
		jdbcTemplate.update(sqlInsertDummySite2);
		List <Site> testList = dao.viewAvailSitesByDate(dummyCampID, LocalDate.of(2020, 12, 24), LocalDate.of(2020, 12, 20));
		assertEquals(0,testList.size());
		
	}
	
	@Test
	public void test_get_list_of_all_available_sites_by_date() {
		jdbcTemplate.update(sqlInsertDummySite1);
		jdbcTemplate.update(sqlInsertDummySite2);
		List <Site> testList = dao.viewAvailSitesByDate(dummyCampID, LocalDate.of(2020, 12, 24), LocalDate.of(2020, 12, 25));
		assertEquals(2,testList.size());
	}
	
	@Test 
	public void test_no_sites_available() {
		List <Site> testList = dao.viewAvailSitesByDate(dummyCampID, LocalDate.of(2020, 12, 24), LocalDate.of(2020, 12, 25));
		assertEquals(0,testList.size());
	}
	
	
}
	


