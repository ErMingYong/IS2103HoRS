/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.RoomFloorAndNumberExistException;
import util.exception.RoomNotFoundException;
import util.exception.UnableToDisableRoomException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRoomException;

/**
 *
 * @author mingy
 */
@Local
public interface RoomEntitySessionBeanLocal {

    public Long createNewRoom(RoomEntity newRoomEntity) throws InputDataValidationException, UnknownPersistenceException, RoomFloorAndNumberExistException;

    public List<RoomEntity> retrieveAllRooms() ;

    public RoomEntity retrieveRoomByRoomId(Long roomId) throws RoomNotFoundException;

    public RoomEntity retrieveRoomByRoomFloorAndRoomNumber(Integer roomFloor, Integer roomNumber) throws RoomNotFoundException;

    public void deleteRoom(RoomEntity roomToDelete) throws RoomNotFoundException, UnableToDisableRoomException ;

    public void updateRoom(RoomEntity roomEntity) throws RoomFloorAndNumberExistException, RoomNotFoundException, UpdateRoomException, InputDataValidationException ;
    
}
