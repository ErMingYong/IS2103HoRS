/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GuestEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.GuestNotFoundException;
import util.exception.GuestUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Stateless
public class GuestEntitySessionBean implements GuestEntitySessionBeanRemote, GuestEntitySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public GuestEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewGuest(GuestEntity newGuest) throws UnknownPersistenceException, GuestUsernameExistException, InputDataValidationException {
        Set<ConstraintViolation<GuestEntity>> constraintViolations = validator.validate(newGuest);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newGuest);
                em.flush();

                return newGuest.getUserEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new GuestUsernameExistException(ex.getMessage());
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
    public List<GuestEntity> retrieveAllGuest() {
        Query query = em.createQuery("SELECT g FROM GuestEntity g");
        List<GuestEntity> listOfGuestEntities = query.getResultList();
        for (GuestEntity guestEntity : listOfGuestEntities) {
            guestEntity.getReservationEntities().size();
        }
        return query.getResultList();
    }

    @Override
    public GuestEntity retrieveGuestById(Long guestId) throws GuestNotFoundException {
        GuestEntity guestEntity = em.find(GuestEntity.class, guestId);
        guestEntity.getReservationEntities().size();

        if (guestEntity != null) {
            guestEntity.getReservationEntities();
            return guestEntity;
        } else {
            throw new GuestNotFoundException("Guest ID " + guestId + " does not exist");
        }
    }

    @Override
    public GuestEntity retrieveGuestByUsername(String guestUsername) throws GuestNotFoundException {
        Query query = em.createQuery("SELECT g FROM GuestEntity g WHERE g.username = :inUsername").setParameter("inUsername", guestUsername);

        try {
            GuestEntity guest = (GuestEntity) query.getSingleResult();
            guest.getReservationEntities().size();
            return guest;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new GuestNotFoundException("Staff Username " + guestUsername + " does not exist!");
        }
    }

    @Override
    public GuestEntity guestLogin(String guestUsername, String guestPassword) throws InvalidLoginCredentialException {

        try {
            GuestEntity guest = retrieveGuestByUsername(guestUsername);

            if (guest.getPassword().equals(guestPassword)) {
                return guest;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (GuestNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<GuestEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
