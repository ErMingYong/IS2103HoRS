/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.util.HashMap;
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
                changeRankingWhenInclude(newRoomType, newRoomType.getRanking());
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
                    changeRankingWhenRemove(newRoomType.getRanking());
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
        List<RoomTypeEntity> listOfRoomTypeEntities = query.getResultList();
        for (RoomTypeEntity roomType : listOfRoomTypeEntities) {
            roomType.getRoomRateEntities().size();
            roomType.getRoomEntities().size();
        }
        return listOfRoomTypeEntities;
    }

    @Override
    public RoomTypeEntity retrieveRoomTypeByRoomTypeId(Long roomTypeId) throws RoomTypeNotFoundException {

        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);
        roomType.getRoomRateEntities().size();
        roomType.getRoomEntities().size();
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
            RoomTypeEntity roomType = (RoomTypeEntity) query.getSingleResult();
            roomType.getRoomRateEntities().size();
            roomType.getRoomEntities().size();
            return roomType;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new RoomTypeNotFoundException("Room Type Name " + roomTypeName + " does not exist");
        }
    }

    @Override
    public HashMap<String, Integer> retrieveRoomTypeRankingsSortedByName() {

        //name-ranking pairing
        HashMap<String, Integer> map = new HashMap<>();

        Query query = em.createQuery("SELECT r FROM RoomTypeEntity r");

        List<RoomTypeEntity> list = query.getResultList();
        for (RoomTypeEntity roomType : list) {
            if (map.containsKey(roomType.getRoomTypeName())) {
            } else {
                map.put(roomType.getRoomTypeName(), roomType.getRanking());
            }
        }

        return map;
    }

    @Override
    public HashMap<Integer, String> retrieveRoomTypeRankingsSortedByRanking() {

        //ranking-name pairing
        HashMap<Integer, String> map = new HashMap<>();

        Query query = em.createQuery("SELECT r FROM RoomTypeEntity r");

        List<RoomTypeEntity> list = query.getResultList();
        for (RoomTypeEntity roomType : list) {
            if (map.containsKey(roomType.getRanking())) {
            } else {
                map.put(roomType.getRanking(), roomType.getRoomTypeName());
            }
        }

        return map;
    }

    @Override
    public void updateRoomType(RoomTypeEntity roomTypeEntity) throws RoomTypeNotFoundException, InputDataValidationException, RoomTypeNameExistException {
        if (roomTypeEntity != null && roomTypeEntity.getRoomTypeId() != null) {
            Set<ConstraintViolation<RoomTypeEntity>> constraintViolations = validator.validate(roomTypeEntity);
            //RoomTypeEntity roomType = em.find(RoomTypeEntity.class,roomTypeEntity.getRoomTypeId());

            //check if new name is used already
            Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.roomTypeName = :inName").setParameter("inName", roomTypeEntity.getRoomTypeName());
            boolean isNameUsed = false;

            if (query.getResultList().size() > 0) {
                RoomTypeEntity rt = (RoomTypeEntity) query.getSingleResult();
                if (rt.getRoomTypeId().equals(roomTypeEntity)) {
                    isNameUsed = true;
                }
            }

            if (isNameUsed) {
                throw new RoomTypeNameExistException();
            }

            if (constraintViolations.isEmpty()) {
                RoomTypeEntity roomTypeEntityToUpdate = retrieveRoomTypeByRoomTypeId(roomTypeEntity.getRoomTypeId());
                roomTypeEntityToUpdate.setRoomTypeName(roomTypeEntity.getRoomTypeName());
                roomTypeEntityToUpdate.setDescription(roomTypeEntity.getDescription());
                roomTypeEntityToUpdate.setSize(roomTypeEntity.getSize());
                roomTypeEntityToUpdate.setBed(roomTypeEntity.getBed());
                roomTypeEntityToUpdate.setCapacity(roomTypeEntity.getCapacity());
                roomTypeEntityToUpdate.setAmenities(roomTypeEntity.getAmenities());
                roomTypeEntityToUpdate.setIsDisabled(roomTypeEntity.getIsDisabled());
                if (roomTypeEntityToUpdate.getRanking() != roomTypeEntity.getRanking()) {
                    roomTypeEntityToUpdate.setRanking(roomTypeEntity.getRanking());
                    updateRankings(roomTypeEntity, roomTypeEntity.getRanking());
                }
                for (RoomRateEntity rr : roomTypeEntityToUpdate.getRoomRateEntities()) {
                    rr.setRoomTypeName(roomTypeEntityToUpdate.getRoomTypeName());
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
        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);
        List<RoomEntity> listOfRoomEntities = roomType.getRoomEntities();
        List<RoomRateEntity> listOfRoomRateEntities = roomType.getRoomRateEntities();
        if (listOfRoomEntities.size() > 0 || listOfRoomRateEntities.size() > 0) {
            try {
                //means roomType still in use so you should disable it so no new rooms can be created with that room type
                disableRoomType(roomType);
                //set ranking to the lowest so that it wont affect the roomtype ranking during allocation
                updateRankings(roomType, 1);
            } catch (UnknownPersistenceException ex) {
                System.out.println("Unknown Error");
            }
        } else {

            if (roomType != null) {
                changeRankingWhenRemove(roomType.getRanking());
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

    //for when you update a roomtype
    private void changeRankingWhenInclude(RoomTypeEntity roomTypeEntity, Integer rank) {
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt ORDER BY rt.ranking");
        List<RoomTypeEntity> listOfRoomTypeEntities = query.getResultList();
        if (rank <= 0) {
            rank = 1;
        } else if (rank > listOfRoomTypeEntities.size()) {
            rank = listOfRoomTypeEntities.size() + 1;
        }
        listOfRoomTypeEntities.add(rank - 1, roomTypeEntity);

        int counter = 1;
        for (RoomTypeEntity roomType : listOfRoomTypeEntities) {
            roomType.setRanking(counter);
            counter += 1;
        }
    }

    private void changeRankingWhenRemove(Integer rank) {
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt ORDER BY rt.ranking");
        List<RoomTypeEntity> listOfRoomTypeEntities = query.getResultList();
        listOfRoomTypeEntities.remove(rank - 1);
        int counter = 1;
        for (RoomTypeEntity roomType : listOfRoomTypeEntities) {
            roomType.setRanking(counter);
            counter += 1;
        }
    }

    private void updateRankings(RoomTypeEntity roomTypeEntity, Integer rank) {
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt ORDER BY rt.ranking");
        List<RoomTypeEntity> listOfRoomTypeEntities = query.getResultList();
        listOfRoomTypeEntities.remove(roomTypeEntity);
        if (rank <= 0) {
            rank = 1;
        } else if (rank > listOfRoomTypeEntities.size()) {
            rank = listOfRoomTypeEntities.size() + 1;
        }
        listOfRoomTypeEntities.add(rank - 1, roomTypeEntity);

        int counter = 1;
        for (RoomTypeEntity roomType : listOfRoomTypeEntities) {
            roomType.setRanking(counter);
            counter += 1;
        }
    }
}
