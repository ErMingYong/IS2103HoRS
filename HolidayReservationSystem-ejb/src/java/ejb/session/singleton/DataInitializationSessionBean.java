/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import ejb.session.stateless.RoomEntitySessionBeanLocal;
import ejb.session.stateless.RoomRateEntitySessionBeanLocal;
import ejb.session.stateless.RoomTypeEntitySessionBeanLocal;
import entity.EmployeeEntity;
import entity.RoomEntity;
import entity.RoomRateEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRightEnum;
import util.enumeration.RoomRateTypeEnum;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.RoomFloorAndNumberExistException;
import util.exception.RoomRateNameExistException;
import util.exception.RoomTypeNameExistException;
import util.exception.RoomTypeNotFoundException;
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
    private RoomRateEntitySessionBeanLocal roomRateEntitySessionBeanLocal;

    @EJB
    private RoomEntitySessionBeanLocal roomEntitySessionBeanLocal;

    @EJB
    private RoomTypeEntitySessionBeanLocal roomTypeEntitySessionBeanLocal;

    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    public DataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            employeeEntitySessionBeanLocal.retrieveEmployeeByUsername("sysadmin");
        } catch (EmployeeNotFoundException ex) {
            initializeData();
        }
    }

    private void initializeData() {
        try {
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("sysadmin", "1", "sysadmin", "password", EmployeeAccessRightEnum.SYSTEM_ADMINISTRATOR));
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("opmanager", "1", "opmanager", "password", EmployeeAccessRightEnum.OPERATION_MANAGER));
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("salesmanager", "1", "salesmanager", "password", EmployeeAccessRightEnum.SALES_MANAGER));
            employeeEntitySessionBeanLocal.createNewEmployee(new EmployeeEntity("guestrelo", "1", "guestrelo", "password", EmployeeAccessRightEnum.GUEST_RELATION_OFFICER));

            roomTypeEntitySessionBeanLocal.createNewRoomType(new RoomTypeEntity("Deluxe Room", "test", "test", "test", 1, "test", 1));
            roomTypeEntitySessionBeanLocal.createNewRoomType(new RoomTypeEntity("Premier Room", "test", "test", "test", 1, "test", 2));
            roomTypeEntitySessionBeanLocal.createNewRoomType(new RoomTypeEntity("Family Room", "test", "test", "test", 1, "test", 3));
            roomTypeEntitySessionBeanLocal.createNewRoomType(new RoomTypeEntity("Junior Suite", "test", "test", "test", 1, "test", 4));
            roomTypeEntitySessionBeanLocal.createNewRoomType(new RoomTypeEntity("Grand Suite", "test", "test", "test", 1, "test", 5));

            RoomEntity e = new RoomEntity(1, 1);
            RoomTypeEntity roomType = roomTypeEntitySessionBeanLocal.retrieveRoomTypeByName("Deluxe Room");
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);

            e = new RoomEntity(2, 1);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);

            e = new RoomEntity(3, 1);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);

            e = new RoomEntity(4, 1);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);

            e = new RoomEntity(5, 1);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            roomType = roomTypeEntitySessionBeanLocal.retrieveRoomTypeByName("Premier Room");
            e = new RoomEntity(1, 2);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(2, 2);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(3, 2);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(4, 2);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(5, 2);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);

            roomType = roomTypeEntitySessionBeanLocal.retrieveRoomTypeByName("Family Room");
            e = new RoomEntity(1, 3);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(2, 3);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(3, 3);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(4, 3);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(5, 3);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);

            roomType = roomTypeEntitySessionBeanLocal.retrieveRoomTypeByName("Junior Suite");
            e = new RoomEntity(1, 4);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(2, 4);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(3, 4);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(4, 4);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(5, 4);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);

            roomType = roomTypeEntitySessionBeanLocal.retrieveRoomTypeByName("Grand Suite");
            e = new RoomEntity(1, 5);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(2, 5);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(3, 5);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(4, 5);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);
            
            e = new RoomEntity(5, 5);
            e.setRoomTypeEntity(roomType);
//            roomType.getRoomEntities().add(e);
            roomEntitySessionBeanLocal.createNewRoom(e);

            RoomRateEntity roomRate = new RoomRateEntity("Deluxe Room Published", "Deluxe Room", BigDecimal.valueOf(100), LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.of(2999, 12, 31), LocalTime.of(0, 0, 0)), RoomRateTypeEnum.PUBLISHED);
            roomType = roomTypeEntitySessionBeanLocal.retrieveRoomTypeByName("Deluxe Room");
            roomType.getRoomRateEntities().add(roomRate);
            roomRate.setRoomTypeEntity(roomType);
            roomRateEntitySessionBeanLocal.createNewRoomRate(roomRate);
            roomRate = new RoomRateEntity("Deluxe Room Normal", "Deluxe Room", BigDecimal.valueOf(50), LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.of(2999, 12, 31), LocalTime.of(0, 0, 0)), RoomRateTypeEnum.NORMAL);
            roomType.getRoomRateEntities().add(roomRate);
            roomRate.setRoomTypeEntity(roomType);
            roomRateEntitySessionBeanLocal.createNewRoomRate(roomRate);

            roomRate = new RoomRateEntity("Premier Room Published", "Premier Room", BigDecimal.valueOf(200), LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.of(2999, 12, 31), LocalTime.of(0, 0, 0)), RoomRateTypeEnum.PUBLISHED);
            roomType = roomTypeEntitySessionBeanLocal.retrieveRoomTypeByName("Premier Room");
            roomType.getRoomRateEntities().add(roomRate);
            roomRate.setRoomTypeEntity(roomType);
            roomRateEntitySessionBeanLocal.createNewRoomRate(roomRate);
            roomRate = new RoomRateEntity("Premier Room Normal", "Premier Room", BigDecimal.valueOf(100), LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.of(2999, 12, 31), LocalTime.of(0, 0, 0)), RoomRateTypeEnum.NORMAL);
            roomType.getRoomRateEntities().add(roomRate);
            roomRate.setRoomTypeEntity(roomType);
            roomRateEntitySessionBeanLocal.createNewRoomRate(roomRate);

            roomRate = new RoomRateEntity("Family Room Published", "Family Room", BigDecimal.valueOf(300),LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.of(2999, 12, 31), LocalTime.of(0, 0, 0)), RoomRateTypeEnum.PUBLISHED);
            roomType = roomTypeEntitySessionBeanLocal.retrieveRoomTypeByName("Family Room");
            roomType.getRoomRateEntities().add(roomRate);
            roomRate.setRoomTypeEntity(roomType);
            roomRateEntitySessionBeanLocal.createNewRoomRate(roomRate);
            roomRate = new RoomRateEntity("Family Room Normal", "Family Room", BigDecimal.valueOf(150), LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.of(2999, 12, 31), LocalTime.of(0, 0, 0)), RoomRateTypeEnum.NORMAL);
            roomType.getRoomRateEntities().add(roomRate);
            roomRate.setRoomTypeEntity(roomType);
            roomRateEntitySessionBeanLocal.createNewRoomRate(roomRate);

            roomRate = new RoomRateEntity("Junior Suite Published", "Junior Suite", BigDecimal.valueOf(400), LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.of(2999, 12, 31), LocalTime.of(0, 0, 0)), RoomRateTypeEnum.PUBLISHED);
            roomType = roomTypeEntitySessionBeanLocal.retrieveRoomTypeByName("Junior Suite");
            roomType.getRoomRateEntities().add(roomRate);
            roomRate.setRoomTypeEntity(roomType);
            roomRateEntitySessionBeanLocal.createNewRoomRate(roomRate);
            roomRate = new RoomRateEntity("Junior Suite Normal", "Junior Suite", BigDecimal.valueOf(200), LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.of(2999, 12, 31), LocalTime.of(0, 0, 0)), RoomRateTypeEnum.NORMAL);
            roomType.getRoomRateEntities().add(roomRate);
            roomRate.setRoomTypeEntity(roomType);
            roomRateEntitySessionBeanLocal.createNewRoomRate(roomRate);

            roomRate = new RoomRateEntity("Grand Suite Published", "Grand Suite", BigDecimal.valueOf(500), LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.of(2999, 12, 31), LocalTime.of(0, 0, 0)), RoomRateTypeEnum.PUBLISHED);
            roomType = roomTypeEntitySessionBeanLocal.retrieveRoomTypeByName("Grand Suite");
            roomType.getRoomRateEntities().add(roomRate);
            roomRate.setRoomTypeEntity(roomType);
            roomRateEntitySessionBeanLocal.createNewRoomRate(roomRate);
            roomRate = new RoomRateEntity("Grand Suite Normal", "Grand Suite", BigDecimal.valueOf(250), LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.of(2999, 12, 31), LocalTime.of(0, 0, 0)), RoomRateTypeEnum.NORMAL);
            roomType.getRoomRateEntities().add(roomRate);
            roomRate.setRoomTypeEntity(roomType);
            roomRateEntitySessionBeanLocal.createNewRoomRate(roomRate);

        } catch (RoomRateNameExistException | RoomTypeNotFoundException | RoomFloorAndNumberExistException | RoomTypeNameExistException | UnknownPersistenceException | InputDataValidationException | EmployeeUsernameExistException ex) {
            ex.printStackTrace();
        }
    }
}
