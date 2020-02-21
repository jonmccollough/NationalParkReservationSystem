package com.techelevator;

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

		LocalDate arrivalScannerLD = null;
		LocalDate departureScannerLD = null;

		boolean mainMenuControl = true;
		while (true) {

			System.out.println("National Parks Welcome Page");
			System.out.println("Please selection your option");
			System.out.println("1. Display National Parks");
			System.out.println("0. Exit");

			String welcomeScanner = userInput.nextLine();
			int welcomeScannerInt = Integer.valueOf(welcomeScanner);

			if (welcomeScannerInt != 1 && welcomeScannerInt != 0) {
				System.out.println("Not a valid input");
			}

			if (welcomeScannerInt == 1) {
				while (true) {
					parkDAO.getAllParks();
					System.out.println("Select park number to see more details: ");
					System.out.println("Select 0 to go back.");

					String secondScanner = userInput.nextLine();
					int secondScannerInt = Integer.valueOf(secondScanner);

					if (secondScannerInt == 0) {
						break;
					}

					while (true) {

						parkDAO.getParkDetails((long) secondScannerInt);
						System.out.println("1. View Campgrounds");
						System.out.println("2. Search a Reservation");
						System.out.println("0. Return to Previous Screen");

						String thirdScanner = userInput.nextLine();
						int thirdScannerInt = Integer.valueOf(thirdScanner);

						if (thirdScannerInt == 0) {
							break;
						}

						while (true) {

							if (thirdScannerInt == 1) {
								campgroundDAO.getAllCampgroundsInfo(secondScannerInt);
								System.out.println(
										"Enter number of campground would you like to reserve(enter 0 to cancel): ");
								
								String fourthScanner = userInput.nextLine();
								int fourthScannerInt = Integer.valueOf(fourthScanner);

								if (fourthScannerInt == 0) {
									break;
								}

								if (fourthScannerInt != 1 && fourthScannerInt != 2 && fourthScannerInt != 3) {
									System.out.println("ERROR: Not a valid campground selection!!!");
									break;
								}

								System.out.println("What is the arrival date? YYYY-MM-DD: ");
								try {
									String arrivalScanner = userInput.nextLine();
									arrivalScannerLD = LocalDate.parse(arrivalScanner);

									System.out.println("What is the departure date? YYYY-MM-DD: ");
									String departureScanner = userInput.nextLine();
									departureScannerLD = LocalDate.parse(departureScanner);

								} catch (Exception e) {
									System.out.println(
											"ERROR: Improper date format. Please follow the following format: YYYY-MM-DD");
									break;
								}

								while (true) {

									if (fourthScannerInt == 1 || fourthScannerInt == 2 || fourthScannerInt == 3) {
										siteDAO.viewAvailSitesByDate(fourthScannerInt, arrivalScannerLD,
												departureScannerLD);
										System.out.println("Enter number of site to be reserved: ");
										System.out.println("Enter 0 to return to Campground Selection");
										String fifthScanner = userInput.nextLine();
										int fifthScannerInt = Integer.valueOf(fifthScanner);

										if (fifthScannerInt == 0) {
											break;
										}

										System.out.println("Enter a name to reserve the site under: ");
										String nameScanner = userInput.nextLine();

										reservationDAO.makeReservation(Long.valueOf(fifthScanner), nameScanner,
												arrivalScannerLD, departureScannerLD);

										System.out.println();
									}
								}

							}
							if (thirdScannerInt == 2) {
								System.out.println("Enter in reservation ID: ");
								String resScanner = userInput.nextLine();
								reservationDAO.viewReservationById(Long.valueOf(resScanner));
							}

						}
					}

				}
			}
			if (welcomeScannerInt == 0) {
				System.exit(0);
			}
		}
	}
}
