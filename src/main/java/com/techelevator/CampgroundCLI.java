package com.techelevator;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {

	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	
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
	}

	public void run() {

	}
	
}




