/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateless.PartnerEntitySessionBeanLocal;
import ejb.session.stateless.ReservationEntitySessionBeanLocal;
import entity.PartnerEntity;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author mingy
 */



@WebService(serviceName = "PartnerEntityWebService")
@Stateless()
public class PartnerEntityWebService {

    @EJB
    private PartnerEntitySessionBeanLocal partnerEntitySessionBeanLocal;
    /*
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    */

    /*
    @EJB
    private ReservationEntitySessionBeanLocal reservationEntitySessionBeanLocal;
    */

    /**
     * This is a sample web service operation
     */
    
    
    @WebMethod(operationName = "partnerLogin")
    public PartnerEntity partnerLogin(@WebParam(name = "username") String partnerUsername,
            @WebParam(name = "password") String partnerPassword) {

        PartnerEntity partner = null;
        try {
            partner = partnerEntitySessionBeanLocal.partnerLogin(partnerUsername, partnerPassword);

            return partner;
        } catch (PartnerNotFoundException ex) {
            System.out.println("Partner Record does not exist");
        } catch (InvalidLoginCredentialException ex) {
            System.out.println("Username or Password is invalid");
        }

        return partner;
    }
}
