
package iaik.chille.electionclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the iaik.chille.electionclient package. 
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

    private final static QName _HelloResponse_QNAME = new QName("http://electionprovider.chille.iaik/", "helloResponse");
    private final static QName _GetVersion_QNAME = new QName("http://electionprovider.chille.iaik/", "getVersion");
    private final static QName _Hello_QNAME = new QName("http://electionprovider.chille.iaik/", "hello");
    private final static QName _GetElectionInformationResponse_QNAME = new QName("http://electionprovider.chille.iaik/", "getElectionInformationResponse");
    private final static QName _GetVersionResponse_QNAME = new QName("http://electionprovider.chille.iaik/", "getVersionResponse");
    private final static QName _GetVotingServersResponse_QNAME = new QName("http://electionprovider.chille.iaik/", "getVotingServersResponse");
    private final static QName _GetVotingServers_QNAME = new QName("http://electionprovider.chille.iaik/", "getVotingServers");
    private final static QName _GetElectionInformation_QNAME = new QName("http://electionprovider.chille.iaik/", "getElectionInformation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: iaik.chille.electionclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetVersionResponse }
     * 
     */
    public GetVersionResponse createGetVersionResponse() {
        return new GetVersionResponse();
    }

    /**
     * Create an instance of {@link GetVotingServers }
     * 
     */
    public GetVotingServers createGetVotingServers() {
        return new GetVotingServers();
    }

    /**
     * Create an instance of {@link HelloResponse }
     * 
     */
    public HelloResponse createHelloResponse() {
        return new HelloResponse();
    }

    /**
     * Create an instance of {@link GetVersion }
     * 
     */
    public GetVersion createGetVersion() {
        return new GetVersion();
    }

    /**
     * Create an instance of {@link GetVotingServersResponse }
     * 
     */
    public GetVotingServersResponse createGetVotingServersResponse() {
        return new GetVotingServersResponse();
    }

    /**
     * Create an instance of {@link GetElectionInformation }
     * 
     */
    public GetElectionInformation createGetElectionInformation() {
        return new GetElectionInformation();
    }

    /**
     * Create an instance of {@link Hello }
     * 
     */
    public Hello createHello() {
        return new Hello();
    }

    /**
     * Create an instance of {@link GetElectionInformationResponse }
     * 
     */
    public GetElectionInformationResponse createGetElectionInformationResponse() {
        return new GetElectionInformationResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://electionprovider.chille.iaik/", name = "helloResponse")
    public JAXBElement<HelloResponse> createHelloResponse(HelloResponse value) {
        return new JAXBElement<HelloResponse>(_HelloResponse_QNAME, HelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://electionprovider.chille.iaik/", name = "getVersion")
    public JAXBElement<GetVersion> createGetVersion(GetVersion value) {
        return new JAXBElement<GetVersion>(_GetVersion_QNAME, GetVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://electionprovider.chille.iaik/", name = "hello")
    public JAXBElement<Hello> createHello(Hello value) {
        return new JAXBElement<Hello>(_Hello_QNAME, Hello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetElectionInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://electionprovider.chille.iaik/", name = "getElectionInformationResponse")
    public JAXBElement<GetElectionInformationResponse> createGetElectionInformationResponse(GetElectionInformationResponse value) {
        return new JAXBElement<GetElectionInformationResponse>(_GetElectionInformationResponse_QNAME, GetElectionInformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://electionprovider.chille.iaik/", name = "getVersionResponse")
    public JAXBElement<GetVersionResponse> createGetVersionResponse(GetVersionResponse value) {
        return new JAXBElement<GetVersionResponse>(_GetVersionResponse_QNAME, GetVersionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVotingServersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://electionprovider.chille.iaik/", name = "getVotingServersResponse")
    public JAXBElement<GetVotingServersResponse> createGetVotingServersResponse(GetVotingServersResponse value) {
        return new JAXBElement<GetVotingServersResponse>(_GetVotingServersResponse_QNAME, GetVotingServersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVotingServers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://electionprovider.chille.iaik/", name = "getVotingServers")
    public JAXBElement<GetVotingServers> createGetVotingServers(GetVotingServers value) {
        return new JAXBElement<GetVotingServers>(_GetVotingServers_QNAME, GetVotingServers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetElectionInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://electionprovider.chille.iaik/", name = "getElectionInformation")
    public JAXBElement<GetElectionInformation> createGetElectionInformation(GetElectionInformation value) {
        return new JAXBElement<GetElectionInformation>(_GetElectionInformation_QNAME, GetElectionInformation.class, null, value);
    }

}
