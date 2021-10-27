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
import entity.RoomEntity;
import entity.RoomTypeEntity;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.EmployeeAccessRightEnum;
import util.enumeration.RoomStatusEnum;
import util.exception.InputDataValidationException;
import util.exception.InvalidAccessRightException;
import util.exception.RoomFloorAndNumberExistException;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeNameExistException;
import util.exception.RoomTypeNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRoomException;
import util.exception.UpdateRoomTypeException;

/**
 *
 * @author mingy
 */
public class HotelOperationModule {

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

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public HotelOperationModule() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public HotelOperationModule(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote, EmployeeEntity currentEmployee) {
        this();
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

    public void menuHotelOperation() throws InvalidAccessRightException, UnknownPersistenceException {
        if (currentEmployee.getEmployeeAccessRightEnum() != EmployeeAccessRightEnum.OPERATION_MANAGER) {
            throw new InvalidAccessRightException("You dont have OPERATION MANAGER rights to access the System Administration Module");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Management Client System :: Hotel Operation Module ***\n");
            System.out.println("1: Create New Room Type");
            System.out.println("2: View Room Type Details");
            System.out.println("3: View All Room Types");
            System.out.println("----------------------------------------");
            System.out.println("4: Create New Room");
            System.out.println("5: Update Room");
            System.out.println("6: Delete Room");
            System.out.println("7: View All Room");
            System.out.println("8: View Room Allocation Exception Report");
            System.out.println("9: Exit");

            while (response < 1 || response > 9) {
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
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 9) {
                break;
            }
        }
    }

    public void doCreateNewRoomType() {
        Scanner scanner = new Scanner(System.in);
        RoomTypeEntity newRoomType = new RoomTypeEntity();

        System.out.println("*** Hotel Management Client :: Hotel Operation :: Create New Room Type ***\n");
        System.out.println("---------------------------------");
        System.out.print("Enter Room Type Name> ");
        newRoomType.setName(scanner.nextLine().trim());

        System.out.print("Enter Room Type Description> ");
        newRoomType.setDescription(scanner.nextLine().trim());

        System.out.println("Enter Room Type Size");
        newRoomType.setSize(scanner.nextLine().trim());

        System.out.println("Enter Room Type Bed");
        newRoomType.setBed(scanner.nextLine().trim());

        System.out.println("Enter Room Type Capacity");
        newRoomType.setCapacity(scanner.nextInt());

        System.out.println("Enter Room Type Amenities");
        newRoomType.setAmenities(scanner.nextLine().trim());

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

    public void doViewRoomTypeDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** Hotel Management Client :: Hotel Operation Module :: View Room Type Details ***\n");
        System.out.print("Enter Room Type Name> ");
        String roomTypeName = scanner.nextLine().trim();

        try {
            RoomTypeEntity roomType = roomTypeEntitySessionBeanRemote.retrieveRoomTypeByName(roomTypeName);
            System.out.printf("%s%s%s%s%d%s%b\n", "Room Type Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Disabled");
            System.out.printf("%s%s%s%s%d%s%b\n", roomType.getName().toString(), roomType.getDescription(), roomType.getSize(), roomType.getBed().toString(), roomType.getCapacity(), roomType.getAmenities(), roomType.getIsDisabled());
            System.out.println("------------------------");
            System.out.println("1: Update Room Type");
            System.out.println("2: Delete Room Type");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = scanner.nextInt();
            while (response < 1 || response > 9) {
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
            roomType.setName(input);
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

        Set<ConstraintViolation<RoomTypeEntity>> constraintViolations = validator.validate(roomType);

        if (constraintViolations.isEmpty()) {
            try {
                roomTypeEntitySessionBeanRemote.updateRoomType(roomType);
                Long updatedRoomTypeId = roomType.getRoomTypeId();
                System.out.println("Room Type updated successfully!: " + updatedRoomTypeId + "\n");
            } catch (UpdateRoomTypeException | RoomTypeNotFoundException ex) {
                System.out.println("An error has occurred while updating product: " + ex.getMessage() + "\n");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");

            }
        } else {
            showInputDataValidationErrorsForRoomTypeEntity(constraintViolations);
        }
    }

    private void doDeleteRoomType(RoomTypeEntity roomType) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** Hotel Management Client :: Hotel Operation Module :: View ROom Type Details :: Delete Room Type ***\n");
        System.out.printf("Confirm Delete Room Type %s (Room Type ID: %d) (Enter 'Y' to Delete)> ", roomType.getName(), roomType.getRoomTypeId());
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
        System.out.printf("%s%s%s%s%s%s%b\n", "Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Disabled");

        for (RoomTypeEntity roomTypeEntity : roomTypeEntities) {
            System.out.printf("%s%s%s%s%s%s%b\n", roomTypeEntity.getName(), roomTypeEntity.getDescription(), roomTypeEntity.getSize(), roomTypeEntity.getBed(), roomTypeEntity.getCapacity(), roomTypeEntity.getAmenities(), roomTypeEntity.getIsDisabled());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    public void doCreateNewRoom() {

        Scanner scanner = new Scanner(System.in);
        RoomEntity newRoom = new RoomEntity();

        System.out.println("*** Hotel Management Client :: Hotel Operation :: Create New Room ***\n");

        int roomFloor;
        int roomNumber;
        while (true) {
            System.out.print("Enter Room Floor> ");
            roomFloor = scanner.nextInt();

            System.out.print("Enter Room Number> ");
            roomNumber = scanner.nextInt();

            try {
                RoomEntity existingRoom = roomEntitySessionBeanRemote.retrieveRoomByRoomFloorAndRoomNumber(roomFloor, roomNumber);
            } catch (RoomNotFoundException ex) {
                newRoom.setRoomFloor(scanner.nextInt());
                newRoom.setRoomNumber(scanner.nextInt());
                break;
            }

            System.out.println("Room Floor and Number already exist! Cannot create duplicate room!");

        }
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
            System.out.print("Enter Room Floor> ");
            roomFloor = scanner.nextInt();

            System.out.print("Enter Room Number> ");
            roomNumber = scanner.nextInt();

            try {
                roomToUpdate = roomEntitySessionBeanRemote.retrieveRoomByRoomFloorAndRoomNumber(roomFloor, roomNumber);
            } catch (RoomNotFoundException ex) {
                System.out.println("Room cannot be Found! Please key in correct room floor and number");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Enter New Room Floor> ");
            roomFloor = scanner.nextInt();

            System.out.print("Enter New Room Number> ");
            roomNumber = scanner.nextInt();

            try {
                RoomEntity existingRoom = roomEntitySessionBeanRemote.retrieveRoomByRoomFloorAndRoomNumber(roomFloor, roomNumber);
            } catch (RoomNotFoundException ex) {
                roomToUpdate.setRoomFloor(scanner.nextInt());
                roomToUpdate.setRoomNumber(scanner.nextInt());
                break;
            }

            System.out.println("Room Floor and Number already exist! Cannot create duplicate room!");
        }

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
            } catch (InputDataValidationException ex) {
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
            } catch (RoomNotFoundException ex) {
                System.out.println("Room cannot be Found! Please key in correct room floor and number");
                continue;
            }
            break;
        }
        //check if room has someone staying inside
        if (roomToDelete.getRoomStatusEnum() == RoomStatusEnum.UNAVAILABLE) {
            //if have customer staying, DISABLE
            roomToDelete.setRoomStatusEnum(RoomStatusEnum.DISABLED);
            try {
                roomEntitySessionBeanRemote.updateRoom(roomToDelete);
            } catch (UpdateRoomException ex) {
                System.out.println("Unable to update Room status to Disabled");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");
            } catch (RoomNotFoundException ex) {
                System.out.println("Room to be disabled cannot be found");
            }

            System.out.println("Room Status has been set to DISABLED as it is currently occupied");
        } else {
            try {
                // can delete
                roomEntitySessionBeanRemote.deleteRoom(roomToDelete);
            } catch (RoomNotFoundException ex) {
                System.out.println("Room to be deleted cannot be found");
            }
        }

    }

    private void doViewAllRooms() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Management Client :: Hotal Operation Module :: View All Rooms ***\n");

        List<RoomEntity> roomEntities = roomEntitySessionBeanRemote.retrieveAllRooms();
        System.out.printf("%d%d\n", "Room Floor", "Room Number");

        for (RoomEntity roomEntity : roomEntities) {
            System.out.printf("%d%d\n", roomEntity.getRoomFloor(), roomEntity.getRoomNumber());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }
    
    public void doViewRoomAllocationExceptionReport() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** Hotel Management Client :: Hotel Operation Module :: View Room Allocation Exception Reports ***\n");
        System.out.print("Select Type of Exception Report To View> ");
        String roomTypeName = scanner.nextLine().trim();

        try {
            RoomTypeEntity roomType = roomTypeEntitySessionBeanRemote.retrieveRoomTypeByName(roomTypeName);
            System.out.printf("%s%s%s%s%d%s%b\n", "Room Type Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Disabled");
            System.out.printf("%s%s%s%s%d%s%b\n", roomType.getName().toString(), roomType.getDescription(), roomType.getSize(), roomType.getBed().toString(), roomType.getCapacity(), roomType.getAmenities(), roomType.getIsDisabled());
            System.out.println("------------------------");
            System.out.println("1: First Type");
            System.out.println("2: Second Type");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = scanner.nextInt();
            while (response < 1 || response > 9) {
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
