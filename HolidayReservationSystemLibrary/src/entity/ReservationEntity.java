/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class ReservationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationEntityId;
    @Future
    @NotNull
    @Column(nullable = false)
    private LocalDateTime reservationDate;
    @NotNull
    @Column(nullable = false)
    @AssertFalse
    private Boolean isCheckedIn;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(min = 1, max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(min = 1, max = 32)
    private String lastName;
    @Email
    @NotNull
    @Column(nullable = false)
    private String email;
    @NotNull
    @Column(nullable = false, length = 8)
    @Size(min = 8, max = 8)
    private String contactNumber;
    @NotNull
    @Column(nullable = false, length = 8)
    @Size(min = 8, max = 8)
    private String passportNumber;

    @OneToOne(mappedBy = "reservationEntity", fetch = FetchType.LAZY, optional = true)
    private RoomEntity roomEntity;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TransactionEntity transactionEntity;

    public ReservationEntity() {
        this.isCheckedIn = false;
    }

    public ReservationEntity(LocalDateTime reservationDate, String firstName, String lastName, String email, String contactNumber, String passportNumber, RoomEntity roomEntity, TransactionEntity transactionEntity) {
        this();
        this.reservationDate = reservationDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.passportNumber = passportNumber;
        this.roomEntity = roomEntity;
        this.transactionEntity = transactionEntity;
    }

    /**
     * @return the reservationEntityId
     */
    public Long getReservationEntityId() {
        return reservationEntityId;
    }

    /**
     * @param reservationEntityId the reservationEntityId to set
     */
    public void setReservationEntityId(Long reservationEntityId) {
        this.reservationEntityId = reservationEntityId;
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
    public Boolean getIsCheckedIn() {
        return isCheckedIn;
    }

    /**
     * @param checkedIn the checkedIn to set
     */
    public void setIsCheckedIn(Boolean isCheckedIn) {
        this.isCheckedIn = isCheckedIn;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservationEntityId != null ? reservationEntityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the resversationEntityId fields are not set
        if (!(object instanceof ReservationEntity)) {
            return false;
        }
        ReservationEntity other = (ReservationEntity) object;
        if ((this.reservationEntityId == null && other.reservationEntityId != null) || (this.reservationEntityId != null && !this.reservationEntityId.equals(other.reservationEntityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ReservationEntity[ id=" + reservationEntityId + " ]";
    }

}
