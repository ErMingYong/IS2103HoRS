/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class GuestEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Email
    @NotNull
    @Column(nullable = false)
    private String email;
    @NotNull
    @Column(nullable = false, length = 8)
    @Size(min = 8, max = 8)
    private String contactNumber;
    @NotNull
    @Column(nullable = false, length = 8, unique = true)
    @Size(min = 8, max = 8)
    private String passportNumber;

    @OneToMany(fetch = FetchType.LAZY)
    //@JoinTable(name = "GuestToReservation")
    private List<ReservationEntity> reservationEntities;

    public GuestEntity() {
        this.reservationEntities = new ArrayList<ReservationEntity>();
    }

    public GuestEntity(String email, String contactNumber, String passportNumber, String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.email = email;
        this.contactNumber = contactNumber;
        this.passportNumber = passportNumber;
        this.reservationEntities = new ArrayList<ReservationEntity>();
    }

    @Override
    public String toString() {
        return "entity.GuestEntity[ id=" + this.getUserEntityId() + " ]";
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return the passportNumber
     */
    public String getPassportNumber() {
        return passportNumber;
    }

    /**
     * @param passportNumber the passportNumber to set
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
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
        // TODO: Warning - this method won't work in the case the guestEntityId fields are not set
        if (!(object instanceof GuestEntity)) {
            return false;
        }
        GuestEntity other = (GuestEntity) object;
        if ((this.getUserEntityId() == null && other.getUserEntityId() != null) || (this.getUserEntityId() != null && !this.getUserEntityId().equals(other.getUserEntityId()))) {
            return false;
        }
        return true;
    }

}
