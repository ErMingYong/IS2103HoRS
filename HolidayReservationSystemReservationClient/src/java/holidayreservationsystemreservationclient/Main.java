/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystemreservationclient;

import ejb.session.stateless.AllocationReportSessionBeanRemote;
import ejb.session.stateless.GuestEntitySessionBeanRemote;
import ejb.session.stateless.ReservationEntitySessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author Koh Wen Jie
 */
public class Main {

    @EJB
    private static AllocationReportSessionBeanRemote allocationReportSessionBeanRemote;

    @EJB
    private static ReservationEntitySessionBeanRemote reservationEntitySessionBeanRemote;

    @EJB
    private static GuestEntitySessionBeanRemote guestEntitySessionBeanRemote;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(allocationReportSessionBeanRemote,
                reservationEntitySessionBeanRemote,
                guestEntitySessionBeanRemote);

        mainApp.runApp();
    }

}
