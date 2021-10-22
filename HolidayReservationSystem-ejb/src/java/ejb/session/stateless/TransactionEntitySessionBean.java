/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import entity.TransactionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.TransactionNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Stateless
public class TransactionEntitySessionBean implements TransactionEntitySessionBeanRemote, TransactionEntitySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public TransactionEntitySessionBean() {
    }

    @Override
    public Long createNewTransaction(TransactionEntity newTransaction) throws UnknownPersistenceException {
        try {
            em.persist(newTransaction);
            em.flush();

            return newTransaction.getTransactionId();
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<TransactionEntity> retrieveAllTransaction() {
        Query query = em.createQuery("SELECT t FROM TransactionEntity t");

        return query.getResultList();
    }

    @Override
    public TransactionEntity retrieveTransactionById(Long transactionId) throws TransactionNotFoundException {

        TransactionEntity transaction = em.find(TransactionEntity.class, transactionId);

        if (transaction != null) {
            transaction.getReservationEntities();
            return transaction;
        } else {
            throw new TransactionNotFoundException("Transaction ID " + transactionId + " does not exist");
        }
    }

    @Override
    public void deleteTransaction(Long transactionId) throws TransactionNotFoundException {

        TransactionEntity transaction = em.find(TransactionEntity.class, transactionId);
        
        if (transaction != null) {
            for (ReservationEntity reservation : transaction.getReservationEntities()) {
                reservation.setTransactionEntity(null);
            }
            
            transaction.getReservationEntities().clear();
            em.remove(transaction);
        } else {
            throw new TransactionNotFoundException("Transaction ID " + transactionId + " does not exist");
        }
    }
    
    @Override
    public void updateTransaction(Long oldTransactionId, TransactionEntity newTransaction) throws UnknownPersistenceException {
        try {
            TransactionEntity oldTransaction = em.find(TransactionEntity.class, oldTransactionId);
            Long newTransactionId = createNewTransaction(newTransaction);
            
            for (ReservationEntity reservation : oldTransaction.getReservationEntities()) {
                reservation.setTransactionEntity(newTransaction);
            }
            
            em.remove(oldTransaction);
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
}
