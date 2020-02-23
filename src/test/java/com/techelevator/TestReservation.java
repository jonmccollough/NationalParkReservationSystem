package com.techelevator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.sql.Date;

public class TestReservation {
	
	Reservations testRes;
	
	@Before
	public void createTestRes() {
	testRes = new Reservations();
	}
	
	@Test
	public void test_reservationId_get_set() {
		testRes.setReservationId(Long.valueOf(1));
		assertEquals(Long.valueOf(1), testRes.getReservationId());	
		
	}
	
	@Test
	public void test_siteId_get_set() {
		testRes.setSiteId(Long.valueOf(1));
		assertEquals(Long.valueOf(1), testRes.getSiteId());	
		
	}
	
	@Test
	public void test_name_get_set() {
		testRes.setName("TestRes");
		assertEquals("TestRes", testRes.getName());	
		
	}
	
	@Test
	public void test_fromDate_get_set() {
		testRes.setFromDate(Date.valueOf("2020-12-12"));
		assertEquals(Date.valueOf("2020-12-12"), testRes.getFromDate());	
		
	}
	
	@Test
	public void test_toDate_get_set() {
		testRes.setToDate(Date.valueOf("2020-12-12"));
		assertEquals(Date.valueOf("2020-12-12"), testRes.getToDate());	
		
	}
	
	@Test
	public void test_createDate_get_set() {
		testRes.setCreateDate(Date.valueOf("2020-12-12"));
		assertEquals(Date.valueOf("2020-12-12"), testRes.getCreateDate());	
		
	}

}
