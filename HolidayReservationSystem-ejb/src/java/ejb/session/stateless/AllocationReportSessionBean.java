/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ExceptionReportEntity;
import entity.ReservationEntity;
import entity.RoomEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.ExceptionReportTypeEnum;
import util.enumeration.RoomStatusEnum;
import util.exception.NoExceptionReportFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Stateless
public class AllocationReportSessionBean implements AllocationReportSessionBeanRemote, AllocationReportSessionBeanLocal {

    @EJB
    private ExceptionReportEntitySessionBeanLocal exceptionReportEntitySessionBeanLocal;

    @EJB
    private RoomTypeEntitySessionBeanLocal roomTypeEntitySessionBeanLocal;

    @PersistenceContext
    private EntityManager em;

    @EJB
    private ReservationEntitySessionBeanLocal reservationEntitySessionBeanLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Schedule(hour = "2", dayOfWeek = "*", info = "allocationReportCheckTimer")
    //method creates an Allocation Report that is stored into the database, hence void
    public void allocationReportCheckTimer() throws UnknownPersistenceException {
        LocalDateTime todayDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

        //retrieve of reservations with today as end date and making their rooms available
        List<ReservationEntity> reservationsEndingToday = reservationEntitySessionBeanLocal.retrieveAllReservationsWithEndDate(todayDate);
        if (!reservationsEndingToday.isEmpty()) {
            for (ReservationEntity reservation : reservationsEndingToday) {
                reservation.getRoomEntity().setRoomStatusEnum(RoomStatusEnum.AVAILABLE);
            }
        }

        //retrieve all available rooms
        Query availableRoomQuery = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomStatusEnum = :inRoomStatus").setParameter("inRoomStatus", RoomStatusEnum.AVAILABLE);
        List<RoomEntity> listOfAvailableRoomEntities = availableRoomQuery.getResultList();

        //maps a room type name to the list of RoomEntities that are available
        HashMap<String, List<RoomEntity>> roomMapping = new HashMap<>();
        for (RoomEntity room : listOfAvailableRoomEntities) {
            if (roomMapping.containsKey(room.getRoomTypeEntity().getRoomTypeName())) {
                //if the room type name exists in the roomMapping hash map
                List<RoomEntity> listRooms = roomMapping.remove(room.getRoomTypeEntity().getRoomTypeName());
                listRooms.add(room);
                roomMapping.put(room.getRoomTypeEntity().getRoomTypeName(), listRooms);
            } else {
                //if the room type name does not exist in the roomMapping hash map
                List<RoomEntity> list = new ArrayList<>();
                list.add(room);
                roomMapping.put(room.getRoomTypeEntity().getRoomTypeName(), list);
            }
        }

        //list of reservations that has to be assigned today
        List<ReservationEntity> reservations = reservationEntitySessionBeanLocal.retrieveAllReservationsWithStartDate(todayDate);
        //check if there is any reservations in the first place, if dont have theres no need to do any allocation
        if (!reservations.isEmpty()) {
            //assigning of rooms to reservations based on a 1-1 direct pairing to the requested rooms
            List<ReservationEntity> listOfReservationsUnallocated = new ArrayList<>();
            for (ReservationEntity reservation : reservations) {
                if (roomMapping.containsKey(reservation.getRoomTypeName()) && (roomMapping.get(reservation.getRoomTypeName()).size() > 0)) {
                    //is wanted roomType is available
                    List<RoomEntity> listRooms = roomMapping.remove(reservation.getRoomTypeName());
                    reservation.setRoomEntity(listRooms.remove(0));
                    roomMapping.put(reservation.getRoomTypeName(), listRooms);
                } else {
                    listOfReservationsUnallocated.add(reservation);
                }
                //else wanted roomType is unavailable, leave the reservation back in list of reservations
            }

            //create helper methods in session bean to facilitate the determining of rankings
            HashMap<String, Integer> roomRankingsByName = roomTypeEntitySessionBeanLocal.retrieveRoomTypeRankingsSortedByName();
            HashMap<Integer, String> roomRankingsByRanking = roomTypeEntitySessionBeanLocal.retrieveRoomTypeRankingsSortedByRanking();

            //assigning reservations to the next higher room type
            List<ReservationEntity> listOfReservationsUnallocatedSecondType = new ArrayList<>();
            for (ReservationEntity reservation : listOfReservationsUnallocated) {
                String requestRoomTypeName = reservation.getRoomTypeName();
                if (roomMapping.containsKey(requestRoomTypeName)) {
                    Integer requestRoomTypeRanking = roomRankingsByName.get(requestRoomTypeName);
                    String nextHigherRoomType = roomRankingsByRanking.get(requestRoomTypeRanking + 1);
                    if (roomMapping.containsKey(nextHigherRoomType) && (roomMapping.get(nextHigherRoomType).size() > 0)) {
                        //next higher ranking room is available, and assigned to the reservation
                        List<RoomEntity> listRooms = roomMapping.remove(nextHigherRoomType);
                        reservation.setRoomEntity(listRooms.remove(0));
                        roomMapping.put(nextHigherRoomType, listRooms);

                        //creation of 1 first type exception report to 1 reservation, since next higher ranking room is allocated
                        ExceptionReportEntity firstTypeException = new ExceptionReportEntity();
                        firstTypeException.setExceptionReportTypeEnum(ExceptionReportTypeEnum.FIRST_TYPE);
                        firstTypeException.setGenerationDate(todayDate);
                        firstTypeException.setReservationEntity(reservation);
                        //lazy catch, will just throw the exception to the next method/client
                        exceptionReportEntitySessionBeanLocal.createNewExceptionReport(firstTypeException);
                    } else {
                        listOfReservationsUnallocatedSecondType.add(reservation);
                    }
                    //if wanted roomType is unavailable, leave the reservation in list of reservations
                }
            }
            for (ReservationEntity reservation : listOfReservationsUnallocatedSecondType) {
                //creation of 1 second type exception report to 1 reservation, since next higher ranking room is not allocated
                ExceptionReportEntity secondTypeException = new ExceptionReportEntity();
                secondTypeException.setExceptionReportTypeEnum(ExceptionReportTypeEnum.SECOND_TYPE);
                secondTypeException.setGenerationDate(todayDate);
                secondTypeException.setReservationEntity(reservation);
                //lazy catch, will just throw the exception to the next method/client
                exceptionReportEntitySessionBeanLocal.createNewExceptionReport(secondTypeException);
            }
        }
    }

    @Override
    public void allocationReportCheckTimerManual() throws UnknownPersistenceException {
        System.out.println("MANUAL ALLOCATION CALLED");
        LocalDateTime todayDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

        //retrieve of reservations with today as end date and making their rooms available
        List<ReservationEntity> reservationsEndingToday = reservationEntitySessionBeanLocal.retrieveAllReservationsWithEndDate(todayDate);
        if (!reservationsEndingToday.isEmpty()) {
            for (ReservationEntity reservation : reservationsEndingToday) {
                reservation.getRoomEntity().setRoomStatusEnum(RoomStatusEnum.AVAILABLE);
            }
        }

        //retrieve all available rooms
        Query availableRoomQuery = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomStatusEnum = :inRoomStatus").setParameter("inRoomStatus", RoomStatusEnum.AVAILABLE);
        List<RoomEntity> listOfAvailableRoomEntities = availableRoomQuery.getResultList();

        //maps a room type name to the list of RoomEntities that are available
        HashMap<String, List<RoomEntity>> roomMapping = new HashMap<>();
        for (RoomEntity room : listOfAvailableRoomEntities) {
            if (roomMapping.containsKey(room.getRoomTypeEntity().getRoomTypeName())) {
                //if the room type name exists in the roomMapping hash map
                List<RoomEntity> listRooms = roomMapping.remove(room.getRoomTypeEntity().getRoomTypeName());
                listRooms.add(room);
                roomMapping.put(room.getRoomTypeEntity().getRoomTypeName(), listRooms);
            } else {
                //if the room type name does not exist in the roomMapping hash map
                List<RoomEntity> list = new ArrayList<>();
                list.add(room);
                roomMapping.put(room.getRoomTypeEntity().getRoomTypeName(), list);
            }
        }

        //list of reservations that has to be assigned today
        List<ReservationEntity> reservationsList = reservationEntitySessionBeanLocal.retrieveAllReservationsWithStartDate(todayDate);

        //check if there is any reservations in the first place, if dont have theres no need to do any allocation
        if (!reservationsList.isEmpty()) {
            //check if reservation already has an exception report generated about it, or have a room allcoated alr, if have can skip
            List<ReservationEntity> reservations = new ArrayList<>();
            for (ReservationEntity res : reservationsList) {
                try {
                    exceptionReportEntitySessionBeanLocal.retrieveExceptionReportByReservation(res);
                } catch (NoExceptionReportFoundException ex) {
                    if (res.getRoomEntity() == null) {
                        reservations.add(res);
                    }
                }

            }

            //assigning of rooms to reservations based on a 1-1 direct pairing to the requested rooms
            List<ReservationEntity> listOfReservationsUnallocated = new ArrayList<>();
            for (ReservationEntity reservation : reservations) {
                if (roomMapping.containsKey(reservation.getRoomTypeName()) && (roomMapping.get(reservation.getRoomTypeName()).size() > 0)) {
                    //is wanted roomType is available
                    List<RoomEntity> listRooms = roomMapping.remove(reservation.getRoomTypeName());
                    reservation.setRoomEntity(listRooms.remove(0));
                    roomMapping.put(reservation.getRoomTypeName(), listRooms);
                } else {
                    listOfReservationsUnallocated.add(reservation);
                }
                //else wanted roomType is unavailable, leave the reservation back in list of reservations
            }

            //create helper methods in session bean to facilitate the determining of rankings
            HashMap<String, Integer> roomRankingsByName = roomTypeEntitySessionBeanLocal.retrieveRoomTypeRankingsSortedByName();
            HashMap<Integer, String> roomRankingsByRanking = roomTypeEntitySessionBeanLocal.retrieveRoomTypeRankingsSortedByRanking();

            //assigning reservations to the next higher room type
            List<ReservationEntity> listOfReservationsUnallocatedSecondType = new ArrayList<>();
            for (ReservationEntity reservation : listOfReservationsUnallocated) {
                String requestRoomTypeName = reservation.getRoomTypeName();
                if (roomMapping.containsKey(requestRoomTypeName)) {
                    Integer requestRoomTypeRanking = roomRankingsByName.get(requestRoomTypeName);
                    String nextHigherRoomType = roomRankingsByRanking.get(requestRoomTypeRanking + 1);
                    if (roomMapping.containsKey(nextHigherRoomType) && (roomMapping.get(nextHigherRoomType).size() > 0)) {
                        //next higher ranking room is available, and assigned to the reservation
                        List<RoomEntity> listRooms = roomMapping.remove(nextHigherRoomType);
                        reservation.setRoomEntity(listRooms.remove(0));
                        roomMapping.put(nextHigherRoomType, listRooms);

                        //creation of 1 first type exception report to 1 reservation, since next higher ranking room is allocated
                        ExceptionReportEntity firstTypeException = new ExceptionReportEntity();
                        firstTypeException.setExceptionReportTypeEnum(ExceptionReportTypeEnum.FIRST_TYPE);
                        firstTypeException.setGenerationDate(todayDate);
                        firstTypeException.setReservationEntity(reservation);
                        //lazy catch, will just throw the exception to the next method/client
                        exceptionReportEntitySessionBeanLocal.createNewExceptionReport(firstTypeException);
                    } else {
                        listOfReservationsUnallocatedSecondType.add(reservation);
                    }
                    //if wanted roomType is unavailable, leave the reservation in list of reservations
                }
            }
            for (ReservationEntity reservation : listOfReservationsUnallocatedSecondType) {
                //creation of 1 second type exception report to 1 reservation, since next higher ranking room is not allocated
                ExceptionReportEntity secondTypeException = new ExceptionReportEntity();
                secondTypeException.setExceptionReportTypeEnum(ExceptionReportTypeEnum.SECOND_TYPE);
                secondTypeException.setGenerationDate(todayDate);
                secondTypeException.setReservationEntity(reservation);
                //lazy catch, will just throw the exception to the next method/client
                exceptionReportEntitySessionBeanLocal.createNewExceptionReport(secondTypeException);
            }
        }
    }

    @Override
    public RoomEntity manualAllocationOfRoomToReservation(String roomTypeName, ReservationEntity reservationEntity) {
        ReservationEntity reservation = em.find(ReservationEntity.class, reservationEntity.getReservationEntityId());
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomStatusEnum = :inRoomStatus AND r.roomTypeEntity.roomTypeName = :inRoomTypeName")
                .setParameter("inRoomStatus", RoomStatusEnum.AVAILABLE)
                .setParameter("inRoomTypeName", roomTypeName);
        RoomEntity roomEntity = (RoomEntity) query.getSingleResult();
        reservation.setRoomEntity(roomEntity);
        reservation.setRoomTypeName(roomTypeName);
        reservation.setIsCheckedIn(true);
        roomEntity.setRoomStatusEnum(RoomStatusEnum.UNAVAILABLE);
        return roomEntity;
    }
}
