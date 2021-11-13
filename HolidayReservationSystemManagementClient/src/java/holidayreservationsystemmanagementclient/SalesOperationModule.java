/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemmanagementclient;

import ejb.session.stateless.RoomRateEntitySessionBeanRemote;
import ejb.session.stateless.RoomTypeEntitySessionBeanRemote;
import entity.EmployeeEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.EmployeeAccessRightEnum;
import util.enumeration.RoomRateTypeEnum;
import util.exception.InputDataValidationException;
import util.exception.InvalidAccessRightException;
import util.exception.RoomRateNameExistException;
import util.exception.RoomRateNotFoundException;
import util.exception.RoomTypeNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRoomRateException;

/**
 *
 * @author mingy
 */
public class SalesOperationModule {

    private RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote;
    private RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote;

    private EmployeeEntity currentEmployee;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public SalesOperationModule() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public SalesOperationModule(RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, EmployeeEntity currentEmployee) {
        this();
        this.roomRateEntitySessionBeanRemote = roomRateEntitySessionBeanRemote;
        this.roomTypeEntitySessionBeanRemote = roomTypeEntitySessionBeanRemote;
        this.currentEmployee = currentEmployee;
    }

    public void menuSalesOperation() throws InvalidAccessRightException {
        if (currentEmployee.getEmployeeAccessRightEnum() != EmployeeAccessRightEnum.SALES_MANAGER) {
            throw new InvalidAccessRightException("You dont have SALES MANAGER rights to access the System Administration Module");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.println("*** Hotel Reservation System Management Client  System :: Hotel Operation Module :: Sales Operation ***\n");
            System.out.println("1: Create New Room Rate");
            System.out.println("2: View Room Rate Details");
            System.out.println("3: View All Room Rates");
            System.out.println("4: Exit");
            System.out.println("> ");
            response = 0;

            while (response < 1 || response > 4) {
                response = scanner.nextInt();

                if (response == 1) {
                    doCreateNewRoomRate();
                } else if (response == 2) {
                    doViewRoomRateDetails();
                } else if (response == 3) {
                    doViewAllRoomRates();
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

    private void doCreateNewRoomRate() {
        Scanner scanner = new Scanner(System.in);
        RoomRateEntity newRoomRate = new RoomRateEntity();

        System.out.println("*** Hotel Management Client :: Hotel Operation (Sales Manager) :: Create New Room Rate ***\n");
        System.out.println("---------------------------------");
        System.out.print("Enter Room Rate Name> ");
        newRoomRate.setRoomRateName(scanner.nextLine().trim());

        System.out.print("Enter Room Rate per night> ");
        newRoomRate.setRatePerNight(scanner.nextBigDecimal());
        scanner.nextLine();

        LocalDateTime dateToView;
        while (true) {
            System.out.println("");
            System.out.println("Please Enter Date To View> ");
            System.out.println("------------------------");
            System.out.println("Please Enter Day of Room Rate Valid Period From>   (please select from 01 - 31)");
            int day = scanner.nextInt();
            System.out.println("Please Enter Month of Room Rate Valid Period From>   (please select from 01 - 12)");
            int month = scanner.nextInt();
            System.out.println("Please Enter Year of Room Rate Valid Period From>   (please select from 2000 - 2999)");
            int year = scanner.nextInt();
            try {
                dateToView = LocalDateTime.of(year, month, day, 0, 0, 0);
            } catch (DateTimeException ex) {
                System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                System.out.println(":::");
                System.out.println(":::");

                continue;
            }
            newRoomRate.setValidPeriodFrom(dateToView);
            break;
        }

        while (true) {
            System.out.println("");
            System.out.println("Please Enter Room Rate Valid Period To> ");
            System.out.println("------------------------");
            System.out.println("Please Enter Day of Room Rate Valid Period To>   (please select from 01 - 31)");
            int day = scanner.nextInt();
            System.out.println("Please Enter Month of Room Rate Valid Period To>   (please select from 01 - 12)");
            int month = scanner.nextInt();
            System.out.println("Please Enter Year of Room Rate Valid Period To>   (please select from 2000 - 2999)");
            int year = scanner.nextInt();
            try {
                dateToView = LocalDateTime.of(year, month, day, 0, 0, 0);
            } catch (DateTimeException ex) {
                System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                System.out.println(":::");
                System.out.println(":::");
                continue;
            }

            if (!dateToView.isBefore(newRoomRate.getValidPeriodFrom())) {
                newRoomRate.setValidPeriodTo(dateToView);
                break;
            }
            System.out.println("DATE INVALID! PLEASE KEY IN DATE THAT IS AFTER \"Valid Period From\" ");
            System.out.println(":::");
            System.out.println(":::");
        }
        scanner.nextLine();
        RoomTypeEntity retrievedRoomType = null;

        while (true) {
            System.out.print("Enter Room Type Name> ");
            String roomType = scanner.nextLine();
            try {
                retrievedRoomType = roomTypeEntitySessionBeanRemote.retrieveRoomTypeByName(roomType);
            } catch (RoomTypeNotFoundException ex) {
                System.out.println("Room Type Name not found, please try again!");
            }

            if (retrievedRoomType != null && retrievedRoomType.getIsDisabled() == true) {
                System.out.println("Cannot create new room of disabled room type!");
                retrievedRoomType = null;
                continue;
            }
            if (retrievedRoomType != null) {
                break;
            }
        }
        newRoomRate.setRoomTypeEntity(retrievedRoomType);
        newRoomRate.setRoomTypeName(retrievedRoomType.getRoomTypeName());

        while (true) {
            System.out.print("Select Access Right:");
            System.out.println("------------------------");
            System.out.println("1: PUBLISHED");
            System.out.println("2: NORMAL");
            System.out.println("3: PEAK");
            System.out.println("4: PROMOTION");
            System.out.println("------------------------");

            Integer roomRateTypeInt = scanner.nextInt();

            if (roomRateTypeInt >= 1 && roomRateTypeInt <= 4) {
                newRoomRate.setRoomRateTypeEnum(RoomRateTypeEnum.values()[roomRateTypeInt - 1]);
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        Set<ConstraintViolation<RoomRateEntity>> constraintViolations = validator.validate(newRoomRate);

        if (constraintViolations.isEmpty()) {
            try {
                Long roomRateId = roomRateEntitySessionBeanRemote.createNewRoomRate(newRoomRate);
                System.out.println("");
                System.out.println("Room Rate created successfully!: " + roomRateId + "\n");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");
            } catch (UnknownPersistenceException ex) {
                System.out.println("Unknown persistence error caught");
            } catch (RoomRateNameExistException ex) {
                System.out.println("Room Rate name already exist!");
            }
        } else {
            showInputDataValidationErrorsForRoomRateEntity(constraintViolations);
        }
    }

    private void doViewRoomRateDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** Hotel Management Client :: Hotel Operation Module :: View Room Rate Details ***\n");
        System.out.print("Enter Room Rate Name> ");
        String roomRateName = scanner.nextLine().trim();

        try {
            RoomRateEntity roomRate = roomRateEntitySessionBeanRemote.retrieveRoomRateByName(roomRateName);

            System.out.printf("%25.25s%25.25s%25.25s%25.25s%25.25s%25.25s\n", "Room Rate Name", "Rate per Night", "Valid Period From", "Valid Period To", "Room Rate Type Enum", "Disabled");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%25.25s%25.25s%25.25s%25.25s%25.25s%25.25s\n", roomRate.getRoomRateName(), roomRate.getRatePerNight().toString(), roomRate.getValidPeriodFrom().toLocalDate().toString(), roomRate.getValidPeriodTo().toLocalDate().toString(), roomRate.getRoomRateTypeEnum().toString(), roomRate.getIsDisabled());

            System.out.println("------------------------");
            System.out.println("1: Update Room Rate");
            System.out.println("2: Delete Room Rate");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = 0;
            while (response < 1 || response > 3) {
                response = scanner.nextInt();

                if (response == 1) {
                    doUpdateRoomRate(roomRate);
                } else if (response == 2) {
                    doDeleteRoomRate(roomRate);
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

        } catch (RoomRateNotFoundException ex) {
            System.out.println("Room Rate Name: " + roomRateName + " does not exist");
            System.out.println("");
        }
    }

    private void doUpdateRoomRate(RoomRateEntity roomRate) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Management Client :: Hotel Operation Module (Sales Manager) :: View Room Rate Details :: Update Room Rate ***\n");

        System.out.println("Enter Room Rate Name (blank if no change)> ");
        String name = scanner.nextLine().trim();
        if (name.length() > 0) {
            roomRate.setRoomRateName(name);
        }

        System.out.println("Would you like to update Room Rate Per Night? Press 1 to update and any other key to skip");
        String input = scanner.nextLine().trim();
        if (input.equals("1")) {
            System.out.println("Enter Room Rate Rate per Night> ");
            int ratePerNight = 0;
            ratePerNight = scanner.nextInt();
            if (ratePerNight != 0) {
                roomRate.setRatePerNight(BigDecimal.valueOf(ratePerNight));
            }
        }

        System.out.println("Would you like to update Valid Period? Press 1 to update and any other key to skip");
        input = scanner.nextLine();
        if (input.equals("1")) {
            LocalDateTime dateToView;
            while (true) {
                System.out.println("");
                System.out.println("Please Enter Room Rate Valid Period From> ");
                System.out.println("------------------------");
                System.out.println("Please Enter Day of Room Rate Valid Period From>   (please select from 01 - 31)");
                int day = scanner.nextInt();
                System.out.println("Please Enter Month of Room Rate Valid Period From>   (please select from 01 - 12)");
                int month = scanner.nextInt();
                System.out.println("Please Enter Year of Room Rate Valid Period From>   (please select from 2000 - 2999)");
                int year = scanner.nextInt();
                try {
                    dateToView = LocalDateTime.of(year, month, day, 0, 0, 0);
                } catch (DateTimeException ex) {
                    System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                    System.out.println(":::");
                    System.out.println(":::");

                    continue;
                }
                roomRate.setValidPeriodFrom(dateToView);
                break;
            }

            while (true) {
                System.out.println("");
                System.out.println("Please Enter Room Rate Valid Period To> ");
                System.out.println("------------------------");
                System.out.println("Please Enter Day of Room Rate Valid Period To>   (please select from 01 - 31)");
                int day = scanner.nextInt();
                System.out.println("Please Enter Month of Room Rate Valid Period To>   (please select from 01 - 12)");
                int month = scanner.nextInt();
                System.out.println("Please Enter Year of Room Rate Valid Period To>   (please select from 2000 - 2999)");
                int year = scanner.nextInt();
                try {
                    dateToView = LocalDateTime.of(year, month, day, 0, 0, 0);
                } catch (DateTimeException ex) {
                    System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                    System.out.println(":::");
                    System.out.println(":::");
                    continue;
                }

                if (dateToView.isAfter(roomRate.getValidPeriodFrom())) {
                    roomRate.setValidPeriodTo(dateToView);
                    break;
                }
                System.out.println("DATE INVALID! PLEASE KEY IN DATE THAT IS AFTER \"Valid Period From\" ");
                System.out.println(":::");
                System.out.println(":::");
            }
            scanner.nextLine();
        }

        RoomTypeEntity retrievedRoomType = null;

        while (true) {
            System.out.print("Enter Room Type Name (blank if no change)> ");
            String roomType = scanner.nextLine();
            if (roomType.length() > 0) {
                try {
                    retrievedRoomType = roomTypeEntitySessionBeanRemote.retrieveRoomTypeByName(roomType);
                } catch (RoomTypeNotFoundException ex) {
                    System.out.println("Room Type Name not found, please try again!");
                }

                if (retrievedRoomType != null && retrievedRoomType.getIsDisabled() == true) {
                    System.out.println("Cannot create new room of disabled room type!");
                    retrievedRoomType = null;
                    continue;
                }
                if (retrievedRoomType != null) {
                    break;
                }
            } else {
                break;
            }
        }

        if (retrievedRoomType != null) {
            roomRate.setRoomTypeEntity(retrievedRoomType);
            roomRate.setRoomTypeName(retrievedRoomType.getRoomTypeName());
        }

        while (true) {
            System.out.print("Select Room Rate Type:");
            System.out.println("------------------------");
            System.out.println("1: PUBLISHED");
            System.out.println("2: NORMAL");
            System.out.println("3: PEAK");
            System.out.println("4: PROMOTION");
            System.out.println("5: REMAIN UNCHANGED");
            System.out.println("------------------------");

            Integer roomRateTypeInt = scanner.nextInt();

            if (roomRateTypeInt >= 1 && roomRateTypeInt <= 4) {
                roomRate.setRoomRateTypeEnum(RoomRateTypeEnum.values()[roomRateTypeInt - 1]);
                break;
            } else if (roomRateTypeInt == 5) {
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        while (true) {
            System.out.print("Disable Room Rate (\"T\" for true, \"F\" for false)> ");
            input = scanner.nextLine().trim();
            if (input.equals("T")) {
                roomRate.setIsDisabled(true);
            } else if (input.equals("F")) {
                roomRate.setIsDisabled(false);
            } else {
                System.out.println("Please input valid option!");
                System.out.println("");
                continue;
            }
            break;
        }

        Set<ConstraintViolation<RoomRateEntity>> constraintViolations = validator.validate(roomRate);

        if (constraintViolations.isEmpty()) {
            try {
                roomRateEntitySessionBeanRemote.updateRoomRate(roomRate);
                Long updatedRoomRateId = roomRate.getRoomRateId();
                System.out.println("Room Rate updated successfully!: " + updatedRoomRateId + "\n");
            } catch (UpdateRoomRateException | RoomRateNotFoundException ex) {
                System.out.println("An error has occurred while updating product: " + ex.getMessage() + "\n");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");

            }
        } else {
            showInputDataValidationErrorsForRoomRateEntity(constraintViolations);
        }
    }

    private void doDeleteRoomRate(RoomRateEntity roomRate) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("*** Hotel Management Client :: Hotel Operation Module (Sales Manager) :: View Room Rate Details :: Delete Room Rate ***\n");
        System.out.printf("Confirm Delete Room Rate %s (Room Rate ID: %d) (Enter 'Y' to Delete)> ", roomRate.getRoomRateName(), roomRate.getRoomRateId());
        input = scanner.nextLine().trim();

        if (input.equals("Y")) {
            try {
                roomRateEntitySessionBeanRemote.deleteRoomRate(roomRate.getRoomRateId());
                System.out.println("Room Rate deleted/disabled successfully!\n");
            } catch (RoomRateNotFoundException ex) {
                System.out.println("An error has occurred while deleting the room rate: " + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Room NOT deleted!\n");
        }
    }

    private void doViewAllRoomRates() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Management Client :: Hotal Operation Module :: View All Room Rates ***\n");

        List<RoomRateEntity> roomRateEntities = roomRateEntitySessionBeanRemote.retrieveAllRoomRates();
        if (roomRateEntities.isEmpty()) {
            System.out.println("-----------------------------------------");
            System.out.println("You do not have any Room Rates!");
            System.out.println("");
            System.out.println("Press any key to go back...");
            scanner.nextLine();

        } else {

            System.out.printf("%30.30s%25.25s%25.25s%25.25s%25.25s%25.25s\n", "Room Rate Name", "Rate per Night", "Valid Period From", "Valid Period To", "Room Rate Type Enum", " Room Rate Disabled");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (RoomRateEntity roomRateEntity : roomRateEntities) {
                System.out.printf("%30.30s%25.25s%25.25s%25.25s%25.25s%25.25s\n", roomRateEntity.getRoomRateName(), roomRateEntity.getRatePerNight().toString(), roomRateEntity.getValidPeriodFrom().toLocalDate().toString(), roomRateEntity.getValidPeriodTo().toLocalDate().toString(), roomRateEntity.getRoomRateTypeEnum().toString(), roomRateEntity.getIsDisabled());
            }

            System.out.println("Press any key to continue...> ");
            scanner.nextLine();
        }

    }

    private void showInputDataValidationErrorsForRoomRateEntity(Set<ConstraintViolation<RoomRateEntity>> constraintViolations) {
        System.out.println("\nInput data validation error");

        for (ConstraintViolation constraintViolation : constraintViolations) {
            System.out.println("\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage());
        }

        System.out.println("\nPlease try again......\n");
    }
}
