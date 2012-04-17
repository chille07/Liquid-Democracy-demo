
package iaik.chille.ballotsignerclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the iaik.chille.ballotsignerclient package. 
 * <p>An ObjectFactory allows you to programatically 
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

    private final static QName _Login_QNAME = new QName("http://ballotsigner.chille.iaik/", "login");
    private final static QName _GetRejectionSignedResponse_QNAME = new QName("http://ballotsigner.chille.iaik/", "getRejectionSignedResponse");
    private final static QName _RejectResponse_QNAME = new QName("http://ballotsigner.chille.iaik/", "rejectResponse");
    private final static QName _GetVoteSigned_QNAME = new QName("http://ballotsigner.chille.iaik/", "getVoteSigned");
    private final static QName _GetPublicKeyResponse_QNAME = new QName("http://ballotsigner.chille.iaik/", "getPublicKeyResponse");
    private final static QName _GetRejectionSigned_QNAME = new QName("http://ballotsigner.chille.iaik/", "getRejectionSigned");
    private final static QName _GetVoteRejectionList_QNAME = new QName("http://ballotsigner.chille.iaik/", "getVoteRejectionList");
    private final static QName _GetVoteSignedResponse_QNAME = new QName("http://ballotsigner.chille.iaik/", "getVoteSignedResponse");
    private final static QName _GetVoteRejectionListResponse_QNAME = new QName("http://ballotsigner.chille.iaik/", "getVoteRejectionListResponse");
    private final static QName _Reject_QNAME = new QName("http://ballotsigner.chille.iaik/", "reject");
    private final static QName _GetZZ1_QNAME = new QName("http://ballotsigner.chille.iaik/", "getZZ1");
    private final static QName _GetPublicKey_QNAME = new QName("http://ballotsigner.chille.iaik/", "getPublicKey");
    private final static QName _GetZZ1Response_QNAME = new QName("http://ballotsigner.chille.iaik/", "getZZ1Response");
    private final static QName _LoginResponse_QNAME = new QName("http://ballotsigner.chille.iaik/", "loginResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: iaik.chille.ballotsignerclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link RejectResponse }
     * 
     */
    public RejectResponse createRejectResponse() {
        return new RejectResponse();
    }

    /**
     * Create an instance of {@link GetPublicKey }
     * 
     */
    public GetPublicKey createGetPublicKey() {
        return new GetPublicKey();
    }

    /**
     * Create an instance of {@link GetRejectionSigned }
     * 
     */
    public GetRejectionSigned createGetRejectionSigned() {
        return new GetRejectionSigned();
    }

    /**
     * Create an instance of {@link GetZZ1Response }
     * 
     */
    public GetZZ1Response createGetZZ1Response() {
        return new GetZZ1Response();
    }

    /**
     * Create an instance of {@link GetVoteSigned }
     * 
     */
    public GetVoteSigned createGetVoteSigned() {
        return new GetVoteSigned();
    }

    /**
     * Create an instance of {@link GetZZ1 }
     * 
     */
    public GetZZ1 createGetZZ1() {
        return new GetZZ1();
    }

    /**
     * Create an instance of {@link GetRejectionSignedResponse }
     * 
     */
    public GetRejectionSignedResponse createGetRejectionSignedResponse() {
        return new GetRejectionSignedResponse();
    }

    /**
     * Create an instance of {@link Reject }
     * 
     */
    public Reject createReject() {
        return new Reject();
    }

    /**
     * Create an instance of {@link GetPublicKeyResponse }
     * 
     */
    public GetPublicKeyResponse createGetPublicKeyResponse() {
        return new GetPublicKeyResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link GetVoteRejectionListResponse }
     * 
     */
    public GetVoteRejectionListResponse createGetVoteRejectionListResponse() {
        return new GetVoteRejectionListResponse();
    }

    /**
     * Create an instance of {@link GetVoteSignedResponse }
     * 
     */
    public GetVoteSignedResponse createGetVoteSignedResponse() {
        return new GetVoteSignedResponse();
    }

    /**
     * Create an instance of {@link GetVoteRejectionList }
     * 
     */
    public GetVoteRejectionList createGetVoteRejectionList() {
        return new GetVoteRejectionList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRejectionSignedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getRejectionSignedResponse")
    public JAXBElement<GetRejectionSignedResponse> createGetRejectionSignedResponse(GetRejectionSignedResponse value) {
        return new JAXBElement<GetRejectionSignedResponse>(_GetRejectionSignedResponse_QNAME, GetRejectionSignedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RejectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "rejectResponse")
    public JAXBElement<RejectResponse> createRejectResponse(RejectResponse value) {
        return new JAXBElement<RejectResponse>(_RejectResponse_QNAME, RejectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVoteSigned }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getVoteSigned")
    public JAXBElement<GetVoteSigned> createGetVoteSigned(GetVoteSigned value) {
        return new JAXBElement<GetVoteSigned>(_GetVoteSigned_QNAME, GetVoteSigned.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPublicKeyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getPublicKeyResponse")
    public JAXBElement<GetPublicKeyResponse> createGetPublicKeyResponse(GetPublicKeyResponse value) {
        return new JAXBElement<GetPublicKeyResponse>(_GetPublicKeyResponse_QNAME, GetPublicKeyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRejectionSigned }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getRejectionSigned")
    public JAXBElement<GetRejectionSigned> createGetRejectionSigned(GetRejectionSigned value) {
        return new JAXBElement<GetRejectionSigned>(_GetRejectionSigned_QNAME, GetRejectionSigned.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVoteRejectionList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getVoteRejectionList")
    public JAXBElement<GetVoteRejectionList> createGetVoteRejectionList(GetVoteRejectionList value) {
        return new JAXBElement<GetVoteRejectionList>(_GetVoteRejectionList_QNAME, GetVoteRejectionList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVoteSignedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getVoteSignedResponse")
    public JAXBElement<GetVoteSignedResponse> createGetVoteSignedResponse(GetVoteSignedResponse value) {
        return new JAXBElement<GetVoteSignedResponse>(_GetVoteSignedResponse_QNAME, GetVoteSignedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVoteRejectionListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getVoteRejectionListResponse")
    public JAXBElement<GetVoteRejectionListResponse> createGetVoteRejectionListResponse(GetVoteRejectionListResponse value) {
        return new JAXBElement<GetVoteRejectionListResponse>(_GetVoteRejectionListResponse_QNAME, GetVoteRejectionListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "reject")
    public JAXBElement<Reject> createReject(Reject value) {
        return new JAXBElement<Reject>(_Reject_QNAME, Reject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetZZ1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getZZ1")
    public JAXBElement<GetZZ1> createGetZZ1(GetZZ1 value) {
        return new JAXBElement<GetZZ1>(_GetZZ1_QNAME, GetZZ1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPublicKey }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getPublicKey")
    public JAXBElement<GetPublicKey> createGetPublicKey(GetPublicKey value) {
        return new JAXBElement<GetPublicKey>(_GetPublicKey_QNAME, GetPublicKey.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetZZ1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getZZ1Response")
    public JAXBElement<GetZZ1Response> createGetZZ1Response(GetZZ1Response value) {
        return new JAXBElement<GetZZ1Response>(_GetZZ1Response_QNAME, GetZZ1Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

}
