package com.techelevator;

import java.time.LocalDate;
import java.util.List;
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

		while (true) {
			System.out.println("National Parks Welcome Page");
			System.out.println("Please selection your option");
			System.out.println("1. Display National Parks");
			System.out.println("0. Exit");
			String welcomeScanner = userInput.nextLine();
			try {
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
						try {
							int secondScannerInt = Integer.valueOf(secondScanner);

							if (secondScannerInt == 0) {
								break;
							}

							while (true) {
								Park thePark = parkDAO.getParkDetails((long) secondScannerInt);
								if (thePark != null) {
									System.out.println("1. View Campgrounds");
									System.out.println("2. Search a Reservation");
									System.out.println("0. Return to Previous Screen");
								} else {
									break;
								}

								String thirdScanner = userInput.nextLine();

								try {
									int thirdScannerInt = Integer.valueOf(thirdScanner);

									if (thirdScannerInt != 0 && thirdScannerInt != 1 && thirdScannerInt != 2) {
										System.out.println("Invalid Input");
										break;
									}

									if (thirdScannerInt == 0) {
										break;
									}

									while (true) {
										if (thirdScannerInt == 1) {
											campgroundDAO.getAllCampgroundsInfo(secondScannerInt);
											System.out.println(
													"Enter number of campground would you like to reserve(enter 0 to cancel): ");

											String fourthScanner = userInput.nextLine();
											try {
												int fourthScannerInt = Integer.valueOf(fourthScanner);
												Long fourthScannerLng = Long.valueOf(fourthScanner);
												if (fourthScannerInt == 0) {
													break;
												}

												int campSwitch = campgroundDAO.verifyCampground(fourthScannerLng);

												if (campSwitch == 1) {
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

													List<Site> availSites = siteDAO.viewAvailSitesByDate(
															fourthScannerInt, arrivalScannerLD, departureScannerLD);
													if (availSites.size() > 0) {

														System.out.println("Enter number of site to be reserved: ");
														System.out.println("Enter 0 to return to Campground Selection");
														String fifthScanner = userInput.nextLine();
														int fifthScannerInt = Integer.valueOf(fifthScanner);

														if (fifthScannerInt == 0) {
															break;
														}

														System.out.println("Enter a name to reserve the site under: ");
														String nameScanner = userInput.nextLine();
														reservationDAO.makeReservation(Long.valueOf(fifthScanner),
																nameScanner, arrivalScannerLD, departureScannerLD);

														System.out.println();
													} else {
														break;
													}

												}
											} catch (Exception e) {
												System.out.println("INVALID INPUT must be a number!");
											}
										}
										if (thirdScannerInt == 2) {
											System.out.println("Enter in reservation ID: ");
											String resScanner = userInput.nextLine();
											reservationDAO.viewReservationById(Long.valueOf(resScanner));
											break;
										}

									}
								} catch (Exception e) {
									System.out.println("INVALID INPUT must be a number!");
								}
							}
						} catch (Exception e) {
							System.out.println("INVALID INPUT must be a number!");
						}
					}
				}
				if (welcomeScannerInt == 0) {
					userInput.close();
					System.exit(0);
				}
			} catch (Exception e) {
				System.out.println("INVALID INPUT must be a number!");

			}
		}
	}
}
