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
				
				boolean thirdControl = true;
				if (thirdScannerInt == 0) {
					thirdControl = false;
				}

				if (thirdScannerInt == 1) {
					campgroundDAO.getAllCampgroundsInfo(secondScannerInt);
					System.out.println("Enter number of campground would you like to reserve(enter 0 to cancel): ");

					String fourthScanner = userInput.nextLine();
					int fourthScannerInt = Integer.valueOf(fourthScanner);
					
					System.out.println("What is the arrival date? YYYY-MM-DD: ");
					String arrivalScanner = userInput.nextLine();
					LocalDate arrivalScannerLD = LocalDate.parse(arrivalScanner);
					
					System.out.println("What is the departure date? YYYY-MM-DD: ");
					String departureScanner = userInput.nextLine();
					LocalDate departureScannerLD = LocalDate.parse(departureScanner);
					
					boolean fourthControl = true;
					if (fourthScannerInt == 0) {
						fourthControl = false;
					}
					
					if (fourthScannerInt != 1 && fourthScannerInt != 2 && fourthScannerInt != 3) {
						System.out.println("Not a valid campground selection");
					}
					
					if (fourthScannerInt == 1 || fourthScannerInt == 2 ||fourthScannerInt == 3 ) {
						siteDAO.viewAvailSitesByDate(fourthScannerInt, arrivalScannerLD, departureScannerLD);
						System.out.println("Enter number of site to be reserved: ");
						System.out.println("0. to return to previous screen");
						String fifthScanner = userInput.nextLine();
						int fifthScannerInt = Integer.valueOf(fifthScanner);
						
						boolean fifthControl = true;
						if (fifthScannerInt == 0) {
							fifthControl = false;
						}
						
						System.out.println("Enter a name to reserve the site under: ");
						String nameScanner = userInput.nextLine();
						
						reservationDAO.makeReservation(Long.valueOf(fifthScanner), nameScanner, arrivalScannerLD, departureScannerLD); 

						System.out.println();
					}
					
				
				}

			}

		}
	}
}
