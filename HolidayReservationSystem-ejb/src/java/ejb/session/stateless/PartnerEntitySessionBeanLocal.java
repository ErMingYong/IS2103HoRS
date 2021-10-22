/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.PartnerNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@Local
public interface PartnerEntitySessionBeanLocal {

    public Long createNewPartner(PartnerEntity newPartner) throws UnknownPersistenceException;

    public List<PartnerEntity> retrieveAllPartner();

    public PartnerEntity retrievePartnerById(Long partnerId) throws PartnerNotFoundException;

    public void deletePartner(Long partnerId) throws PartnerNotFoundException;

    public void updatePartner(Long oldPartnerId, PartnerEntity newPartner) throws UnknownPersistenceException;
    
}
