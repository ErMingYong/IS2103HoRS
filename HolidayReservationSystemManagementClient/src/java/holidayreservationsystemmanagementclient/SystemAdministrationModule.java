/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemmanagementclient;

import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import ejb.session.stateless.PartnerEntitySessionBeanRemote;
import entity.EmployeeEntity;
import entity.PartnerEntity;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.EmployeeUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidAccessRightException;
import util.exception.PartnerUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
public class SystemAdministrationModule {

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;
    private PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote;

    private EmployeeEntity currentEmployee;

    public SystemAdministrationModule() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public SystemAdministrationModule(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, EmployeeEntity currentEmployee) {
        this();
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
        this.partnerEntitySessionBeanRemote = partnerEntitySessionBeanRemote;
        this.currentEmployee = currentEmployee;
    }

    public void menuSystemAdministration() throws InvalidAccessRightException {
        if (currentEmployee.getEmployeeAccessRightEnum() != EmployeeAccessRightEnum.SYSTEM_ADMINISTRATOR) {
            throw new InvalidAccessRightException("You dont have SYSTEM ADMINISTRATOR rights to access the System Administration Module");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.println("*** Hotel Reservation System Management Client  System :: System Administration ***\n");
            System.out.println("1: Create New Employee");
            System.out.println("2: View All Employees");
            System.out.println("3: Create New Partner");
            System.out.println("4: View All Partners");
            System.out.println("5: Exit");
            System.out.println(">");
            response = 0;

            while (response < 1 || response > 5) {
                response = scanner.nextInt();

                if (response == 1) {
                    doCreateNewEmployee();
                } else if (response == 2) {
                    doViewAllEmployees();
                } else if (response == 3) {
                    doCreateNewPartner();
                } else if (response == 4) {
                    doViewAllPartners();
                } else if (response == 5) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 5) {
                break;
            }
        }
    }

    private void doCreateNewEmployee() {
        Scanner scanner = new Scanner(System.in);
        EmployeeEntity newEmployeeEntity = new EmployeeEntity();

        System.out.println("*** Hotel Reservation System Management Client  System :: System Administration :: Create New Employee ***\n");
        System.out.print("Enter First Name> ");
        newEmployeeEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newEmployeeEntity.setLastName(scanner.nextLine().trim());

        while (true) {
            System.out.print("Select Access Right:");
            System.out.println("------------------------");
            System.out.println("1: System Administrator");
            System.out.println("2: Guest Relation Officer");
            System.out.println("3: Operation Manager");
            System.out.println("4: Sales Manager");
            System.out.println("------------------------");

            Integer accessRightInt = scanner.nextInt();

            if (accessRightInt >= 1 && accessRightInt <= 4) {
                newEmployeeEntity.setEmployeeAccessRightEnum(EmployeeAccessRightEnum.values()[accessRightInt - 1]);
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        scanner.nextLine();
        System.out.println("------------------------");
        System.out.print("Enter Username> ");
        newEmployeeEntity.setUserName(scanner.nextLine().trim());
        System.out.println("------------------------");
        System.out.print("Enter Password> ");
        newEmployeeEntity.setPassword(scanner.nextLine().trim());

        Set<ConstraintViolation<EmployeeEntity>> constraintViolations = validator.validate(newEmployeeEntity);

        if (constraintViolations.isEmpty()) {
            try {
                Long newEmployeeId = employeeEntitySessionBeanRemote.createNewEmployee(newEmployeeEntity);
                System.out.println("------------------------");
                System.out.println("New employee created successfully!: " + newEmployeeId + "\n");
            } catch (EmployeeUsernameExistException ex) {
                System.out.println("An error has occurred while creating the new employee!: The user name already exist\n");
            } catch (UnknownPersistenceException ex) {
                System.out.println("An unknown error has occurred while creating the new employee!: " + ex.getMessage() + "\n");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        } else {
            showInputDataValidationErrorsForEmployeeEntity(constraintViolations);
        }
    }

    private void doViewAllEmployees() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Reservation System Management Client  System :: System Administration :: View All Employees ***\n");

        List<EmployeeEntity> employeeEntities = employeeEntitySessionBeanRemote.retrieveAllEmployees();
        if (employeeEntities.isEmpty()) {
            System.out.println("-----------------------------------------");
            System.out.println("You do not have any Employees!");
            System.out.println("");
            System.out.println("Press any key to go back...");
            scanner.nextLine();

        } else {
            System.out.printf("%8.8s%20.20s%20.20s%30.30s%20.20s%20.20s\n", "Employee ID", "First Name", "Last Name", "Access Right", "Username", "Password");

            for (EmployeeEntity employeeEntity : employeeEntities) {
                System.out.printf("%8.8s%20.20s%20.20s%30.30s%20.20s%20.20s\n", employeeEntity.getUserEntityId().toString(), employeeEntity.getFirstName(), employeeEntity.getLastName(), employeeEntity.getEmployeeAccessRightEnum().toString(), employeeEntity.getUserName(), employeeEntity.getPassword());
            }

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doCreateNewPartner() {
        Scanner scanner = new Scanner(System.in);
        PartnerEntity newPartnerEntity = new PartnerEntity();

        System.out.println("*** Hotel Reservation System Management Client  System :: System Administration :: Create New Partner ***\n");
        System.out.println("------------------------");
        System.out.print("Enter First Name> ");
        newPartnerEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newPartnerEntity.setLastName(scanner.nextLine().trim());

        System.out.println("------------------------");
        System.out.print("Enter Username> ");
        newPartnerEntity.setUserName(scanner.nextLine().trim());
        System.out.println("------------------------");
        System.out.print("Enter Password> ");
        newPartnerEntity.setPassword(scanner.nextLine().trim());

        Set<ConstraintViolation<PartnerEntity>> constraintViolations = validator.validate(newPartnerEntity);

        if (constraintViolations.isEmpty()) {
            try {
                Long newPartnerId = partnerEntitySessionBeanRemote.createNewPartner(newPartnerEntity);
                System.out.println("------------------------");
                System.out.println("New partner created successfully!: " + newPartnerId + "\n");
            } catch (PartnerUsernameExistException ex) {
                System.out.println("An error has occurred while creating the new partner!: The user name already exist\n");
            } catch (UnknownPersistenceException ex) {
                System.out.println("An unknown error has occurred while creating the new partner!: " + ex.getMessage() + "\n");
            } catch (InputDataValidationException ex) {
                System.out.println(ex.getMessage() + "\n");
            }
        } else {
            showInputDataValidationErrorsForPartnerEntity(constraintViolations);
        }
    }

    private void doViewAllPartners() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Reservation System Management Client  System :: System Administration :: View All Partners ***\n");

        List<PartnerEntity> partnerEntities = partnerEntitySessionBeanRemote.retrieveAllPartners();
        if (partnerEntities.isEmpty()) {
            System.out.println("-----------------------------------------");
            System.out.println("You do not have any Partners!");
            System.out.println("");
            System.out.println("Press any key to go back...");
            scanner.nextLine();
        } else {

            System.out.printf("%8.8s%20.20s%20.20s%20.20s%20.20s\n", "Partner ID", "First Name", "Last Name", "Username", "Password");

            for (PartnerEntity partnerEntity : partnerEntities) {
                System.out.printf("%8.8s%20.20s%20.20s%20.20s%20.20s\n", partnerEntity.getUserEntityId().toString(), partnerEntity.getFirstName(), partnerEntity.getLastName(), partnerEntity.getUserName(), partnerEntity.getPassword());
            }

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }

    }

    private void showInputDataValidationErrorsForEmployeeEntity(Set<ConstraintViolation<EmployeeEntity>> constraintViolations) {
        System.out.println("\nInput data validation error!:");

        for (ConstraintViolation constraintViolation : constraintViolations) {
            System.out.println("\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage());
        }

        System.out.println("\nPlease try again......\n");
    }

    private void showInputDataValidationErrorsForPartnerEntity(Set<ConstraintViolation<PartnerEntity>> constraintViolations) {
        System.out.println("\nInput data validation error!:");

        for (ConstraintViolation constraintViolation : constraintViolations) {
            System.out.println("\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage());
        }

        System.out.println("\nPlease try again......\n");
    }
}
