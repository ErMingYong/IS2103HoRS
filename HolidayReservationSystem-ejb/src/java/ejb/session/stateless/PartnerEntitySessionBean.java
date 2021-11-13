/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;
import util.exception.PartnerUsernameExistException;
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

    public PartnerEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewPartner(PartnerEntity newPartnerEntity) throws UnknownPersistenceException, PartnerUsernameExistException, InputDataValidationException {
        Set<ConstraintViolation<PartnerEntity>> constraintViolations = validator.validate(newPartnerEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newPartnerEntity);
                em.flush();

                return newPartnerEntity.getUserEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new PartnerUsernameExistException(ex.getMessage());
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<PartnerEntity> retrieveAllPartners() {
        Query query = em.createQuery("SELECT p FROM PartnerEntity p");
        List<PartnerEntity> listOfPartnerEntities = query.getResultList();
        for (PartnerEntity partnerEntity : listOfPartnerEntities) {
            partnerEntity.getReservationEntities().size();
        }

        return listOfPartnerEntities;
    }

    @Override
    public PartnerEntity retrievePartnerByPartnerId(Long partnerId) throws PartnerNotFoundException {
        PartnerEntity partner = em.find(PartnerEntity.class, partnerId);

        if (partner != null) {
            partner.getReservationEntities().size();
            return partner;
        } else {
            throw new PartnerNotFoundException("Partner ID " + partnerId + " does not exist");
        }
    }

    @Override
    public PartnerEntity retrievePartnerByUsername(String username) throws PartnerNotFoundException {

        Query query = em.createQuery("SELECT p FROM PartnerEntity p WHERE p.username = :inUsername")
                .setParameter("inUsername", username);

        PartnerEntity partner = (PartnerEntity) query.getSingleResult();

        if (partner != null) {
            partner.getReservationEntities().size();
            return partner;
        } else {
            throw new PartnerNotFoundException("Partner Username " + username + " does not exist");
        }
    }

    @Override
    public PartnerEntity partnerLogin(String partnerUsername, String partnerPassword) throws PartnerNotFoundException, InvalidLoginCredentialException {
        try {
            PartnerEntity partner = retrievePartnerByUsername(partnerUsername);

            if (partner != null) {
                if (partner.getPassword().equals(partnerPassword)) {
                    return partner;
                } else {
                    throw new InvalidLoginCredentialException("Password do not match");
                }
            } else {
                throw new InvalidLoginCredentialException("Username does not exist");
            }
        } catch (PartnerNotFoundException ex) {
            throw new PartnerNotFoundException("Partner Username " + partnerUsername + " does not exist");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PartnerEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
