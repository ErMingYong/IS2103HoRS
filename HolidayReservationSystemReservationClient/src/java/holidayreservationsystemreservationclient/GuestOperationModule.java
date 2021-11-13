/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemreservationclient;

import ejb.session.stateless.AllocationReportSessionBeanRemote;
import ejb.session.stateless.GuestEntitySessionBeanRemote;
import ejb.session.stateless.ReservationEntitySessionBeanRemote;
import entity.GuestEntity;
import entity.ReservationEntity;
import entity.RoomTypeEntity;
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

    private AllocationReportSessionBeanRemote allocationReportSessionBeanRemote;
    private ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote;
    private GuestEntitySessionBeanRemote guestEntitySessionBeanRemote;

    private GuestEntity currentGuest;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public GuestOperationModule() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    public GuestOperationModule(AllocationReportSessionBeanRemote allocationReportSessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, GuestEntity currentGuest) {
        this();
        this.allocationReportSessionBeanRemote = allocationReportSessionBeanRemote;
        this.reservationEntitySessionBeanRemote = reservationEntitySessionBeanRemote;
        this.guestEntitySessionBeanRemote = guestEntitySessionBeanRemote;
        this.currentGuest = currentGuest;
    }

    public void menuGuestOperation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Reservation Client System :: Guest Operation ***\n");
            System.out.println("1: Search Hotel Room");
            System.out.println("2: View My Reservation Details");
            System.out.println("3: View All My Reservations");
            System.out.println("4: Exit");
            System.out.println("");
            System.out.println(">");
            response = 0;

            while (response < 1 || response > 4) {
                response = scanner.nextInt();

                if (response == 1) {
                    doSearchHotelRoom();
                } else if (response == 2) {
                    doViewMyReservationDetails();
                } else if (response == 3) {
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

            if (reservationStartDate.isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))) {
                System.out.println("PLEASE SELECT A DATE FROM TODAY ONWARDS!");
                System.out.println("::::::::::::::::::::");
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

            if (!reservationEndDate.isAfter(reservationStartDate)) {
                System.out.println("PLEASE SELECT A DATE AFTER START DATE!");
                System.out.println("::::::::::::::::::::");
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
        HashMap<RoomTypeEntity, HashMap<String, BigDecimal>> map;
        try {
            map = reservationEntitySessionBeanRemote.retrieveRoomTypeAvailabilities(reservationStartDate, reservationEndDate, noRooms, false);
            List<RoomTypeEntity> listOfKeys = new ArrayList<>(map.keySet());

            while (true) {
                System.out.println("");
                System.out.println("------------------------");
                System.out.println("Available Rooms to book from " + reservationStartDate.toLocalDate().toString() + " to " + reservationEndDate.toLocalDate().toString());
                System.out.printf("%5.5s%30.30s%30.30s%30.30s\n", "S/N", "Room Type", "Total Price of Stay", "Quantity Available");

                int counter = 1;
                for (RoomTypeEntity roomType : listOfKeys) {
                    HashMap<String, BigDecimal> roomTypeMap = map.get(roomType);
                    if (roomTypeMap.get("numRoomType").intValue() > 0) {
                        System.out.printf("%5d%30.30s%30.30s%30.30s\n", counter, roomType.getRoomTypeName(), roomTypeMap.get("bestPrice"), roomTypeMap.get("numRoomType"));
                        counter += 1;
                    }
                }
                System.out.println("------------------------");
                Integer response = 0;
                System.out.println("1: Reserve room/s ");
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

            System.out.println("");

        } catch (InsufficientRoomsAvailableException ex) {
            System.out.println("Insufficient rooms are available from " + reservationStartDate.toLocalDate().toString() + " to " + reservationEndDate.toLocalDate().toString());
        }
    }

    private void doHotelReserve(HashMap<RoomTypeEntity, HashMap<String, BigDecimal>> map, LocalDateTime reservationStartDate, LocalDateTime reservationEndDate, Integer numRooms) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Hotel Management Client :: Guest Operation Module :: Online Reserve ***\n");
        System.out.println("");
        List<Pair<ReservationEntity, List<String>>> listOfNewReservationPairs = new ArrayList<>();

        BigDecimal totalPayment = BigDecimal.ZERO;
        List<RoomTypeEntity> listOfKeys = new ArrayList<>(map.keySet());
        int numReservation = 1;
        while (numRooms >= numReservation) {
            ReservationEntity newReservation = new ReservationEntity();
            newReservation.setFirstName(currentGuest.getFirstName());
            newReservation.setLastName(currentGuest.getLastName());
            newReservation.setEmail(currentGuest.getEmail());
            newReservation.setContactNumber(currentGuest.getContactNumber());
            newReservation.setPassportNumber(currentGuest.getPassportNumber());
            newReservation.setReservationStartDate(reservationStartDate);
            newReservation.setReservationEndDate(reservationEndDate);

            System.out.println("");
            System.out.println("------------------------");
            System.out.println("Available Rooms to book from " + reservationStartDate.toLocalDate().toString() + " to " + reservationEndDate.toLocalDate().toString());
            System.out.printf("%5.5s%30.30s%30.30s%30.30s\n", "S/N", "Room Type", "Total Price of Stay", "Quantity Available");
            List<RoomTypeEntity> roomTypeNameList = new ArrayList<>();
            int counter = 1;
            for (RoomTypeEntity roomType : listOfKeys) {
                HashMap<String, BigDecimal> roomTypeMap = map.get(roomType);
                if (roomTypeMap.get("numRoomType").intValue() > 0) {
                    System.out.printf("%5d%30.30s%30.30s%30.30s\n", counter, roomType.getRoomTypeName(), roomTypeMap.get("bestPrice"), roomTypeMap.get("numRoomType"));
                    counter += 1;
                    roomTypeNameList.add(roomType);
                }
            }

            System.out.println("------------------------");
            System.out.println("Please select room type for reservation number: " + numReservation);
            Integer response = 0;
            RoomTypeEntity selectedRoomType = null;
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
                    newReservation.setRoomTypeName(selectedRoomType.getRoomTypeName());
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
            System.out.println("::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("Reservations are successful!");
            System.out.println("Total Payment: " + totalPayment.toString());
            System.out.println("");
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
            Integer option = 0;
            if (!listOfReservations.isEmpty()) {
                listOfReservations.sort((ReservationEntity x, ReservationEntity y) -> {
                    if (y.getReservationEntityId() > x.getReservationEntityId()) {
                        return 1;
                    } else if (y.getReservationEntityId() < x.getReservationEntityId()) {
                        return -1;
                    } else {
                        return 0;
                    }
                });
                while (true) {
                    System.out.println("--------------------------------------------------------------------");
                    for (int i = 0; i < listOfReservations.size(); i++) {
                        System.out.println((i + 1) + ":               Reservation Id: " + listOfReservations.get(i).getReservationEntityId());
                    }
                    option = 0;
                    while (option < 1 || option > listOfReservations.size()) {
                        System.out.println("--------------------------------------------------------------------");
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
                            String roomTypeName = reservation.getRoomTypeName();
                            String reservationPrice = reservation.getReservationPrice().toString();
                            String reservationContactNumber = reservation.getContactNumber();
                            String reservationPassportNumber = reservation.getPassportNumber();

                            System.out.println("Reservation successfully retrieved. Reservation Id: " + reservationId);
                            System.out.println("Reservation First Name: " + reservationFirstName);
                            System.out.println("Reservation Last Name: " + reservationLastname);
                            System.out.println("Reservation Email: " + reservationEmail);
                            System.out.println("Reservation Contact Number: " + reservationContactNumber);
                            System.out.println("Reservation Passport Number: " + reservationPassportNumber);
                            System.out.println("Reserved Room Type: " + roomTypeName);
                            System.out.println("Reservation Price: " + reservationPrice);
                            System.out.println("");
                            System.out.println("Reservation Start Date: " + reservationStartDate.toLocalDate());
                            System.out.println("Reservation End Date: " + reservationEndDate.toLocalDate());
                            System.out.println("-----------------------------------------------");
                            System.out.print("Press any key to continue...> ");
                            System.out.println("");
                            scanner.nextLine();
                        } else {
                            System.out.println("Please select a valid input!");
                            System.out.println("");
                        }
                    }
                    System.out.println("");
                    System.out.println("Continue to view Reservation? Press 'Y', to Exit Press any other key...");
                    System.out.println("");
                    String response = scanner.nextLine();
                    if (response.equals("Y")) {
                        continue;
                    }
                    break;
                }
            } else {
                System.out.println("-----------------------------------------");
                System.out.println("You do not have any Reservations!");
                System.out.println("");
                System.out.println("Press any key to go back...");
                System.out.println("");
                scanner.nextLine();
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

            if (!reservationEntities.isEmpty()) {
                reservationEntities.sort((ReservationEntity x, ReservationEntity y) -> {
                    if (x.getReservationStartDate().isAfter(y.getReservationStartDate())) {
                        return 1;
                    } else if (x.getReservationStartDate().isEqual(y.getReservationStartDate())) {
                        return 0;
                    } else {
                        return -1;
                    }
                });
                System.out.printf("%15.15s%30.30s%30.30s%30.30s%30.30s\n", "Reservation Id", "Reservation Start Date", "Reservation End Date", "Room Type Name", "Reservation Price");
                for (ReservationEntity reservationEntity : reservationEntities) {
                    System.out.printf("%15.15s%30.30s%30.30s%30.30s%30.30s\n", reservationEntity.getReservationEntityId(), reservationEntity.getReservationStartDate().toLocalDate().toString(), reservationEntity.getReservationEndDate().toLocalDate().toString(), reservationEntity.getRoomTypeName(), reservationEntity.getReservationPrice());
                }
                System.out.print("Press any key to continue...> ");
                scanner.nextLine();
            } else {
                System.out.println("-----------------------------------------");
                System.out.println("You do not have any Reservations!");
                System.out.println("");
                System.out.println("Press any key to go back...");
                scanner.nextLine();
            }

        } catch (GuestNotFoundException ex) {
            Logger.getLogger(GuestOperationModule.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
