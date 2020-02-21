package com.techelevator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO {
	
private JdbcTemplate jdbcTemplate;
public static List<Site> availableSites = new ArrayList<>();
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> viewAvailSitesByDate(int campground, LocalDate checkInDate, LocalDate checkOutDate) {
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
		
		while(results.next()) {
			Site theSite = mapRowToSite(results);
			availableSites.add(theSite);
		}
		
	    long diffDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
	    BigDecimal diffDaysBigD = new BigDecimal(diffDays);
		
		
		for(Site site : availableSites) {
			System.out.println(site.getSiteNumber() + " " + (site.getDailyFee().multiply(diffDaysBigD)));
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




