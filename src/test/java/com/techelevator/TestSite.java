package com.techelevator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;

public class TestSite {

	Site testSite;

	@Before
	public void createTestSite() {
		testSite = new Site();
	}

	@Test
	public void test_siteId_get_set() {
		testSite.setSiteId(Long.valueOf(1));
		assertEquals(Long.valueOf(1), testSite.getSiteId());
	}
	
	@Test
	public void test_campgroundId_get_set() {
		testSite.setCampgroundId(Long.valueOf(1));
		assertEquals(Long.valueOf(1), testSite.getCampgroundId());
	}
	
	@Test
	public void test_siteNumber_get_set() {
		testSite.setSiteNumber(Long.valueOf(1));
		assertEquals(Long.valueOf(1), testSite.getSiteNumber());
	}
	
	@Test
	public void test_accessible_get_set() {
		testSite.setAccessible(false);
		assertEquals(false, testSite.isAccessible());
	}
	
	@Test
	public void test_maxRvLength_get_set() {
		testSite.setMaxRvLength(25);
		assertEquals(25, testSite.getMaxRvLength());
	}
	
	@Test
	public void test_utilities_get_set() {
		testSite.setUtilities(false);
		assertEquals(false, testSite.isUtilities());
	}
	
	@Test
	public void test_dailyFee_get_set() {
		testSite.setDailyFee(BigDecimal.TEN);
		assertEquals(BigDecimal.TEN, testSite.getDailyFee());
	}
	

}