
package ws.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for roomRateTypeEnum.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="roomRateTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="PUBLISHED"/&amp;gt;
 *     &amp;lt;enumeration value="NORMAL"/&amp;gt;
 *     &amp;lt;enumeration value="PEAK"/&amp;gt;
 *     &amp;lt;enumeration value="PROMOTION"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "roomRateTypeEnum")
@XmlEnum
public enum RoomRateTypeEnum {

    PUBLISHED,
    NORMAL,
    PEAK,
    PROMOTION;

    public String value() {
        return name();
    }

    public static RoomRateTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
