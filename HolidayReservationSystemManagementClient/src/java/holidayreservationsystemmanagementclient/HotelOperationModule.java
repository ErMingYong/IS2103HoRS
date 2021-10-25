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
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.EmployeeAccessRightEnum;
import util.enumeration.RoomStatusEnum;
import util.exception.InvalidAccessRightException;

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

    public void menuHotelOperation() throws InvalidAccessRightException {
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
                    //VIEW ROOM TYPE DETAILS
                    doViewRoomTypeDetails();
                    //INCLUDES -> UPDATE ROOM TYPE
                    //INCLUDES -> DELETE ROOM TYPE
                } else if (response == 3) {
                    doViewAllRoomTypes();
                } else if (response == 4) {
                    doCreateNewRoom();
                } else if (response == 5) {
                    //doUpdateRoom();
                } else if (response == 6) {
                    //DELETE ROOM
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

        System.out.println("*** Hotel Management Client :: Hotal Operation Module :: View Room Type Details ***\n");
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

    /*
    private void doUpdateRoom() {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        System.out.println("*** Hotel Management Client :: Hotel Operation :: Update Room ***\n");
        
        System.out.print("Enter Room Floor (blank if no change)> ");
        input = scanner.nextLine().trim();
        
        if (input.length() > 0) {
            
        }
    }*/
    
    
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
}
