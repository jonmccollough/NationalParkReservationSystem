package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.junit.Test;


public class TestJDBCampgroundDAO {


	private static SingleConnectionDataSource dataSource;
	private static final long TEST_CAMP = 8;
	private CampgroundDAO dao;

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
		String sqlCampground = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) " +
								"VALUES (?,  1, 'Murder Forrest', 07, 08, 10)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlCampground, TEST_CAMP);
		dao = new JDBCCampgroundDAO(dataSource);
	
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
	public void getAllCampgroundsInfo() {
		List<Campground> actualCg = dao.getAllCampgroundsInfo(1);
		assertEquals(4, actualCg.size());
	}
	
	@Test
	public void verifyCampgroundInvalidTest() {
		int actualCG = dao.verifyCampground((long)0);
		assertEquals(1, actualCG);
	}
	
	@Test
	public void verifyCampgroundValidTest() {
		int actualCG = dao.verifyCampground((long)1);
		assertEquals(0, actualCG);
	}
	
	private Campground getCg() {
		Long campgroundId = (long) 8;
		int parkId = 1;
		String name = "Murder Forrest";
		String openFromMm = "01";
		String openToMm = "12";
		BigDecimal dailyFee = BigDecimal.TEN;
		
		Campground newCg = new Campground();
		newCg.setCampgroundId(campgroundId);
		newCg.setParkId(parkId);
		newCg.setName(name);
		newCg.setOpenFromMm(openFromMm);
		newCg.setOpenToMm(openToMm);
		newCg.setDailyFee(dailyFee);
		return newCg;
	}
	
	
}
