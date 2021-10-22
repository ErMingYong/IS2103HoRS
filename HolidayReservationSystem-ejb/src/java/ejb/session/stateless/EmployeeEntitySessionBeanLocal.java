/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.EmployeeNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Local
public interface EmployeeEntitySessionBeanLocal {

    public Long createNewEmployee(EmployeeEntity newEmployee) throws UnknownPersistenceException;

    public List<EmployeeEntity> retrieveAllEmployees();

    public EmployeeEntity retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException;

    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException;

    public void updateEmployee(Long oldEmployeeId, EmployeeEntity newEmployee) throws EmployeeNotFoundException, UnknownPersistenceException;
    
}
