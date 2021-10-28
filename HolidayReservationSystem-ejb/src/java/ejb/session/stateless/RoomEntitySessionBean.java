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
import util.exception.RoomFloorAndNumberExistException;
import util.exception.RoomTypeNameExistException;
import util.exception.RoomNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRoomException;

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
    public Long createNewRoom(RoomEntity newRoomEntity) throws InputDataValidationException, UnknownPersistenceException, RoomFloorAndNumberExistException {
        Set<ConstraintViolation<RoomEntity>> constraintViolations = validator.validate(newRoomEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newRoomEntity);
                em.flush();

                return newRoomEntity.getRoomId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new RoomFloorAndNumberExistException(ex.getMessage());
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
    public List<RoomEntity> retrieveAllRooms() {
        Query query = em.createQuery("SELECT r FROM RoomEntity r");
        List<RoomEntity> listOfRoomEntities = query.getResultList();
        for (RoomEntity roomEntity : listOfRoomEntities) {
            roomEntity.getRoomTypeEntity();
        }
        return listOfRoomEntities;
    }

    @Override
    public RoomEntity retrieveRoomByRoomId(Long roomId) throws RoomNotFoundException {

        RoomEntity room = em.find(RoomEntity.class, roomId);
        room.getRoomTypeEntity();

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
        room.getRoomTypeEntity();
        
        return room;
    }

    @Override
    public void deleteRoom(RoomEntity roomToDelete) throws RoomNotFoundException {
        RoomEntity room = retrieveRoomByRoomId(roomToDelete.getRoomId());
        if (room != null) {
            room.setRoomTypeEntity(null);

            em.remove(room);
        } else {
            throw new RoomNotFoundException("Room ID " + room.getRoomId() + " does not exist");
        }
    }

    
    //updateRoom needs to update room status as well
    public void updateRoom(RoomEntity roomEntity) throws RoomNotFoundException, UpdateRoomException, InputDataValidationException
    {
        if(roomEntity != null && roomEntity.getRoomId()!= null)
        {
            Set<ConstraintViolation<RoomEntity>>constraintViolations = validator.validate(roomEntity);
        
            if(constraintViolations.isEmpty())
            {
                RoomEntity roomEntityToUpdate = retrieveRoomByRoomId(roomEntity.getRoomId());
                
                //Because we are allowing room floor and number to be changed, i am not sure what to use to do the checking here so i will use primary key
                if(roomEntityToUpdate.getRoomId().equals(roomEntity.getRoomId()))
                {
                    roomEntityToUpdate.setRoomFloor(roomEntity.getRoomFloor());
                    roomEntityToUpdate.setRoomNumber(roomEntity.getRoomNumber());
                    roomEntityToUpdate.setRoomStatusEnum(roomEntity.getRoomStatusEnum());
                    roomEntityToUpdate.setRoomTypeEntity(roomEntity.getRoomTypeEntity());

                }
                else
                {
                    throw new UpdateRoomException("SKU Code of room record to be updated does not match the existing record");
                }
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new RoomNotFoundException("Room ID not provided for room to be updated");
        }
    }
    
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<RoomEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
