/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationsystemjavaseclient;

import java.util.List;
import java.util.Scanner;
import ws.client.LocalDateTime;
import ws.client.PartnerEntity;
import ws.client.PartnerEntityWebService;
import ws.client.PartnerNotFoundException_Exception;
import ws.client.ReservationEntity;

/**
 *
 * @author mingy
 */
public class PartnerOperationModule {

    private PartnerEntityWebService webServicePort;

    private PartnerEntity currentPartner;

    public PartnerOperationModule() {
    }

    public PartnerOperationModule(PartnerEntityWebService webServicePort, PartnerEntity currentPartner) {
        this.webServicePort = webServicePort;
        this.currentPartner = currentPartner;
    }

    public void menuGuestOperation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Reservation Partner System :: Partner Operation ***\n");
            System.out.println("1: Partner Reserve Hotel Room");
            System.out.println("2: View Partner Reservation Details");
            System.out.println("3: View All Partner Reservations");
            System.out.println("4: Exit");
            response = 0;

            while (response < 1 || response > 4) {
                response = scanner.nextInt();

                if (response == 1) {
                    doSearchHotelRoom();
                } else if (response == 2) {
                    try {
                        doViewPartnerReservationDetails();
                    } catch (PartnerNotFoundException_Exception ex) {
                        System.out.println("Partner account does not exist");
                    }
                } else if (response == 3) {
                    doViewAllPartnerReservation();
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

    private void doSearchHotelRoom() {

    }

    private void doViewPartnerReservationDetails() throws PartnerNotFoundException_Exception {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** Hotel Reservation System Reservation Partner System :: Partner Operation :: View Partner Reservation Details***\n");
            List<ReservationEntity> partnerReservations = webServicePort.retrieveAllPartnerReservations(currentPartner.getUserEntityId());

            Integer option = 0;
            while (true) {
                System.out.println("----------------------------------------");
                for (int i = 0; i < partnerReservations.size(); i++) {
                    System.out.println((i + 1) + " Reservation Id: " + partnerReservations.get(i).getReservationEntityId());
                }
                option = 0;
                while (option < 1 || option > partnerReservations.size()) {
                    System.out.println("----------------------------------------");
                    System.out.println("Enter Reservation Number Option");
                    option = scanner.nextInt();

                    if (option >= 1 && option <= partnerReservations.size()) {
                        ReservationEntity reservation = partnerReservations.get(option - 1);
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
                        System.out.println("Reservation Last Name: " + reservationLastname);
                        System.out.println("Reservation Start Date: " + reservationStartDate.toString());
                        System.out.println("Reservation End Date: " + reservationEndDate.toString());
                        System.out.println("Reservation Email: " + reservationEmail);
                        System.out.println("Reservation Contact Number: " + reservationContactNumber);
                        System.out.println("Reservation Passport Number: " + reservationPassportNumber);

                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    } else {
                        System.out.println("Please select a valid input!");
                        System.out.println("");
                    }
                }
                System.out.println("");
                System.out.println("Continue to view Reservation? Press 'Y', to Exit Press any other key...");
                String response = scanner.nextLine();
                if (response == "Y") {
                    continue;
                }
                break;
            }
        } catch (PartnerNotFoundException_Exception ex) {
            System.out.println("Partner account does not exist");
        }

    }

    private void doViewAllPartnerReservation() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** Hotel Reservation System Reservation Partner System :: Partner Operation :: View All Parnter Reservations ***\n");

            List<ReservationEntity> partnerReservations = webServicePort.retrieveAllPartnerReservations(currentPartner.getUserEntityId());

            System.out.printf("%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s\n", "Reservation Start Date", "Reservation End Date", "First Name", "Last Name", "Email", "Contact Number", "Passport Number");
            for (ReservationEntity reservationEntity : partnerReservations) {
                System.out.printf("%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s%15.15s\n", reservationEntity.getReservationStartDate().toString(), reservationEntity.getReservationEndDate().toString(), reservationEntity.getFirstName(), reservationEntity.getLastName(), reservationEntity.getEmail(), reservationEntity.getContactNumber(), reservationEntity.getPassportNumber());
            }
        } catch (PartnerNotFoundException_Exception ex) {
            System.out.println("Partner does not exist");
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }
}
