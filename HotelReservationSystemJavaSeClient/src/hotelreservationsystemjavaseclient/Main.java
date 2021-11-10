/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationsystemjavaseclient;

import ws.client.PartnerEntityWebService;
import ws.client.PartnerEntityWebService_Service;

/**
 *
 * @author mingy
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    private static PartnerEntityWebService webServicePort;

    public static void main(String[] args) {
        // TODO code application logic here

        PartnerEntityWebService_Service webService = new PartnerEntityWebService_Service();
        webServicePort = webService.getPartnerEntityWebServicePort();
        
        MainApp mainApp = new MainApp(webServicePort);
        mainApp.runApp();
    }
}