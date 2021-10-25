/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mingy
 */
@Entity
public class TransactionEntity implements Serializable {



    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime transactionDateTime;
    @DecimalMin("0.00")
    @NotNull
    @Column(nullable = false)
    private BigDecimal transactionAmount;

    @OneToMany(mappedBy = "transactionEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private List<ReservationEntity> reservationEntities;

    public TransactionEntity() {
        this.reservationEntities = new ArrayList<ReservationEntity>();
    }

    public TransactionEntity(LocalDateTime transactionDateTime, BigDecimal transactionAmount) {
        this();
        this.transactionDateTime = transactionDateTime;
        this.transactionAmount = transactionAmount;
    }
    
    
           
    
    
       /**
     * @return the transactionDateTime
     */
    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    /**
     * @param transactionDateTime the transactionDateTime to set
     */
    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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
        hash += (transactionId != null ? transactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the transactionId fields are not set
        if (!(object instanceof TransactionEntity)) {
            return false;
        }
        TransactionEntity other = (TransactionEntity) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TransactionEntity[ id=" + transactionId + " ]";
    }
}
