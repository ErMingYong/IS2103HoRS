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
import javax.persistence.PersistenceException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.EmployeeAccessRightEnum;
import util.enumeration.RoomStatusEnum;
import util.exception.InvalidAccessRightException;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeNotFoundException;
import util.exception.UnknownPersistenceException;

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
            System.out.println("*** Hotel Reservation System Management Client  System :: Hotel Operation Module ***\n");
            System.out.println("1: Create New Room Type");
            System.out.println("2: View Room Type Details");
            System.out.println("3: View All Room Types");
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
                    doViewRoomTypeDetails();
                    //INCLUDES -> UPDATE ROOM TYPE
                    //INCLUDES -> DELETE ROOM TYPE
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
                    doViewAllRoom();
                } else if (response == 8) {
                    //VIEW ROOM ALLOCATION EXCEPTION REPORT
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

        System.out.println("Enable/Disable Room Type (0: Enabled, 1: Disabled)> ");
        Integer response = scanner.nextInt();
        while (true) {
            if (response == 0) {
                newRoomType.setDisabled(Boolean.TRUE);
                break;
            } else if (response == 1) {
                newRoomType.setDisabled(Boolean.FALSE);
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        /*
        Set<ConstraintViolation<RoomTypeEntity>> constraintViolations = validator.validate(newRoomType);
        
        if (constraintViolations.isEmpty()) {
            try {
                Long roomTypeId = roomTypeEntitySessionBeanRemote.createNewRoomType(newRoomType);
                System.out.println("Room Type created successfully!: " + roomTypeId + "\n");
            } catch ()
                // TO DO CONTINUED HERE
        }*/
        //BEAN VALIDATION CHECKS
    }

    public void doViewRoomTypeDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** Hotel Management Client :: Hotel Operaetion Moduel: View Room Type Details ***\n");
        System.out.print("Enter Room Type Name> ");
        String roomTypeName = scanner.nextLine().trim();

        try {
            RoomTypeEntity roomType = roomTypeEntitySessionBeanRemote.retrieveRoomTypeByName(roomTypeName);
            System.out.printf("%s%s%s%s%d%s%b\n", "Room Type Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Disabled");
            System.out.printf("%s%s%s%s%d%s%b\n", roomType.getName().toString(), roomType.getDescription(), roomType.getSize(), roomType.getBed().toString(), roomType.getCapacity(), roomType.getAmenities(), roomType.getDisabled());
            System.out.println("------------------------");
            System.out.println("1: Update Room Type");
            System.out.println("2: Delete Room Type");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = scanner.nextInt();

            if (response == 1) {
                doUpdateRoomType(roomType);
            } else if (response == 2) {
                doDeleteRoomType(roomType);
            }

        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Room Type Name: " + roomTypeName + " does not exist");
        }
    }

    private void doViewAllRoomTypes() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Management Client :: Hotal Operation Module :: View All Room Types ***\n");

        List<RoomTypeEntity> roomTypeEntities = roomTypeEntitySessionBeanRemote.retrieveAllRoomType();
        System.out.printf("%s%s%s%s%d%s%b\n", "Name", "Description", "Size", "Bed", "Capacity", "Amenities", "Disabled");

        for (RoomTypeEntity roomTypeEntity : roomTypeEntities) {
            System.out.printf("%s%s%s%s%d%s%b\n", roomTypeEntity.getName().toString(), roomTypeEntity.getDescription(), roomTypeEntity.getSize(), roomTypeEntity.getBed().toString(), roomTypeEntity.getCapacity(), roomTypeEntity.getAmenities(), roomTypeEntity.getDisabled());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    public void doCreateNewRoom() {
        Scanner scanner = new Scanner(System.in);
        RoomEntity newRoom = new RoomEntity();

        System.out.println("*** Hotel Management Client :: Hotel Operation :: Create New Room ***\n");
        System.out.print("Enter Room  Floor> ");
        newRoom.setRoomFloor(scanner.nextInt());

        System.out.print("Enter Room Number> ");
        newRoom.setRoomNumber(scanner.nextInt());

        System.out.println("Select Room Status (0: Unavailable, 1: Available, 2: Disabled)> ");
        Integer response = scanner.nextInt();
        while (true) {
            if (response == 0) {
                newRoom.setRoomStatusEnum(RoomStatusEnum.UNAVAILABLE);
                break;
            } else if (response == 1) {
                newRoom.setRoomStatusEnum(RoomStatusEnum.AVAILABLE);
                break;
            } else if (response == 2) {
                newRoom.setRoomStatusEnum(RoomStatusEnum.DISABLED);
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        /*
        Set<ConstraintViolation<RoomEntity>> constraintViolations = validator.validate(newRoom);
        
        if (constraintViolations.isEmpty()) {
            try {
                Long roomId = roomEntitySessionBeanRemote.createNewRoom(newRoom);
                System.out.println("Room created successfully!: " + roomId + "\n");
            } catch ()
                // TO DO CONTINUED HERE
        }*/
        //BEAN VALIDATION CHECKS
    }

    private void doUpdateRoom() throws UnknownPersistenceException {
        Scanner scanner = new Scanner(System.in);
        Integer input = 0;

        try {
            System.out.println("*** Hotel Management Client :: Hotel Operation :: Update Room ***\n");

            System.out.println("Enter Room Number");
            input = scanner.nextInt();

            Integer roomNumber = input / 100;
            Integer roomFloor = input % 100;

            RoomEntity oldRoom = roomEntitySessionBeanRemote.retrieveRoomByRoomFloorAndRoomNumber(roomFloor, roomNumber);
            Long oldRoomId = oldRoom.getRoomId();
            RoomEntity newRoom = new RoomEntity();

            System.out.println("Enter new Room Floor (leave blank if no change)");
            Integer newRoomFloor = scanner.nextInt();
            if (newRoomFloor != null) {
                newRoom.setRoomFloor(newRoomFloor);
            }

            System.out.println("Enter new Room Number (leave blank if no change)");
            Integer newRoomNumber = scanner.nextInt();
            if (newRoomNumber != null) {
                newRoom.setRoomNumber(newRoomNumber);
            }

            roomEntitySessionBeanRemote.updateRoom(oldRoomId, newRoom);

            /*
            Set<ConstraintViolation<RoomEntity>> constraintViolations = validator.validate(room);

            if (constraintViolations.isEmpty()) {
                try {
                    roomEntitySessionBeanRemote.updateRoom(room);
                    Long updatedRoomId = room.getRoomId();
                    System.out.println("Room updated successfully!: " + updatedRoomId + "\n");
                } catch () // TO DO CONTINUED HERE
                
                }*/
            //BEAN VALIDATION CHECKS
        } catch (RoomNotFoundException ex) {
            System.out.println("Room Number: " + input + " does not exist");
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    private void doDeleteRoom() {
        Scanner scanner = new Scanner(System.in);
        Integer input = 0;

        try {
            System.out.println("*** Hotel Management Client :: Hotel Operation :: Delete Room ***\n");

            System.out.println("Enter Room Number");
            input = scanner.nextInt();

            Integer roomNumber = input / 100;
            Integer roomFloor = input % 100;

            RoomEntity oldRoom = roomEntitySessionBeanRemote.retrieveRoomByRoomFloorAndRoomNumber(roomFloor, roomNumber);
            Long oldRoomid = oldRoom.getRoomId();

            roomEntitySessionBeanRemote.deleteRoom(oldRoomid);
        } catch (RoomNotFoundException ex) {
            System.out.println("Room Number: " + input + " does not exist");
        }
    }

    private void doViewAllRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Management Client :: Hotal Operation Module :: View All Room ***\n");

        List<RoomEntity> roomEntities = roomEntitySessionBeanRemote.retrieveAllRoom();
        System.out.printf("%d%d\n", "Room Floor", "Room Number");

        for (RoomEntity roomEntity : roomEntities) {
            System.out.printf("%d%d\n", roomEntity.getRoomFloor(), roomEntity.getRoomNumber());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
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

        while (true) {
            System.out.print("Select Room 's Status (0: Disable, 1: Enable)> ");
            Integer roomDisabled = scanner.nextInt();

            if (roomDisabled == 0) {
                roomType.setDisabled(Boolean.TRUE);
                break;
            } else if (roomDisabled == 1) {
                roomType.setDisabled(Boolean.FALSE);
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        /*
            Set<ConstraintViolation<RoomTypeEntity>> constraintViolations = validator.validate(roomType);

            if (constraintViolations.isEmpty()) {
                try {
                    roomTypeEntitySessionBeanRemote.updateRoom(roomType);
                    Long updatedRoomTypeId = room.getRoomTypeId();
                    System.out.println("Room Type updated successfully!: " + updatedRoomTypeId + "\n");
                } catch () // TO DO CONTINUED HERE
                
                }*/
        //BEAN VALIDATION CHECKS
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
                System.out.println("Room Type deleted successfully!\n");
            } catch (RoomTypeNotFoundException ex) {
                System.out.println("An error has occurred while deleting the staff: " + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Staff NOT deleted!\n");
        }
    }
}
