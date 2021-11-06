/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemmanagementclient;

import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import ejb.session.stateless.ExceptionReportEntitySessionBeanRemote;
import ejb.session.stateless.GuestEntitySessionBeanRemote;
import ejb.session.stateless.PartnerEntitySessionBeanRemote;
import ejb.session.stateless.ReservationEntitySessionBeanRemote;
import ejb.session.stateless.RoomEntitySessionBeanRemote;
import ejb.session.stateless.RoomRateEntitySessionBeanRemote;
import ejb.session.stateless.RoomTypeEntitySessionBeanRemote;
import ejb.session.stateless.TransactionEntitySessionBeanRemote;
import entity.EmployeeEntity;
import entity.ReservationEntity;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.InputDataValidationException;
import util.exception.InsufficientRoomsAvailableException;
import util.exception.InvalidAccessRightException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
public class FrontOfficeModule {

    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;
    private ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote;
    private GuestEntitySessionBeanRemote guestEntitySessionBeanRemote;
    private PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote;
    private ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote;
    private RoomEntitySessionBeanRemote roomEntitySessionBeanRemote;
    private RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote;
    private RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote;
    private TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote;

    private EmployeeEntity currentEmployee;

    public FrontOfficeModule() {
    }

    public FrontOfficeModule(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote, EmployeeEntity currentEmployee) {
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
        this.exceptionReportEntitySessionBeanRemote = exceptionReportEntitySessionBeanRemote;
        this.guestEntitySessionBeanRemote = guestEntitySessionBeanRemote;
        this.partnerEntitySessionBeanRemote = partnerEntitySessionBeanRemote;
        this.reservationEntitySessionBeanRemote = reservationEntitySessionBeanRemote;
        this.roomEntitySessionBeanRemote = roomEntitySessionBeanRemote;
        this.roomRateEntitySessionBeanRemote = roomRateEntitySessionBeanRemote;
        this.roomTypeEntitySessionBeanRemote = roomTypeEntitySessionBeanRemote;
        this.transactionEntitySessionBeanRemote = transactionEntitySessionBeanRemote;
        this.currentEmployee = currentEmployee;
    }

    public void menuFrontOfficeOperation() throws InvalidAccessRightException {
        if (currentEmployee.getEmployeeAccessRightEnum() != EmployeeAccessRightEnum.GUEST_RELATION_OFFICER) {
            throw new InvalidAccessRightException("You dont have GUEST RELATION OFFICER rights to access the System Administration Module");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Management Client  System :: Front Office Module ***\n");
            System.out.println("1: Walk-in Search Room");
            System.out.println("2: Check-in Guest");
            System.out.println("3: Check-out Guest");
            System.out.println("4: Exit");
            response = 0;

            while (response < 1 || response > 4) {
                response = scanner.nextInt();

                if (response == 1) {
                    //WALK-IN SEARCH ROOM
                    //INCLUDES WALK-IN RESERVE ROOM
                    doWalkInSearch();
                } else if (response == 2) {
                    //CHECK-IN GUEST
                    doCheckIn();
                } else if (response == 3) {
                    //CHECK-OUT GUEST
                    doCheckOut();
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

    public void doWalkInSearch() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Management Client :: Front Office Module :: Walk In Search ***\n");
        System.out.print("Enter Room Type Name> ");
        String roomTypeName = scanner.nextLine().trim();

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
            System.out.println("Please Enter Reservation Start Date> ");
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

        HashMap<String, HashMap<String, BigDecimal>> map;
        try {
            map = reservationEntitySessionBeanRemote.retrieveAvailableRoomTypes(reservationStartDate, reservationEndDate, noRooms);
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
                        doWalkInReserve(map, reservationStartDate, reservationEndDate, noRooms);
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

    private void doWalkInReserve(HashMap<String, HashMap<String, BigDecimal>> map, LocalDateTime reservationStartDate, LocalDateTime reservationEndDate, Integer numRooms) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Hotel Management Client :: Front Office Module :: Walk In Reserve ***\n");
        System.out.println("");
        List<ReservationEntity> listOfNewReservations = new ArrayList<>();

        try {
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
                if (contactNumber.length() != 8) {
                    break;
                } else {
                    System.out.println("Please input a valid Contact Number");
                }
            }

            while (true) {
                System.out.println("Enter Walk-In Guest's Passport Number>");
                passportNumber = scanner.nextLine();
                if (passportNumber.length() != 8) {
                    break;
                } else {
                    System.out.println("Please input a valid Passport Number");
                }
            }

            BigDecimal totalPayment = BigDecimal.ZERO;
            List<String> listOfKeys = new ArrayList<>(map.keySet());
            int numReservation = 1;
            while (numRooms > numReservation) {
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
                while (response < 1 || response > roomTypeNameList.size()) {
                    response = 0;
                    response = scanner.nextInt();
                    if (response < 1 || response > roomTypeNameList.size()) {
                        selectedRoomType = roomTypeNameList.get(response - 1);
                        newReservation.setRoomTypeName(selectedRoomType);
                        newReservation.setReservationPrice(map.get(selectedRoomType).get("bestPrice"));
                        totalPayment.add(newReservation.getReservationPrice());
                    } else {
                        System.out.println("Invalid option, please try again!\n");
                        continue;
                    }

                }
                reservationEntitySessionBeanRemote.createNewReservation(newReservation);

                HashMap<String, BigDecimal> stringToBigDecimalMap = map.get(selectedRoomType);
                stringToBigDecimalMap.put("numRoomType", stringToBigDecimalMap.get("numRoomType").subtract(BigDecimal.ONE));

                numReservation += 1;
            }
        } catch (UnknownPersistenceException ex) {
            System.out.println("Unable to create Reservation, Please Try Again!");
        } catch (InputDataValidationException ex) {
            System.out.println("Some details are invalid, Please Try Again!");
        }

        LocalDateTime currDateTime = LocalDateTime.now();
        LocalDateTime currDate2Am = LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 0));

        //after 2am walk in, if reservations are for TODAY, then immediately allcoate
        if (currDateTime.isAfter(currDate2Am) && reservationStartDate.isEqual(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)))) {
            //allocationTimerSessionBeanRemote.allocateSelectedReservationsToRoomsNow(listOfNewReservations);
        }
    }

    public void doCheckIn() {
        //check guest in by informing them of the allocated rooms
        //set rooms to unavailable
        //if no room allocated, find exception report and let staff handle ( you dont need to do anything just show the report)

        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Hotel Management Client :: Front Office Module :: Guest Check In ***\n");
        System.out.println("");
        
        System.out.println("Please Enter Guest Passport Number");
        String passportNumber = scanner.nextLine();
        
        reservationEntitySessionBeanRemote.retrieveReservationByPassportNumber(passportNumber);
        
        
    }

    public void doCheckOut() {
        //check guest out by taking in the room they stayed in
        // set the room to be Available again
        //detach room from reservation
    }
}
