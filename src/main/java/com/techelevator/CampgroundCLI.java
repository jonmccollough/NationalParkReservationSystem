package com.techelevator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

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
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);

	}

	public void run() {

		Scanner userInput = new Scanner(System.in);

		boolean mainMenuControl = true;
		while (mainMenuControl) {

			System.out.println("National Parks Welcome Page");
			System.out.println("Please selection your option");
			System.out.println("1. Display National Parks");
			System.out.println("2. Exit");

			String welcomeScanner = userInput.nextLine();
			int welcomeScannerInt = Integer.valueOf(welcomeScanner);

			if (welcomeScannerInt != 1 && welcomeScannerInt != 2) {
				System.out.println("Not a valid input");
			}
			if (welcomeScannerInt == 2) {
				mainMenuControl = false;
			}
			if (welcomeScannerInt == 1) {
				parkDAO.getAllParks();
				System.out.println("Select park number to see more details: ");
				System.out.println("Select 0 to go back.");

				String secondScanner = userInput.nextLine();
				int secondScannerInt = Integer.valueOf(secondScanner);

				boolean secondControl = true;
				if (secondScannerInt == 0) {
					secondControl = false;
				}
				if (secondScannerInt != 1 && secondScannerInt != 2 && secondScannerInt != 3) {
					System.out.println("Not a valid national park selection");
				}
				parkDAO.getParkDetails((long) secondScannerInt);
				System.out.println("1. View Campgrounds");
				System.out.println("0. Return to Previous Screen");

				String thirdScanner = userInput.nextLine();
				int thirdScannerInt = Integer.valueOf(thirdScanner);
				if (thirdScannerInt == 0) {
					secondControl = false;
				}
				
				boolean thirdControl = true;
				if (thirdScannerInt == 0) {
					thirdControl = false;
				}

				if (thirdScannerInt == 1) {
					campgroundDAO.getAllCampgroundsInfo(secondScannerInt);
					System.out.println("1. Search for Available Reservation");
					System.out.println("0. Return to Previous Screen");

				}

			}

		}
	}
}
