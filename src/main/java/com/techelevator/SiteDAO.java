package com.techelevator;

import java.util.Date;
import java.util.List;

public interface SiteDAO {
	
	public List<Site> viewAvailSitesByDate(Date checkInDate, Date checkOutDate);

}
