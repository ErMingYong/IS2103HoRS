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
import entity.ReservationEntity;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewReservationException;
import util.exception.GuestNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InsufficientRoomsAvailableException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
public class GuestOperationModule {

//    private TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote;
    private AllocationReportSessionBeanRemote allocationReportSessionBeanRemote;
    private RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote;
    private RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote;
    private RoomEntitySessionBeanRemote roomEntitySessionBeanRemote;
    private ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote;
    private PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote;
    private GuestEntitySessionBeanRemote guestEntitySessionBeanRemote;
    private ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBean;
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;

    private GuestEntity currentGuest;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public GuestOperationModule() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    public GuestOperationModule(AllocationReportSessionBeanRemote allocationReportSessionBeanRemote, EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote rateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, GuestEntity currentGuest) {
        this();
        this.allocationReportSessionBeanRemote = allocationReportSessionBeanRemote;
        this.roomTypeEntitySessionBeanRemote = roomTypeEntitySessionBeanRemote;
        this.roomRateEntitySessionBeanRemote = roomRateEntitySessionBeanRemote;
        this.roomEntitySessionBeanRemote = roomEntitySessionBeanRemote;
        this.reservationEntitySessionBeanRemote = reservationEntitySessionBeanRemote;
        this.partnerEntitySessionBeanRemote = partnerEntitySessionBeanRemote;
        this.guestEntitySessionBeanRemote = guestEntitySessionBeanRemote;
        this.exceptionReportEntitySessionBean = exceptionReportEntitySessionBean;
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
        this.currentGuest = currentGuest;
    }

    public void menuGuestOperation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Reservation Client System :: Guest Operation ***\n");
            System.out.println("1: Reserve Hotel Room");
            System.out.println("2: View My Reservation Details");
            System.out.println("3: View All My Reservations");
            System.out.println("4: Exit");
            response = 0;

            while (response < 1 || response > 4) {
                response = scanner.nextInt();

                if (response == 1) {
                    //SEARCH HOTEL ROOM
                    //INCLUDES -> RESERVER HOTEL ROOM
                    doSearchHotelRoom();
                } else if (response == 2) {
                    //VIEW MY RESERVATION DETAILS
                    doViewMyReservationDetails();
                } else if (response == 3) {
                    //VIEW ALL MY RESERVATIONS
                    doViewAllMyReservation();
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

            while (true) {
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
                Integer response = 0;
                System.out.println("1: Reserve room/s (Walk-In)");
                System.out.println("2: Back\n");
                while (response < 1 || response > 2) {
                    response = scanner.nextInt();
                    if (response == 1) {
                        doHotelReserve(map, reservationStartDate, reservationEndDate, noRooms);
                    } else if (response == 2) {
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!\n");
                    }
                }
                break;
            }

        } catch (InsufficientRoomsAvailableException ex) {
            System.out.println("Insufficient rooms are available from " + reservationStartDate.toLocalDate().toString() + " to " + reservationEndDate.toLocalDate().toString());
        }
    }

    private void doHotelReserve(HashMap<String, HashMap<String, BigDecimal>> map, LocalDateTime reservationStartDate, LocalDateTime reservationEndDate, Integer numRooms) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Hotel Management Client :: Front Office Module :: Walk In Reserve ***\n");
        System.out.println("");
        List<Pair<ReservationEntity, List<String>>> listOfNewReservationPairs = new ArrayList<>();

        String firstName = "";
        String lastName = "";
        String email = "";
        String contactNumber = "";
        String passportNumber = "";

        while (true) {
            System.out.println("Enter Walk-In Guest's First Name>");
            firstName = scanner.nextLine();
            if (firstName.length() > 0) {
                break;
            } else {
                System.out.println("Please input a First Name");
            }
        }

        while (true) {
            System.out.println("Enter Walk-In Guest's Last Name>");
            lastName = scanner.nextLine();
            if (lastName.length() > 0) {
                break;
            } else {
                System.out.println("Please input a Last Name");
            }
        }

        //unsure how to check if email is valid at the client side
        System.out.println("Enter Walk-In Guest's Email>");
        email = scanner.nextLine();

        while (true) {
            System.out.println("Enter Walk-In Guest's Contact Number>");
            contactNumber = scanner.nextLine();
            if (contactNumber.length() == 8) {
                break;
            } else {
                System.out.println("Please input a valid Contact Number");
            }
        }

        while (true) {
            System.out.println("Enter Walk-In Guest's Passport Number>");
            passportNumber = scanner.nextLine();
            if (passportNumber.length() == 8) {
                break;
            } else {
                System.out.println("Please input a valid Passport Number");
            }
        }

        BigDecimal totalPayment = BigDecimal.ZERO;
        List<String> listOfKeys = new ArrayList<>(map.keySet());
        int numReservation = 1;
        while (numRooms >= numReservation) {
            ReservationEntity newReservation = new ReservationEntity();
            newReservation.setFirstName(firstName);
            newReservation.setLastName(lastName);
            newReservation.setEmail(email);
            newReservation.setContactNumber(contactNumber);
            newReservation.setPassportNumber(passportNumber);
            newReservation.setReservationStartDate(reservationStartDate);
            newReservation.setReservationEndDate(reservationEndDate);

            System.out.println("");
            System.out.println("------------------------");
            System.out.println("Available Rooms to book from " + reservationStartDate.toLocalDate().toString() + " to " + reservationEndDate.toLocalDate().toString());
            System.out.printf("%5.5s%20.20s%20.20s%20.20s\n", "S/N", "Room Type", "Total Price of Stay", "Quantity Available");
            List<String> roomTypeNameList = new ArrayList<>();
            int counter = 1;
            for (String roomType : listOfKeys) {
                HashMap<String, BigDecimal> roomTypeMap = map.get(roomType);
                if (roomTypeMap.get("numRoomType").intValue() > 0) {
                    System.out.printf("%5d%20.20s%20.20s%20.20s\n", counter, roomType, roomTypeMap.get("bestPrice"), roomTypeMap.get("numRoomType"));
                    counter += 1;
                    roomTypeNameList.add(roomType);
                }
            }

            System.out.println("------------------------");
            System.out.println("Please select room type for reservation number: " + numReservation);
            Integer response = 0;
            String selectedRoomType = "";
            List<String> listOfRoomRateNames = new ArrayList<>();
            while (response < 1 || response > roomTypeNameList.size()) {
                response = 0;
                response = scanner.nextInt();
                if (response >= 1 && response <= roomTypeNameList.size()) {
                    selectedRoomType = roomTypeNameList.get(response - 1);
                    HashMap<String, BigDecimal> selectedRoomTypeMap = map.get(selectedRoomType);
                    listOfRoomRateNames = new ArrayList<>(selectedRoomTypeMap.keySet());
                    listOfRoomRateNames.remove("bestPrice");
                    listOfRoomRateNames.remove("numRoomType");
                    newReservation.setRoomTypeName(selectedRoomType);
                    newReservation.setReservationPrice(map.get(selectedRoomType).get("bestPrice"));
                    totalPayment = totalPayment.add(newReservation.getReservationPrice());
                } else {
                    System.out.println("Invalid option, please try again!\n");
                    continue;
                }
            }

            listOfNewReservationPairs.add(new Pair(newReservation, listOfRoomRateNames));

            HashMap<String, BigDecimal> stringToBigDecimalMap = map.get(selectedRoomType);
            stringToBigDecimalMap.put("numRoomType", stringToBigDecimalMap.get("numRoomType").subtract(BigDecimal.ONE));

            numReservation += 1;
        }

        try {
            reservationEntitySessionBeanRemote.createNewReservationsForGuest(listOfNewReservationPairs, currentGuest);
            LocalDateTime currDateTime = LocalDateTime.now();
            LocalDateTime currDate2Am = LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 0));

            //after 2am walk in, if reservations are for TODAY, then immediately allcoate
            if (currDateTime.isAfter(currDate2Am) && reservationStartDate.isEqual(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))) {
                allocationReportSessionBeanRemote.allocationReportCheckTimerManual();
            }
        } catch (UnknownPersistenceException | CreateNewReservationException ex) {
            System.out.println("Unable to create Reservations, Please Try Again!");
        } catch (InputDataValidationException ex) {
            System.out.println("Some details are invalid, Please Try Again!");
        }
    }

    private void doViewMyReservationDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Reservation System Reservation Client System :: Guest Operation :: View My Reservation Details***\n");
        GuestEntity managedGuest;
        try {
            managedGuest = guestEntitySessionBeanRemote.retrieveGuestById(currentGuest.getUserEntityId());
            List<ReservationEntity> listOfReservations = managedGuest.getReservationEntities();
            listOfReservations.sort((ReservationEntity x, ReservationEntity y) -> {
                if (x.getReservationStartDate().isAfter(y.getReservationStartDate())) {
                    return 1;
                } else if (x.getReservationStartDate().isEqual(y.getReservationStartDate())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            Integer option = 0;
            while (true) {
                System.out.println("----------------------------------------");
                for (int i = 0; i < listOfReservations.size(); i++) {
                    System.out.println((i + 1) + " Reservation Id: " + listOfReservations.get(i).getReservationEntityId());
                }
                option = 0;
                while (option < 1 || option > listOfReservations.size()) {
                    System.out.println("----------------------------------------");
                    System.out.println("Enter Reservation Number Option");
                    option = scanner.nextInt();

                    if (option >= 1 && option <= listOfReservations.size()) {
                        ReservationEntity reservation = listOfReservations.get(option - 1);
                        Long reservationId = reservation.getReservationEntityId();
                        LocalDateTime reservationStartDate = reservation.getReservationStartDate();
                        LocalDateTime reservationEndDate = reservation.getReservationEndDate();
                        String reservationFirstName = reservation.getFirstName();
                        String reservationLastname = reservation.getLastName();
                        String reservationEmail = reservation.getEmail();
                        String reservationContactNumber = reservation.getContactNumber();
                        String reservationPassportNumber = reservation.getPassportNumber();
                        System.out.println("Reservation successfully retrieved. Reservation Id: " + reservationId);
                        System.out.println("Reservation First Name: " + reservationFirstName);
                        System.out.println("Reservation Last Name: " + reservationLastname);
                        System.out.println("Reservation Start Date: " + reservationStartDate.toString());
                        System.out.println("Reservation End Date: " + reservationEndDate.toString());
                        System.out.println("Reservation Email: " + reservationEmail);
                        System.out.println("Reservation Contact Number: " + reservationContactNumber);
                        System.out.println("Reservation Passport Number: " + reservationPassportNumber);

                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    } else {
                        System.out.println("Please select a valid input!");
                        System.out.println("");
                    }
                }
                System.out.println("");
                System.out.println("Continue to view Reservation? Press 'Y', to Exit Press any other key...");
                String response = scanner.nextLine();
                if (response == "Y") {
                    continue;
                }
                break;
            }
        } catch (GuestNotFoundException ex) {
            Logger.getLogger(GuestOperationModule.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void doViewAllMyReservation() {
        Scanner scanner = new Scanner(System.in);
        GuestEntity managedGuest;
        try {
            managedGuest = guestEntitySessionBeanRemote.retrieveGuestById(currentGuest.getUserEntityId());
            System.out.println("*** Hotel Reservation System Reservation Client System :: Guest Operation :: View All My Reservations ***\n");
            List<ReservationEntity> reservationEntities = managedGuest.getReservationEntities();
            reservationEntities.sort((ReservationEntity x, ReservationEntity y) -> {
                if (x.getReservationStartDate().isAfter(y.getReservationStartDate())) {
                    return 1;
                } else if (x.getReservationStartDate().isEqual(y.getReservationStartDate())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            //List<ReservationEntity> reservationEntitires = reservationEntitySessionBeanRemote.retrieveReservationByPassportNumber(managedGuest.getPassportNumber());
            System.out.printf("%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s\n", "Reservation Start Date", "Reservation End Date", "First Name", "Last Name", "Email", "Contact Number", "Passport Number");
            for (ReservationEntity reservationEntity : reservationEntities) {
                System.out.printf("%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s\n", reservationEntity.getReservationStartDate().toString(), reservationEntity.getReservationEndDate().toString(), reservationEntity.getFirstName(), reservationEntity.getLastName(), reservationEntity.getEmail(), reservationEntity.getContactNumber(), reservationEntity.getPassportNumber());
            }
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (GuestNotFoundException ex) {
            Logger.getLogger(GuestOperationModule.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
