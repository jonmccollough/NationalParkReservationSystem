package com.techelevator;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class TestCampground {

	Campground cg = new Campground();

	@Test
	public void getAndSetCgId() {
		cg.setCampgroundId((long)1);
		assertTrue(cg.getCampgroundId().equals((long)1));
	}
	
	@Test
	public void getAndSetCgParkId() {
		cg.setParkId(1);
		assertTrue(cg.getParkId() == 1);
	}
	
	@Test
	public void getAndSetCgName() {
		cg.setName("Test of Fury");
		assertTrue(cg.getName() == "Test of Fury");
	}
	
	@Test
	public void getAndSetCgOpenFromMm() {
		cg.setOpenFromMm("01");
		assertTrue(cg.getOpenFromMm() == "01");
	}
	
	@Test
	public void getAndSetCgOpenToMm() {
		cg.setOpenToMm("12");
		assertTrue(cg.getOpenToMm() == "12");
	}
	
	@Test
	public void getAndSetCgDailyFee() {
		cg.setDailyFee(BigDecimal.TEN);
		assertTrue(cg.getDailyFee() == BigDecimal.TEN);
	}
}
