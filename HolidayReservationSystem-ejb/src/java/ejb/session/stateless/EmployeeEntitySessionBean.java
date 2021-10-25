/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Stateless
public class EmployeeEntitySessionBean implements EmployeeEntitySessionBeanRemote, EmployeeEntitySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public EmployeeEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewEmployee(EmployeeEntity newEmployee) throws UnknownPersistenceException {
        try {
            em.persist(newEmployee);
            em.flush();

            //uses .getUserEntity() as employee is an inheritance of User Entity
            return newEmployee.getUserEntityId();
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<EmployeeEntity> retrieveAllEmployees() {
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e");

        return query.getResultList();
    }

    @Override
    public EmployeeEntity retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException {
        EmployeeEntity employee = em.find(EmployeeEntity.class, employeeId);

        if (employee != null) {
            //DID NOT DO FETCHING FOR LIST OF RESERVATIONS AS RELATIONSHIP IS STILL UNDER CONSIDERATION
            return employee;
        } else {
            throw new EmployeeNotFoundException("Employee ID " + employeeId + " does not exist");
        }
    }

    @Override
    public EmployeeEntity retrieveEmployeeByUsername(String employeeUsername) throws EmployeeNotFoundException {
        EmployeeEntity employee = em.find(EmployeeEntity.class, employeeUsername);

        if (employee != null) {
            //DID NOT DO FETCHING FOR LIST OF RESERVATIONS AS RELATIONSHIP IS STILL UNDER CONSIDERATION
            return employee;
        } else {
            throw new EmployeeNotFoundException("Employee Username " + employeeUsername + " does not exist");
        }
    }

    @Override
    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException {
        EmployeeEntity employee = em.find(EmployeeEntity.class, employeeId);

        //DID NOT DO REMOVAL OF RELATIONSHIP TO RESERVATIONS AS RELATIONSHIP IS STILL UNDER CONSIDERATION
        if (employee != null) {
            em.remove(employee);
        } else {
            throw new EmployeeNotFoundException("Employee ID " + employeeId + " does not exist");
        }
    }

    @Override
    public void updateEmployee(Long oldEmployeeId, EmployeeEntity newEmployee) throws EmployeeNotFoundException, UnknownPersistenceException {
        try {
            EmployeeEntity oldEmployee = retrieveEmployeeById(oldEmployeeId);
            Long newEmployeeId = createNewEmployee(newEmployee);
            newEmployee.setExceptionReportEntities(oldEmployee.getExceptionReportEntities());

            //DID NOT DO HANDLING OF RELATIONSHIP TO RESERVATIONS AS RELATIONSHIP IS STILL UNNDER CONSIDERATION
            em.remove(oldEmployee);
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        } catch (EmployeeNotFoundException ex) {
            throw new EmployeeNotFoundException("Employee ID " + oldEmployeeId + " does not exist");
        }
    }

    @Override
    public EmployeeEntity employeeLogin(String employeeUsername, String employeePassword) throws EmployeeNotFoundException, InvalidLoginCredentialException {

        try {
            EmployeeEntity employee = retrieveEmployeeByUsername(employeeUsername);

            if (employee != null) {
                if (employee.getPassword().equals(employeePassword)) {
                    return employee;
                } else {
                    throw new InvalidLoginCredentialException("Password do not match");
                }
            } else {
                throw new InvalidLoginCredentialException("Username does not exist");
            }
        } catch (EmployeeNotFoundException ex) {
            throw new EmployeeNotFoundException("Employee Username: " + employeeUsername + " does not exist");
        }
    }
}
