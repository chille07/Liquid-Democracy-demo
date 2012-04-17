/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.security;

import java.security.Key;
import java.security.KeyPair;
import java.security.Provider;
import java.util.Collections;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author chille
 */
public class XMLSignature
{
  

  public static Document signate(
          Document doc,
          Key private_key,
          KeyPair kp
          ) throws Exception
  {
    // http://www.java-tips.org/java-ee-tips/xml-digital-signature-api/using-the-java-xml-digital-signatur-2.html
    String providerName = System.getProperty("jsr105Provider",
    "org.jcp.xml.dsig.internal.dom.XMLDSigRI");

    XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM",
      (Provider) Class.forName(providerName).newInstance());

    TransformParameterSpec transformSpec = null;
    Reference ref = fac.newReference("",
      fac.newDigestMethod(DigestMethod.SHA1, null),
      Collections.singletonList(fac.newTransform(Transform.ENVELOPED, transformSpec)),
      null, null);
    
    C14NMethodParameterSpec spec = null;
    SignedInfo si = fac.newSignedInfo(
      fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS, spec),
      fac.newSignatureMethod(SignatureMethod.DSA_SHA1,  null),
      Collections.singletonList(ref));
            
    KeyInfoFactory kif = fac.getKeyInfoFactory();
    KeyValue kv = kif.newKeyValue(kp.getPublic());
    KeyInfo ki =  kif.newKeyInfo(Collections.singletonList(kv));
    
    //DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    //dbf.setNamespaceAware(true);
    //DocumentBuilder builder = dbf.newDocumentBuilder();


    DOMSignContext dsc = new DOMSignContext(private_key, doc.getDocumentElement());

    javax.xml.crypto.dsig.XMLSignature signature = fac.newXMLSignature(si, ki);
    signature.sign(dsc);

    return doc;
  }


  public static SignedInfo signate2() throws Exception
  {/*
    XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
    DigestMethod digestMethod = fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null);
    C14NMethodParameterSpec spec = null;
    CanonicalizationMethod cm = fac.newCanonicalizationMethod(
        "http://www.w3.org/2001/10/xml-exc-c14n#",spec);
    SignatureMethod sm = fac.newSignatureMethod(
        "http://www.w3.org/2000/09/xmldsig#rsa-sha1",null);

    ArrayList transformList = new ArrayList();
    TransformParameterSpec transformSpec = null;
    Transform envTransform = fac.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#",transformSpec);
    Transform exc14nTransform = fac.newTransform(
        "http://www.w3.org/2000/09/xmldsig#enveloped-signature",transformSpec);
    transformList.add(envTransform);
    transformList.add(exc14nTransform);
    Reference ref = fac.newReference("",digestMethod,transformList,null,null);
    ArrayList refList = new ArrayList();
    refList.add(ref);
    SignedInfo signedInfo =fac.newSignedInfo(cm,sm,refList);
    return signedInfo;
    * */ return null;
    
  }
  

  public static boolean validate(Document doc, Node sig, Key publicKey) throws Exception
  {
    System.out.flush();
    System.err.flush();
    System.err.println("********************************************\n\n\n ");
    Node signature = null;
    if(sig != null)
    {
      if(sig.getNodeName().compareToIgnoreCase("Signature")==0)
        signature = sig;
    }
    if(signature == null)
    {
      NodeList nl = doc.getElementsByTagName("Signature");
      if(nl.getLength()>0)
      {
        signature = nl.item(0);
      }
    }
    if(signature == null)
    {
      System.err.println("No Signature found :-(");
      return false;
    }

    DOMValidateContext validationContext = new DOMValidateContext(publicKey, signature);
    XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
    javax.xml.crypto.dsig.XMLSignature esignature =  signatureFactory.unmarshalXMLSignature(validationContext);
    //validationContext.setIdAttributeNS(signature, "", "myID");// other source
    URIDereferencer deref = null;
    //validationContext.setURIDereferencer(deref);
    //validationContext.setURIDereferencer(new URIResolverImpl());
    boolean validMessage  = esignature.validate(validationContext);

    if(validMessage){
      System.err.println("Signature Validation passed :-)");
    }else{
      System.err.println("Signature Validation Failed :-(");
    }
    return validMessage;
  }
}
