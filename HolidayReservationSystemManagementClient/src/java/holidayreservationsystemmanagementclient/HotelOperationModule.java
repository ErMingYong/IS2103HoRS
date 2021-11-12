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
import entity.EmployeeEntity;
import entity.ExceptionReportEntity;
import entity.RoomEntity;
import entity.RoomTypeEntity;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.EmployeeAccessRightEnum;
import util.enumeration.ExceptionReportTypeEnum;
import util.enumeration.RoomStatusEnum;
import util.exception.InputDataValidationException;
import util.exception.InvalidAccessRightException;
import util.exception.RoomFloorAndNumberExistException;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeNameExistException;
import util.exception.RoomTypeNotFoundException;
import util.exception.UnableToDisableRoomException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRoomException;

/**
 *
 * @author mingy
 */
public class HotelOperationModule {

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

    private EmployeeEntity currentEmployee;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public HotelOperationModule() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public HotelOperationModule(AllocationReportSessionBeanRemote allocationReportSessionBeanRemote, EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, EmployeeEntity currentEmployee) {
        this();
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
        this.currentEmployee = currentEmployee;
    }

    public void menuHotelOperation() throws InvalidAccessRightException, UnknownPersistenceException {
        if (currentEmployee.getEmployeeAccessRightEnum() != EmployeeAccessRightEnum.OPERATION_MANAGER) {
            throw new InvalidAccessRightException("You dont have OPERATION MANAGER rights to access the System Administration Module");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.println("*** Hotel Reservation System Management Client System :: Hotel Operation Module :: Hotel Operation ***\n");
            System.out.println("1: Create New Room Type");
            System.out.println("2: View Room Type Details");
            System.out.println("3: View All Room Types");
            System.out.println("----------------------------------------");
            System.out.println("4: Create New Room");
            System.out.println("5: Update Room");
            System.out.println("6: Delete Room");
            System.out.println("7: View All Rooms");
            System.out.println("8: View Room Allocation Exception Report");
            System.out.println("9: Trigger Allocation By Date");
            System.out.println("10: Exit");
            System.out.println(">");
            response = 0;

            while (response < 1 || response > 10) {
                response = scanner.nextInt();

                if (response == 1) {
                    doCreateNewRoomType();
                } else if (response == 2) {
                    //INCLUDES -> UPDATE ROOM TYPE
                    //INCLUDES -> DELETE ROOM TYPE
                    doViewRoomTypeDetails();
                } else if (response == 3) {
                    doViewAllRoomTypes();
                } else if (response == 4) {
                    doCreateNewRoom();
                } else if (response == 5) {
                    try {
                        doUpdateRoom();
                    } catch (PersistenceException ex) {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else if (response == 6) {
                    doDeleteRoom();
                } else if (response == 7) {
                    doViewAllRooms();
                } else if (response == 8) {
                    //VIEW ROOM ALLOCATION EXCEPTION REPORT
                    doViewRoomAllocationExceptionReport();
                } else if (response == 9) {
                    doTriggerAllocationByDate();
                } else if (response == 10) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 10) {
                break;
            }
        }
    }

    public void doCreateNewRoomType() {
        Scanner scanner = new Scanner(System.in);
        RoomTypeEntity newRoomType = new RoomTypeEntity();

        System.out.println("*** Hotel Management Client :: Hotel Operation :: Create New Room Type ***\n");
        System.out.println("---------------------------------");
        System.out.println("Enter Room Type Name> ");
        newRoomType.setRoomTypeName(scanner.nextLine().trim());

        System.out.println("Enter Room Type Description> ");
        newRoomType.setDescription(scanner.nextLine().trim());

        System.out.println("Enter Room Type Size");
        newRoomType.setSize(scanner.nextLine().trim());

        System.out.println("Enter Room Type Bed");
        newRoomType.setBed(scanner.nextLine().trim());

        System.out.println("Enter Room Type Capacity");
        newRoomType.setCapacity(scanner.nextInt());
        scanner.nextLine();

        System.out.println("Enter Room Type Amenities");
        newRoomType.setAmenities(scanner.nextLine().trim());

        System.out.println("Enter Room Type Ranking");
        newRoomType.setRanking(scanner.nextInt());

        Set<ConstraintViolation<RoomTypeEntity>> constraintViolations = validator.validate(newRoomType);

        if (constraintViolations.isEmpty()) {
            try {
                Long roomTypeId = roomTypeEntitySessionBeanRemote.createNewRoomType(newRoomType);
                System.out.println("Room Type created successfully!: " + roomTypeId + "\n");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");
            } catch (UnknownPersistenceException ex) {
                System.out.println("Unknown persistence error caught");
            } catch (RoomTypeNameExistException ex) {
                System.out.println("RoomType name already exist!");
            }
        } else {
            showInputDataValidationErrorsForRoomTypeEntity(constraintViolations);
        }
    }

    //PROBLEM
    public void doViewRoomTypeDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** Hotel Management Client :: Hotel Operation Module :: View Room Type Details ***\n");
        System.out.print("Enter Room Type Name> ");
        String roomTypeName = scanner.nextLine().trim();

        try {
            RoomTypeEntity roomType = roomTypeEntitySessionBeanRemote.retrieveRoomTypeByName(roomTypeName);
            System.out.printf("%30.30s%30.30s%10.10s%10.10s%10.10s%15.20s%10.10s%10.10s\n", "Room Type Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Disabled", "Ranking");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%30.30s%30.30s%10.10s%10.10s%10d%15.20s%10.10b%10d\n", roomType.getRoomTypeName(), roomType.getDescription(), roomType.getSize(), roomType.getBed(), roomType.getCapacity(), roomType.getAmenities(), roomType.getIsDisabled(), roomType.getRanking());
            System.out.println("------------------------");
            System.out.println("1: Update Room Type");
            System.out.println("2: Delete Room Type");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = 0;
            while (response < 1 || response > 3) {
                response = scanner.nextInt();
                if (response == 1) {
                    doUpdateRoomType(roomType);
                } else if (response == 2) {
                    doDeleteRoomType(roomType);
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Room Type Name: " + roomTypeName + " does not exist");
        }
    }

    private void doUpdateRoomType(RoomTypeEntity roomType) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        System.out.println("*** Hotel Management Client :: Hotel Operation Module:: View Room Type Details :: Update Room Type ***\n");

        System.out.print("Enter Room Type Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomType.setRoomTypeName(input);
        }

        System.out.print("Enter Room Type Description (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomType.setDescription(input);
        }

        System.out.print("Enter Room Type Size (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomType.setSize(input);
        }

        System.out.print("Enter Room Type Bed (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomType.setBed(input);
        }

        System.out.print("Enter Room Type Capacity (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomType.setCapacity(Integer.valueOf(input));
        }

        System.out.print("Enter Room Type Amenities (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            roomType.setAmenities(input);
        }
        while (true) {
            System.out.print("Disable Room Type (\"T\" for true, \"F\" for false)> ");
            input = scanner.nextLine().trim();
            if (input.equals("T")) {
                roomType.setIsDisabled(true);
            } else if (input.equals("F")) {
                roomType.setIsDisabled(false);
            } else {
                System.out.println("Please input valid option!");
                System.out.println("");
                continue;
            }
            break;
        }

        System.out.print("Enter Room Type Ranking (Original Rank: " + roomType.getRanking() + ")> ");
        int rank = 0;
        rank = scanner.nextInt();
        if (rank > 0) {
            roomType.setRanking(rank);
        }
        scanner.nextLine();

        Set<ConstraintViolation<RoomTypeEntity>> constraintViolations = validator.validate(roomType);

        if (constraintViolations.isEmpty()) {
            try {
                roomTypeEntitySessionBeanRemote.updateRoomType(roomType);
                Long updatedRoomTypeId = roomType.getRoomTypeId();
                System.out.println("Room Type updated successfully!: " + updatedRoomTypeId + "\n");
            } catch (RoomTypeNotFoundException ex) {
                System.out.println("An error has occurred while updating product: " + ex.getMessage() + "\n");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");

            } catch (RoomTypeNameExistException ex) {
                System.out.println("RoomType Name inputted is already being used by another Room Type!");
            }
        } else {
            showInputDataValidationErrorsForRoomTypeEntity(constraintViolations);
        }
        System.out.println("-------------------------------");
    }

    private void doDeleteRoomType(RoomTypeEntity roomType) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** Hotel Management Client :: Hotel Operation Module :: View ROom Type Details :: Delete Room Type ***\n");
        System.out.printf("Confirm Delete Room Type %s (Room Type ID: %d) (Enter 'Y' to Delete)> ", roomType.getRoomTypeName(), roomType.getRoomTypeId());
        input = scanner.nextLine().trim();

        if (input.equals("Y")) {
            try {
                roomTypeEntitySessionBeanRemote.deleteRoomType(roomType.getRoomTypeId());
                System.out.println("Room Type deleted/disabled successfully!\n");
            } catch (RoomTypeNotFoundException ex) {
                System.out.println("An error has occurred while deleting the room type: " + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Room NOT deleted!\n");
        }
    }

    private void doViewAllRoomTypes() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Management Client :: Hotal Operation Module :: View All Room Types ***\n");

        List<RoomTypeEntity> roomTypeEntities = roomTypeEntitySessionBeanRemote.retrieveAllRoomTypes();
        if (roomTypeEntities.isEmpty()) {
            System.out.println("-----------------------------------------");
            System.out.println("You do not have any Room Types!");
            System.out.println("");
            System.out.println("Press any key to go back...");
            scanner.nextLine();
        } else {
            System.out.printf("%15.15s%30.30s%10.10s%10.10s%10.10s%15.20s%10.10s%10.10s\n", "Room Type Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Disabled", "Ranking");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            for (RoomTypeEntity roomTypeEntity : roomTypeEntities) {
                System.out.printf("%15.15s%30.30s%10.10s%10.10s%10d%15.20s%10.10b%10d\n", roomTypeEntity.getRoomTypeName(), roomTypeEntity.getDescription(), roomTypeEntity.getSize(), roomTypeEntity.getBed(), roomTypeEntity.getCapacity(), roomTypeEntity.getAmenities(), roomTypeEntity.getIsDisabled(), roomTypeEntity.getRanking());
            }

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }

    }

    public void doCreateNewRoom() {

        Scanner scanner = new Scanner(System.in);
        RoomEntity newRoom = new RoomEntity();

        System.out.println("*** Hotel Management Client :: Hotel Operation :: Create New Room ***\n");

        int roomFloor;
        int roomNumber;

        System.out.print("Enter Room Floor> ");
        roomFloor = scanner.nextInt();
        System.out.print("Enter Room Number> ");
        roomNumber = scanner.nextInt();

        newRoom.setRoomFloor(roomFloor);
        newRoom.setRoomNumber(roomNumber);

        scanner.nextLine();
        System.out.println("-------------------------------");
        //Cannot set disabled, by default in creation should be false for isDisabled
        //Need to include room type
        RoomTypeEntity retrievedRoomType = null;

        while (true) {
            System.out.print("Enter Room Type Name> ");
            String roomType = scanner.nextLine();
            try {
                retrievedRoomType = roomTypeEntitySessionBeanRemote.retrieveRoomTypeByName(roomType);
            } catch (RoomTypeNotFoundException ex) {
                System.out.println("Room Type Name not found, please try again!");
                continue;
            }

            if (retrievedRoomType.getIsDisabled() == true) {
                System.out.println("Cannot create new room of disabled room type!");
                retrievedRoomType = null;
                continue;
            }
            if (retrievedRoomType != null) {
                break;
            }
        }
        newRoom.setRoomTypeEntity(retrievedRoomType);

        Set<ConstraintViolation<RoomEntity>> constraintViolations = validator.validate(newRoom);

        if (constraintViolations.isEmpty()) {
            try {
                Long newRoomId = roomEntitySessionBeanRemote.createNewRoom(newRoom);
                System.out.println("New room created successfully!: " + newRoomId + "\n");
            } catch (RoomFloorAndNumberExistException ex) {
                System.out.println("An error has occurred while creating the new room!: The room number and floor already exist\n");
            } catch (UnknownPersistenceException ex) {
                System.out.println("An unknown error has occurred while creating the new room!: " + ex.getMessage() + "\n");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        } else {
            showInputDataValidationErrorsForRoomEntity(constraintViolations);
        }
    }

    private void doUpdateRoom() throws UnknownPersistenceException {
        Scanner scanner = new Scanner(System.in);
        Integer input = 0;
        System.out.println("*** Hotel Management Client :: Hotel Operation :: Update Room ***\n");

        int roomFloor;
        int roomNumber;
        RoomEntity roomToUpdate;
        while (true) {
            System.out.println("Enter Room Floor> ");
            roomFloor = scanner.nextInt();

            System.out.println("Enter Room Number> ");
            roomNumber = scanner.nextInt();

            try {
                roomToUpdate = roomEntitySessionBeanRemote.retrieveRoomByRoomFloorAndRoomNumber(roomFloor, roomNumber);
            } catch (RoomNotFoundException ex) {
                System.out.println("Room cannot be Found! Please key in correct room floor and number");
                continue;
            }
            break;
        }

        System.out.println("Enter New Room Floor> ");
        roomFloor = scanner.nextInt();

        System.out.println("Enter New Room Number> ");
        roomNumber = scanner.nextInt();

        roomToUpdate.setRoomFloor(roomFloor);
        roomToUpdate.setRoomNumber(roomNumber);

        RoomTypeEntity retrievedRoomType = null;
        scanner.nextLine();
        while (true) {
            System.out.print("Enter Room Type Name> ");
            String roomType = scanner.nextLine();
            try {
                retrievedRoomType = roomTypeEntitySessionBeanRemote.retrieveRoomTypeByName(roomType);
            } catch (RoomTypeNotFoundException ex) {
                System.out.println("Room Type Name not found, please try again!");
            }

            if (retrievedRoomType.getIsDisabled() == true) {
                System.out.println("Cannot update room using disabled room type!");
                retrievedRoomType = null;
                continue;
            }
            if (retrievedRoomType != null) {
                break;
            }
        }

        roomToUpdate.setRoomTypeEntity(retrievedRoomType);

        while (true) {
            System.out.print("Select Room Status> ");
            System.out.println("------------------------");
            System.out.println("1: Available");
            System.out.println("2: Not Available");
            System.out.println("3: Disabled");
            System.out.println("------------------------");

            Integer roomStatus = scanner.nextInt();

            if (roomStatus >= 1 && roomStatus <= 3) {
                roomToUpdate.setRoomStatusEnum(RoomStatusEnum.values()[roomStatus - 1]);
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        Set<ConstraintViolation<RoomEntity>> constraintViolations = validator.validate(roomToUpdate);

        if (constraintViolations.isEmpty()) {
            try {
                roomEntitySessionBeanRemote.updateRoom(roomToUpdate);
                System.out.println("Room updated successfully!\n");
            } catch (RoomNotFoundException | UpdateRoomException ex) {
                System.out.println("An error has occurred while updating Room: " + ex.getMessage() + "\n");
            } catch (InputDataValidationException | RoomFloorAndNumberExistException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        } else {
            showInputDataValidationErrorsForRoomEntity(constraintViolations);
        }
    }

    private void doDeleteRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Hotel Management Client :: Hotel Operation :: Delete Room ***\n");

        int roomFloor;
        int roomNumber;
        RoomEntity roomToDelete;
        while (true) {
            System.out.print("Enter Room Floor> ");
            roomFloor = scanner.nextInt();

            System.out.print("Enter Room Number> ");
            roomNumber = scanner.nextInt();

            try {
                roomToDelete = roomEntitySessionBeanRemote.retrieveRoomByRoomFloorAndRoomNumber(roomFloor, roomNumber);
                System.out.println("Room has been deleted/disabled!");
            } catch (RoomNotFoundException ex) {
                System.out.println("Room cannot be Found! Please key in correct room floor and number");
                continue;
            }
            break;
        }

        try {
            // can delete
            roomEntitySessionBeanRemote.deleteRoom(roomToDelete);
        } catch (RoomNotFoundException ex) {
            System.out.println("Room to be deleted cannot be found");
        } catch (UnableToDisableRoomException ex) {
            System.out.println("Room Cannot be disabled/deleted!");
        }
    }

    private void doViewAllRooms() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Management Client :: Hotal Operation Module :: View All Rooms ***\n");

        List<RoomEntity> roomEntities = roomEntitySessionBeanRemote.retrieveAllRooms();
        if (roomEntities.isEmpty()) {
            System.out.println("-----------------------------------------");
            System.out.println("You do not have any Rooms!");
            System.out.println("");
            System.out.println("Press any key to go back...");
            scanner.nextLine();
        } else {
            System.out.printf("%30s%30s%30s%30s\n", "Room Floor", "Room Number", "Room Type", "Room Status");

            for (RoomEntity roomEntity : roomEntities) {
                System.out.printf("%30d%30d%30.30s%30.30s\n", roomEntity.getRoomFloor(), roomEntity.getRoomNumber(), roomEntity.getRoomTypeEntity().getRoomTypeName(), roomEntity.getRoomStatusEnum());
            }

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }

    }

    public void doViewRoomAllocationExceptionReport() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        boolean doExit = false;

        System.out.println("*** Hotel Management Client :: Hotel Operation Module :: View Room Allocation Exception Reports ***\n");
        ExceptionReportTypeEnum exceptionReportTypeEnum = ExceptionReportTypeEnum.FIRST_TYPE;
        while (true) {
            System.out.print("Select Type of Exception Report To View> ");
            System.out.println("------------------------");
            System.out.println("1: First Type");
            System.out.println("2: Second Type");
            System.out.println("3: Exit");

            System.out.print("> ");
            response = 0;

            while (response < 1 || response > 3) {
                response = scanner.nextInt();
                if (response == 1) {
                    exceptionReportTypeEnum = ExceptionReportTypeEnum.FIRST_TYPE;
                } else if (response == 2) {
                    exceptionReportTypeEnum = ExceptionReportTypeEnum.SECOND_TYPE;
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }

            scanner.nextLine();
            LocalDateTime dateToView;
            while (true) {
                System.out.println("Please Enter Date To View> ");
                System.out.println("------------------------");
                System.out.println("Please Enter Day of Date To View>   (please select from 01 - 31)");
                int day = scanner.nextInt();
                System.out.println("Please Enter Month of Date To View>   (please select from 01 - 12)");
                int month = scanner.nextInt();
                System.out.println("Please Enter Year of Date To View>   (please select from 2000 - 2999)");
                int year = scanner.nextInt();
                try {
                    dateToView = LocalDateTime.of(year, month, day, 0, 0, 0);
                } catch (DateTimeException ex) {
                    System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                    continue;
                }
                break;
            }

            List<ExceptionReportEntity> listOfReports = exceptionReportEntitySessionBeanRemote.retrieveExceptionReportsByTypeAndDate(exceptionReportTypeEnum, dateToView);
            if (listOfReports.isEmpty()) {
                System.out.println("No Exception Report Of " + exceptionReportTypeEnum.name() + " for the date " + dateToView.toString());
            } else {

                System.out.println("GENERATED LIST OF REPORTS (" + exceptionReportTypeEnum + ")");
                System.out.println("----------------------------------------------");
                System.out.println("Please select report to view");
                int counter = 1;
                for (ExceptionReportEntity exceptionReportEntity : listOfReports) {
                    System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

                    String exceptionReportId = exceptionReportEntity.getExceptionReportId().toString();
                    String generatedDate = exceptionReportEntity.getGenerationDate().toLocalDate().toString();
                    String guestName = exceptionReportEntity.getReservationEntity().getFirstName() + " " + exceptionReportEntity.getReservationEntity().getLastName();
                    String guestPassport = exceptionReportEntity.getReservationEntity().getPassportNumber();
                    String guestEmail = exceptionReportEntity.getReservationEntity().getEmail();
                    String bookedRoomType = exceptionReportEntity.getReservationEntity().getRoomTypeName();
                    String roomTypeAllocation;
                    if (exceptionReportEntity.getReservationEntity().getRoomEntity() != null) {
                        roomTypeAllocation = exceptionReportEntity.getReservationEntity().getRoomEntity().getRoomTypeEntity().getRoomTypeName();
                    } else {
                        roomTypeAllocation = "NO ROOM ALLOCATED";
                    }

                    System.out.printf("%5.5s%25.25s%25.25s%20.20s%20.20s%20.20s%20.20s%20.20s\n", "S/N", "Exception Report Id", "Generated Date", "Guest Name", "Guest Passport", "Guest Email", "Room Type Booked", "Room Type Allocated");
                    System.out.printf("%5.5s%25.25s%25.25s%20.20s%20.20s%20.20s%20.20s%20.20s\n", counter, exceptionReportId, generatedDate, guestName, guestPassport, guestEmail, bookedRoomType, roomTypeAllocation);

                    counter++;
                }
                System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
                System.out.println("Press any key to go back...");
                scanner.nextLine();
                scanner.nextLine();
                System.out.println("");
            }

        }
    }

    public void doTriggerAllocationByDate() {
        System.out.println("*** Hotel Management Client :: Hotel Operation Module :: Trigger Allocation By Date ***\n");

        Scanner scanner = new Scanner(System.in);
        LocalDateTime allocationDate;
        while (true) {
            System.out.println("Please Enter Allocation Date> ");
            System.out.println("------------------------");
            System.out.println("Please Enter Day of Date>   (please select from 01 - 31)");
            int day = scanner.nextInt();
            System.out.println("Please Enter Month of Date>   (please select from 01 - 12)");
            int month = scanner.nextInt();
            System.out.println("Please Enter Year of Date>   (please select from 2000 - 2999)");
            int year = scanner.nextInt();
            try {
                allocationDate = LocalDateTime.of(year, month, day, 0, 0, 0);
            } catch (DateTimeException ex) {
                System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                continue;
            }
            break;

        }
        try {
            allocationReportSessionBeanRemote.allocateReservationByDate(allocationDate);
            System.out.println("Allocation for Date: " + allocationDate.toLocalDate() + " has started");
            System.out.println("");
        } catch (UnknownPersistenceException ex) {
            System.out.println("Something went wrong with the allcoation");
            System.out.println("");
        }

    }

    private void showInputDataValidationErrorsForRoomTypeEntity(Set<ConstraintViolation<RoomTypeEntity>> constraintViolations) {
        System.out.println("\nInput data validation error!:");

        for (ConstraintViolation constraintViolation : constraintViolations) {
            System.out.println("\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage());
        }

        System.out.println("\nPlease try again......\n");
    }

    private void showInputDataValidationErrorsForRoomEntity(Set<ConstraintViolation<RoomEntity>> constraintViolations) {
        System.out.println("\nInput data validation error!:");

        for (ConstraintViolation constraintViolation : constraintViolations) {
            System.out.println("\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage());
        }

        System.out.println("\nPlease try again......\n");
    }
}
