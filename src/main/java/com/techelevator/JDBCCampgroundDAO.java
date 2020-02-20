package com.techelevator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground;
		
		theCampground = new Campground();
		theCampground.setCampgroundId(results.getLong("campground_id"));
		theCampground.setParkId(results.getInt("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpenFromMm(results.getString("open_from_mm"));
		theCampground.setOpenToMm(results.getString("open_to_mm"));
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		
		return theCampground;
	}
	
	@Override
	public List<Campground> getAllCampgroundsInfo(int parkId) {
		List<Campground> campgroundsList = new ArrayList<>();
				String sqlGetAllCampgroundsInfo = "SELECT * " +
									  	   		  "FROM campground "+
									  	          "JOIN park " +
									  	          "USING (park_id)" +
									  	          "WHERE park_id = 1 ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgroundsInfo);
		while(results.next()) {
			Campground campground = mapRowToCampground(results);
			campgroundsList.add(campground);
		}
		for( Campground campground : campgroundsList) {
			System.out.println(campground.getCampgroundId() + " " + campground.getName() + " " + convertMonths(campground.getOpenFromMm()) +
								" " + convertMonths(campground.getOpenToMm()) +
								" " + campground.getDailyFee());
		}
		
		return campgroundsList;
	}

	private String convertMonths(String numMonth) {
		if(numMonth.equals("01")){
			return "January";
		}
		if(numMonth.equals("02")){
			return "February";
		}
		if(numMonth.equals("03")){
			return "March";
		}
		if(numMonth.equals("04")){
			return "April";
		}
		if(numMonth.equals("05")){
			return "May";
		}
		if(numMonth.equals("06")){
			return "June";
		}
		if(numMonth.equals("07")){
			return "July";
		}
		if(numMonth.equals("08")){
			return "August";
		}
		if(numMonth.equals("09")){
			return "September";
		}
		if(numMonth.equals("10")){
			return "October";
		}
		if(numMonth.equals("11")){
			return "November";
		}
		else { return "December";
		}
	}
}
