package com.techelevator;

import java.util.List;

public interface ParkDAO {

	public List<Park> getAllParks();
		
	public Park getParkDetails(Long parkId);

	void save(Park newPark);
		
}
