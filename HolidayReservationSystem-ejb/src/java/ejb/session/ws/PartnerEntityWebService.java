/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateless.PartnerEntitySessionBeanLocal;
import entity.PartnerEntity;
import entity.ReservationEntity;
import java.util.ArrayList;
import java.util.List;
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

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

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
            @WebParam(name = "password") String partnerPassword) throws PartnerNotFoundException, InvalidLoginCredentialException {

        PartnerEntity partner = null;
        partner = partnerEntitySessionBeanLocal.retrievePartnerByUsername(partnerUsername);

        if (partner == null) {
            throw new PartnerNotFoundException("Partner with username " + partnerUsername + " does not exist");
        } else if (!partner.getPassword().equals(partnerPassword)) {
            throw new InvalidLoginCredentialException("Wrong password");
        }

        return partner;
    }

    @WebMethod(operationName = "retrieveAllPartnerReservations")
    public List<ReservationEntity> retrieveAllPartnerReservations(@WebParam(name = "partnerId") Long partnerId) throws PartnerNotFoundException {

        try {
            PartnerEntity partner = partnerEntitySessionBeanLocal.retrievePartnerByPartnerId(partnerId);
            List<ReservationEntity> partnerReservations = partner.getReservationEntities();

            return partnerReservations;
        } catch (PartnerNotFoundException ex) {
            throw new PartnerNotFoundException("Partner does not exist");
        }
    }
}
