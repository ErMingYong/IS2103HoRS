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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
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
import util.exception.CreateNewReservationException;
import util.exception.InputDataValidationException;
import util.exception.InsufficientRoomsAvailableException;
import util.exception.ReservationNotFoundException;
import util.exception.RoomRateNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReservationException;

/**
 *
 * @author mingy
 */
@Stateless
public class ReservationEntitySessionBean implements ReservationEntitySessionBeanRemote, ReservationEntitySessionBeanLocal {

    @EJB
    private RoomRateEntitySessionBeanLocal roomRateEntitySessionBeanLocal;
    @Resource
    private EJBContext eJBContext;

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

//    public class Pair<F, S> {
//
//        private F first; //first member of pair
//        private S second; //second member of pair
//
//        public Pair(F first, S second) {
//            this.first = first;
//            this.second = second;
//        }
//
//        public void setFirst(F first) {
//            this.first = first;
//        }
//
//        public void setSecond(S second) {
//            this.second = second;
//        }
//
//        public F getFirst() {
//            return first;
//        }
//
//        public S getSecond() {
//            return second;
//        }
//    }
    @Override
    public Long createNewReservation(ReservationEntity newReservation, List<String> listOfRoomRateNames) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<ReservationEntity>> constraintViolations = validator.validate(newReservation);
        //CHECK INVENTORY FOR THAT ROOMTYPE ONCE MORE BEFORE PERSISTING RESERVATION
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomStatusEnum = :inRoomStatus AND r.roomTypeEntity.roomTypeName = :inRoomTypeEntity").setParameter("inRoomStatus", RoomStatusEnum.AVAILABLE).setParameter("inRoomTypeEntity", newReservation.getRoomTypeName());
        Query queryUnavailable = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomStatusEnum = :inRoomStatus  AND r.roomTypeEntity.roomTypeName = :inRoomTypeEntity").setParameter("inRoomStatus", RoomStatusEnum.UNAVAILABLE).setParameter("inRoomTypeEntity", newReservation.getRoomTypeName());
        List<RoomEntity> listOfRoomEntities = query.getResultList();
        listOfRoomEntities.addAll(queryUnavailable.getResultList());
        int currInventory = listOfRoomEntities.size();
        query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.reservationEndDate > :inDate AND r.roomTypeName = :inRoomTypeName").setParameter("inDate", LocalDateTime.now()).setParameter("inRoomTypeName", newReservation.getRoomTypeName());
        List<ReservationEntity> list = query.getResultList();
        LocalDateTime startDate = newReservation.getReservationStartDate();
        LocalDateTime endDate = newReservation.getReservationEndDate();
        for (ReservationEntity res : list) {
            LocalDateTime resStartDate = res.getReservationStartDate();
            LocalDateTime resEndDate = res.getReservationEndDate();
            if ((resStartDate.isAfter(startDate) && resStartDate.isBefore(endDate)) || (resEndDate.isAfter(startDate) && resEndDate.isBefore(endDate)) || ((resStartDate.isAfter(startDate) || resStartDate.isEqual(startDate)) && (resEndDate.isBefore(endDate)) || resEndDate.isEqual(endDate))) {
                currInventory -= 1;
            }
        }
        if (currInventory == 0) {
            throw new CreateNewReservationException();
        }
        //throw new CreateNewReservationException();
        //
        if (constraintViolations.isEmpty()) {
            try {
                for (String roomRateName : listOfRoomRateNames) {
                    RoomRateEntity roomRate;
                    try {
                        roomRate = roomRateEntitySessionBeanLocal.retrieveRoomRateByName(roomRateName);
                        newReservation.getRoomRateEntities().add(roomRate);
                    } catch (RoomRateNotFoundException ex) {
                        Logger.getLogger(ReservationEntitySessionBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

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
    public void createNewReservations(List<Pair<ReservationEntity, List<String>>> list) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException {
        for (Pair<ReservationEntity, List<String>> pair : list) {
            try {
                createNewReservation(pair.getKey(), pair.getValue());
            } catch (CreateNewReservationException ex) {
                eJBContext.setRollbackOnly();
                throw new CreateNewReservationException();
            }
        }
    }

    @Override
    public List<ReservationEntity> retrieveAllReservations() {
        Query query = em.createQuery("SELECT r FROM ReservationEntity r");

        List<ReservationEntity> listReservations = query.getResultList();
        for (ReservationEntity reservationEntity : listReservations) {
            reservationEntity.getRoomEntity();
            reservationEntity.getRoomRateEntities().size();
        }

        return listReservations;
    }

    @Override
    public ReservationEntity retrieveReservationById(Long reservationId) throws ReservationNotFoundException {

        ReservationEntity reservation = em.find(ReservationEntity.class, reservationId);
        if (reservation != null) {
            if (reservation.getRoomEntity() != null) {
                reservation.getRoomEntity();
                reservation.getRoomRateEntities().size();
            }
            return reservation;
        } else {
            throw new ReservationNotFoundException("Reservation Id: " + reservationId + " does not exist");
        }
    }

    @Override
    public List<ReservationEntity> retrieveReservationByPassportNumber(String passportNumber
    ) {

        Query query = em.createQuery(
                "SELECT r FROM ReservationEntity r WHERE r.passportNumber = :passportNum")
                .setParameter("passportNum", passportNumber);

        List<ReservationEntity> reservations = query.getResultList();

        for (ReservationEntity reservation : reservations) {
            reservation.getRoomEntity();
            reservation.getRoomRateEntities().size();
        }
        return reservations;
    }

    @Override
    public List<ReservationEntity> retrieveAllReservationsWithStartDate(LocalDateTime startDate
    ) {
        Query query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.reservationStartDate = :inDate").setParameter("inDate", startDate);

        List<ReservationEntity> reservations = query.getResultList();

        for (ReservationEntity reservation : reservations) {
            reservation.getRoomEntity();
            reservation.getRoomRateEntities().size();
        }

        return reservations;

    }

    @Override
    public void deleteReservation(ReservationEntity reservationToDelete) throws ReservationNotFoundException {
        ReservationEntity reservation = retrieveReservationById(reservationToDelete.getReservationEntityId());
        if (reservation != null) {
            reservation.setRoomEntity(null);

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

    @Override
    public HashMap<String, HashMap<String, BigDecimal>> retrieveAvailableRoomTypes(LocalDateTime startDate, LocalDateTime endDate,
            Integer numRooms) throws InsufficientRoomsAvailableException {
        System.out.println("Retrieving Room Types");
        //GET TOTAL INVENTORY
        //must take into account unavailable rooms as well, as they may just be unavailable now and not the day that you wan to make the booking
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomStatusEnum = :inRoomStatus").setParameter("inRoomStatus", RoomStatusEnum.AVAILABLE);
        Query queryUnavailable = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomStatusEnum = :inRoomStatus").setParameter("inRoomStatus", RoomStatusEnum.UNAVAILABLE);
        List<RoomEntity> listOfRoomEntities = query.getResultList();
        listOfRoomEntities.addAll(queryUnavailable.getResultList());
        System.out.println("working here");
        HashMap<String, HashMap<String, BigDecimal>> map = new HashMap<>();
        int totalRooms = listOfRoomEntities.size();
        for (RoomEntity room : listOfRoomEntities) {
            System.out.println("here");
            if (map.containsKey(room.getRoomTypeEntity().getRoomTypeName())) {
                HashMap<String, BigDecimal> stringToBigDecimalMap = map.get(room.getRoomTypeEntity().getRoomTypeName());
                stringToBigDecimalMap.put("numRoomType", stringToBigDecimalMap.get("numRoomType").add(BigDecimal.ONE));
                map.put(room.getRoomTypeEntity().getRoomTypeName(), stringToBigDecimalMap);
            } else {
                HashMap<String, BigDecimal> stringToBigDecimalMap = new HashMap<>();
                stringToBigDecimalMap.put("numRoomType", BigDecimal.ONE);
                Pair<List<RoomRateEntity>, BigDecimal> pair = calculatePriceOfStay(startDate, endDate, room.getRoomTypeEntity());

                stringToBigDecimalMap.put("bestPrice", pair.getValue());
                for (RoomRateEntity roomRate : pair.getKey()) {
                    stringToBigDecimalMap.put(roomRate.getRoomRateName(), BigDecimal.ONE);
                    System.out.println("here 2");
                }
                map.put(room.getRoomTypeEntity().getRoomTypeName(), stringToBigDecimalMap);
            }
        }
        System.out.println("done getting initial inventory");
        //GET NUMBER OF ROOM USED
        int numRoomsUsed = 0;
        //COMPUTATION HEAVY how to only get reservations that endDate is already over so i dont have a huge list
        //query only reservations that HAVE NOT PASSED (NOW < ENDDATE)
        query = em.createQuery("SELECT r FROM ReservationEntity r WHERE r.reservationEndDate > :inDate").setParameter("inDate", LocalDateTime.now());
        List<ReservationEntity> listOfReservationEntities = query.getResultList();
        System.out.println("getting whats used");
        for (ReservationEntity res : listOfReservationEntities) {
            LocalDateTime resStartDate = res.getReservationStartDate();
            LocalDateTime resEndDate = res.getReservationEndDate();
            if ((resStartDate.isAfter(startDate) && resStartDate.isBefore(endDate)) || (resEndDate.isAfter(startDate) && resEndDate.isBefore(endDate)) || ((resStartDate.isAfter(startDate) || resStartDate.isEqual(startDate)) && (resEndDate.isBefore(endDate)) || resEndDate.isEqual(endDate))) {
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
        System.out.println("Retrieved Room Types");
        return map;
    }

    private Pair<List<RoomRateEntity>, BigDecimal> calculatePriceOfStay(LocalDateTime startDate, LocalDateTime endDate, RoomTypeEntity roomTypeEntity) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        LocalDateTime currDate = startDate;
        System.out.println("calc here");
        List<RoomRateEntity> list = new ArrayList<>();
        while (currDate.isBefore(endDate)) {
            System.out.println("calc here 2");
            Query query = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.roomTypeEntity.roomTypeId = :inRoomType AND rr.roomRateTypeEnum = :inRoomRateTypeEnum").setParameter("inRoomType", roomTypeEntity.getRoomTypeId()).setParameter("inRoomRateTypeEnum", RoomRateTypeEnum.PUBLISHED);
            List<RoomRateEntity> listOfRoomRateEntities = query.getResultList();
            BigDecimal lowest = BigDecimal.valueOf(99999);
            RoomRateEntity lowestRoomRate = null;
            for (RoomRateEntity roomRate : listOfRoomRateEntities) {
                System.out.println("calc here 3");
                if (((currDate.isAfter(roomRate.getValidPeriodFrom()) && currDate.isBefore(roomRate.getValidPeriodTo()))
                        || currDate.isEqual(roomRate.getValidPeriodFrom()) || currDate.isEqual(roomRate.getValidPeriodTo())) && roomRate.getRoomRateTypeEnum().equals(RoomRateTypeEnum.PUBLISHED)) {
                    if (roomRate.getRatePerNight().compareTo(lowest) < 0) {
                        lowest = roomRate.getRatePerNight();
                        lowestRoomRate = roomRate;
                    }
                }
                totalPrice = totalPrice.add(lowest);
                list.add(lowestRoomRate);
                currDate = currDate.plusDays(1);
            }
        }
        System.out.println("calc 4");
        Pair<List<RoomRateEntity>, BigDecimal> pair = new Pair<>(list, totalPrice);
        return pair;
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ReservationEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
