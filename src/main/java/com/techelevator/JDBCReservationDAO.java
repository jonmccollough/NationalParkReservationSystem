package com.techelevator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {
	
private JdbcTemplate jdbcTemplate;

	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Long makeReservation(Long siteNumber, String name, LocalDate fromDate, LocalDate toDate) {
		Long reservationNumber = null;
		Map<Long, Long> availableSiteNumbersAndIDs = new HashMap<>();

		
		for(Site site: JDBCSiteDAO.availableSites) {
			availableSiteNumbersAndIDs.put(site.getSiteNumber(), site.getSiteId());
		}
		
		if (availableSiteNumbersAndIDs.containsKey(siteNumber)) {
			reservationNumber = getNextResId();
			String sqlInsertRes = "INSERT INTO reservation VALUES (?, ?, ?, ?, ?, ?)";

			jdbcTemplate.update(sqlInsertRes, reservationNumber, availableSiteNumbersAndIDs.get(siteNumber), name, fromDate, toDate, LocalDate.now());
			System.out.println("BOOKED! Reservation Confirmation Number: " + reservationNumber);
			return reservationNumber;
		}
		System.out.println("Unavailable Campsite Entered");
		return reservationNumber;
	}

	@Override
	public void viewReservationById(Long reservationId) {
		// TODO Auto-generated method stub

	}
	
	private long getNextResId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new reservation");
		}
	}

}
