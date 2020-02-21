package com.techelevator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	private List<Park> park = new ArrayList<>();

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

		String sqlGetAllParks = "SELECT * " + "FROM park " + "ORDER BY name";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while (results.next()) {
			Park thePark = mapRowToPark(results);
			park.add(thePark);
		}
		System.out.println("Id   Park Name");
		System.out.println("==============");
		for (Park prk : park) {

			System.out.println(prk.getParkId() + ")   " + prk.getName());
		}
		System.out.println();

		return park;
	}

	@Override
	public Park getParkDetails(Long parkId) {
		Park thePark = null;
		List<Long> availableParks = new ArrayList<>();
		for (Park prk : park) {
			availableParks.add(prk.getParkId());
		}
		if (availableParks.contains(parkId)) {

			String sqlGetParkId = "SELECT * " + " FROM park " + "WHERE park_id = ? ";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkId, parkId);
			results.next();
			thePark = mapRowToPark(results);
			System.out.println(thePark.getName() + " National Park");
			System.out.println("=====================================");
			System.out.println("Location:\t" + thePark.getLocation());
			System.out.println("Established:\t" + thePark.getEstablishDate());
			System.out.println("Area:\t\t" + thePark.getArea() + "sq km");
			System.out.println("Annual Vstrs:\t" + thePark.getVisitors());
			System.out.println();
			System.out.println(thePark.getDescription());
			System.out.println();

			return thePark;
		}
		System.out.println("ERROR: Not a Valid Park Selection!!!");
		return thePark;
	}

}
