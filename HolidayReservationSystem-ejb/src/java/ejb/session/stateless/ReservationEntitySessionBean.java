/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import entity.RoomEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
import util.enumeration.RoomRateTypeEnum;
import util.enumeration.RoomStatusEnum;
import util.exception.InputDataValidationException;
import util.exception.InsufficientRoomsAvailableException;
import util.exception.ReservationNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReservationException;

/**
 *
 * @author mingy
 */
@Stateless
public class ReservationEntitySessionBean implements ReservationEntitySessionBeanRemote, ReservationEntitySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public ReservationEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewReservation(ReservationEntity newReservation) throws UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<ReservationEntity>> constraintViolations = validator.validate(newReservation);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newReservation);
                em.flush();

                return newReservation.getReservationEntityId();
            } catch (PersistenceException ex) {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<ReservationEntity> retrieveAllReservations() {
        Query query = em.createQuery("SELECT r FROM ReservationEntity r");

        List<ReservationEntity> listReservations = query.getResultList();
        for (ReservationEntity reservationEntity : listReservations) {
            reservationEntity.getRoomEntity();
            reservationEntity.getTransactionEntity();
        }

        return listReservations;
    }

    @Override
    public ReservationEntity retrieveReservationById(Long reservationId) throws ReservationNotFoundException {

        ReservationEntity reservation = em.find(ReservationEntity.class, reservationId);
        if (reservation != null) {
            if (reservation.getRoomEntity() != null) {
                reservation.getRoomEntity();
            }
            if (reservation.getTransactionEntity() != null) {
                reservation.getTransactionEntity();
            }
            return reservation;
        } else {
            throw new ReservationNotFoundException("Reservation Id: " + reservationId + " does not exist");
        }
    }

    @Override
    public ReservationEntity retrieveReservationByPassportNumber(String passportNumber) throws ReservationNotFoundException {

        ReservationEntity reservation = (ReservationEntity) em.createQuery(
                "SELECT r FROM ReservationEntity r WHERE r.passportNumber = :passportNum")
                .setParameter("passportNum", passportNumber)
                .getSingleResult();

        reservation.getRoomEntity();
        reservation.getTransactionEntity();
        return reservation;
    }

    @Override
    public void deleteReservation(ReservationEntity reservationToDelete) throws ReservationNotFoundException {
        ReservationEntity reservation = retrieveReservationById(reservationToDelete.getReservationEntityId());
        if (reservation != null) {
            reservation.setRoomEntity(null);
            reservation.setTransactionEntity(null);

            em.remove(reservation);
        } else {
            throw new ReservationNotFoundException("Reservation Id: " + reservation.getReservationEntityId() + " does not exist");
        }
    }

    @Override
    public void updateReservation(ReservationEntity reservation) throws ReservationNotFoundException, UpdateReservationException, InputDataValidationException {

        if (reservation != null && reservation.getReservationEntityId() != null) {
            Set<ConstraintViolation<ReservationEntity>> constraintViolations = validator.validate(reservation);

            if (constraintViolations.isEmpty()) {

                ReservationEntity reservationToUpdate = retrieveReservationById(reservation.getReservationEntityId());

                if (reservationToUpdate.getReservationEntityId().equals(reservation.getReservationEntityId())) {
                    reservationToUpdate.setReservationStartDate(reservation.getReservationStartDate());
                    reservationToUpdate.setReservationEndDate(reservation.getReservationEndDate());
                    reservationToUpdate.setFirstName(reservation.getFirstName());
                    reservationToUpdate.setLastName(reservation.getLastName());
                    reservationToUpdate.setEmail(reservation.getEmail());
                    reservationToUpdate.setContactNumber(reservation.getContactNumber());
                    reservationToUpdate.setPassportNumber(reservation.getPassportNumber());
                } else {
                    throw new UpdateReservationException("Reservation Id of reservation record does not match the existing record");
                }

            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ReservationNotFoundException("Rservation Id: " + reservation.getReservationEntityId() + " does not exist");
        }
    }

    public HashMap<String, HashMap<String, BigDecimal>> retrieveAvailableRoomTypes(LocalDateTime startDate, LocalDateTime endDate, Integer numRooms) throws InsufficientRoomsAvailableException {

        //GET TOTAL INVENTORY
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomStatusEnum = :inRoomStatus").setParameter("inRoomStatus", RoomStatusEnum.AVAILABLE);
        List<RoomEntity> listOfRoomEntities = query.getResultList();
        HashMap<String, HashMap<String, BigDecimal>> map = new HashMap<>();
        int totalRooms = listOfRoomEntities.size();
        for (RoomEntity room : listOfRoomEntities) {
            if (map.containsKey(room.getRoomTypeEntity().getRoomTypeName())) {
                HashMap<String, BigDecimal> stringToBigDecimalMap = map.get(room.getRoomTypeEntity().getRoomTypeName());
                stringToBigDecimalMap.put("numRoomType", stringToBigDecimalMap.get("numRoomType").add(BigDecimal.ONE));
                map.put(room.getRoomTypeEntity().getRoomTypeName(), stringToBigDecimalMap);
            } else {
                HashMap<String, BigDecimal> stringToBigDecimalMap = new HashMap<>();
                stringToBigDecimalMap.put("numRoomType", BigDecimal.ONE);
                stringToBigDecimalMap.put("bestPrice", calculatePriceOfStay(startDate, endDate, room.getRoomTypeEntity()));
                map.put(room.getRoomTypeEntity().getRoomTypeName(), stringToBigDecimalMap);
            }
        }

        //GET NUMBER OF ROOM USED
        int numRoomsUsed = 0;
        query = em.createQuery("SELECT r FROM ReservationEntity r ");
        List<ReservationEntity> listOfReservationEntities = query.getResultList();
        for (ReservationEntity res : listOfReservationEntities) {
            LocalDateTime resStartDate = res.getReservationStartDate();
            LocalDateTime resEndDate = res.getReservationEndDate();
            if (resStartDate.isAfter(startDate) && resStartDate.isBefore(endDate) || resEndDate.isAfter(startDate) && resEndDate.isBefore(endDate) || resStartDate.isAfter(startDate) && resEndDate.isBefore(endDate)) {
                HashMap<String, BigDecimal> stringToBigDecimalMap = map.get(res.getRoomTypeName());
                BigDecimal newNum = stringToBigDecimalMap.get("numRoomType").subtract(BigDecimal.ONE);
                numRoomsUsed += 1;
                //shouldnt be possible but just in case
                if (newNum.intValue() < 0) {
                    newNum = BigDecimal.ZERO;
                }
                stringToBigDecimalMap.put("numRoomType", newNum);
            }
        }

        if (totalRooms - numRoomsUsed < numRooms) {
            throw new InsufficientRoomsAvailableException();
        }

        return map;
    }

    private BigDecimal calculatePriceOfStay(LocalDateTime startDate, LocalDateTime endDate, RoomTypeEntity roomTypeEntity) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        LocalDateTime currDate = startDate;
        while (!currDate.isEqual(endDate)) {
            Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.roomTypeEntity =: inRoomType AND rr.roomRateTypeEnum =:inRoomRateTypeEnum").setParameter("inRoomType", roomTypeEntity).setParameter("inRoomRateTypeEnum", RoomRateTypeEnum.PUBLISHED);
            List<RoomRateEntity> listOfRoomRateEntities = query.getResultList();
            BigDecimal lowest = BigDecimal.valueOf(99999);
            for (RoomRateEntity roomRate : listOfRoomRateEntities) {

                if ((currDate.isAfter(roomRate.getValidPeriodFrom()) && currDate.isBefore(roomRate.getValidPeriodTo()))
                        || currDate.isEqual(roomRate.getValidPeriodFrom()) || currDate.isEqual(roomRate.getValidPeriodTo())) {
                    if (roomRate.getRatePerNight().compareTo(lowest) < 0) {
                        lowest = roomRate.getRatePerNight();
                    }
                }
            }
            totalPrice.add(lowest);
            currDate.plusDays(1);
        }

        return totalPrice;
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ReservationEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
