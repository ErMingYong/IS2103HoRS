/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import entity.RoomTypeEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.RoomNotFoundException;
import util.exception.RoomTypeNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Stateless
public class RoomEntitySessionBean implements RoomEntitySessionBeanRemote, RoomEntitySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public RoomEntitySessionBean() {
    }

    @Override
    public Long createNewRoom(RoomEntity newRoom) throws UnknownPersistenceException {
        try {
            em.persist(newRoom);
            em.flush();

            return newRoom.getRoomId();
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<RoomEntity> retrieveAllRoom() {
        Query query = em.createQuery("SELECT r FROM RoomEntity r");

        return query.getResultList();
    }

    @Override
    public RoomEntity retrieveRoomById(Long roomId) throws RoomNotFoundException {

        RoomEntity room = em.find(RoomEntity.class, roomId);

        if (room != null) {
            room.getRoomTypeEntity();
            return room;
        } else {
            throw new RoomNotFoundException("Room ID " + roomId + " does not exist");
        }
    }

    @Override
    public void deleteRoom(Long roomId) throws RoomNotFoundException {
        RoomEntity room = retrieveRoomById(roomId);
        if (room != null) {
            room.setRoomTypeEntity(null);

            em.remove(room);
        } else {
            throw new RoomNotFoundException("Room ID " + roomId + " does not exist");
        }
    }
    
    
    @Override
    public void updateRoom(Long oldRoomId, RoomEntity newRoom) throws UnknownPersistenceException {
        try {
            RoomEntity oldRoom = em.find(RoomEntity.class, oldRoomId);
            Long newRoomId = createNewRoom(newRoom);
            
            newRoom.setRoomTypeEntity(oldRoom.getRoomTypeEntity());
            
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());            
        }
    }
}
