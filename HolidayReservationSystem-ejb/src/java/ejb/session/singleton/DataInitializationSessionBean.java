/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.EmployeeEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Startup
@Singleton
@LocalBean
public class DataInitializationSessionBean {

    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;
    
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct()
    {
        try
        {
            employeeEntitySessionBeanLocal.retrieveEmployeeByUsername("manager");
        }
        catch(EmployeeNotFoundException ex)
        {
            initializeData();
        }
    }
        private void initializeData()
    {
        try
        {
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("manager", "1", "manager", "password"));
        }
        catch(UnknownPersistenceException|InputDataValidationException|EmployeeUsernameExistException ex)
        {
            ex.printStackTrace();
        } 
    }
    
}
