/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class ReservationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resversationEntityId;
    private LocalDateTime reservationDate;
    private Boolean checkedIn;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String passportNumber;
    
    @OneToOne(mappedBy = "reservationEntity", fetch = FetchType.LAZY)
    private RoomEntity roomEntity;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TransactionEntity transactionEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    private GuestEntity guestEntity;

    public Long getResversationEntityId() {
        return resversationEntityId;
    }

    public void setResversationEntityId(Long resversationEntityId) {
        this.resversationEntityId = resversationEntityId;
    }
    
    /**
     * @return the reservationDate
     */
    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    /**
     * @param reservationDate the reservationDate to set
     */
    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }


    /**
     * @return the checkedIn
     */
    public Boolean getCheckedIn() {
        return checkedIn;
    }

    /**
     * @param checkedIn the checkedIn to set
     */
    public void setCheckedIn(Boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * @return the roomEntity
     */
    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    /**
     * @param roomEntity the roomEntity to set
     */
    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    /**
     * @return the transactionEntity
     */
    public TransactionEntity getTransactionEntity() {
        return transactionEntity;
    }

    /**
     * @param transactionEntity the transactionEntity to set
     */
    public void setTransactionEntity(TransactionEntity transactionEntity) {
        this.transactionEntity = transactionEntity;
    }

    /**
     * @return the guestEntity
     */
    public GuestEntity getGuestEntity() {
        return guestEntity;
    }

    /**
     * @param guestEntity the guestEntity to set
     */
    public void setGuestEntity(GuestEntity guestEntity) {
        this.guestEntity = guestEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resversationEntityId != null ? resversationEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the resversationEntityId fields are not set
        if (!(object instanceof ReservationEntity)) {
            return false;
        }
        ReservationEntity other = (ReservationEntity) object;
        if ((this.resversationEntityId == null && other.resversationEntityId != null) || (this.resversationEntityId != null && !this.resversationEntityId.equals(other.resversationEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ReservationEntity[ id=" + resversationEntityId + " ]";
    }
    
}
