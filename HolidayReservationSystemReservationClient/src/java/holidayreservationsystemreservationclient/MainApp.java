/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemreservationclient;

import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import ejb.session.stateless.ExceptionReportEntitySessionBeanRemote;
import ejb.session.stateless.GuestEntitySessionBeanRemote;
import ejb.session.stateless.PartnerEntitySessionBeanRemote;
import ejb.session.stateless.ReservationEntitySessionBeanRemote;
import ejb.session.stateless.RoomEntitySessionBeanRemote;
import ejb.session.stateless.RoomRateEntitySessionBeanRemote;
import ejb.session.stateless.RoomTypeEntitySessionBeanRemote;
import ejb.session.stateless.TransactionEntitySessionBeanRemote;
import entity.GuestEntity;
import java.util.Scanner;
import util.exception.GuestNotFoundException;
import util.exception.InvalidAccessRightException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author mingy
 */
public class MainApp {

    private TransactionEntitySessionBeanRemote transactionEntitySessionBean;
    private RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBean;
    private RoomRateEntitySessionBeanRemote roomRateEntitySessionBean;
    private RoomEntitySessionBeanRemote roomEntitySessionBean;
    private ReservationEntitySessionBeanRemote reservationEntitySessionBean;
    private PartnerEntitySessionBeanRemote partnerEntitySessionBean;
    private GuestEntitySessionBeanRemote guestEntitySessionBean;
    private ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBean;
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBean;

    private GuestOperationModule guestOperationModule;

    private GuestEntity currentGuest;

    public MainApp() {
    }

    public MainApp(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote) {
        this.transactionEntitySessionBean = transactionEntitySessionBean;
        this.roomTypeEntitySessionBean = roomTypeEntitySessionBean;
        this.roomRateEntitySessionBean = roomRateEntitySessionBean;
        this.roomEntitySessionBean = roomEntitySessionBean;
        this.reservationEntitySessionBean = reservationEntitySessionBean;
        this.partnerEntitySessionBean = partnerEntitySessionBean;
        this.guestEntitySessionBean = guestEntitySessionBean;
        this.exceptionReportEntitySessionBean = exceptionReportEntitySessionBean;
        this.employeeEntitySessionBean = employeeEntitySessionBean;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Holiday Reservation System Reservation Client ***\n");
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

                        guestOperationModule = new GuestOperationModule(employeeEntitySessionBean,
                                exceptionReportEntitySessionBean,
                                guestEntitySessionBean,
                                partnerEntitySessionBean,
                                reservationEntitySessionBean,
                                roomEntitySessionBean,
                                roomRateEntitySessionBean,
                                roomTypeEntitySessionBean,
                                transactionEntitySessionBean,
                                currentGuest);
                        mainMenu();
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid Login Credentials!: " + ex.getMessage() + "\n|");
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
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

        System.out.println("*** Hotel Reservation System Reservationz Client :: Login ***\n");
        System.out.println("Enter Username: ");
        username = scanner.nextLine().trim();
        System.out.println("Enter Password: ");
        username = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            try {
                currentGuest = guestEntitySessionBean.guestLogin(username, password);
            } catch (GuestNotFoundException ex) {
                System.out.println("User account does not exist");
            }
        } else {
            throw new InvalidLoginCredentialException("Missing Login Credentials");
        }
    }

    private void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Reservations Client ***\n");
            System.out.println("You are login as " + currentGuest.getFirstName() + " " + currentGuest.getLastName() + " with " + currentGuest.getPassportNumber().toString() + " passport number\n");
            System.out.println("1: Guest Operation Module");
            System.out.println("2: Logout\n");

            while (response < 1 || response > 2) {
                System.out.println("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    guestOperationModule.menuGuestOperation();
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 2) {
                break;
            }
        }
    }

}
