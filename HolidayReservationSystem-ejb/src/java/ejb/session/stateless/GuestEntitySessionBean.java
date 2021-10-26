/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GuestEntity;
import entity.ReservationEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.GuestNotFoundException;
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
    public Long createNewGuest(GuestEntity newGuest) throws UnknownPersistenceException {
        try {
            em.persist(newGuest);
            em.flush();

            return newGuest.getUserEntityId();
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
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
        GuestEntity guestEntity = em.find(GuestEntity.class, guestUsername);

        if (guestEntity != null) {
            guestEntity.getReservationEntities();

            return guestEntity;
        } else {
            throw new GuestNotFoundException("Guest Username " + guestUsername + " does not exist");
        }
    }

    @Override
    public void deleteGuest(Long guestId) throws GuestNotFoundException {
        GuestEntity guest = em.find(GuestEntity.class, guestId);

        if (guest != null) {
            em.remove(guest);
        } else {
            throw new GuestNotFoundException("Guest ID " + guestId + " does not exist");
        }
    }

    @Override
    public void updateGuest(Long oldGuestId, GuestEntity newGuest) throws GuestNotFoundException, UnknownPersistenceException {
        try {
            GuestEntity oldGuest = retrieveGuestById(oldGuestId);
            Long newGuestId = createNewGuest(newGuest);

            newGuest.setReservationEntities(oldGuest.getReservationEntities());
            oldGuest.getReservationEntities().clear();
            em.remove(oldGuest);
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        } catch (GuestNotFoundException ex) {
            throw new GuestNotFoundException("Guest ID " + oldGuestId + " does not exist");
        }
    }

    @Override
    public GuestEntity guestLogin(String guestUsername, String guestPassword) throws GuestNotFoundException, InvalidLoginCredentialException {
        try {
            GuestEntity guest = retrieveGuestByUsername(guestUsername);

            if (guest != null) {
                if (guest.getPassword().equals(guestPassword)) {
                    return guest;
                } else {
                    throw new InvalidLoginCredentialException("Password do not match");
                }
            } else {
                throw new InvalidLoginCredentialException("Username does not exist");
            }
        } catch (GuestNotFoundException ex) {
            throw new GuestNotFoundException("Guest Username " + guestUsername + " does not exist");
        }
    }
}
