package com.techelevator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private Park mapRowToPark(SqlRowSet results) {
		Park thePark;
		
		thePark = new Park();
		thePark.setArea(results.getInt("area"));
		thePark.setParkId(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		
		return thePark;
		
	}
	
	
	@Override
	public List<Park> getAllParks() {
		List<Park> park = new ArrayList<>();
		String sqlGetAllParks = "SELECT * " +
								"FROM park " +
								"ORDER BY name";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while(results.next()) {
			Park thePark = mapRowToPark(results);
			park.add(thePark);
		}
		for (Park prk : park ) {
			System.out.println( prk.getParkId() + " " + prk.getName() );
		}
		
		return park;
	}

	@Override
	public Park getParkDetails(Long parkId) {
		String sqlGetParkId = "SELECT * " +
							" FROM park " +
							"WHERE park_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkId, parkId);
		results.next();
		Park thePark = mapRowToPark(results);
		System.out.println( thePark.getName() );
		System.out.println( thePark.getLocation());
		System.out.println( thePark.getEstablishDate());
		System.out.println( thePark.getArea());
		System.out.println( thePark.getVisitors());
		System.out.println( thePark.getDescription());
		
		return thePark;
	}

}








