/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationsystemjavaseclient;

import java.util.Scanner;
import ws.client.InvalidLoginCredentialException_Exception;
import ws.client.PartnerEntity;
import ws.client.PartnerEntityWebService;
import ws.client.PartnerNotFoundException_Exception;

/**
 *
 * @author mingy
 */
public class MainApp {

    private PartnerEntityWebService webServicePort;
    private PartnerOperationModule partnerOperationModule;

    private PartnerEntity currentPartner;

    public MainApp() {
    }

    public MainApp(PartnerEntityWebService webServicePort) {
        this.webServicePort = webServicePort;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Holiday Reservation System Partner Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;

            while (response < 1 || response > 2) {
                System.out.println("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    doLogin();
                    partnerOperationModule = new PartnerOperationModule(webServicePort, currentPartner);
                    System.out.println("Login successful!\n");
                    mainMenu();
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 2) {
                break;
            }
        }
    }

    private void doLogin() {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** Hotel Reservation System Reservation System :: Login ***\n");
        System.out.println("Enter Username: ");
        username = scanner.nextLine().trim();
        System.out.println("Enter Password: ");
        password = scanner.nextLine().trim();

        try {
            currentPartner = webServicePort.partnerLogin(username, password);
        } catch (InvalidLoginCredentialException_Exception ex) {
            System.out.println("Invalid Login Credentials");
        } catch (PartnerNotFoundException_Exception ex) {
            System.out.println("Partner account does not exist");
        }
    }

    private void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Hotel Reservation System Reservations Client ***\n");
            System.out.println("You are login as " + currentPartner.getFirstName() + " " + currentPartner.getLastName());
            System.out.println("1: Partner Operation Module");
            System.out.println("2: Logout\n");
            System.out.println("");
            response = 0;

            while (response < 1 || response > 2) {
                System.out.println("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    partnerOperationModule.menuGuestOperation();
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 2) {
                break;
            }
        }
    }
}
