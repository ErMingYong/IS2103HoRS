/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemreservationclient;

import ejb.session.stateless.EmployeeEntitySessionBeanRemote;
import ejb.session.stateless.ExceptionReportEntitySessionBeanRemote;
import ejb.session.stateless.GuestEntitySessionBeanRemote;
import ejb.session.stateless.PartnerEntitySessionBeanRemote;
import ejb.session.stateless.ReservationEntitySessionBeanRemote;
import ejb.session.stateless.RoomEntitySessionBeanRemote;
import ejb.session.stateless.RoomRateEntitySessionBeanRemote;
import ejb.session.stateless.RoomTypeEntitySessionBeanRemote;
import ejb.session.stateless.TransactionEntitySessionBeanRemote;
import entity.GuestEntity;
import entity.ReservationEntity;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author mingy
 */
public class GuestOperationModule {

    private TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote;
    private RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote;
    private RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote;
    private RoomEntitySessionBeanRemote roomEntitySessionBeanRemote;
    private ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote;
    private PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote;
    private GuestEntitySessionBeanRemote guestEntitySessionBeanRemote;
    private ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBean;
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;

    private GuestEntity currentGuest;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public GuestOperationModule() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    public GuestOperationModule(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote rateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote, GuestEntity currentGuest) {
        this();
        this.transactionEntitySessionBeanRemote = transactionEntitySessionBeanRemote;
        this.roomTypeEntitySessionBeanRemote = roomTypeEntitySessionBeanRemote;
        this.roomRateEntitySessionBeanRemote = roomRateEntitySessionBeanRemote;
        this.roomEntitySessionBeanRemote = roomEntitySessionBeanRemote;
        this.reservationEntitySessionBeanRemote = reservationEntitySessionBeanRemote;
        this.partnerEntitySessionBeanRemote = partnerEntitySessionBeanRemote;
        this.guestEntitySessionBeanRemote = guestEntitySessionBeanRemote;
        this.exceptionReportEntitySessionBean = exceptionReportEntitySessionBean;
        this.employeeEntitySessionBeanRemote = employeeEntitySessionBeanRemote;
        this.currentGuest = currentGuest;
    }

    public void menuGuestOperation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Reservation Client  System :: Guest Operation ***\n");
            System.out.println("1: Reserve Hotel Room");
            System.out.println("2: View My Reservation Details");
            System.out.println("3: View All My Reservations");
            System.out.println("4: Exit");

            while (response < 1 || response > 4) {
                response = scanner.nextInt();

                if (response == 1) {
                    //RESERVE HOTEL ROOM
                } else if (response == 2) {
                    //VIEW MY RESERVATION DETAILS
                    doViewMyReservationDetails();
                } else if (response == 3) {
                    //VIEW ALL MY RESERVATIONS
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

    private void doViewMyReservationDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Reservation System Reservation Client  System :: Guest Operation :: View My Reservation Details***\n");
        
        for (int i = 0; i < currentGuest.getReservationEntities().size(); i++) {
            System.out.println((i + 1) + " Reservation Id: " + currentGuest.getReservationEntities().get(i).getReservationEntityId());
        }
        
        System.out.println("Enter Reservation Number Option");
        Integer option = scanner.nextInt();
        
        ReservationEntity reservation = currentGuest.getReservationEntities().get(option - 1);
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
        System.out.println("Reservation Last Name: "  + reservationLastname);
        System.out.println("Reservation Start Date: " + reservationStartDate.toString());
        System.out.println("Reservation End Date: " + reservationEndDate.toString());
        System.out.println("Reservation Email: " + reservationEmail);
        System.out.println("Reservation Contact Number: " + reservationContactNumber);
        System.out.println("Reservation Passport Number: " + reservationPassportNumber);

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }

    private void doViewAllMyReservation() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Hotel Reservation System Reservation Client  System :: Guest Operation :: View All My Reservations ***\n");
        
        List<ReservationEntity> reservationEntitires = reservationEntitySessionBeanRemote.retrieveAllReservations();
        System.out.printf("%s%s%s%s%s%s%s\n", "Reservation Start Date", "Reservation End Date", "First Name", "Last Name", "Email", "Contact Number", "Passport Number");
        
        for (ReservationEntity reservationEntity : reservationEntitires) {
            System.out.printf("%s%s%s%s%s%s%s\n", reservationEntity.getReservationStartDate().toString(), reservationEntity.getReservationEndDate().toString(), reservationEntity.getFirstName(), reservationEntity.getLastName(), reservationEntity.getEmail(), reservationEntity.getContactNumber(), reservationEntity.getPassportNumber());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
        
    }
}
