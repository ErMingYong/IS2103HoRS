/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.RoomRateTypeEnum;

/**
 *
 * @author mingy
 */
@Entity
public class RoomRateEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomRateId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(min = 1, max = 32)
    private String roomRateName;
    @DecimalMin("10.00")
    @DecimalMax("9999.00")
    @NotNull
    @Column(nullable = false)
    private BigDecimal ratePerNight;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime validPeriodFrom;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime validPeriodTo;
    @NotNull
    @Column(nullable = false)
    private Boolean isDisabled;
    @Enumerated
    @NotNull
    @Column(nullable = false)
    private RoomRateTypeEnum roomRateTypeEnum;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RoomTypeEntity roomTypeEntity;

    public RoomRateEntity() {
        this.isDisabled = false;
    }

    public RoomRateEntity(String roomRateName, BigDecimal ratePerNight, LocalDateTime validPeriodFrom, LocalDateTime validPeriodTo, RoomRateTypeEnum roomRateTypeEnum) {
        this();
        this.roomRateName = roomRateName;
        this.ratePerNight = ratePerNight;
        this.validPeriodFrom = validPeriodFrom;
        this.validPeriodTo = validPeriodTo;
        this.roomRateTypeEnum = roomRateTypeEnum;
    }

    /**
     * @return the roomRateName
     */
    public String getRoomRateName() {
        return roomRateName;
    }

    /**
     * @param roomRateName the roomRateName to set
     */
    public void setRoomRateName(String roomRateName) {
        this.roomRateName = roomRateName;
    }

    public BigDecimal getRatePerNight() {
        return ratePerNight;
    }

    public void setRatePerNight(BigDecimal ratePerNight) {
        this.ratePerNight = ratePerNight;
    }

    /**
     * @return the validPeriodFrom
     */
    public LocalDateTime getValidPeriodFrom() {
        return validPeriodFrom;
    }

    /**
     * @param validPeriodFrom the validPeriodFrom to set
     */
    public void setValidPeriodFrom(LocalDateTime validPeriodFrom) {
        this.validPeriodFrom = validPeriodFrom;
    }

    /**
     * @return the validPeriodTo
     */
    public LocalDateTime getValidPeriodTo() {
        return validPeriodTo;
    }

    /**
     * @param validPeriodTo the validPeriodTo to set
     */
    public void setValidPeriodTo(LocalDateTime validPeriodTo) {
        this.validPeriodTo = validPeriodTo;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Long getRoomRateId() {
        return roomRateId;
    }

    public void setRoomRateId(Long roomRateId) {
        this.roomRateId = roomRateId;
    }
    
        /**
     * @return the roomRateTypeEnum
     */
    public RoomRateTypeEnum getRoomRateTypeEnum() {
        return roomRateTypeEnum;
    }

    /**
     * @param roomRateTypeEnum the roomRateTypeEnum to set
     */
    public void setRoomRateTypeEnum(RoomRateTypeEnum roomRateTypeEnum) {
        this.roomRateTypeEnum = roomRateTypeEnum;
    }

    /**
     * @return the roomTypeEntity
     */
    public RoomTypeEntity getRoomTypeEntity() {
        return roomTypeEntity;
    }

    /**
     * @param roomTypeEntity the roomTypeEntity to set
     */
    public void setRoomTypeEntity(RoomTypeEntity roomTypeEntity) {
        this.roomTypeEntity = roomTypeEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomRateId != null ? roomRateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomRateId fields are not set
        if (!(object instanceof RoomRateEntity)) {
            return false;
        }
        RoomRateEntity other = (RoomRateEntity) object;
        if ((this.roomRateId == null && other.roomRateId != null) || (this.roomRateId != null && !this.roomRateId.equals(other.roomRateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomRateEntity[ id=" + roomRateId + " ]";
    }

}
