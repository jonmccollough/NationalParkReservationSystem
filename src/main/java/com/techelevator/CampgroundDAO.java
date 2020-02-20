package com.techelevator;

import java.math.BigDecimal;
import java.util.List;

public interface CampgroundDAO {
	
	public List<Campground> getAllCampgroundsInfo(int park_id, String name, String open_from_mm, String open_to_mm, BigDecimal daily_fee);
}
