/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author mingy
 */
@Entity
public class PartnerEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private List<ReservationEntity> reservationEntities;

    public PartnerEntity() {
        this.reservationEntities = new ArrayList<ReservationEntity>();
    }

    public PartnerEntity(List<ReservationEntity> reservationEntities, String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.reservationEntities = reservationEntities;
    }
    

        /**
     * @return the reservationEntities
     */
    public List<ReservationEntity> getReservationEntities() {
        return reservationEntities;
    }

    /**
     * @param reservationEntities the reservationEntities to set
     */
    public void setReservationEntities(List<ReservationEntity> reservationEntities) {
        this.reservationEntities = reservationEntities;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getUserEntityId() != null ? this.getUserEntityId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerEntity)) {
            return false;
        }
        PartnerEntity other = (PartnerEntity) object;
        if ((this.getUserEntityId() == null && other.getUserEntityId() != null) || (this.getUserEntityId() != null && !this.getUserEntityId().equals(other.getUserEntityId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PartnerEntity[ id=" + this.getUserEntityId() + " ]";
    }
    
}
