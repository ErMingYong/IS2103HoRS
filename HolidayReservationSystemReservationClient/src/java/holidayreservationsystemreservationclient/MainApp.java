/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemreservationclient;

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
import entity.GuestEntity;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.GuestNotFoundException;
import util.exception.GuestUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InsufficientRoomsAvailableException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
public class MainApp {

    private AllocationReportSessionBeanRemote allocationReportSessionBeanRemote;
    private RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote;
    private RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote;
    private RoomEntitySessionBeanRemote roomEntitySessionBeanRemote;
    private ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote;
    private PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote;
    private GuestEntitySessionBeanRemote guestEntitySessionBeanRemote;
    private ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote;
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;

    private GuestOperationModule guestOperationModule;

    private GuestEntity currentGuest;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public MainApp() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    public MainApp(AllocationReportSessionBeanRemote allocationReportSessionBeanRemote, EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote) {
        this();
        this.allocationReportSessionBeanRemote = allocationReportSessionBeanRemote;
        this.roomTypeEntitySessionBeanRemote = roomTypeEntitySessionBeanRemote;
        this.roomRateEntitySessionBeanRemote = roomRateEntitySessionBeanRemote;
        this.roomEntitySessionBeanRemote = roomEntitySessionBeanRemote;
        this.reservationEntitySessionBeanRemote = reservationEntitySessionBeanRemote;
        this.partnerEntitySessionBeanRemote = partnerEntitySessionBeanRemote;
        this.guestEntitySessionBeanRemote = guestEntitySessionBeanRemote;
        this.exceptionReportEntitySessionBeanRemote = exceptionReportEntitySessionBeanRemote;
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Holiday Reservation System Reservation Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: Register as Guest");
            System.out.println("3: Search For Hotel Rooms");
            System.out.println("4: Exit\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.println("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");

                        guestOperationModule = new GuestOperationModule(allocationReportSessionBeanRemote,
                                employeeEntitySessionBeanRemote,
                                exceptionReportEntitySessionBeanRemote,
                                guestEntitySessionBeanRemote,
                                partnerEntitySessionBeanRemote,
                                reservationEntitySessionBeanRemote,
                                roomEntitySessionBeanRemote,
                                roomRateEntitySessionBeanRemote,
                                roomTypeEntitySessionBeanRemote,
                                currentGuest);
                        mainMenu();
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid Login Credentials!: " + ex.getMessage() + "\n|");
                    }
                } else if (response == 2) {
                    doRegisterAsGuest();
                    break;
                } else if (response == 3) {
                    doSearchHotelRoom();
                    break;
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 4) {
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
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            try {
                currentGuest = guestEntitySessionBeanRemote.guestLogin(username, password);
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
            response = 0;

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
                Long newGuestId = guestEntitySessionBeanRemote.createNewGuest(newGuest);
                System.out.println("------------------------");
                System.out.println("New guest created successfully!: " + newGuestId + "\n");
            } catch (GuestUsernameExistException ex) {
                System.out.println("An error has occurred while creating the new guest!: The user name already exist\n");
            } catch (UnknownPersistenceException ex) {
                System.out.println("An unknown error has occurred while creating the new guest!: " + ex.getMessage() + "\n");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        } else {
            showInputDataValidationErrorsForGuestEntity(constraintViolations);
        }
    }

    private void doSearchHotelRoom() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Reservation System Reservation Client System :: Guest Operation :: Search Hotel Room ***\n");

        LocalDateTime reservationStartDate;
        while (true) {
            System.out.println("");
            System.out.println("Please Enter Reservation Start Date> ");
            System.out.println("------------------------");
            System.out.println("Enter Day>     (please select from 01 - 31)");
            int day = scanner.nextInt();
            System.out.println("Enter Month>   (please select from 01 - 12)");
            int month = scanner.nextInt();
            System.out.println("Enter Year>    (please select from 2000 - 2999)");
            int year = scanner.nextInt();
            try {
                reservationStartDate = LocalDateTime.of(year, month, day, 0, 0, 0);
            } catch (DateTimeException ex) {
                System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                System.out.println(":::");
                System.out.println("");

                continue;
            }
            break;
        }

        LocalDateTime reservationEndDate;
        while (true) {
            System.out.println("");
            System.out.println("Please Enter Reservation End Date> ");
            System.out.println("------------------------");
            System.out.println("Enter Day>     (please select from 01 - 31)");
            int day = scanner.nextInt();
            System.out.println("Enter Month>   (please select from 01 - 12)");
            int month = scanner.nextInt();
            System.out.println("Enter Year>    (please select from 2000 - 2999)");
            int year = scanner.nextInt();
            try {
                reservationEndDate = LocalDateTime.of(year, month, day, 0, 0, 0);
            } catch (DateTimeException ex) {
                System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                System.out.println(":::");
                System.out.println("");

                continue;
            }
            break;
        }
        int noRooms = 0;
        while (true) {
            noRooms = 0;
            System.out.println("Please select number of rooms to book>");
            noRooms = scanner.nextInt();
            if (noRooms > 0) {
                break;
            } else {
                System.out.println("Please select a valid number above 0!");
            }
        }
        scanner.nextLine();
        System.out.println("Please wait while we retrieve the available room types...");
        HashMap<String, HashMap<String, BigDecimal>> map;
        try {
            map = reservationEntitySessionBeanRemote.retrieveAvailableRoomTypesOnline(reservationStartDate, reservationEndDate, noRooms);
            List<String> listOfKeys = new ArrayList<>(map.keySet());

            System.out.println("");
            System.out.println("------------------------");
            System.out.println("Available Rooms to book from " + reservationStartDate.toLocalDate().toString() + " to " + reservationEndDate.toLocalDate().toString());
            System.out.printf("%5.5s%20.20s%20.20s%20.20s\n", "S/N", "Room Type", "Total Price of Stay", "Quantity Available");
            int counter = 1;
            for (String roomType : listOfKeys) {
                HashMap<String, BigDecimal> roomTypeMap = map.get(roomType);
                if (roomTypeMap.get("numRoomType").intValue() > 0) {
                    System.out.printf("%5d%20.20s%20.20s%20.20s\n", counter, roomType, roomTypeMap.get("bestPrice"), roomTypeMap.get("numRoomType"));
                    counter += 1;
                }
            }
            System.out.println("------------------------");
            System.out.println("Please Proceed To Login As Guest To Be Able To Start Reserving!");
            System.out.println("If You Do Not Have An Account, Please Register For One For Free!");
            System.out.println("------------------------");
            System.out.println("");
            System.out.println("Press any key to go back...");
            String response = scanner.nextLine();
            System.out.println("");
            

        } catch (InsufficientRoomsAvailableException ex) {
            System.out.println("Insufficient rooms are available from " + reservationStartDate.toLocalDate().toString() + " to " + reservationEndDate.toLocalDate().toString());
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
