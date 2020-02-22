package com.techelevator;

import static org.junit.Assert.assertTrue;

import java.sql.Date;

import org.junit.Test;

public class TestPark {
	
	Park park = new Park();
	
	@Test 
	public void getAndSetParkId() {
		park.setParkId((long) 1);
		assertTrue(park.getParkId() == 1);
	}
	

	@Test 
	public void getAndSetParkName() {
		park.setName("Testney World");
		assertTrue(park.getName() == "Testney World");
	}
	
	@Test
	public void getAndSetParkLocation() {
		park.setLocation("Tesstessee");
		assertTrue(park.getLocation() == "Tesstessee");
	}
	
	@Test
	public void getAndSetParkEstablishDate() {
		park.setEstablishDate(Date.valueOf("2001-01-01"));
		assertTrue(park.getEstablishDate().equals(Date.valueOf("2001-01-01")));
	}
		
	@Test
	public void getAndSetArea() {
		park.setArea(10000);
		assertTrue("getAndSetArea didnt return equal", park.getArea() == 10000);
	}

	@Test 
	public void getAndSetVisitors() {
		park.setVisitors(2);
		assertTrue("Couldn't handle just letting 2 people into the park could you?", park.getVisitors()== 2);
	}
	
	@Test
	public void getAndSetDescription() {
		park.setDescription("Hey kids, wanna do some testing?");
		assertTrue("You messed up a description test, kid", park.getDescription() == "Hey kids, wanna do some testing?");
	}
	
}
