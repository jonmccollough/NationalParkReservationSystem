package com.techelevator;

import java.util.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.JDBCSiteDAO;

public class TestJDBCReservationDAO extends DAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCReservationDAO daoRes;
	private JDBCSiteDAO daoSite;
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
		daoRes = new JDBCReservationDAO(dataSource);
		daoSite = new JDBCSiteDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);

		String sqlInsertDummyPark = "INSERT INTO park VALUES (DEFAULT, 'TESTPARKNAME', 'TESTPARKLOC', '2222-01-01', 1, 1, 'TESTPARKDESC')";
		jdbcTemplate.update(sqlInsertDummyPark);
		String sqlInsertDummyCamp = "INSERT INTO campground VALUES (DEFAULT, (SELECT park_id FROM park WHERE name = 'TESTPARKNAME'), 'TESTCAMPNAME', '13', '14', 10)";
		jdbcTemplate.update(sqlInsertDummyCamp);
		sqlInsertDummySite1 = "INSERT INTO site VALUES (DEFAULT, (SELECT campground_id FROM campground WHERE name = 'TESTCAMPNAME'), 1, 5, false, 10, false)";
		sqlInsertDummySite2 = "INSERT INTO site VALUES (DEFAULT, (SELECT campground_id FROM campground WHERE name = 'TESTCAMPNAME'), 2, 5, false, 10, false)";
		jdbcTemplate.update(sqlInsertDummySite1);
		jdbcTemplate.update(sqlInsertDummySite2);
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
	public void test_make_res() {

		daoSite.viewAvailSitesByDate(dummyCampID, LocalDate.of(2020, 12, 12), LocalDate.of(2020, 12, 24));
		Long testConfNumber = daoRes.makeReservation(1L, "TEST", LocalDate.of(2020, 12, 12),
				LocalDate.of(2020, 12, 24));
		assertNotNull(testConfNumber);

	}

	@Test
	public void test_invalid_site_number_res_attempt() {

		daoSite.viewAvailSitesByDate(dummyCampID, LocalDate.of(2020, 12, 12), LocalDate.of(2020, 12, 24));
		Long testConfNumber = daoRes.makeReservation(5L, "TEST", LocalDate.of(2020, 12, 12),
				LocalDate.of(2020, 12, 24));
		assertNull(testConfNumber);

	}

	@Test
	public void test_find_res_by_id() {
		ZoneId timeZone = ZoneId.systemDefault();
		LocalDate now = LocalDate.now();
		LocalDate fromDate = LocalDate.of(2020, 12, 12);
		LocalDate toDate = LocalDate.of(2020, 12, 24);

		daoSite.viewAvailSitesByDate(dummyCampID, fromDate, toDate);

		Long testConfNumber = daoRes.makeReservation(1L, "TEST", fromDate, toDate);
		Reservations testRes = daoRes.viewReservationById(testConfNumber);

		Reservations actualRes = new Reservations();
		actualRes.setCreateDate(Date.from(now.atStartOfDay(timeZone).toInstant()));
		actualRes.setFromDate(Date.from(fromDate.atStartOfDay(timeZone).toInstant()));
		actualRes.setToDate(Date.from(toDate.atStartOfDay(timeZone).toInstant()));
		actualRes.setName("TEST");
		actualRes.setReservationId(testConfNumber);
		actualRes.setSiteId(testRes.getSiteId());

		assertEquals(actualRes.getCreateDate(), testRes.getCreateDate());
		assertEquals(actualRes.getFromDate(), testRes.getFromDate());
		assertEquals(actualRes.getToDate(), testRes.getToDate());
		assertEquals(actualRes.getName(), testRes.getName());
		assertEquals(actualRes.getReservationId(), testRes.getReservationId());
		assertEquals(actualRes.getSiteId(), testRes.getSiteId());
	}

	@Test
	public void test_no_res_for_id() {
		Reservations testRes = new Reservations();
		testRes = daoRes.viewReservationById(0L);
		assertNull(testRes);

	}

}
