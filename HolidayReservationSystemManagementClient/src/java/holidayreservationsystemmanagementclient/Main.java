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
import javax.ejb.EJB;

/**
 *
 * @author Koh Wen Jie
 */
public class Main {

    @EJB
    private static TransactionEntitySessionBeanRemote transactionEntitySessionBean;

    @EJB
    private static RoomTypeEntitySessionBeanRemote roomTypeEntitySessionBean;

    @EJB
    private static RoomRateEntitySessionBeanRemote roomRateEntitySessionBean;

    @EJB
    private static RoomEntitySessionBeanRemote roomEntitySessionBean;

    @EJB
    private static ReservationEntitySessionBeanRemote reservationEntitySessionBean;

    @EJB
    private static PartnerEntitySessionBeanRemote partnerEntitySessionBean;

    @EJB
    private static GuestEntitySessionBeanRemote guestEntitySessionBean;

    @EJB
    private static ExceptionReportEntitySessionBeanRemote exceptionReportEntitySessionBean;

    @EJB
    private static EmployeeEntitySessionBeanRemote employeeEntitySessionBean;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainApp mainApp = new MainApp(employeeEntitySessionBean,
                exceptionReportEntitySessionBean,
                guestEntitySessionBean,
                partnerEntitySessionBean,
                reservationEntitySessionBean,
                roomEntitySessionBean,
                roomRateEntitySessionBean,
                roomTypeEntitySessionBean,
                transactionEntitySessionBean);
        
        mainApp.runApp();
    }

}
