/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.TransactionEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.TransactionNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Local
public interface TransactionEntitySessionBeanLocal {

    public Long createNewTransaction(TransactionEntity newTransaction) throws UnknownPersistenceException;

    public List<TransactionEntity> retrieveAllTransaction();

    public TransactionEntity retrieveTransactionById(Long transactionId) throws TransactionNotFoundException;

    public void deleteTransaction(Long transactionId) throws TransactionNotFoundException;

    public void updateTransaction(Long oldTransactionId, TransactionEntity newTransaction) throws UnknownPersistenceException;
    
}
