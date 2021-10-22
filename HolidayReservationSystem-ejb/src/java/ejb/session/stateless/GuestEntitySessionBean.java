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
import util.exception.GuestNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Stateless
public class GuestEntitySessionBean implements GuestEntitySessionBeanRemote, GuestEntitySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public GuestEntitySessionBean() {
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

        return query.getResultList();
    }

    @Override
    public GuestEntity retrieveGuestById(Long guestId) throws GuestNotFoundException {
        GuestEntity guest = em.find(GuestEntity.class, guestId);

        if (guest != null) {
            guest.getReservationEntities();
            return guest;
        } else {
            throw new GuestNotFoundException("Guest ID " + guestId + " does not exist");
        }
    }

    @Override
    public void deleteGuest(Long guestId) throws GuestNotFoundException {
        GuestEntity guest = em.find(GuestEntity.class, guestId);

        if (guest != null) {
            for (ReservationEntity reservation : guest.getReservationEntities()) {
                reservation.setGuestEntity(null);
            }

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
}
