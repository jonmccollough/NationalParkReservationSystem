package com.techelevator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SiteDAO {
	
	public List<Site> viewAvailSitesByDate(int campground, LocalDate checkInDate, LocalDate checkOutDate);

}
