/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;
import util.exception.PartnerUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePartnerException;

/**
 *
 * @author mingy
 */
@Remote
public interface PartnerEntitySessionBeanRemote {

    public Long createNewPartner(PartnerEntity newPartnerEntity) throws UnknownPersistenceException, PartnerUsernameExistException, InputDataValidationException;

    public List<PartnerEntity> retrieveAllPartners();

    public PartnerEntity retrievePartnerByPartnerId(Long partnerId) throws PartnerNotFoundException;

    public PartnerEntity retrievePartnerByUsername(String username) throws PartnerNotFoundException;

    public void deletePartner(Long partnerId) throws PartnerNotFoundException;

    public void updatePartner(PartnerEntity partnerEntity) throws PartnerNotFoundException, UpdatePartnerException, InputDataValidationException;

    public PartnerEntity partnerLogin(String partnerUsername, String partnerPassword) throws PartnerNotFoundException, InvalidLoginCredentialException;

}
