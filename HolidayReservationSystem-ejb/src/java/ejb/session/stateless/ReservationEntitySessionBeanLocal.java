/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.ReservationNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReservationException;

/**
 *
 * @author mingy
 */
@Local
public interface ReservationEntitySessionBeanLocal {

    public Long createNewReservation(ReservationEntity newReservation) throws UnknownPersistenceException, InputDataValidationException;

    public List<ReservationEntity> retrieveAllReservations();

    public ReservationEntity retrieveReservationById(Long reservationId) throws ReservationNotFoundException;

    public List<ReservationEntity> retrieveReservationByPassportNumber(String passportNumber);

    public void deleteReservation(ReservationEntity reservationToDelete) throws ReservationNotFoundException;

    public void updateReservation(ReservationEntity reservation) throws ReservationNotFoundException, UpdateReservationException, InputDataValidationException;
    
}
