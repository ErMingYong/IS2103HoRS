/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
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
import util.exception.RoomRateNameExistException;
import util.exception.RoomRateNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRoomRateException;

/**
 *
 * @author mingy
 */
@Stateless
public class RoomRateEntitySessionBean implements RoomRateEntitySessionBeanRemote, RoomRateEntitySessionBeanLocal {

    @EJB
    private RoomTypeEntitySessionBeanLocal roomRateEntitySessionBeanLocal;

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public RoomRateEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewRoomRate(RoomRateEntity newRoomRate) throws InputDataValidationException, RoomRateNameExistException, UnknownPersistenceException {
        Set<ConstraintViolation<RoomRateEntity>> constraintViolations = validator.validate(newRoomRate);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newRoomRate);
                em.flush();
                RoomTypeEntity roomType = em.find(RoomTypeEntity.class, newRoomRate.getRoomTypeEntity().getRoomTypeId());
                roomType.getRoomRateEntities().add(newRoomRate);

                return newRoomRate.getRoomRateId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new RoomRateNameExistException();
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
    public List<RoomRateEntity> retrieveAllRoomRates() {
        Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr");

        List<RoomRateEntity> listOfRoomRates = query.getResultList();
        for (RoomRateEntity roomRateEntity : listOfRoomRates) {
            roomRateEntity.getRoomTypeEntity();
        }
        return listOfRoomRates;
    }

    @Override
    public RoomRateEntity retrieveRoomRateById(Long roomRateId) throws RoomRateNotFoundException {
        RoomRateEntity roomRate = em.find(RoomRateEntity.class, roomRateId);

        if (roomRate != null) {
            roomRate.getRoomTypeEntity();
            return roomRate;
        } else {
            throw new RoomRateNotFoundException("Room Rate ID " + roomRateId + " does not exist");
        }
    }

    @Override
    public RoomRateEntity retrieveRoomRateByName(String roomRateName) throws RoomRateNotFoundException {

        Query query = em.createQuery("SELECT r FROM RoomRateEntity r WHERE r.roomRateName = :inRoomRateName");
        query.setParameter("inRoomRateName", roomRateName);
        try {
            RoomRateEntity roomRate = (RoomRateEntity) query.getSingleResult();
            roomRate.getRoomTypeEntity();
            return roomRate;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new RoomRateNotFoundException("Room Rate Name " + roomRateName + " does not exist");
        }
    }

    @Override
    public void deleteRoomRate(Long roomRateId) throws RoomRateNotFoundException {

        RoomRateEntity roomRate = em.find(RoomRateEntity.class, roomRateId);
        //Get reservations that are ongoing where the roomrate is the name of the roomrate to be deleted
        //assume roomtype has more than 1 roomrate so that after you delete the ManyToOne is still preserved
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.roomRateEntities.contains(:inName) = true").setParameter("inName", roomRate);

        List<ReservationEntity> listOfReservationEntities = query.getResultList();
//        LocalDateTime currentDate = LocalDateTime.now();
        boolean toDisable = false;
//        for (ReservationEntity res : listOReservationEntities) {
//            if (res.getReservationStartDate().isAfter(currentDate) && res.getRoomRateName().equals(roomRate.getRoomRateName())) {
//                toDisable = true;
//                break;
//            }
//        }
        if (listOfReservationEntities.size() > 0) {
            toDisable = true;
        }
        if (toDisable) {
            try {
                //means roomRate still in use so you should disable it so no new rooms can be created with that room type
                disableRoomRate(roomRate);
            } catch (UnknownPersistenceException ex) {
                System.out.println("Unknown Error");
            }
        } else {

            if (roomRate != null) {
                em.remove(roomRate);
            } else {
                throw new RoomRateNotFoundException("Room Type ID " + roomRateId + " does not exist");
            }
        }
    }

    @Override
    public void disableRoomRate(RoomRateEntity roomRateToDisable) throws UnknownPersistenceException {
        try {
            roomRateToDisable.setIsDisabled(true);
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    //ISSUE HERE, MIGHT NOT BE ABLE TO UPDATE PROPERLY
    @Override
    public void updateRoomRate(RoomRateEntity roomRate) throws RoomRateNotFoundException, UpdateRoomRateException, InputDataValidationException {
        if (roomRate != null && roomRate.getRoomRateId() != null) {
            Set<ConstraintViolation<RoomRateEntity>> constraintViolations = validator.validate(roomRate);

            if (constraintViolations.isEmpty()) {
                RoomRateEntity roomRateToUpdate = retrieveRoomRateById(roomRate.getRoomRateId());

                roomRateToUpdate.setRoomRateName(roomRate.getRoomRateName());
                roomRateToUpdate.setRatePerNight(roomRate.getRatePerNight());
                roomRateToUpdate.setValidPeriodFrom(roomRate.getValidPeriodFrom());
                roomRateToUpdate.setValidPeriodTo(roomRate.getValidPeriodTo());
                // name is deliberately NOT updated to demonstrate that client is not allowed to update room name through this business method
                //cannot set isDisabled through this method as well

            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new RoomRateNotFoundException("Room Rate not found");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<RoomRateEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
