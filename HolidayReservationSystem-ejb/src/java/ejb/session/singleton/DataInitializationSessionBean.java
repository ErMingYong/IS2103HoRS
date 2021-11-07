/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.EmployeeEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Singleton
@LocalBean
@Startup
public class DataInitializationSessionBean {

    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    public DataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            employeeEntitySessionBeanLocal.retrieveEmployeeByUsername("manager");
        } catch (EmployeeNotFoundException ex) {
            initializeData();
        }
    }

    private void initializeData() {
        try {
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("manager", "1", "manager", "password", EmployeeAccessRightEnum.SYSTEM_ADMINISTRATOR));
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("sysadmin", "1", "sysadmin", "password", EmployeeAccessRightEnum.SYSTEM_ADMINISTRATOR));
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("opmanager", "1", "opmanager", "password", EmployeeAccessRightEnum.OPERATION_MANAGER));
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("salesmanager", "1", "salesmanager", "password", EmployeeAccessRightEnum.SALES_MANAGER));
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("guestrelo", "1", "guestrelo", "password", EmployeeAccessRightEnum.GUEST_RELATION_OFFICER));

        } catch (UnknownPersistenceException | InputDataValidationException | EmployeeUsernameExistException ex) {
            ex.printStackTrace();
        }
    }

}
