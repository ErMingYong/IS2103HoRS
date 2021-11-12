
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

    private final static QName _CreateNewReservationException_QNAME = new QName("http://ws.session.ejb/", "CreateNewReservationException");
    private final static QName _InputDataValidationException_QNAME = new QName("http://ws.session.ejb/", "InputDataValidationException");
    private final static QName _InsufficientRoomsAvailableException_QNAME = new QName("http://ws.session.ejb/", "InsufficientRoomsAvailableException");
    private final static QName _InvalidDateRangeException_QNAME = new QName("http://ws.session.ejb/", "InvalidDateRangeException");
    private final static QName _InvalidLoginCredentialException_QNAME = new QName("http://ws.session.ejb/", "InvalidLoginCredentialException");
    private final static QName _PartnerNotFoundException_QNAME = new QName("http://ws.session.ejb/", "PartnerNotFoundException");
    private final static QName _UnknownPersistenceException_QNAME = new QName("http://ws.session.ejb/", "UnknownPersistenceException");
    private final static QName _AllocationReportCheckTimerManual_QNAME = new QName("http://ws.session.ejb/", "allocationReportCheckTimerManual");
    private final static QName _AllocationReportCheckTimerManualResponse_QNAME = new QName("http://ws.session.ejb/", "allocationReportCheckTimerManualResponse");
    private final static QName _CreateNewReservationsForPartner_QNAME = new QName("http://ws.session.ejb/", "createNewReservationsForPartner");
    private final static QName _CreateNewReservationsForPartnerResponse_QNAME = new QName("http://ws.session.ejb/", "createNewReservationsForPartnerResponse");
    private final static QName _PartnerLogin_QNAME = new QName("http://ws.session.ejb/", "partnerLogin");
    private final static QName _PartnerLoginResponse_QNAME = new QName("http://ws.session.ejb/", "partnerLoginResponse");
    private final static QName _RetrieveAllPartnerReservations_QNAME = new QName("http://ws.session.ejb/", "retrieveAllPartnerReservations");
    private final static QName _RetrieveAllPartnerReservationsResponse_QNAME = new QName("http://ws.session.ejb/", "retrieveAllPartnerReservationsResponse");
    private final static QName _RetrieveRoomTypeAvailabilities_QNAME = new QName("http://ws.session.ejb/", "retrieveRoomTypeAvailabilities");
    private final static QName _RetrieveRoomTypeAvailabilitiesResponse_QNAME = new QName("http://ws.session.ejb/", "retrieveRoomTypeAvailabilitiesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateNewReservationException }
     * 
     */
    public CreateNewReservationException createCreateNewReservationException() {
        return new CreateNewReservationException();
    }

    /**
     * Create an instance of {@link InputDataValidationException }
     * 
     */
    public InputDataValidationException createInputDataValidationException() {
        return new InputDataValidationException();
    }

    /**
     * Create an instance of {@link InsufficientRoomsAvailableException }
     * 
     */
    public InsufficientRoomsAvailableException createInsufficientRoomsAvailableException() {
        return new InsufficientRoomsAvailableException();
    }

    /**
     * Create an instance of {@link InvalidDateRangeException }
     * 
     */
    public InvalidDateRangeException createInvalidDateRangeException() {
        return new InvalidDateRangeException();
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
     * Create an instance of {@link UnknownPersistenceException }
     * 
     */
    public UnknownPersistenceException createUnknownPersistenceException() {
        return new UnknownPersistenceException();
    }

    /**
     * Create an instance of {@link AllocationReportCheckTimerManual }
     * 
     */
    public AllocationReportCheckTimerManual createAllocationReportCheckTimerManual() {
        return new AllocationReportCheckTimerManual();
    }

    /**
     * Create an instance of {@link AllocationReportCheckTimerManualResponse }
     * 
     */
    public AllocationReportCheckTimerManualResponse createAllocationReportCheckTimerManualResponse() {
        return new AllocationReportCheckTimerManualResponse();
    }

    /**
     * Create an instance of {@link CreateNewReservationsForPartner }
     * 
     */
    public CreateNewReservationsForPartner createCreateNewReservationsForPartner() {
        return new CreateNewReservationsForPartner();
    }

    /**
     * Create an instance of {@link CreateNewReservationsForPartnerResponse }
     * 
     */
    public CreateNewReservationsForPartnerResponse createCreateNewReservationsForPartnerResponse() {
        return new CreateNewReservationsForPartnerResponse();
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
     * Create an instance of {@link RetrieveRoomTypeAvailabilities }
     * 
     */
    public RetrieveRoomTypeAvailabilities createRetrieveRoomTypeAvailabilities() {
        return new RetrieveRoomTypeAvailabilities();
    }

    /**
     * Create an instance of {@link RetrieveRoomTypeAvailabilitiesResponse }
     * 
     */
    public RetrieveRoomTypeAvailabilitiesResponse createRetrieveRoomTypeAvailabilitiesResponse() {
        return new RetrieveRoomTypeAvailabilitiesResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateNewReservationException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateNewReservationException }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "CreateNewReservationException")
    public JAXBElement<CreateNewReservationException> createCreateNewReservationException(CreateNewReservationException value) {
        return new JAXBElement<CreateNewReservationException>(_CreateNewReservationException_QNAME, CreateNewReservationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputDataValidationException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InputDataValidationException }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "InputDataValidationException")
    public JAXBElement<InputDataValidationException> createInputDataValidationException(InputDataValidationException value) {
        return new JAXBElement<InputDataValidationException>(_InputDataValidationException_QNAME, InputDataValidationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsufficientRoomsAvailableException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InsufficientRoomsAvailableException }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "InsufficientRoomsAvailableException")
    public JAXBElement<InsufficientRoomsAvailableException> createInsufficientRoomsAvailableException(InsufficientRoomsAvailableException value) {
        return new JAXBElement<InsufficientRoomsAvailableException>(_InsufficientRoomsAvailableException_QNAME, InsufficientRoomsAvailableException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidDateRangeException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InvalidDateRangeException }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "InvalidDateRangeException")
    public JAXBElement<InvalidDateRangeException> createInvalidDateRangeException(InvalidDateRangeException value) {
        return new JAXBElement<InvalidDateRangeException>(_InvalidDateRangeException_QNAME, InvalidDateRangeException.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UnknownPersistenceException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UnknownPersistenceException }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "UnknownPersistenceException")
    public JAXBElement<UnknownPersistenceException> createUnknownPersistenceException(UnknownPersistenceException value) {
        return new JAXBElement<UnknownPersistenceException>(_UnknownPersistenceException_QNAME, UnknownPersistenceException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllocationReportCheckTimerManual }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AllocationReportCheckTimerManual }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "allocationReportCheckTimerManual")
    public JAXBElement<AllocationReportCheckTimerManual> createAllocationReportCheckTimerManual(AllocationReportCheckTimerManual value) {
        return new JAXBElement<AllocationReportCheckTimerManual>(_AllocationReportCheckTimerManual_QNAME, AllocationReportCheckTimerManual.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllocationReportCheckTimerManualResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AllocationReportCheckTimerManualResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "allocationReportCheckTimerManualResponse")
    public JAXBElement<AllocationReportCheckTimerManualResponse> createAllocationReportCheckTimerManualResponse(AllocationReportCheckTimerManualResponse value) {
        return new JAXBElement<AllocationReportCheckTimerManualResponse>(_AllocationReportCheckTimerManualResponse_QNAME, AllocationReportCheckTimerManualResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateNewReservationsForPartner }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateNewReservationsForPartner }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "createNewReservationsForPartner")
    public JAXBElement<CreateNewReservationsForPartner> createCreateNewReservationsForPartner(CreateNewReservationsForPartner value) {
        return new JAXBElement<CreateNewReservationsForPartner>(_CreateNewReservationsForPartner_QNAME, CreateNewReservationsForPartner.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateNewReservationsForPartnerResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateNewReservationsForPartnerResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "createNewReservationsForPartnerResponse")
    public JAXBElement<CreateNewReservationsForPartnerResponse> createCreateNewReservationsForPartnerResponse(CreateNewReservationsForPartnerResponse value) {
        return new JAXBElement<CreateNewReservationsForPartnerResponse>(_CreateNewReservationsForPartnerResponse_QNAME, CreateNewReservationsForPartnerResponse.class, null, value);
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveRoomTypeAvailabilities }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetrieveRoomTypeAvailabilities }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "retrieveRoomTypeAvailabilities")
    public JAXBElement<RetrieveRoomTypeAvailabilities> createRetrieveRoomTypeAvailabilities(RetrieveRoomTypeAvailabilities value) {
        return new JAXBElement<RetrieveRoomTypeAvailabilities>(_RetrieveRoomTypeAvailabilities_QNAME, RetrieveRoomTypeAvailabilities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveRoomTypeAvailabilitiesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RetrieveRoomTypeAvailabilitiesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "retrieveRoomTypeAvailabilitiesResponse")
    public JAXBElement<RetrieveRoomTypeAvailabilitiesResponse> createRetrieveRoomTypeAvailabilitiesResponse(RetrieveRoomTypeAvailabilitiesResponse value) {
        return new JAXBElement<RetrieveRoomTypeAvailabilitiesResponse>(_RetrieveRoomTypeAvailabilitiesResponse_QNAME, RetrieveRoomTypeAvailabilitiesResponse.class, null, value);
    }

}
