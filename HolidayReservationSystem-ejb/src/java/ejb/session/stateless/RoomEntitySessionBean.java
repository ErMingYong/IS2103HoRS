/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
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
import util.exception.RoomTypeNameExistException;
import util.exception.RoomNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Stateless
public class RoomEntitySessionBean implements RoomEntitySessionBeanRemote, RoomEntitySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public RoomEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewRoom(RoomEntity newRoomEntity) throws InputDataValidationException, UnknownPersistenceException, RoomTypeNameExistException {
        Set<ConstraintViolation<RoomEntity>> constraintViolations = validator.validate(newRoomEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newRoomEntity);
                em.flush();

                return newRoomEntity.getRoomId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new RoomTypeNameExistException(ex.getMessage());
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
    public List<RoomEntity> retrieveAllRoom() {
        Query query = em.createQuery("SELECT r FROM RoomEntity r");

        return query.getResultList();
    }

    @Override
    public RoomEntity retrieveRoomByRoomId(Long roomId) throws RoomNotFoundException {

        RoomEntity room = em.find(RoomEntity.class, roomId);

        if (room != null) {
            room.getRoomTypeEntity();
            return room;
        } else {
            throw new RoomNotFoundException("Room ID " + roomId + " does not exist");
        }
    }

    @Override
    public RoomEntity retrieveRoomByRoomFloorAndRoomNumber(Integer roomFloor, Integer roomNumber) throws RoomNotFoundException {

        RoomEntity room = (RoomEntity) em.createQuery(
                "SELECT r FROM Room r WHERE r.roomFloor = :rmFloor AND r.roomNumber = :rmNumber")
                .setParameter("rmFloor", roomFloor)
                .setParameter("rmNumber", roomNumber)
                .getSingleResult();

        return room;
    }

    @Override
    public void deleteRoom(Long roomId) throws RoomNotFoundException {
        RoomEntity room = retrieveRoomByRoomId(roomId);
        if (room != null) {
            room.setRoomTypeEntity(null);

            em.remove(room);
        } else {
            throw new RoomNotFoundException("Room ID " + roomId + " does not exist");
        }
    }

    @Override
    public void updateRoom(Long oldRoomId, RoomEntity newRoom) throws RoomTypeNameExistException, InputDataValidationException, UnknownPersistenceException {
        try {
            RoomEntity oldRoom = em.find(RoomEntity.class, oldRoomId);
            Long newRoomId = createNewRoom(newRoom);

            newRoom.setRoomTypeEntity(oldRoom.getRoomTypeEntity());

            em.remove(oldRoom);

        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
    
//    @Override
//    public void updateRoom(RoomEntity roomEntity) throws RoomNotFoundException, UpdateRoomException, InputDataValidationException
//    {
//        if(roomEntity != null && roomEntity.getRoomId() != null)
//        {
//            Set<ConstraintViolation<RoomEntity>>constraintViolations = validator.validate(roomEntity);
//        
//            if(constraintViolations.isEmpty())
//            {
//                RoomEntity roomEntityToUpdate = retrieveRoomByRoomId(roomEntity.getRoomId());
//
//                if(roomEntityToUpdate.getRoomFloor().equals(roomEntity.getRoomFloor()) && roomEntityToUpdate.getRoomNumber().equals(roomEntity.getRoomNumber()))
//                {
//                    roomEntityToUpdate.setFirstName(roomEntity.getFirstName());
//                    roomEntityToUpdate.setLastName(roomEntity.getLastName());
//                    roomEntityToUpdate.setAccessRightEnum(roomEntity.getAccessRightEnum());                
//                    // Username and password are deliberately NOT updated to demonstrate that client is not allowed to update account credential through this business method
//                }
//                else
//                {
//                    throw new UpdateRoomException("Username of room record to be updated does not match the existing record");
//                }
//            }
//            else
//            {
//                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
//            }
//        }
//        else
//        {
//            throw new RoomNotFoundException("Room ID not provided for room to be updated");
//        }
//    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<RoomEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
