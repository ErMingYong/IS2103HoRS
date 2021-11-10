
package ws.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for partnerEntity complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="partnerEntity"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://ws.session.ejb/}userEntity"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="reservationEntities" type="{http://ws.session.ejb/}reservationEntity" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "partnerEntity", propOrder = {
    "reservationEntities"
})
public class PartnerEntity
    extends UserEntity
{

    @XmlElement(nillable = true)
    protected List<ReservationEntity> reservationEntities;

    /**
     * Gets the value of the reservationEntities property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the reservationEntities property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getReservationEntities().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ReservationEntity }
     * 
     * 
     */
    public List<ReservationEntity> getReservationEntities() {
        if (reservationEntities == null) {
            reservationEntities = new ArrayList<ReservationEntity>();
        }
        return this.reservationEntities;
    }

}
