/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.RoomTypeNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Stateless
public class RoomTypeEntitySessionBean implements RoomTypeEntitySessionBeanRemote, RoomTypeEntitySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatoryFactory;
    private final Validator validator;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public RoomTypeEntitySessionBean() {
        validatoryFactory = Validation.buildDefaultValidatorFactory();
        validator = validatoryFactory.getValidator();
    }

    @Override
    public Long createNewRoomType(RoomTypeEntity newRoomType) throws UnknownPersistenceException {

        try {
            em.persist(newRoomType);
            em.flush();

            return newRoomType.getRoomTypeId();
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<RoomTypeEntity> retrieveAllRoomType() {
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt");

        return query.getResultList();
    }

    @Override
    public RoomTypeEntity retrieveRoomTypeById(Long roomTypeId) throws RoomTypeNotFoundException {

        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);

        if (roomType != null) {
            roomType.getRoomRateEntities().size();
            return roomType;
        } else {
            throw new RoomTypeNotFoundException("Room Type ID " + roomTypeId + " does not exist");
        }
    }

    @Override
    public RoomTypeEntity retrieveRoomTypeByName(String roomTypeName) throws RoomTypeNotFoundException {

        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeName);

        if (roomType != null) {
            roomType.getRoomRateEntities().size();
            return roomType;
        } else {
            throw new RoomTypeNotFoundException("Room Type Name " + roomTypeName + " does not exist");
        }
    }

    @Override
    public void deleteRoomType(Long roomTypeId) throws RoomTypeNotFoundException {

        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);

        if (roomType != null) {
            roomType.getRoomRateEntities().clear();
            em.remove(roomType);
        } else {
            throw new RoomTypeNotFoundException("Room Type ID " + roomTypeId + " does not exist");
        }
    }

    @Override
    public void updateRoomType(Long oldRoomTypeId, RoomTypeEntity newRoomType) throws UnknownPersistenceException {
        try {
            RoomTypeEntity oldRoomType = em.find(RoomTypeEntity.class, oldRoomTypeId);
            Long newRoomTypeId = createNewRoomType(newRoomType);

            for (RoomRateEntity roomRate : oldRoomType.getRoomRateEntities()) {
                roomRate.setRoomTypeEntity(newRoomType);
            }

            oldRoomType.getRoomRateEntities().clear();
            em.remove(oldRoomType);
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
}
