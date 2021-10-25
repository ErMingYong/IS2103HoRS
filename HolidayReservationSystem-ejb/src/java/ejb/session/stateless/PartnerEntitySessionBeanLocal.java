/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.PartnerNotFoundException;
import util.exception.PartnerUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePartnerException;

/**
 *
 * @author mingy
 */
@Local
public interface PartnerEntitySessionBeanLocal {

    public Long createNewPartner(PartnerEntity newPartnerEntity) throws UnknownPersistenceException, PartnerUsernameExistException, InputDataValidationException;

    public List<PartnerEntity> retrieveAllPartners();

    public PartnerEntity retrievePartnerByPartnerId(Long partnerId) throws PartnerNotFoundException;

    public void deletePartner(Long partnerId) throws PartnerNotFoundException;

    public void updatePartner(PartnerEntity partnerEntity) throws PartnerNotFoundException, UpdatePartnerException, InputDataValidationException;

}
