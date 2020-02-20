package com.techelevator;

import java.util.Date;
import java.util.List;

public interface ParkDAO {

	public List<Park> getAllParks();
		
	public List<Park> getParkDetails( Long park_id, String name, String location, Date establish_date, int area, int visitors, String description);

}
