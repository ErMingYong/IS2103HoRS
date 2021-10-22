/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ExceptionReportEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.ExceptionReportNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Stateless
public class ExceptionReportEntitySessionBean implements ExceptionReportEntitySessionBeanRemote, ExceptionReportEntitySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public ExceptionReportEntitySessionBean() {
    }
    
    @Override
    public Long createNewExceptionReport(ExceptionReportEntity newExceptionReport) throws UnknownPersistenceException {
        try {
            em.persist(newExceptionReport);
            em.flush();
            
            return newExceptionReport.getExceptionReportId();
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
    
    @Override
    public List<ExceptionReportEntity> retrieveAllExceptionReport() {
        Query query = em.createQuery("SELECT er FROM ExceptionReportEntity er");
        
        return query.getResultList();
    }
    
    @Override
    public ExceptionReportEntity retrieveExceptionReportById(Long exceptionReportId) throws ExceptionReportNotFoundException {
        ExceptionReportEntity exceptionReport = em.find(ExceptionReportEntity.class, exceptionReportId);
        
        if (exceptionReport != null) {
            exceptionReport.getFirstTypeExceptionReservations();
            exceptionReport.getSecondTypeExceptionReservations();
            
            return exceptionReport;
        } else {
            throw new ExceptionReportNotFoundException("Exception Report ID " + exceptionReportId + " does not exist");
        }
    }
    
    @Override
    public void deleteExceptionReport(Long exceptionReportId) throws ExceptionReportNotFoundException {
        ExceptionReportEntity exceptionReport = em.find(ExceptionReportEntity.class, exceptionReportId);
        
        if (exceptionReport != null) {
            exceptionReport.setFirstTypeExceptionReservations(null);
            exceptionReport.setSecondTypeExceptionReservations(null);
            
            em.remove(exceptionReport);
        } else {
            throw new ExceptionReportNotFoundException("Exception Report IDD " + exceptionReportId + " does not exist");
        }
    }
    
    @Override
    public void updateExceptionReport(Long oldExceptionReportId, ExceptionReportEntity newExceptionReport) throws ExceptionReportNotFoundException, UnknownPersistenceException {
        try {
            ExceptionReportEntity oldExceptionReport = retrieveExceptionReportById(oldExceptionReportId);
            Long newExceptionReportId = createNewExceptionReport(newExceptionReport);
            
            newExceptionReport.setFirstTypeExceptionReservations(oldExceptionReport.getFirstTypeExceptionReservations());
            newExceptionReport.setSecondTypeExceptionReservations(oldExceptionReport.getSecondTypeExceptionReservations());
            em.remove(oldExceptionReport);
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        } catch (ExceptionReportNotFoundException ex) {
            throw new ExceptionReportNotFoundException("Exception Report ID " + oldExceptionReportId + " does not exist");
        }
    }
}
