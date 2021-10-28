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
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.GuestNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

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

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public MainApp() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    public MainApp(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote) {
        this();
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
                    doRegisterAsGuest();
                    break;
                } else if (response == 3) {
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

    private void doRegisterAsGuest() {
        Scanner scanner = new Scanner(System.in);
        GuestEntity newGuest = new GuestEntity();

        System.out.println("*** Hotel Reservation System Reservation Client System :: Guest :: Register New Guest ***\n");
        System.out.println("------------------------");
        System.out.print("Enter First Name> ");
        newGuest.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newGuest.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Username> ");
        newGuest.setUserName(scanner.nextLine().trim());
        System.out.print("Enter Password> ");
        newGuest.setPassword(scanner.nextLine().trim());
        System.out.print("Enter Email> ");
        newGuest.setEmail(scanner.nextLine().trim());
        System.out.println("Enter Contact Number");
        newGuest.setContactNumber(scanner.nextLine().trim());
        System.out.println("Enter Passport Number");
        newGuest.setPassportNumber(scanner.nextLine().trim());

        Set<ConstraintViolation<GuestEntity>> constraintViolations = validator.validate(newGuest);

        if (constraintViolations.isEmpty()) {
            try {
                Long newGuestId = guestEntitySessionBean.createNewGuest(newGuest);
                System.out.println("------------------------");
                System.out.println("New guest created successfully!: " + newGuestId + "\n");
            } catch (UnknownPersistenceException ex) {
                System.out.println("An unknown error has occurred while creating the new employee!: " + ex.getMessage() + "\n");
            }
        } else {
            showInputDataValidationErrorsForGuestEntity(constraintViolations);
        }
    }

    private void showInputDataValidationErrorsForGuestEntity(Set<ConstraintViolation<GuestEntity>> constraintViolations) {
        System.out.println("\nInput data validation error!:");

        for (ConstraintViolation constraintViolation : constraintViolations) {
            System.out.println("\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage());
        }

        System.out.println("\nPlease try again......\n");
    }

}
