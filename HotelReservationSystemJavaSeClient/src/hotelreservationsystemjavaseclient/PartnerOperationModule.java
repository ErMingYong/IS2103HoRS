/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationsystemjavaseclient;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javafx.util.Pair;
import ws.client.LocalDateTime;
import ws.client.PartnerEntity;
import ws.client.PartnerEntityWebService;
import ws.client.PartnerNotFoundException_Exception;
import ws.client.ReservationEntity;
import ws.client.RoomTypeEntity;

/**
 *
 * @author mingy
 */
public class PartnerOperationModule {

    private PartnerEntityWebService webServicePort;

    private PartnerEntity currentPartner;

    public PartnerOperationModule() {
    }

    public PartnerOperationModule(PartnerEntityWebService webServicePort, PartnerEntity currentPartner) {
        this.webServicePort = webServicePort;
        this.currentPartner = currentPartner;
    }

    public void menuGuestOperation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Reservation Partner System :: Partner Operation ***\n");
            System.out.println("1: Partner Reserve Hotel Room");
            System.out.println("2: View Partner Reservation Details");
            System.out.println("3: View All Partner Reservations");
            System.out.println("4: Exit");
            response = 0;

            while (response < 1 || response > 4) {
                response = scanner.nextInt();

                if (response == 1) {
                    doSearchHotelRoom();
                } else if (response == 2) {
                    try {
                        doViewPartnerReservationDetails();
                    } catch (PartnerNotFoundException_Exception ex) {
                        System.out.println("Partner account does not exist");
                    }
                } else if (response == 3) {
                    doViewAllPartnerReservation();
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

        System.out.println("*** Hotel Reservation System Reservation Client System :: Partner Operation :: Search Hotel Room ***\n");

        java.time.LocalDateTime reservationStartDate;
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
                reservationStartDate = java.time.LocalDateTime.of(year, month, day, 0, 0, 0);
            } catch (DateTimeException ex) {
                System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                System.out.println(":::");
                System.out.println("");

                continue;
            }
            break;
        }

        java.time.LocalDateTime reservationEndDate;
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
                reservationEndDate = java.time.LocalDateTime.of(year, month, day, 0, 0, 0);
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
        HashMap<RoomTypeEntity, HashMap<String, BigDecimal>> map;
        try {
            map = webServicePort.retrieveRoomTypeAvailabilities(reservationStartDate, reservationEndDate, noRooms, false);
            List<RoomTypeEntity> listOfKeys = new ArrayList<>(map.keySet());

            while (true) {
                System.out.println("");
                System.out.println("------------------------");
                System.out.println("Available Rooms to book from " + reservationStartDate.toLocalDate().toString() + " to " + reservationEndDate.toLocalDate().toString());
                System.out.printf("%5.5s%20.20s%20.20s%20.20s\n", "S/N", "Room Type", "Total Price of Stay", "Quantity Available");
                int counter = 1;
                for (RoomTypeEntity roomType : listOfKeys) {
                    HashMap<String, BigDecimal> roomTypeMap = map.get(roomType);
                    if (roomTypeMap.get("numRoomType").intValue() > 0) {
                        System.out.printf("%5d%20.20s%20.20s%20.20s\n", counter, roomType.getRoomTypeName(), roomTypeMap.get("bestPrice"), roomTypeMap.get("numRoomType"));
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

            System.out.println("");

        } catch (InsufficientRoomsAvailableException ex) {
            System.out.println("Insufficient rooms are available from " + reservationStartDate.toLocalDate().toString() + " to " + reservationEndDate.toLocalDate().toString());
        }
    }
    
    private void doHotelReserve(HashMap<RoomTypeEntity, HashMap<String, BigDecimal>> map, java.time.LocalDateTime reservationStartDate, java.time.LocalDateTime reservationEndDate, Integer numRooms) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Hotel Management Client :: Front Office Module :: Walk In Reserve ***\n");
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
            System.out.printf("%5.5s%20.20s%20.20s%20.20s\n", "S/N", "Room Type", "Total Price of Stay", "Quantity Available");
            List<RoomTypeEntity> roomTypeNameList = new ArrayList<>();
            int counter = 1;
            for (RoomTypeEntity roomType : listOfKeys) {
                HashMap<String, BigDecimal> roomTypeMap = map.get(roomType);
                if (roomTypeMap.get("numRoomType").intValue() > 0) {
                    System.out.printf("%5d%20.20s%20.20s%20.20s\n", counter, roomType.getRoomTypeName(), roomTypeMap.get("bestPrice"), roomTypeMap.get("numRoomType"));
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
            reservationEntitySessionBeanRemote.createNewReservations(listOfNewReservationPairs);
            System.out.println("::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("Reservations are successful!");
            System.out.println("");
            java.time.LocalDateTime currDateTime = java.time.LocalDateTime.now();
            java.time.LocalDateTime currDate2Am = java.time.LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 0));

            //after 2am walk in, if reservations are for TODAY, then immediately allcoate
            if (currDateTime.isAfter(currDate2Am) && reservationStartDate.isEqual(java.time.LocalDateTime.of(LocalDate.now(), LocalTime.MIN))) {
                allocationReportSessionBeanRemote.allocationReportCheckTimerManual();
            }
        } catch (UnknownPersistenceException | CreateNewReservationException ex) {
            System.out.println("Unable to create Reservations, Please Try Again!");
        } catch (InputDataValidationException ex) {
            System.out.println("Some details are invalid, Please Try Again!");
        }
    }

    private void doViewPartnerReservationDetails() throws PartnerNotFoundException_Exception {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** Hotel Reservation System Reservation Partner System :: Partner Operation :: View Partner Reservation Details***\n");
            List<ReservationEntity> partnerReservations = webServicePort.retrieveAllPartnerReservations(currentPartner.getUserEntityId());

            Integer option = 0;
            while (true) {
                System.out.println("----------------------------------------");
                for (int i = 0; i < partnerReservations.size(); i++) {
                    System.out.println((i + 1) + " Reservation Id: " + partnerReservations.get(i).getReservationEntityId());
                }
                option = 0;
                while (option < 1 || option > partnerReservations.size()) {
                    System.out.println("----------------------------------------");
                    System.out.println("Enter Reservation Number Option");
                    option = scanner.nextInt();

                    if (option >= 1 && option <= partnerReservations.size()) {
                        ReservationEntity reservation = partnerReservations.get(option - 1);
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
        } catch (PartnerNotFoundException_Exception ex) {
            System.out.println("Partner account does not exist");
        }

    }

    private void doViewAllPartnerReservation() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** Hotel Reservation System Reservation Partner System :: Partner Operation :: View All Parnter Reservations ***\n");

            List<ReservationEntity> partnerReservations = webServicePort.retrieveAllPartnerReservations(currentPartner.getUserEntityId());

            System.out.printf("%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s\n", "Reservation Start Date", "Reservation End Date", "First Name", "Last Name", "Email", "Contact Number", "Passport Number");
            for (ReservationEntity reservationEntity : partnerReservations) {
                System.out.printf("%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s\n", reservationEntity.getReservationStartDate().toString(), reservationEntity.getReservationEndDate().toString(), reservationEntity.getFirstName(), reservationEntity.getLastName(), reservationEntity.getEmail(), reservationEntity.getContactNumber(), reservationEntity.getPassportNumber());
            }
        } catch (PartnerNotFoundException_Exception ex) {
            System.out.println("Partner does not exist");
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }
}
