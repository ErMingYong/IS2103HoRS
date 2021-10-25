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
import java.util.Scanner;

/**
 *
 * @author mingy
 */
public class GuestOperationModule {

    private TransactionEntitySessionBeanRemote transactionEntitySessionBean;
    private RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBean;
    private RoomRateEntitySessionBeanRemote roomRateEntitySessionBean;
    private RoomEntitySessionBeanRemote roomEntitySessionBean;
    private ReservationEntitySessionBeanRemote reservationEntitySessionBean;
    private PartnerEntitySessionBeanRemote partnerEntitySessionBean;
    private GuestEntitySessionBeanRemote guestEntitySessionBean;
    private ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBean;
    private EmployeeEntitySessionBeanRemote employeeEntitySessionBean;
    
    private GuestEntity currentGuest;

    public GuestOperationModule() {
    }

    public GuestOperationModule(EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote, ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote, GuestEntitySessionBeanRemote guestEntitySessionBeanRemote, PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote, ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote, RoomEntitySessionBeanRemote roomEntitySessionBeanRemote, RoomRateEntitySessionBeanRemote rateEntitySessionBeanRemote, RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote, TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote, GuestEntity currentGuest) {
        this.transactionEntitySessionBean = transactionEntitySessionBean;
        this.roomTypeEntitySessionBean = roomTypeEntitySessionBean;
        this.roomRateEntitySessionBean = roomRateEntitySessionBean;
        this.roomEntitySessionBean = roomEntitySessionBean;
        this.reservationEntitySessionBean = reservationEntitySessionBean;
        this.partnerEntitySessionBean = partnerEntitySessionBean;
        this.guestEntitySessionBean = guestEntitySessionBean;
        this.exceptionReportEntitySessionBean = exceptionReportEntitySessionBean;
        this.employeeEntitySessionBean = employeeEntitySessionBean;
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
                } else if (response == 3) {
                    //VIEW ALL MY RESERVATIONS
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
