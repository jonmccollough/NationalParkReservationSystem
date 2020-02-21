package com.techelevator;

import java.time.LocalDate;
import java.util.Date;

public interface ReservationDAO {
	
	public Long makeReservation(Long siteNumber, String name, LocalDate fromDate, LocalDate toDate);
	public Reservations viewReservationById(Long reservationId);

}
