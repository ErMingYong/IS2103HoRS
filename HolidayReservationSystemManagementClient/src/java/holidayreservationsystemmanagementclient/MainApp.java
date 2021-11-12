/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemmanagementclient;

import ejb.session.stateless.AllocationReportSessionBeanRemote;
import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import ejb.session.stateless.ExceptionReportEntitySessionBeanRemote;
import ejb.session.stateless.GuestEntitySessionBeanRemote;
import ejb.session.stateless.PartnerEntitySessionBeanRemote;
import ejb.session.stateless.ReservationEntitySessionBeanRemote;
import ejb.session.stateless.RoomEntitySessionBeanRemote;
import ejb.session.stateless.RoomRateEntitySessionBeanRemote;
import ejb.session.stateless.RoomTypeEntitySessionBeanRemote;
//import ejb.session.stateless.TransactionEntitySessionBeanRemote;
import entity.EmployeeEntity;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidAccessRightException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
public class MainApp {

    private AllocationReportSessionBeanRemote allocationReportSessionBeanRemote;
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;
    private ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote;
    private GuestEntitySessionBeanRemote guestEntitySessionBeanRemote;
    private PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote;
    private ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote;
    private RoomEntitySessionBeanRemote roomEntitySessionBeanRemote;
    private RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote;
    private RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote;
//    private TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote;

    private SystemAdministrationModule systemAdministrationModule;
    private HotelOperationModule hotelOperationModule;
    private SalesOperationModule salesOperationModule;
    private FrontOfficeModule frontOfficeModule;

    private EmployeeEntity currentEmployee;

    public MainApp() {
    }

    public MainApp(AllocationReportSessionBeanRemote allocationReportSessionBeanRemote, EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote) {
        this.allocationReportSessionBeanRemote = allocationReportSessionBeanRemote;
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
        this.exceptionReportEntitySessionBeanRemote = exceptionReportEntitySessionBeanRemote;
        this.guestEntitySessionBeanRemote = guestEntitySessionBeanRemote;
        this.partnerEntitySessionBeanRemote = partnerEntitySessionBeanRemote;
        this.reservationEntitySessionBeanRemote = reservationEntitySessionBeanRemote;
        this.roomEntitySessionBeanRemote = roomEntitySessionBeanRemote;
        this.roomRateEntitySessionBeanRemote = roomRateEntitySessionBeanRemote;
        this.roomTypeEntitySessionBeanRemote = roomTypeEntitySessionBeanRemote;
//        this.transactionEntitySessionBeanRemote = transactionEntitySessionBeanRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Holiday Reservation System Management Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;

            while (response < 1 || response > 2) {
                System.out.println("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");

                        systemAdministrationModule = new SystemAdministrationModule(employeeEntitySessionBeanRemote,
                                exceptionReportEntitySessionBeanRemote,
                                guestEntitySessionBeanRemote,
                                partnerEntitySessionBeanRemote,
                                reservationEntitySessionBeanRemote,
                                roomEntitySessionBeanRemote,
                                roomRateEntitySessionBeanRemote,
                                roomTypeEntitySessionBeanRemote,
                                currentEmployee);
                        hotelOperationModule = new HotelOperationModule(allocationReportSessionBeanRemote,
                                employeeEntitySessionBeanRemote,
                                exceptionReportEntitySessionBeanRemote,
                                guestEntitySessionBeanRemote,
                                partnerEntitySessionBeanRemote,
                                reservationEntitySessionBeanRemote,
                                roomEntitySessionBeanRemote,
                                roomRateEntitySessionBeanRemote,
                                roomTypeEntitySessionBeanRemote,
                                currentEmployee);
                        salesOperationModule = new SalesOperationModule(employeeEntitySessionBeanRemote,
                                exceptionReportEntitySessionBeanRemote,
                                guestEntitySessionBeanRemote,
                                partnerEntitySessionBeanRemote,
                                reservationEntitySessionBeanRemote,
                                roomEntitySessionBeanRemote,
                                roomRateEntitySessionBeanRemote,
                                roomTypeEntitySessionBeanRemote,
                                currentEmployee);
                        frontOfficeModule = new FrontOfficeModule(allocationReportSessionBeanRemote,
                                employeeEntitySessionBeanRemote,
                                exceptionReportEntitySessionBeanRemote,
                                guestEntitySessionBeanRemote,
                                partnerEntitySessionBeanRemote,
                                reservationEntitySessionBeanRemote,
                                roomEntitySessionBeanRemote,
                                roomRateEntitySessionBeanRemote,
                                roomTypeEntitySessionBeanRemote,
                                currentEmployee);
                        mainMenu();
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid Login Credentials!: " + ex.getMessage() + "\n|");
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                    response = 0;
                }
            }

            if (response == 2) {
                break;
            }
        }
    }

    private void doLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** Hotel Reservation System Management Client :: Login ***\n");
        System.out.println("Enter Username: ");
        username = scanner.nextLine().trim();
        System.out.println("Enter Password: ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            currentEmployee = employeeEntitySessionBeanRemote.employeeLogin(username, password);
        } else {
            throw new InvalidLoginCredentialException("Missing Login Credentials");
        }
    }

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {

            System.out.println("*** Hotel Reservation System Management Client ***\n");
            System.out.println("You are login as " + currentEmployee.getFirstName() + " " + currentEmployee.getLastName() + " with " + currentEmployee.getEmployeeAccessRightEnum().toString() + " rights\n");
            System.out.println("1: System Administration Operation");
            System.out.println("2: Hotel Operation");
            System.out.println("3: Sales Operation");
            System.out.println("4: Front Office Operation");
            System.out.println("5: Logout\n");
            System.out.println(">");
            response = 0;

            while (response < 1 || response > 5) {
                response = 0;
                response = scanner.nextInt();
                if (response == 1) {
                    try {
                        systemAdministrationModule.menuSystemAdministration();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    try {
                        hotelOperationModule.menuHotelOperation();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    } catch (UnknownPersistenceException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (response == 3) {
                    try {
                        salesOperationModule.menuSalesOperation();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                } else if (response == 4) {
                    try {
                        frontOfficeModule.menuFrontOfficeOperation();
                    } catch (InvalidAccessRightException ex) {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                } else if (response == 5) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 5) {
                break;
            }
        }
    }
}
