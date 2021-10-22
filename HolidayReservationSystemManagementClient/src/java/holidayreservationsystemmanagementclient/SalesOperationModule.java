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

    public SalesOperationModule() {
    }

    public SalesOperationModule(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote, EmployeeEntity currentEmployee) {
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
                } else if (response == 2) {
                    //VIEW ROOM RATE DETAILS
                    //INCLUDES -> UPDATE ROOM RATE
                    //INCLUDES -> DELETE ROOM RATE
                } else if (response == 3) {
                    //VIEW ALL ROOM RATES
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
