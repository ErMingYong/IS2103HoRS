
package ws.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for reservationEntity complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="reservationEntity"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="contactNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="isCheckedIn" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="passportNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="reservationEndDate" type="{http://ws.session.ejb/}localDateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="reservationEntityId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="reservationPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="reservationStartDate" type="{http://ws.session.ejb/}localDateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomEntity" type="{http://ws.session.ejb/}roomEntity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomRateEntities" type="{http://ws.session.ejb/}roomRateEntity" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="roomTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reservationEntity", propOrder = {
    "contactNumber",
    "email",
    "firstName",
    "isCheckedIn",
    "lastName",
    "passportNumber",
    "reservationEndDate",
    "reservationEntityId",
    "reservationPrice",
    "reservationStartDate",
    "roomEntity",
    "roomRateEntities",
    "roomTypeName"
})
public class ReservationEntity {

    protected String contactNumber;
    protected String email;
    protected String firstName;
    protected Boolean isCheckedIn;
    protected String lastName;
    protected String passportNumber;
    protected LocalDateTime reservationEndDate;
    protected Long reservationEntityId;
    protected BigDecimal reservationPrice;
    protected LocalDateTime reservationStartDate;
    protected RoomEntity roomEntity;
    @XmlElement(nillable = true)
    protected List<RoomRateEntity> roomRateEntities;
    protected String roomTypeName;

    /**
     * Gets the value of the contactNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * Sets the value of the contactNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactNumber(String value) {
        this.contactNumber = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the isCheckedIn property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCheckedIn() {
        return isCheckedIn;
    }

    /**
     * Sets the value of the isCheckedIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCheckedIn(Boolean value) {
        this.isCheckedIn = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the passportNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassportNumber() {
        return passportNumber;
    }

    /**
     * Sets the value of the passportNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportNumber(String value) {
        this.passportNumber = value;
    }

    /**
     * Gets the value of the reservationEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link LocalDateTime }
     *     
     */
    public LocalDateTime getReservationEndDate() {
        return reservationEndDate;
    }

    /**
     * Sets the value of the reservationEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDateTime }
     *     
     */
    public void setReservationEndDate(LocalDateTime value) {
        this.reservationEndDate = value;
    }

    /**
     * Gets the value of the reservationEntityId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReservationEntityId() {
        return reservationEntityId;
    }

    /**
     * Sets the value of the reservationEntityId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReservationEntityId(Long value) {
        this.reservationEntityId = value;
    }

    /**
     * Gets the value of the reservationPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReservationPrice() {
        return reservationPrice;
    }

    /**
     * Sets the value of the reservationPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReservationPrice(BigDecimal value) {
        this.reservationPrice = value;
    }

    /**
     * Gets the value of the reservationStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link LocalDateTime }
     *     
     */
    public LocalDateTime getReservationStartDate() {
        return reservationStartDate;
    }

    /**
     * Sets the value of the reservationStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDateTime }
     *     
     */
    public void setReservationStartDate(LocalDateTime value) {
        this.reservationStartDate = value;
    }

    /**
     * Gets the value of the roomEntity property.
     * 
     * @return
     *     possible object is
     *     {@link RoomEntity }
     *     
     */
    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    /**
     * Sets the value of the roomEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomEntity }
     *     
     */
    public void setRoomEntity(RoomEntity value) {
        this.roomEntity = value;
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

}
