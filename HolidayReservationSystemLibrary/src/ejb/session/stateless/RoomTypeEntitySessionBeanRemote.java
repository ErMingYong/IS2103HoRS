/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.InputDataValidationException;
import util.exception.RoomTypeNameExistException;
import util.exception.RoomTypeNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRoomTypeException;

/**
 *
 * @author mingy
 */
@Remote
public interface RoomTypeEntitySessionBeanRemote {

    public Long createNewRoomType(RoomTypeEntity newRoomTypeEntity) throws InputDataValidationException, RoomTypeNameExistException, UnknownPersistenceException;

    public List<RoomTypeEntity> retrieveAllRoomTypes();

    public RoomTypeEntity retrieveRoomTypeByRoomTypeId(Long roomTypeId) throws RoomTypeNotFoundException;

    public RoomTypeEntity retrieveRoomTypeByName(String roomTypeName) throws RoomTypeNotFoundException;

    public void deleteRoomType(Long roomTypeId) throws RoomTypeNotFoundException;

    public void updateRoomType(RoomTypeEntity roomTypeEntity) throws RoomTypeNotFoundException, InputDataValidationException, RoomTypeNameExistException;

    public void disableRoomType(RoomTypeEntity roomTypeToDisable) throws UnknownPersistenceException;
}
