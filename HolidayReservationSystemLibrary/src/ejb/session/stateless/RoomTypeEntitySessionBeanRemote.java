/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.RoomTypeNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Remote
public interface RoomTypeEntitySessionBeanRemote {

    public Long createNewRoomType(RoomTypeEntity newRoomType) throws UnknownPersistenceException;

    public List<RoomTypeEntity> retrieveAllRoomType();

    public RoomTypeEntity retrieveRoomTypeById(Long roomTypeId) throws RoomTypeNotFoundException;

    public void deleteRoomType(Long roomTypeId) throws RoomTypeNotFoundException;

    public void updateRoomType(Long oldRoomTypeId, RoomTypeEntity newRoomType) throws RoomTypeNotFoundException, UnknownPersistenceException;

}
