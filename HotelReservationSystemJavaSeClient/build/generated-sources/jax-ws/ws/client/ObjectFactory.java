
package ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.client package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InvalidLoginCredentialException_QNAME = new QName("http://ws.session.ejb/", "InvalidLoginCredentialException");
    private final static QName _PartnerNotFoundException_QNAME = new QName("http://ws.session.ejb/", "PartnerNotFoundException");
    private final static QName _PartnerLogin_QNAME = new QName("http://ws.session.ejb/", "partnerLogin");
    private final static QName _PartnerLoginResponse_QNAME = new QName("http://ws.session.ejb/", "partnerLoginResponse");
    private final static QName _RetrieveAllPartnerReservations_QNAME = new QName("http://ws.session.ejb/", "retrieveAllPartnerReservations");
    private final static QName _RetrieveAllPartnerReservationsResponse_QNAME = new QName("http://ws.session.ejb/", "retrieveAllPartnerReservationsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvalidLoginCredentialException }
     * 
     */
    public InvalidLoginCredentialException createInvalidLoginCredentialException() {
        return new InvalidLoginCredentialException();
    }

    /**
     * Create an instance of {@link PartnerNotFoundException }
     * 
     */
    public PartnerNotFoundException createPartnerNotFoundException() {
        return new PartnerNotFoundException();
    }

    /**
     * Create an instance of {@link PartnerLogin }
     * 
     */
    public PartnerLogin createPartnerLogin() {
        return new PartnerLogin();
    }

    /**
     * Create an instance of {@link PartnerLoginResponse }
     * 
     */
    public PartnerLoginResponse createPartnerLoginResponse() {
        return new PartnerLoginResponse();
    }

    /**
     * Create an instance of {@link RetrieveAllPartnerReservations }
     * 
     */
    public RetrieveAllPartnerReservations createRetrieveAllPartnerReservations() {
        return new RetrieveAllPartnerReservations();
    }

    /**
     * Create an instance of {@link RetrieveAllPartnerReservationsResponse }
     * 
     */
    public RetrieveAllPartnerReservationsResponse createRetrieveAllPartnerReservationsResponse() {
        return new RetrieveAllPartnerReservationsResponse();
    }

    /**
     * Create an instance of {@link PartnerEntity }
     * 
     */
    public PartnerEntity createPartnerEntity() {
        return new PartnerEntity();
    }

    /**
     * Create an instance of {@link ReservationEntity }
     * 
     */
    public ReservationEntity createReservationEntity() {
        return new ReservationEntity();
    }

    /**
     * Create an instance of {@link LocalDateTime }
     * 
     */
    public LocalDateTime createLocalDateTime() {
        return new LocalDateTime();
    }

    /**
     * Create an instance of {@link RoomEntity }
     * 
     */
    public RoomEntity createRoomEntity() {
        return new RoomEntity();
    }

    /**
     * Create an instance of {@link RoomTypeEntity }
     * 
     */
    public RoomTypeEntity createRoomTypeEntity() {
        return new RoomTypeEntity();
    }

    /**
     * Create an instance of {@link RoomRateEntity }
     * 
     */
    public RoomRateEntity createRoomRateEntity() {
        return new RoomRateEntity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidLoginCredentialException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InvalidLoginCredentialException }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "InvalidLoginCredentialException")
    public JAXBElement<InvalidLoginCredentialException> createInvalidLoginCredentialException(InvalidLoginCredentialException value) {
        return new JAXBElement<InvalidLoginCredentialException>(_InvalidLoginCredentialException_QNAME, InvalidLoginCredentialException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartnerNotFoundException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PartnerNotFoundException }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "PartnerNotFoundException")
    public JAXBElement<PartnerNotFoundException> createPartnerNotFoundException(PartnerNotFoundException value) {
        return new JAXBElement<PartnerNotFoundException>(_PartnerNotFoundException_QNAME, PartnerNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartnerLogin }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PartnerLogin }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "partnerLogin")
    public JAXBElement<PartnerLogin> createPartnerLogin(PartnerLogin value) {
        return new JAXBElement<PartnerLogin>(_PartnerLogin_QNAME, PartnerLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartnerLoginResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PartnerLoginResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "partnerLoginResponse")
    public JAXBElement<PartnerLoginResponse> createPartnerLoginResponse(PartnerLoginResponse value) {
        return new JAXBElement<PartnerLoginResponse>(_PartnerLoginResponse_QNAME, PartnerLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveAllPartnerReservations }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetrieveAllPartnerReservations }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "retrieveAllPartnerReservations")
    public JAXBElement<RetrieveAllPartnerReservations> createRetrieveAllPartnerReservations(RetrieveAllPartnerReservations value) {
        return new JAXBElement<RetrieveAllPartnerReservations>(_RetrieveAllPartnerReservations_QNAME, RetrieveAllPartnerReservations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveAllPartnerReservationsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetrieveAllPartnerReservationsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "retrieveAllPartnerReservationsResponse")
    public JAXBElement<RetrieveAllPartnerReservationsResponse> createRetrieveAllPartnerReservationsResponse(RetrieveAllPartnerReservationsResponse value) {
        return new JAXBElement<RetrieveAllPartnerReservationsResponse>(_RetrieveAllPartnerReservationsResponse_QNAME, RetrieveAllPartnerReservationsResponse.class, null, value);
    }

}
