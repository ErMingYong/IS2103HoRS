/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Remote
public interface EmployeeEntitySessionBeanRemote {

    public Long createNewEmployee(EmployeeEntity newEmployee) throws UnknownPersistenceException;

    public List<EmployeeEntity> retrieveAllEmployees();

    public EmployeeEntity retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException;

    public EmployeeEntity retrieveEmployeeByUsername(String employeeUsername) throws EmployeeNotFoundException;

    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException;

    public void updateEmployee(Long oldEmployeeId, EmployeeEntity newEmployee) throws EmployeeNotFoundException, UnknownPersistenceException;

    public EmployeeEntity employeeLogin(String employeeUsername, String employeePassword) throws EmployeeNotFoundException, InvalidLoginCredentialException;
    
}