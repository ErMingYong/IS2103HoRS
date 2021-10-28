/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReservationEntity;
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

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ReservationEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
