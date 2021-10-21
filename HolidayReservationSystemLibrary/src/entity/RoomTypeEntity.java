/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class RoomTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    private String name;
    private String description;
    private String size;
    private String bed;
    private Integer capacity;
    private String amenities;
    private Boolean disabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomTypeEntity")
    @JoinColumn(nullable = false)
    private List<RoomRateEntity> roomRateEntities;

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the bed
     */
    public String getBed() {
        return bed;
    }

    /**
     * @param bed the bed to set
     */
    public void setBed(String bed) {
        this.bed = bed;
    }

    /**
     * @return the capacity
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the amenities
     */
    public String getAmenities() {
        return amenities;
    }

    /**
     * @param amenities the amenities to set
     */
    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    /**
     * @return the disabled
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * @param disabled the disabled to set
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * @return the roomRateEntities
     */
    public List<RoomRateEntity> getRoomRateEntities() {
        return roomRateEntities;
    }

    /**
     * @param roomRateEntities the roomRateEntities to set
     */
    public void setRoomRateEntities(List<RoomRateEntity> roomRateEntities) {
        this.roomRateEntities = roomRateEntities;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomTypeId != null ? roomTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomTypeId fields are not set
        if (!(object instanceof RoomTypeEntity)) {
            return false;
        }
        RoomTypeEntity other = (RoomTypeEntity) object;
        if ((this.roomTypeId == null && other.roomTypeId != null) || (this.roomTypeId != null && !this.roomTypeId.equals(other.roomTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomTypeEntity[ id=" + roomTypeId + " ]";
    }

}
