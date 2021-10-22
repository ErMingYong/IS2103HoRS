/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.RoomNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Remote
public interface RoomEntitySessionBeanRemote {

    public Long createNewRoom(RoomEntity newRoom) throws UnknownPersistenceException;

    public List<RoomEntity> retrieveAllRoom();

    public RoomEntity retrieveRoomById(Long roomId) throws RoomNotFoundException;

    public void deleteRoom(Long roomId) throws RoomNotFoundException;

    public void updateRoom(Long oldRoomId, RoomEntity newRoom) throws UnknownPersistenceException;

}
