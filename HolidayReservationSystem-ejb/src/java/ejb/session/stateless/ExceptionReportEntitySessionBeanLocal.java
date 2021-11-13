/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ExceptionReportEntity;
import entity.ReservationEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.ExceptionReportTypeEnum;
import util.exception.ExceptionReportNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.NoExceptionReportFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Local
public interface ExceptionReportEntitySessionBeanLocal {

    public Long createNewExceptionReport(ExceptionReportEntity newExceptionReport) throws UnknownPersistenceException, InputDataValidationException ;

    public List<ExceptionReportEntity> retrieveAllExceptionReport();

    public ExceptionReportEntity retrieveExceptionReportById(Long exceptionReportId) throws ExceptionReportNotFoundException;

    public List<ExceptionReportEntity> retrieveExceptionReportsByTypeAndDate(ExceptionReportTypeEnum exceptionReportTypeEnum, LocalDateTime date);

    public ExceptionReportEntity retrieveExceptionReportByReservation(ReservationEntity res) throws NoExceptionReportFoundException;
    
}
