/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
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
import util.exception.InputDataValidationException;
import util.exception.RoomTypeNameExistException;
import util.exception.RoomTypeNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRoomTypeException;

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
    public Long createNewRoomType(RoomTypeEntity newRoomType) throws InputDataValidationException, RoomTypeNameExistException, UnknownPersistenceException {
        Set<ConstraintViolation<RoomTypeEntity>> constraintViolations = validator.validate(newRoomType);

        if (constraintViolations.isEmpty()) {
            try {
                incrementRankingsAbove(newRoomType.getRanking());
                em.persist(newRoomType);
                em.flush();

                return newRoomType.getRoomTypeId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new RoomTypeNameExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    decrementRankingsAbove(newRoomType.getRanking());
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<RoomTypeEntity> retrieveAllRoomTypes() {
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt");

        return query.getResultList();
    }

    @Override
    public RoomTypeEntity retrieveRoomTypeByRoomTypeId(Long roomTypeId) throws RoomTypeNotFoundException {

        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);

        if (roomType != null) {
            return roomType;
        } else {
            throw new RoomTypeNotFoundException("Room Type ID " + roomTypeId + " does not exist");
        }
    }

    @Override
    public RoomTypeEntity retrieveRoomTypeByName(String roomTypeName) throws RoomTypeNotFoundException {

        Query query = em.createQuery("SELECT r FROM RoomTypeEntity r WHERE r.roomTypeName = :inName");
        query.setParameter("inName", roomTypeName);
        try {
            return (RoomTypeEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new RoomTypeNotFoundException("Room Type Name " + roomTypeName + " does not exist");
        }
    }

    @Override
    public void updateRoomType(RoomTypeEntity roomTypeEntity) throws RoomTypeNotFoundException, UpdateRoomTypeException, InputDataValidationException {
        if (roomTypeEntity != null && roomTypeEntity.getRoomTypeId() != null) {
            Set<ConstraintViolation<RoomTypeEntity>> constraintViolations = validator.validate(roomTypeEntity);

            if (constraintViolations.isEmpty()) {
                RoomTypeEntity roomTypeEntityToUpdate = retrieveRoomTypeByRoomTypeId(roomTypeEntity.getRoomTypeId());

                if (roomTypeEntityToUpdate.getRoomTypeName().equals(roomTypeEntity.getRoomTypeName())) {
                    roomTypeEntityToUpdate.setDescription(roomTypeEntity.getDescription());
                    roomTypeEntityToUpdate.setSize(roomTypeEntity.getSize());
                    roomTypeEntityToUpdate.setBed(roomTypeEntity.getBed());
                    roomTypeEntityToUpdate.setCapacity(roomTypeEntity.getCapacity());
                    roomTypeEntityToUpdate.setAmenities(roomTypeEntity.getAmenities());
                    //RED FLAG
                    if (roomTypeEntityToUpdate.getRanking() != roomTypeEntity.getRanking()) {
                        roomTypeEntityToUpdate.setRanking(roomTypeEntity.getRanking());

                    }
                    // name is deliberately NOT updated to demonstrate that client is not allowed to update room name through this business method
                    //cannot set isDisabled through this method as well
                } else {
                    throw new UpdateRoomTypeException("Username of roomType record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new RoomTypeNotFoundException("RoomType ID not provided for roomType to be updated");
        }
    }

    @Override
    public void deleteRoomType(Long roomTypeId) throws RoomTypeNotFoundException {
        //if i not wrong must check through the rooms and make sure none using the roomtype before you can delete
        //RED FLAG
        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);

        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomTypeEntity.roomTypeName = :inName").setParameter("inName", roomType.getRoomTypeName());
        if (query.getResultList().size() > 0) {
            try {
                //means roomType still in use so you should disable it so no new rooms can be created with that room type
                disableRoomType(roomType);
            } catch (UnknownPersistenceException ex) {
                System.out.println("Unknown Error");;
            }
        } else {

            if (roomType != null) {
                em.remove(roomType);
            } else {
                throw new RoomTypeNotFoundException("Room Type ID " + roomTypeId + " does not exist");
            }
        }
    }

    //USE THIS TO DISABLE ROOM
    @Override
    public void disableRoomType(RoomTypeEntity roomTypeToDisable) throws UnknownPersistenceException {
        try {
            roomTypeToDisable.setIsDisabled(Boolean.TRUE);
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<RoomTypeEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    //for when you create a new roomtype
    private void incrementRankingsAbove(Integer rank) {

        int highestRank = rank;
        while (true) {
            Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.ranking =:inRanking ")
                    .setParameter("inRanking", highestRank);
            List<RoomTypeEntity> listOfRoomTypeEntities = query.getResultList();

            if (listOfRoomTypeEntities.size() != 0) {
                highestRank += 1;
            } else {
                break;
            }
        }

        while (highestRank > rank) {
            Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.ranking =:inRanking ")
                    .setParameter("inRanking", highestRank);
            List<RoomTypeEntity> listOfRoomTypeEntities = query.getResultList();
            for (RoomTypeEntity roomTypeEntity : listOfRoomTypeEntities) {
                roomTypeEntity.setRanking(rank + 1);
            }
            highestRank -= 1;
        }
    }

    //for when you create a new roomtype
    private void decrementRankingsAbove(Integer rank) {
        while (true) {
            Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.ranking =:inRanking ")
                    .setParameter("inRanking", rank);
            List<RoomTypeEntity> listOfRoomTypeEntities = query.getResultList();
            if (listOfRoomTypeEntities.size() == 0) {
                break;
            }
            for (RoomTypeEntity roomTypeEntity : listOfRoomTypeEntities) {
                roomTypeEntity.setRanking(rank - 1);
            }
            rank += 1;
        }
    }
}
