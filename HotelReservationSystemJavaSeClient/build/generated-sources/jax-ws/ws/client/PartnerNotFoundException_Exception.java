
package ws.client;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.3
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "PartnerNotFoundException", targetNamespace = "http://ws.session.ejb/")
public class PartnerNotFoundException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private PartnerNotFoundException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public PartnerNotFoundException_Exception(String message, PartnerNotFoundException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public PartnerNotFoundException_Exception(String message, PartnerNotFoundException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: ws.client.PartnerNotFoundException
     */
    public PartnerNotFoundException getFaultInfo() {
        return faultInfo;
    }

}