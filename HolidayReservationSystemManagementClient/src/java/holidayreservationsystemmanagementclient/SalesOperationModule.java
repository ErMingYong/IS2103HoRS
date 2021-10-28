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
import entity.RoomRateEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.InputDataValidationException;
import util.exception.InvalidAccessRightException;
import util.exception.RoomRateNameExistException;
import util.exception.RoomRateNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRoomRateException;

/**
 *
 * @author mingy
 */
public class SalesOperationModule {

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

    public SalesOperationModule() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public SalesOperationModule(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote, EmployeeEntity currentEmployee) {
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

    public void menuSalesOperation() throws InvalidAccessRightException {
        if (currentEmployee.getEmployeeAccessRightEnum() != EmployeeAccessRightEnum.SALES_MANAGER) {
            throw new InvalidAccessRightException("You dont have SALES MANAGER rights to access the System Administration Module");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Management Client  System :: Sales Operation Module ***\n");
            System.out.println("1: Create New Room Rate");
            System.out.println("2: View Room Rate Details");
            System.out.println("3: View All Room Rates");
            System.out.println("4: Exit");

            while (response < 1 || response > 4) {
                response = scanner.nextInt();

                if (response == 1) {
                    //CREATE NEW ROOM RATE
                    doCreateNewRoomRate();
                } else if (response == 2) {
                    //VIEW ROOM RATE DETAILS
                    //INCLUDES -> UPDATE ROOM RATE
                    //INCLUDES -> DELETE ROOM RATE
                    doViewRoomRateDetails();
                } else if (response == 3) {
                    //VIEW ALL ROOM RATES
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

        LocalDateTime dateToView;
        while (true) {
            System.out.println("Please Enter Room Rate Valid Period From> ");
            System.out.println("------------------------");
            System.out.print("Please Enter Day of Room Rate Valid Period From>   (please select from 01 - 31)");
            String day = scanner.nextLine();
            System.out.print("Please Enter Month of Room Rate Valid Period From>   (please select from 01 - 12)");
            String month = scanner.nextLine();
            System.out.print("Please Enter Year of Room Rate Valid Period From>   (please select from 2000 - 2999)");
            String year = scanner.nextLine();
            String date = year + "-" + month + "-" + day;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                dateToView = LocalDateTime.parse(date, formatter);
                newRoomRate.setValidPeriodFrom(dateToView);
            } catch (DateTimeParseException ex) {
                System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                continue;
            }
            break;
        }

        while (true) {
            System.out.println("Please Enter Room Rate Valid Period To> ");
            System.out.println("------------------------");
            System.out.print("Please Enter Day of Room Rate Valid Period To>   (please select from 01 - 31)");
            String day = scanner.nextLine();
            System.out.print("Please Enter Month of Room Rate Valid Period To>   (please select from 01 - 12)");
            String month = scanner.nextLine();
            System.out.print("Please Enter Year of Room Rate Valid Period To>   (please select from 2000 - 2999)");
            String year = scanner.nextLine();
            String date = year + "-" + month + "-" + day;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                dateToView = LocalDateTime.parse(date, formatter);
                newRoomRate.setValidPeriodTo(dateToView);
            } catch (DateTimeParseException ex) {
                System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                continue;
            }
            break;
        }

        System.out.println("Select Room Rate: (0)Disabled/(1)Enabled");
        while (true) {
            if (scanner.nextInt() == 0) {
                newRoomRate.setIsDisabled(Boolean.TRUE);
                break;
            } else if (scanner.nextInt() == 1) {
                newRoomRate.setIsDisabled(Boolean.FALSE);
                break;
            } else {
                System.out.println("Inavlid option, please try again\n");
            }
        }

        Set<ConstraintViolation<RoomRateEntity>> constraintViolations = validator.validate(newRoomRate);

        if (constraintViolations.isEmpty()) {
            try {
                Long roomRateId = roomRateEntitySessionBeanRemote.createNewRoomRate(newRoomRate);
                System.out.println("Room Type created successfully!: " + roomRateId + "\n");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");
            } catch (UnknownPersistenceException ex) {
                System.out.println("Unknown persistence error caught");
            } catch (RoomRateNameExistException ex) {
                System.out.println("RoomType name already exist!");
            }
        } else {
            showInputDataValidationErrorsForRoomRateEntity(constraintViolations);
        }
    }

    private void doViewRoomRateDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("*** Hotel Management Client :: Hotel Operation Module :: View Room Type Details ***\n");
        System.out.print("Enter Room Type Name> ");
        String roomTypeName = scanner.nextLine().trim();

        try {
            RoomRateEntity roomRate = roomRateEntitySessionBeanRemote.retrieveRoomRateByName(roomTypeName);
            
            System.out.printf("%s%s%s%s\n", "Room Rate Name", "Rate per Night", "Valid Period From", "Valid Period To");
            System.out.printf("%s%s%s%s\n", roomRate.getRoomRateName(), roomRate.getRatePerNight().toString(), roomRate.getValidPeriodFrom().toString(), roomRate.getValidPeriodTo().toString());
             
            System.out.println("------------------------");
            System.out.println("1: Update Room Rate");
            System.out.println("2: Delete Room Rate");
            System.out.println("3: Back\n");
            System.out.print("> ");
            response = scanner.nextInt();
            while (response < 1 || response > 3) {
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
            System.out.println("Room Type Name: " + roomTypeName + " does not exist");
        }
    }

    private void doUpdateRoomRate(RoomRateEntity roomRate) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Management Client :: Hotel Operation Module (Sales Manager) :: View Room Rate Details :: Update Room Rate ***\n");

        System.out.print("Enter Room Rate Name (blank if no change)> ");
        String name = scanner.nextLine().trim();
        if (name.length() > 0) {
            roomRate.setRoomRateName(name);
        }

        System.out.print("Enter Room Rate Rate per Night (blank if no change)> ");
        BigDecimal ratePerNight = scanner.nextBigDecimal();
        if (ratePerNight != null) {
            roomRate.setRatePerNight(ratePerNight);
        }

        LocalDateTime dateToView;
        while (true) {
            System.out.println("Please Enter Room Rate Valid Period From> (blank if no change) ");
            System.out.println("------------------------");
            System.out.print("Please Enter Day of Room Rate Valid Period From>   (please select from 01 - 31)");
            String day = scanner.nextLine();
            System.out.print("Please Enter Month of Room Rate Valid Period From>   (please select from 01 - 12)");
            String month = scanner.nextLine();
            System.out.print("Please Enter Year of Room Rate Valid Period From>   (please select from 2000 - 2999)");
            String year = scanner.nextLine();
            String date = year + "-" + month + "-" + day;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                dateToView = LocalDateTime.parse(date, formatter);
                roomRate.setValidPeriodFrom(dateToView);
            } catch (DateTimeParseException ex) {
                System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                continue;
            }
            break;
        }

        while (true) {
            System.out.println("Please Enter Room Rate Valid Period To> (blank if no change) ");
            System.out.println("------------------------");
            System.out.print("Please Enter Day of Room Rate Valid Period To>   (please select from 01 - 31)");
            String day = scanner.nextLine();
            System.out.print("Please Enter Month of Room Rate Valid Period To>   (please select from 01 - 12)");
            String month = scanner.nextLine();
            System.out.print("Please Enter Year of Room Rate Valid Period To>   (please select from 2000 - 2999)");
            String year = scanner.nextLine();
            String date = year + "-" + month + "-" + day;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                dateToView = LocalDateTime.parse(date, formatter);
                roomRate.setValidPeriodTo(dateToView);
            } catch (DateTimeParseException ex) {
                System.out.println("DATE INVALID! PLEASE KEY IN APPROPRIATE DATE");
                continue;
            }
            break;
        }

        Set<ConstraintViolation<RoomRateEntity>> constraintViolations = validator.validate(roomRate);

        if (constraintViolations.isEmpty()) {
            try {
                roomRateEntitySessionBeanRemote.updateRoomRate(roomRate);
                Long updatedRoomTypeId = roomRate.getRoomRateId();
                System.out.println("Room Type updated successfully!: " + updatedRoomTypeId + "\n");
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
        System.out.printf("Confirm Delete Room Type %s (Room Rate ID: %d) (Enter 'Y' to Delete)> ", roomRate.getRoomRateName(), roomRate.getRoomRateId());
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

        System.out.println("*** Hotel Management Client :: Hotal Operation Module :: View All Room Types ***\n");

        List<RoomRateEntity> roomRateEntities = roomRateEntitySessionBeanRemote.retrieveAllRoomRate();
            System.out.printf("%s%s%s%s\n", "Room Rate Name", "Rate per Night", "Valid Period From", "Valid Period To");

        for (RoomRateEntity roomRateEntity : roomRateEntities) {
            System.out.printf("%s%s%s%s\n", roomRateEntity.getRoomRateName(), roomRateEntity.getRatePerNight().toString(), roomRateEntity.getValidPeriodFrom().toString(), roomRateEntity.getValidPeriodTo().toString());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    private void showInputDataValidationErrorsForRoomRateEntity(Set<ConstraintViolation<RoomRateEntity>> constraintViolations) {
        System.out.println("\nInput data validation error");

        for (ConstraintViolation constraintViolation : constraintViolations) {
            System.out.println("\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage());
        }

        System.out.println("\nPlease try again......\n");
    }
}
