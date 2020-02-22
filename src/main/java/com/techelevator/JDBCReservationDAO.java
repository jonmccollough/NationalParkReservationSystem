package com.techelevator;

import java.time.LocalDate;
import java.util.HashMap;
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
	public Reservations viewReservationById(Long reservationId) {
		Reservations theRes = null;
		SqlRowSet reservationByIdResult = jdbcTemplate.queryForRowSet("SELECT * FROM reservation WHERE reservation_id = ?", reservationId);
		if (reservationByIdResult.next()) {
			theRes = mapRowToRes(reservationByIdResult);
			System.out.println("Reservation ID \t Name \t \t Site ID \t Start Date \t End Date \t Created Date");
			System.out.println(theRes.getReservationId() + "\t \t " + theRes.getName() + "\t " + theRes.getSiteId() + "\t " + theRes.getFromDate() + "\t " + theRes.getToDate() + "\t " + theRes.getCreateDate());
			return theRes;
		}
		System.out.println("No reservation found for that ID number");
			return theRes;
		}
	
	private Reservations mapRowToRes(SqlRowSet results){
		Reservations newRes = new Reservations();
		newRes.setReservationId(results.getLong("reservation_id"));
		newRes.setName(results.getString("name"));
		newRes.setFromDate(results.getDate("from_date"));
		newRes.setToDate(results.getDate("to_date"));
		newRes.setCreateDate(results.getDate("create_date"));
		newRes.setSiteId(results.getLong("site_id"));
		return newRes;
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
