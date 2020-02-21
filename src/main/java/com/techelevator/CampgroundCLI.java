package com.techelevator;

import java.sql.Date;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {

	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO; 
	private ReservationDAO reservationDAO;
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/parks");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource dataSource) {
		parkDAO = new JDBCParkDAO(dataSource);
		parkDAO.getAllParks();
		parkDAO.getParkDetails(Long.valueOf(1));
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		campgroundDAO.getAllCampgroundsInfo(1);
		siteDAO = new JDBCSiteDAO(dataSource);
		siteDAO.viewAvailSitesByDate(1, LocalDate.of(2020,02,20), LocalDate.of(2020,3,23));
		reservationDAO = new JDBCReservationDAO(dataSource);
		reservationDAO.makeReservation(Long.valueOf(5), "TESTER", LocalDate.of(2020,02,20), LocalDate.of(2020,3,23));
		
	}

	public void run() {

	}
	
}




