package iaik.chille.ballotsigner;

import iaik.chille.security.KeyHelper;
import iaik.chille.security.XMLHelper;
import iaik.chille.security.XMLSignature;
import java.io.File;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author chille
 */
@WebService(serviceName = "BallotSigner")
public class BallotSigner
{
  protected String id = null;
  protected String zz1 = "";
  protected String rejectionCode = "";
  
  public static String keyFile = "ballotsigner3.key";
  // notice: same code is in VotingServer.java
  protected static KeyPair _kp = null;
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
  @WebMethod(operationName = "login")
  public String login(@WebParam(name = "id") String id, @WebParam(name = "something") String something)
  {
    if(something.compareTo("xyz")==0) // TODO: dummy login check
      // check via handysignature or citizen card
    {
      this.id = id;
      return "OK";
    }
    else
    {
      this.id = null;
      return "Login Failed. Use any ID and 'xyz' for something";
    }
  }

  /**
   * Web service operation
   * vote should be an xml code
   */
  @WebMethod(operationName = "getVoteSigned")
  public String getVoteSigned(@WebParam(name = "vote") String xml)
  {
    if(id != null)
    {
      try // TODO: prevent errors by checking ranges before accessing item(0).
      {
        // format: <vote id=".."><election id=".."><choice id=".."/></election></vote>
        Document doc = XMLHelper.parseXML(xml);
        Element vote = (Element) doc.getElementsByTagName("vote").item(0);
        Element election = (Element) vote.getElementsByTagName("election").item(0);
        // note: we cannot access the encrypted vote here.

        // TODO: check if electionid is valid
        if(election.getAttribute("id").compareTo("ABC")==0) // dummy operation
        {
          return "* Vote rejected: Election is not valid at this time.";
        }
        // TODO: user has already voted?
        if("blubb".equals(id))  // dummy operation
        {
          return "* Vote rejected: User has already voted.";
        }

        if(!vote.getAttribute("id").startsWith(zz1))
        {
          zz1 = "";
          return "* Vote rejected: ID of Vote does not match.";
        }
        if(vote.getAttribute("id").length()< zz1.length()*2)
        {
          zz1 = "";
          return "* Vote rejected: ID of Ballot is too short.";
        }
        zz1 = "";

        // do the signature
        KeyPair kp = BallotSigner.getKeyPair();
        Document sig_doc = XMLSignature.signate(doc, kp.getPrivate(), kp);
        String returnvalue = XMLHelper.documentToString(sig_doc);

        // also generate rejection code
        vote.removeChild(vote.getElementsByTagName("Signature").item(0));
        election.removeChild(election.getFirstChild());
        doc.renameNode(vote,"","rejection");
        sig_doc = XMLSignature.signate(doc, kp.getPrivate(), kp);
        rejectionCode = XMLHelper.documentToString(sig_doc);

        // return the signed document
        return returnvalue;
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
        Logger.getLogger(BallotSigner.class.getName()).log(Level.SEVERE, null, ex);
        return "* Exception: "+ex.toString();
      }
    }
    else
    {
      return "* Error: not logged in";
    }
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getVoteRejectionList")
  public String getVoteRejectionList(@WebParam(name = "electionid") String electionid)
  {
    try
    {
      Document doc = XMLHelper.generateDocument();
      Element rejectionList = doc.createElement("rejectionlist");
      rejectionList.setAttribute("electionid", electionid);
      doc.appendChild(rejectionList);

      // TODO: implement
      {
        Element rejection = doc.createElement("rejection");
        rejection.setAttribute("id","TODO");
        rejectionList.appendChild(rejection);
      }

      KeyPair kp = KeyHelper.GenerateDSAKey(); // TODO: load key
      doc = XMLSignature.signate(doc, kp.getPrivate(), kp);

      return XMLHelper.documentToString(doc);
    } catch (Exception ex)
    {
      ex.printStackTrace();
      return "* Error: "+ex.toString();
    }
  }

  

  /**
   * Web service operation
   */
  @WebMethod(operationName = "reject")
  public String reject(@WebParam(name = "rejection_vote") String rejection_vote)
  {
    try{
      Document doc = XMLHelper.parseXML(rejection_vote);
      KeyPair kp = BallotSigner.getKeyPair();
      boolean validSignature = XMLSignature.validate(doc, null, kp.getPublic());
      if(validSignature)
      {
        Element rejection = (Element)doc.getElementsByTagName("rejection");
        String ballotid = rejection.getAttribute("id");
        Element election = (Element) rejection.getElementsByTagName("election");
        String electionID = election.getAttribute("id");
        
        // TODO: check if election exists
        // TODO: check if user is logged in
        // TODO: add ballotID to electionIDs rejection list
        // TODO: restore users possibility to vote
        
        rejection.setAttribute("rejected", "true");

        return XMLHelper.documentToString(doc);
      }
      else
      {
        return "! Error: invalid Signature";
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return "! Exception: "+ex.getMessage();
    }
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getZZ1")
  public String getZZ1() {
    zz1 = UUID.randomUUID().toString();
    return zz1;
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getPublicKey")
  public String getPublicKey(@WebParam(name = "electionid") String electionid)
  {
    try
    {
      return KeyHelper.getBase64FromKey(BallotSigner.getKeyPair().getPublic());
    }
    catch (Exception ex)
    {
      return "* Error: "+ex.toString();
    }
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getRejectionSigned")
  public String getRejectionSigned()
  {
    String returnvalue = rejectionCode;
    rejectionCode = "";
    return returnvalue;
  }
}
