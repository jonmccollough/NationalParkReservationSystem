package com.techelevator;

import java.util.Date;

public interface ReservationDAO {
	
	public Long makeReservation(String name, Date fromDate, Date toDate);
	public void viewReservationById(Long reservationId);

}
