/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.PartnerNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Stateless
public class PartnerEntitySessionBean implements PartnerEntitySessionBeanRemote, PartnerEntitySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public PartnerEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewPartner(PartnerEntity newPartner) throws UnknownPersistenceException {
        try {
            em.persist(newPartner);
            em.flush();
            
            return newPartner.getUserEntityId();
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
    
    @Override
    public List<PartnerEntity> retrieveAllPartner() {
        Query query = em.createQuery("SELECT p FROM PartnerEntity p");
        
        return query.getResultList();
    }
    
    @Override
    public PartnerEntity retrievePartnerById(Long partnerId) throws PartnerNotFoundException {
        PartnerEntity partner = em.find(PartnerEntity.class, partnerId);
        
        if (partner != null) {
            partner.getReservationEntities();
            return partner;
        } else {
            throw new PartnerNotFoundException("Partner ID " + partnerId + " does not exist");
        }
    }
    
    @Override
    public void deletePartner(Long partnerId) throws PartnerNotFoundException {
        PartnerEntity partner = em.find(PartnerEntity.class, partnerId);
        
        if (partner != null) {
            em.remove(partner);
        } else {
            throw new PartnerNotFoundException("Partner ID " + partnerId + " does not exist");
        }
    }
    
    @Override
    public void updatePartner(Long oldPartnerId, PartnerEntity newPartner) throws UnknownPersistenceException {
        try {
            PartnerEntity oldPartner = em.find(PartnerEntity.class, oldPartnerId);
            Long newPartnerId = createNewPartner(newPartner);
            
            newPartner.setReservationEntities(oldPartner.getReservationEntities());
            em.remove(oldPartner);
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
}
