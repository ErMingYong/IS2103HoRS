/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.GuestEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.GuestNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Local
public interface GuestEntitySessionBeanLocal {

    public Long createNewGuest(GuestEntity newGuest) throws UnknownPersistenceException;

    public List<GuestEntity> retrieveAllGuest();

    public GuestEntity retrieveGuestById(Long guestId) throws GuestNotFoundException;

    public GuestEntity retrieveGuestByUsername(String username) throws GuestNotFoundException;

    public void deleteGuest(Long guestId) throws GuestNotFoundException;

    public void updateGuest(Long oldGuestId, GuestEntity newGuest) throws GuestNotFoundException, UnknownPersistenceException;

    public GuestEntity guestLogin(String guestUsername, String guestPassword) throws GuestNotFoundException, InvalidLoginCredentialException;
    
}
