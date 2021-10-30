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
import util.exception.EmployeeUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateEmployeeException;

/**
 *
 * @author mingy
 */
@Local
public interface EmployeeEntitySessionBeanLocal {

    public Long createNewEmployee(EmployeeEntity newEmployeeEntity) throws InputDataValidationException, EmployeeUsernameExistException,UnknownPersistenceException; 

    public List<EmployeeEntity> retrieveAllEmployees();

    public EmployeeEntity retrieveEmployeeByEmployeeId(Long employeeId) throws EmployeeNotFoundException;

    public EmployeeEntity retrieveEmployeeByUsername(String employeeUsername) throws EmployeeNotFoundException;

    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException;

    public void updateEmployee(EmployeeEntity employeeEntity) throws EmployeeNotFoundException, UpdateEmployeeException, InputDataValidationException ;

    public EmployeeEntity employeeLogin(String employeeUsername, String employeePassword) throws InvalidLoginCredentialException ;
    
}
