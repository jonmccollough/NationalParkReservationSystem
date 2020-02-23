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


public class TestJDBCParkDAO {

	private static SingleConnectionDataSource dataSource;
	private static final long TEST_PARK = 20;
	private ParkDAO dao;

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
		String sqlInsertPark = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) VALUES (?, 'Testney World', 'Tesstessee', '2001-01-01', 1000, 2, 'Test Description')";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertPark, TEST_PARK);
		
		dao = new JDBCParkDAO(dataSource);
	
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	protected DataSource getDataSource() {
		return dataSource;
	}
	
	@Test
	public void getAllParks(){
	//	Park newPark = getPark();
	//	dao.save(newPark);
		List<Park> actualPark = dao.getAllParks();
		Assert.assertNotNull("Did not find parks", actualPark);
		assertEquals(4, actualPark.size());
	}
	
	@Test
	public void getParkDetails(){
	//	final Long parkId = (long) 1;
	//	Park newPark = getPark();
	//	dao.save(newPark);
		
	//	Park actualPark = dao.getParkDetails((long) 1);
	//	Assert.assertNotNull("Park details were null, again", actualPark);
		//assertParksAreEqual(newPark, actualPark);
	}
	
	
	private Park getPark() {
		Long parkId = (long) 20;
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
