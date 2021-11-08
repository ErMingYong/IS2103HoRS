/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import javax.ejb.Local;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Local
public interface AllocationReportSessionBeanLocal {

    public void allocationReportCheckTimer() throws UnknownPersistenceException;

    public void allocationReportCheckTimerManual() throws UnknownPersistenceException;
    
}
