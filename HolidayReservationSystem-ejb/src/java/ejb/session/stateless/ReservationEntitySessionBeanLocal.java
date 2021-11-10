/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GuestEntity;
import entity.PartnerEntity;
import entity.ReservationEntity;
import entity.RoomTypeEntity;
import entity.UserEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.Local;
import util.exception.CreateNewReservationException;
import util.exception.InputDataValidationException;
import util.exception.InsufficientRoomsAvailableException;
import util.exception.ReservationNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReservationException;

/**
 *
 * @author mingy
 */
@Local
public interface ReservationEntitySessionBeanLocal {

    public Long createNewReservation(ReservationEntity newReservation, List<String> listOfRoomRateNames) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException;

    //public Long createNewReservationForUser(ReservationEntity newReservation, List<String> listOfRoomRateNames, UserEntity user) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException;

    public List<ReservationEntity> retrieveAllReservations();

    public ReservationEntity retrieveReservationById(Long reservationId) throws ReservationNotFoundException;

    public List<ReservationEntity> retrieveReservationByPassportNumber(String passportNumber);

    public void deleteReservation(ReservationEntity reservationToDelete) throws ReservationNotFoundException;

    public void updateReservation(ReservationEntity reservation) throws ReservationNotFoundException, UpdateReservationException, InputDataValidationException;

//    public HashMap<String, HashMap<String, BigDecimal>> retrieveAvailableRoomTypes(LocalDateTime startDate, LocalDateTime endDate, Integer numRooms) throws InsufficientRoomsAvailableException;
    public List<ReservationEntity> retrieveAllReservationsWithStartDate(LocalDateTime startDate);

    public void createNewReservations(List<Pair<ReservationEntity, List<String>>> list) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException;

    public void setReservationToCheckedIn(ReservationEntity reservationEntity);

    public List<ReservationEntity> retrieveReservationByPassportNumberForCheckIn(String passportNumber);

    public List<ReservationEntity> retrieveReservationByPassportNumberForCheckOut(String passportNumber);

    public void setReservationToCheckedOut(ReservationEntity reservationEntity);

//    public HashMap<String, HashMap<String, BigDecimal>> retrieveAvailableRoomTypesOnline(LocalDateTime startDate, LocalDateTime endDate, Integer numRooms) throws InsufficientRoomsAvailableException;
    //public void createNewReservationsForUser(List<Pair<ReservationEntity, List<String>>> list, UserEntity user) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException;

    public HashMap<RoomTypeEntity, HashMap<String, BigDecimal>> retrieveRoomTypeAvailabilities(LocalDateTime startDate, LocalDateTime endDate, Integer numRooms, Boolean isWalkIn) throws InsufficientRoomsAvailableException;

    public List<ReservationEntity> retrieveAllReservationsWithEndDate(LocalDateTime endDate);

    public Long createNewReservationForGuest(ReservationEntity newReservation, List<String> listOfRoomRateNames, GuestEntity guest) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException;

    public void createNewReservationsForGuest(List<Pair<ReservationEntity, List<String>>> list, GuestEntity guest) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException;

    public Long createNewReservationForPartner(ReservationEntity newReservation, List<String> listOfRoomRateNames, PartnerEntity partner) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException;

    public void createNewReservationsForPartner(List<Pair<ReservationEntity, List<String>>> list, PartnerEntity partner) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException;


}
