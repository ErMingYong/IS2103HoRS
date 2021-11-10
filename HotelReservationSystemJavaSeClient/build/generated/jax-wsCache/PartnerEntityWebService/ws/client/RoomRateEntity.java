
package ws.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for roomRateEntity complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="roomRateEntity"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="isDisabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ratePerNight" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomRateId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomRateName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomRateTypeEnum" type="{http://ws.session.ejb/}roomRateTypeEnum" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomTypeEntity" type="{http://ws.session.ejb/}roomTypeEntity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="validPeriodFrom" type="{http://ws.session.ejb/}localDateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="validPeriodTo" type="{http://ws.session.ejb/}localDateTime" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roomRateEntity", propOrder = {
    "isDisabled",
    "ratePerNight",
    "roomRateId",
    "roomRateName",
    "roomRateTypeEnum",
    "roomTypeEntity",
    "roomTypeName",
    "validPeriodFrom",
    "validPeriodTo"
})
public class RoomRateEntity {

    protected Boolean isDisabled;
    protected BigDecimal ratePerNight;
    protected Long roomRateId;
    protected String roomRateName;
    @XmlSchemaType(name = "string")
    protected RoomRateTypeEnum roomRateTypeEnum;
    protected RoomTypeEntity roomTypeEntity;
    protected String roomTypeName;
    protected LocalDateTime validPeriodFrom;
    protected LocalDateTime validPeriodTo;

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
     * Gets the value of the ratePerNight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRatePerNight() {
        return ratePerNight;
    }

    /**
     * Sets the value of the ratePerNight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRatePerNight(BigDecimal value) {
        this.ratePerNight = value;
    }

    /**
     * Gets the value of the roomRateId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRoomRateId() {
        return roomRateId;
    }

    /**
     * Sets the value of the roomRateId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRoomRateId(Long value) {
        this.roomRateId = value;
    }

    /**
     * Gets the value of the roomRateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomRateName() {
        return roomRateName;
    }

    /**
     * Sets the value of the roomRateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomRateName(String value) {
        this.roomRateName = value;
    }

    /**
     * Gets the value of the roomRateTypeEnum property.
     * 
     * @return
     *     possible object is
     *     {@link RoomRateTypeEnum }
     *     
     */
    public RoomRateTypeEnum getRoomRateTypeEnum() {
        return roomRateTypeEnum;
    }

    /**
     * Sets the value of the roomRateTypeEnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomRateTypeEnum }
     *     
     */
    public void setRoomRateTypeEnum(RoomRateTypeEnum value) {
        this.roomRateTypeEnum = value;
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
     * Gets the value of the validPeriodFrom property.
     * 
     * @return
     *     possible object is
     *     {@link LocalDateTime }
     *     
     */
    public LocalDateTime getValidPeriodFrom() {
        return validPeriodFrom;
    }

    /**
     * Sets the value of the validPeriodFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDateTime }
     *     
     */
    public void setValidPeriodFrom(LocalDateTime value) {
        this.validPeriodFrom = value;
    }

    /**
     * Gets the value of the validPeriodTo property.
     * 
     * @return
     *     possible object is
     *     {@link LocalDateTime }
     *     
     */
    public LocalDateTime getValidPeriodTo() {
        return validPeriodTo;
    }

    /**
     * Sets the value of the validPeriodTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDateTime }
     *     
     */
    public void setValidPeriodTo(LocalDateTime value) {
        this.validPeriodTo = value;
    }

}
