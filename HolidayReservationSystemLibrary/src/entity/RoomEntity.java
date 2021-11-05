/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import util.enumeration.RoomStatusEnum;

/**
 *
 * @author mingy
 */
@Entity
public class RoomEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    @NotNull
    @Column(nullable = false)
    @Max(99)
    @Min(1)
    private Integer roomFloor;
    @NotNull
    @Column(nullable = false)
    @Max(99)
    @Min(1)
    private Integer roomNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private RoomStatusEnum roomStatusEnum;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RoomTypeEntity roomTypeEntity;

    public RoomEntity() {
        this.roomStatusEnum = RoomStatusEnum.AVAILABLE;
    }

    public RoomEntity(Integer roomFloor, Integer roomNumber, RoomTypeEntity roomTypeEntity) {
        this();
        this.roomFloor = roomFloor;
        this.roomNumber = roomNumber;
        this.roomTypeEntity = roomTypeEntity;
    }

    public Integer getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(Integer roomFloor) {
        this.roomFloor = roomFloor;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomStatusEnum getRoomStatusEnum() {
        return roomStatusEnum;
    }

    public void setRoomStatusEnum(RoomStatusEnum roomStatusEnum) {
        this.roomStatusEnum = roomStatusEnum;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomId fields are not set
        if (!(object instanceof RoomEntity)) {
            return false;
        }
        RoomEntity other = (RoomEntity) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomEntity[ id=" + roomId + " ]";
    }

}
