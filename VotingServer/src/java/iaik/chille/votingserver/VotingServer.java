/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.votingserver;

import iaik.chille.ballotsignerclient.BallotSigner;
import iaik.chille.ballotsignerclient.BallotSigner_Service;
import iaik.chille.security.KeyHelper;
import iaik.chille.security.PropertyHandler;
import iaik.chille.security.XMLHelper;
import iaik.chille.security.XMLSignature;
import java.io.File;
import java.net.URL;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author chille
 */
@WebService(serviceName = "VotingServer")
public class VotingServer
{
  public static String keyFile = "votingserver.key";
  protected static KeyPair _kp = null;
  // notice: same code is in BallotSigner.java


  protected boolean checkSig(Document doc, String electionID)
  {
    try
    {
      BallotSigner bs = this.getPort();
      Key key = KeyHelper.getKeyFromBase64(bs.getPublicKey(electionID),"DSA",true);
      return XMLSignature.validate(doc, null, key);
    }
    catch(Exception ex)
    {
      System.err.println(ex.getMessage());
      //ex.printStackTrace();
      return false;
    }
  }

  protected static KeyPair getKeyPair() throws Exception
  {
    if(_kp==null)
    {
      File file = new File(keyFile);
      if(file.exists())
      {
        KeyHelper.loadKeyStore(keyFile, "pw");
        Key publicKey = KeyHelper.getKey("dsa_public", "pw");
        Key privateKey = KeyHelper.getKey("dsa_private", "pw");
        _kp = new KeyPair((PublicKey)publicKey, (PrivateKey)privateKey);
      }
      else
      {
        KeyHelper.generateKeyStore();
        _kp = KeyHelper.GenerateDSAKey();
        KeyHelper.storeKey("dsa_public", "pw", _kp.getPublic());
        KeyHelper.storeKey("dsa_private", "pw", _kp.getPrivate());
        KeyHelper.saveKeyStore(keyFile,"pw");
      }
    }
    return _kp;
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getVote")
  public String getVote(@WebParam(name = "electionID") String electionID, @WebParam(name = "voteID") String voteID)
  {
    String fn = "./votes/"+electionID+"/"+voteID+".xml";
    try
    {
      // file could be send directly instead of parsing...
      Document doc = XMLHelper.fileToDocument(fn);
      return XMLHelper.documentToString(doc);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return ex.getMessage();
    }
  }


  // TODO: create config file 
  private BallotSigner getPort() throws Exception
  {
    PropertyHandler p = PropertyHandler.getInstance("WEB-INF/connection.properties");
    // following wrong results in "CM4" error: ... is not a valid service
    QName BALLOTSIGNER_QNAME = new QName(p.get("BALLOTSIGNER_QNAME",
            "http://ballotsigner.chille.iaik/"), "BallotSigner");

    // following wrong results in "CM4" error: failed to access the wsdl at: ...
    URL url = new URL(p.get("BALLOTSIGNER_WSDL",
            "http://localhost:8084/BallotSigner/BallotSigner.wsdl"));
    BallotSigner_Service bs_s = new BallotSigner_Service(url, BALLOTSIGNER_QNAME);
    BallotSigner bs = bs_s.getBallotSignerPort();


    // following wrong results in "CM2" error: Webservice is not started or not reachable
    ((BindingProvider)bs).getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
            p.get("BALLOTSIGNER_URL","http://localhost:8084/BallotSigner/BallotSigner"));
    return bs;
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "registerVote")
  public String registerVote(@WebParam(name = "vote") String xml)
  {
    try{
      Document doc = XMLHelper.parseXML(xml);
      Element el = (Element) doc.getElementsByTagName("election").item(0);
      String elid = el.getAttribute("id");

      Element vote = (Element) doc.getElementsByTagName("vote").item(0);
      String voteid = vote.getAttribute("id");

      // TODO: check if elid exists on ElectionServer
      // elid is in collection of elections of ElectionServer
      
      //  check if is signed by BallotSigner
      if(!this.checkSig(doc,elid))
      {
        return "-- Signature check failed --";
      }

      // store the vote --> filesystem, later evtl. to database
      String folder = "./votes/"+elid;
      File f = new File(folder);
      f.mkdirs();
      XMLHelper.documentToFile(doc, folder+"/"+voteid+".xml");

      // give the Client a proof that we accepted the signature
      KeyPair kp = VotingServer.getKeyPair();
      doc = XMLSignature.signate(doc, kp.getPrivate(), kp);
      return XMLHelper.documentToString(doc);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return ex.getMessage();
    }
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "rejectVote")
  public String rejectVote(@WebParam(name = "rejection") String rejection) {
    //TODO: we dont have to implement this, but it could be nice
    // if we could delete invalid votes

    // --> check if signature of rejection Code is valid (by BallotSigner)
    // --> delete the xml code from the file system
    // --> sign the rejection and return it to the client
    return null;
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getVotes")
  public String getVotes(@WebParam(name = "electionID") String electionID) {
    //TODO: find all votes, put them in a single document and return it to the client
    return null;
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getPublicKey")
  public String getPublicKey()
  {
    try
    {
      return KeyHelper.getBase64FromKey(VotingServer.getKeyPair().getPublic());
    }
    catch (Exception ex)
    {
      return "* Error: "+ex.toString();
    }
  }

}
