
package ws.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for roomStatusEnum.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="roomStatusEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="AVAILABLE"/&amp;gt;
 *     &amp;lt;enumeration value="UNAVAILABLE"/&amp;gt;
 *     &amp;lt;enumeration value="DISABLED"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "roomStatusEnum")
@XmlEnum
public enum RoomStatusEnum {

    AVAILABLE,
    UNAVAILABLE,
    DISABLED;

    public String value() {
        return name();
    }

    public static RoomStatusEnum fromValue(String v) {
        return valueOf(v);
    }

}
