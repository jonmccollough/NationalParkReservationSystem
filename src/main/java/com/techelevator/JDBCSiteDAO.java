package com.techelevator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCSiteDAO implements SiteDAO {
	
private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> viewAvailSitesByDate(Date checkInDate, Date checkOutDate) {
		List<Site> availableSites = new ArrayList<>();
		String sqlViewAvailableSites = "SELECT * FROM site LEFT JOIN reservation USING (site_id) WHERE IN ? AND ? IS NULL"
		
		return availableSites;
	}

}
