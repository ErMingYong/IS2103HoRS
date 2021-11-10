
package ws.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for roomTypeEntity complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="roomTypeEntity"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="amenities" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="bed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="isDisabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ranking" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomEntities" type="{http://ws.session.ejb/}roomEntity" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomRateEntities" type="{http://ws.session.ejb/}roomRateEntity" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomTypeId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roomTypeEntity", propOrder = {
    "amenities",
    "bed",
    "capacity",
    "description",
    "isDisabled",
    "ranking",
    "roomEntities",
    "roomRateEntities",
    "roomTypeId",
    "roomTypeName",
    "size"
})
public class RoomTypeEntity {

    protected String amenities;
    protected String bed;
    protected Integer capacity;
    protected String description;
    protected Boolean isDisabled;
    protected Integer ranking;
    @XmlElement(nillable = true)
    protected List<RoomEntity> roomEntities;
    @XmlElement(nillable = true)
    protected List<RoomRateEntity> roomRateEntities;
    protected Long roomTypeId;
    protected String roomTypeName;
    protected String size;

    /**
     * Gets the value of the amenities property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmenities() {
        return amenities;
    }

    /**
     * Sets the value of the amenities property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmenities(String value) {
        this.amenities = value;
    }

    /**
     * Gets the value of the bed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBed() {
        return bed;
    }

    /**
     * Sets the value of the bed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBed(String value) {
        this.bed = value;
    }

    /**
     * Gets the value of the capacity property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCapacity(Integer value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the isDisabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDisabled() {
        return isDisabled;
    }

    /**
     * Sets the value of the isDisabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDisabled(Boolean value) {
        this.isDisabled = value;
    }

    /**
     * Gets the value of the ranking property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRanking() {
        return ranking;
    }

    /**
     * Sets the value of the ranking property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRanking(Integer value) {
        this.ranking = value;
    }

    /**
     * Gets the value of the roomEntities property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the roomEntities property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRoomEntities().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RoomEntity }
     * 
     * 
     */
    public List<RoomEntity> getRoomEntities() {
        if (roomEntities == null) {
            roomEntities = new ArrayList<RoomEntity>();
        }
        return this.roomEntities;
    }

    /**
     * Gets the value of the roomRateEntities property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the roomRateEntities property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRoomRateEntities().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RoomRateEntity }
     * 
     * 
     */
    public List<RoomRateEntity> getRoomRateEntities() {
        if (roomRateEntities == null) {
            roomRateEntities = new ArrayList<RoomRateEntity>();
        }
        return this.roomRateEntities;
    }

    /**
     * Gets the value of the roomTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRoomTypeId() {
        return roomTypeId;
    }

    /**
     * Sets the value of the roomTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRoomTypeId(Long value) {
        this.roomTypeId = value;
    }

    /**
     * Gets the value of the roomTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomTypeName() {
        return roomTypeName;
    }

    /**
     * Sets the value of the roomTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomTypeName(String value) {
        this.roomTypeName = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSize(String value) {
        this.size = value;
    }

}
