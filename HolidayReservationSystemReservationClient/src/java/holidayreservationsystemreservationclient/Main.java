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
//import ejb.session.stateless.TransactionEntitySessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author Koh Wen Jie
 */
public class Main {

//    @EJB
//    private static TransactionEntitySessionBeanRemote transactionEntitySessionBeanRemote;

    @EJB
    private static RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBeanRemote;

    @EJB
    private static RoomRateEntitySessionBeanRemote roomRateEntitySessionBeanRemote;

    @EJB
    private static RoomEntitySessionBeanRemote roomEntitySessionBeanRemote;

    @EJB
    private static ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote;

    @EJB
    private static PartnerEntitySessionBeanRemote partnerEntitySessionBeanRemote;

    @EJB
    private static GuestEntitySessionBeanRemote guestEntitySessionBeanRemote;

    @EJB
    private static ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBeanRemote;

    @EJB
    private static EmployeeEntitySessionBeanRemote employeeEntitySessionBeanRemote;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainApp mainApp = new MainApp(employeeEntitySessionBeanRemote,
                exceptionReportEntitySessionBeanRemote,
                guestEntitySessionBeanRemote,
                partnerEntitySessionBeanRemote,
                reservationEntitySessionBeanRemote,
                roomEntitySessionBeanRemote,
                roomRateEntitySessionBeanRemote,
                roomTypeEntitySessionBeanRemote);

        mainApp.runApp();
    }

}
