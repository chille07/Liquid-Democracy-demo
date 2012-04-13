
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
    private final static QName _GetVoteSigned_QNAME = new QName("http://ballotsigner.chille.iaik/", "getVoteSigned");
    private final static QName _GetVoteSignedResponse_QNAME = new QName("http://ballotsigner.chille.iaik/", "getVoteSignedResponse");
    private final static QName _LoginResponse_QNAME = new QName("http://ballotsigner.chille.iaik/", "loginResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: iaik.chille.ballotsignerclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetVoteSigned }
     * 
     */
    public GetVoteSigned createGetVoteSigned() {
        return new GetVoteSigned();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link GetVoteSignedResponse }
     * 
     */
    public GetVoteSignedResponse createGetVoteSignedResponse() {
        return new GetVoteSignedResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVoteSigned }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "getVoteSigned")
    public JAXBElement<GetVoteSigned> createGetVoteSigned(GetVoteSigned value) {
        return new JAXBElement<GetVoteSigned>(_GetVoteSigned_QNAME, GetVoteSigned.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ballotsigner.chille.iaik/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

}
