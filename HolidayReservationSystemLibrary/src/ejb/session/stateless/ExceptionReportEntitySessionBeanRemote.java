/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ExceptionReportEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.ExceptionReportNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Remote
public interface ExceptionReportEntitySessionBeanRemote {

    public Long createNewExceptionReport(ExceptionReportEntity newExceptionReport) throws UnknownPersistenceException;

    public List<ExceptionReportEntity> retrieveAllExceptionReport();

    public ExceptionReportEntity retrieveExceptionReportById(Long exceptionReportId) throws ExceptionReportNotFoundException;

    public void deleteExceptionReport(Long exceptionReportId) throws ExceptionReportNotFoundException;

    public void updateExceptionReport(Long oldExceptionReportId, ExceptionReportEntity newExceptionReport) throws ExceptionReportNotFoundException, UnknownPersistenceException;

}
