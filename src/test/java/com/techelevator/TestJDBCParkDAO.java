package com.techelevator;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;


public class TestJDBCParkDAO {

	private static SingleConnectionDataSource dataSource;
//	private static final long TEST_PARK = 20;
	private ParkDAO dao;
	private Long testParkIdLong;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/parks");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@Before
	public void setup() {
		dao = new JDBCParkDAO(dataSource);
		String sqlInsertPark = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) VALUES (DEFAULT, 'Testney World', 'Tesstessee', '2001-01-01', 1000, 2, 'Test Description')";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertPark);
		String sqlGetParkId = "SELECT park_id FROM park WHERE name = 'Testney World' ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkId);
		results.next();
		testParkIdLong = results.getLong("park_id");
		int testParkIdInt = testParkIdLong.intValue();
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void getAllParks(){

		List<Park> actualPark = dao.getAllParks();
		Assert.assertNotNull("Did not find parks", actualPark);
	//	assertEquals(4, actualPark.size());
	}
	
	@Test
	public void getParkDetails(){
		Park newPark = makePark();
		dao.getAllParks();
		Park actualPark = dao.getParkDetails(testParkIdLong);
		
		Assert.assertNotNull("Park details were null, again", actualPark);
		assertParksAreEqual(newPark, actualPark);
	}
	
	@Test
	public void getNextParkIdValidTest() {
		
		dao.getAllParks();
		String sqlInsertParkIdTest = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) VALUES (DEFAULT, 'TestPark', 'TestLoc', '2001-01-01', 12000, 1000, 'Test Description2')";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertParkIdTest);
		String sqlGetParkId = "SELECT park_id FROM park WHERE name = 'Testney World' ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkId);
		results.next();
		testParkIdLong = results.getLong("park_id");
		Park actualPark = dao.getParkDetails(testParkIdLong);
		Assert.assertNotNull(actualPark);
	}
	
	@Test
	public void getNextParkIdInvalidTest() {
		dao.getAllParks();
		Park actualPark = dao.getParkDetails(0L);
		Assert.assertNull(actualPark);
	}
	
	
	private Park makePark() {
		Long parkId = testParkIdLong;
		String name = "Testney World";
		String location = "Tesstessee";
		Date establishDate = Date.valueOf("2001-01-01");
		int area = 1000;
		int visitors = 2;
		String description = "Test Description";
		
		Park newPark = new Park();
		newPark.setParkId(parkId);
		newPark.setName(name);
		newPark.setLocation(location);
		newPark.setEstablishDate(establishDate);
		newPark.setArea(area);
		newPark.setVisitors(visitors);
		newPark.setDescription(description);
		return newPark;
	}
	
	private void assertParksAreEqual(Park expectedPark, Park actualPark) {
		Assert.assertEquals(expectedPark.getParkId(), actualPark.getParkId());
		Assert.assertEquals(expectedPark.getName(), actualPark.getName());
		Assert.assertEquals(expectedPark.getLocation(), actualPark.getLocation());
		Assert.assertEquals(expectedPark.getEstablishDate(), actualPark.getEstablishDate());
		Assert.assertEquals(expectedPark.getArea(), actualPark.getArea());
		Assert.assertEquals(expectedPark.getVisitors(), actualPark.getVisitors());
		Assert.assertEquals(expectedPark.getDescription(), actualPark.getDescription());
	}

}
