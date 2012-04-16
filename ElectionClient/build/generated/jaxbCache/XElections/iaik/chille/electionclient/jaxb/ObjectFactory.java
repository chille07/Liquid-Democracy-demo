//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.17 at 12:08:34 AM MESZ 
//


package iaik.chille.electionclient.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the iaik.chille.electionclient.jaxb package. 
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

    private final static QName _Url_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "url");
    private final static QName _Question_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "question");
    private final static QName _ValidFrom_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "validFrom");
    private final static QName _Answer_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "answer");
    private final static QName _PrivateKey_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "privateKey");
    private final static QName _Title_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "title");
    private final static QName _Detail_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "detail");
    private final static QName _ValidTo_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "validTo");
    private final static QName _Id_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "id");
    private final static QName _PublicKey_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "publicKey");
    private final static QName _Result_QNAME = new QName("http://chille.iaik.tugraz.at/ElectionServer", "result");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: iaik.chille.electionclient.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Choice }
     * 
     */
    public Choice createChoice() {
        return new Choice();
    }

    /**
     * Create an instance of {@link Election }
     * 
     */
    public Election createElection() {
        return new Election();
    }

    /**
     * Create an instance of {@link Elections }
     * 
     */
    public Elections createElections() {
        return new Elections();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "url")
    public JAXBElement<String> createUrl(String value) {
        return new JAXBElement<String>(_Url_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "question")
    public JAXBElement<String> createQuestion(String value) {
        return new JAXBElement<String>(_Question_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "validFrom")
    public JAXBElement<XMLGregorianCalendar> createValidFrom(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ValidFrom_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "answer")
    public JAXBElement<String> createAnswer(String value) {
        return new JAXBElement<String>(_Answer_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "privateKey")
    public JAXBElement<String> createPrivateKey(String value) {
        return new JAXBElement<String>(_PrivateKey_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "title")
    public JAXBElement<String> createTitle(String value) {
        return new JAXBElement<String>(_Title_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "detail")
    public JAXBElement<String> createDetail(String value) {
        return new JAXBElement<String>(_Detail_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "validTo")
    public JAXBElement<XMLGregorianCalendar> createValidTo(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ValidTo_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "id")
    public JAXBElement<String> createId(String value) {
        return new JAXBElement<String>(_Id_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "publicKey")
    public JAXBElement<String> createPublicKey(String value) {
        return new JAXBElement<String>(_PublicKey_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://chille.iaik.tugraz.at/ElectionServer", name = "result")
    public JAXBElement<Integer> createResult(Integer value) {
        return new JAXBElement<Integer>(_Result_QNAME, Integer.class, null, value);
    }

}
