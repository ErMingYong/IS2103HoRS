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
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.InvalidAccessRightException;

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

    public void menuFrontOfficer() throws InvalidAccessRightException {
        if (currentEmployee.getEmployeeAccessRightEnum() != EmployeeAccessRightEnum.GUEST_RELATION_OFFICER) {
            throw new InvalidAccessRightException("You dont have GUEST RELATION OFFICER rights to access the System Administration Module");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Management Client  System :: Front Officer Module ***\n");
            System.out.println("1: Walk-in Search Room");
            System.out.println("2: Check-in Guest");
            System.out.println("3: Check-out Guest");
            System.out.println("4: Exit");

            while (response < 1 || response > 4) {
                response = scanner.nextInt();

                if (response == 1) {
                    //WALK-IN SEARCH ROOM
                    //INCLUDES WALK-IN RESERVE ROOM
                } else if (response == 2) {
                    //CHECK-IN GUEST
                } else if (response == 3) {
                    //CHECK-OUT GUEST
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
}
