/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
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
    public Long createNewEmployee(EmployeeEntity newEmployeeEntity) throws InputDataValidationException, EmployeeUsernameExistException, UnknownPersistenceException {
        Set<ConstraintViolation<EmployeeEntity>> constraintViolations = validator.validate(newEmployeeEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newEmployeeEntity);
                em.flush();

                return newEmployeeEntity.getUserEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new EmployeeUsernameExistException(ex.getMessage());
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<EmployeeEntity> retrieveAllEmployees() {
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e");
        List<EmployeeEntity> listOfEmployeeEntities = query.getResultList();
        return listOfEmployeeEntities;
    }

    @Override
    public EmployeeEntity retrieveEmployeeByEmployeeId(Long employeeId) throws EmployeeNotFoundException {
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
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :inUsername").setParameter("inUsername", employeeUsername);

        try {
            return (EmployeeEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EmployeeNotFoundException("Staff Username " + employeeUsername + " does not exist!");
        }
    }

    //UNUSED
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

    //UNUSED
    @Override
    public void updateEmployee(EmployeeEntity employeeEntity) throws EmployeeNotFoundException, UpdateEmployeeException, InputDataValidationException {
        if (employeeEntity != null && employeeEntity.getUserEntityId() != null) {
            Set<ConstraintViolation<EmployeeEntity>> constraintViolations = validator.validate(employeeEntity);

            if (constraintViolations.isEmpty()) {
                EmployeeEntity employeeEntityToUpdate = retrieveEmployeeByEmployeeId(employeeEntity.getUserEntityId());

                if (employeeEntityToUpdate.getUserName().equals(employeeEntity.getUserName())) {
                    employeeEntityToUpdate.setFirstName(employeeEntity.getFirstName());
                    employeeEntityToUpdate.setLastName(employeeEntity.getLastName());
                    employeeEntityToUpdate.setEmployeeAccessRightEnum(employeeEntity.getEmployeeAccessRightEnum());
                    // Username and password are deliberately NOT updated to demonstrate that client is not allowed to update account credential through this business method
                } else {
                    throw new UpdateEmployeeException("Username of employee record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new EmployeeNotFoundException("Employee ID not provided for employee to be updated");
        }
    }

    @Override
    public EmployeeEntity employeeLogin(String employeeUsername, String employeePassword) throws InvalidLoginCredentialException {

        try {
            EmployeeEntity employee = retrieveEmployeeByUsername(employeeUsername);

            if (employee.getPassword().equals(employeePassword)) {
                    return employee;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (EmployeeNotFoundException ex) {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<EmployeeEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
