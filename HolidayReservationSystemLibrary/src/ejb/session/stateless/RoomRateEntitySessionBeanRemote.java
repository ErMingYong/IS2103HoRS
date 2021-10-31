/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.RoomRateEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.InputDataValidationException;
import util.exception.RoomRateNameExistException;
import util.exception.RoomRateNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateRoomRateException;

/**
 *
 * @author mingy
 */
@Remote
public interface RoomRateEntitySessionBeanRemote {

    public Long createNewRoomRate(RoomRateEntity newRoomRate) throws InputDataValidationException, RoomRateNameExistException, UnknownPersistenceException;

    public List<RoomRateEntity> retrieveAllRoomRates();

    public RoomRateEntity retrieveRoomRateById(Long roomRateId) throws RoomRateNotFoundException;

    public RoomRateEntity retrieveRoomRateByName(String roomRateName) throws RoomRateNotFoundException;

    public void deleteRoomRate(Long roomRateId) throws RoomRateNotFoundException;

    public void disableRoomRate(RoomRateEntity roomRateToDisable) throws UnknownPersistenceException;

    public void updateRoomRate(RoomRateEntity roomRate) throws RoomRateNotFoundException, UpdateRoomRateException, InputDataValidationException;

}
