package com.techelevator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO {
	
private JdbcTemplate jdbcTemplate;
public static List<Site> availableSites;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> viewAvailSitesByDate(int campground, LocalDate checkInDate, LocalDate checkOutDate) {
		availableSites = new ArrayList<>();
		if (checkInDate.isBefore(LocalDate.now())) {
			System.out.println("Invalid Dates entered, cannot book past dates without a Delorean.");
			return availableSites;
			
		}
		
		if (checkOutDate.isBefore(checkInDate)) {
			System.out.println("Invalid Dates entered, checkout date cannot be before check in");
			return availableSites;
		}
		
				
		String sqlViewAvailableSites = "SELECT site_id, site_number, daily_fee FROM campground " + 
										"JOIN site USING (campground_id) " + 
										"LEFT JOIN reservation USING (site_id) " + 
										"WHERE campground_id = ? AND site_id NOT IN ( " + 
										"SELECT site_id FROM campground " + 
										"JOIN site USING(campground_id) " + 
										"LEFT JOIN reservation USING (site_id) " + 
										"WHERE campground_id = ? " + 
										"AND (? BETWEEN from_date AND to_date " + 
										"OR ? BETWEEN from_date AND to_date " + 
										"OR (? < from_date AND ? > to_date))) " + 
										"GROUP BY site_id, site_number, daily_fee " + 
										"LIMIT 5 ";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlViewAvailableSites, campground, campground, checkInDate, checkOutDate, checkInDate, checkOutDate);
		availableSites = new ArrayList<>();
		while(results.next()) {
			Site theSite = mapRowToSite(results);
			availableSites.add(theSite);
		}
		
		if (availableSites.size() == 0) {
			System.out.println("No sites available, too bad for you.");
			return availableSites;
		}
		
	    long diffDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
	    BigDecimal diffDaysBigD = new BigDecimal(diffDays);
		
		System.out.println("Available Sites \tTotal Cost of Stay");
		System.out.println("===============================================");
		for(Site site : availableSites) {
			System.out.println("\t   " + site.getSiteNumber() + " \t\t" + (site.getDailyFee().multiply(diffDaysBigD)));
		}
				
		return availableSites;
	}
	
	private Site mapRowToSite(SqlRowSet results) {
		Site newSite;
		newSite = new Site();
		newSite.setSiteId(results.getLong("site_id"));
		newSite.setSiteNumber(results.getLong("site_number"));
		newSite.setDailyFee(results.getBigDecimal("daily_fee"));
		return newSite;
	}
}




