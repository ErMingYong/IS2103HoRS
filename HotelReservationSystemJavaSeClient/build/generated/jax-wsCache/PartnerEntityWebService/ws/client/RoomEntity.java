
package ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for roomEntity complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="roomEntity"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="roomFloor" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomStatusEnum" type="{http://ws.session.ejb/}roomStatusEnum" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomTypeEntity" type="{http://ws.session.ejb/}roomTypeEntity" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roomEntity", propOrder = {
    "roomFloor",
    "roomId",
    "roomNumber",
    "roomStatusEnum",
    "roomTypeEntity"
})
public class RoomEntity {

    protected Integer roomFloor;
    protected Long roomId;
    protected Integer roomNumber;
    @XmlSchemaType(name = "string")
    protected RoomStatusEnum roomStatusEnum;
    protected RoomTypeEntity roomTypeEntity;

    /**
     * Gets the value of the roomFloor property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRoomFloor() {
        return roomFloor;
    }

    /**
     * Sets the value of the roomFloor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRoomFloor(Integer value) {
        this.roomFloor = value;
    }

    /**
     * Gets the value of the roomId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRoomId() {
        return roomId;
    }

    /**
     * Sets the value of the roomId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRoomId(Long value) {
        this.roomId = value;
    }

    /**
     * Gets the value of the roomNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the value of the roomNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRoomNumber(Integer value) {
        this.roomNumber = value;
    }

    /**
     * Gets the value of the roomStatusEnum property.
     * 
     * @return
     *     possible object is
     *     {@link RoomStatusEnum }
     *     
     */
    public RoomStatusEnum getRoomStatusEnum() {
        return roomStatusEnum;
    }

    /**
     * Sets the value of the roomStatusEnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomStatusEnum }
     *     
     */
    public void setRoomStatusEnum(RoomStatusEnum value) {
        this.roomStatusEnum = value;
    }

    /**
     * Gets the value of the roomTypeEntity property.
     * 
     * @return
     *     possible object is
     *     {@link RoomTypeEntity }
     *     
     */
    public RoomTypeEntity getRoomTypeEntity() {
        return roomTypeEntity;
    }

    /**
     * Sets the value of the roomTypeEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomTypeEntity }
     *     
     */
    public void setRoomTypeEntity(RoomTypeEntity value) {
        this.roomTypeEntity = value;
    }

}
