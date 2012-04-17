/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.election.userclient;

import iaik.chille.security.PropertyHandler;
import iaik.chille.ballotsignerclient.BallotSigner;
import iaik.chille.ballotsignerclient.BallotSigner_Service;
import iaik.chille.electionclient.jaxb.Choice;
import iaik.chille.electionclient.jaxb.Election;
import iaik.chille.security.KeyHelper;
import iaik.chille.security.XMLEncrypt;
import iaik.chille.security.XMLHelper;
import iaik.chille.security.XMLSignature;
import iaik.chille.votingclient.VotingServer;
import iaik.chille.votingclient.VotingServer_Service;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.security.KeyPair;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author chille
 */
public class ClientHandler
{
  protected static ClientHandler _instance;
  
  public static ClientHandler getInstance()
  {
    if(_instance == null)
      _instance = new ClientHandler();
    return _instance;
  }

  protected ClientHandler()
  {
    try{
      if((new File("keystore.ks")).exists())
      {
        KeyHelper.loadKeyStore("keystore.ks", "abcdefgh");
      }
      else
      {
        KeyHelper.generateKeyStore();
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      System.exit(948);
    }
  }

  public void storeKeyStore()
  {
    try {
      KeyHelper.saveKeyStore("keystore.ks", "abcdefgh");
    } catch (Exception ex) {
      ex.printStackTrace();
      Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private BallotSigner getBallotSignerPort() throws MalformedURLException
  {
    PropertyHandler p = PropertyHandler.getInstance();
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


  private VotingServer getVotingServerPort() throws MalformedURLException
  {
    PropertyHandler p = PropertyHandler.getInstance();
    // following wrong results in "CM4" error: ... is not a valid service
    QName VOTINGSERVER_QNAME = new QName(p.get("VOTINGSERVER_QNAME",
            "http://votingserver.chille.iaik/"), "VotingServer");

    // following wrong results in "CM4" error: failed to access the wsdl at: ...
    URL url = new URL(p.get("VOTINGSERVER_WSDL",
            "http://localhost:8084/VotingServer/VotingServer?wsdl"));
    VotingServer_Service bs_s = new VotingServer_Service(url, VOTINGSERVER_QNAME);
    VotingServer bs = bs_s.getVotingServerPort();


    // following wrong results in "CM2" error: Webservice is not started or not reachable
    ((BindingProvider)bs).getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
            p.get("VOTINGSERVER_URL","http://localhost:8084/VotingServer/VotingServer"));
    return bs;
  }

  private Document generateVoteDocument(Election el, Choice ch, String zz1) throws Exception
  {
    Document doc = XMLHelper.generateDocument();
    Element vote = doc.createElement("vote");
    vote.setAttribute("id", zz1); // this is a randomly choosen string
    doc.appendChild(vote);

    Element election = doc.createElement("election");
    election.setAttribute("id", el.getId());
    vote.appendChild(election);

    Element choice = doc.createElement("choice");
    choice.setAttribute("id", ch.getId());
    election.appendChild(choice);

    XMLHelper.documentToFile(doc, "client_unencrypted_vote.xml");
    XMLViewer.getInstance().addXML("Vote: #1: unencrypted vote",XMLHelper.documentToString(doc));

    // encrypt my vote
    SecretKey aesKey = KeyHelper.GenerateSymmetricKey();
    //KeyPair rsaKey = KeyHelper.GenerateRSAKey();
    //Key electionKey = rsaKey.getPublic();

    // This is the Public Key of the Election Server for this specific vote.
    if(el.getPublicKey()==null)
    {
      alert("Error","Public Key is null");
    }
    Key electionKey = KeyHelper.getRSAKeyFromBase64(el.getPublicKey(), true);
    doc = XMLEncrypt.encryptAES(aesKey,electionKey,doc,choice,false);
    //System.err.println("*** Encrypted Vote: ***");
    //System.out.println(XMLHelper.documentToString(doc));
    XMLHelper.documentToFile(doc, "client_encrypted_vote.xml");
    XMLViewer.getInstance().addXML("Vote: #2: encrypted vote",XMLHelper.documentToString(doc));

    //Document doc2 = XMLEncrypt.decryptAES(doc, rsaKey.getPrivate());
    //System.err.println("*** Decrypted Vote: ***");
    //System.out.println(XMLHelper.documentToString(doc));
   // XMLHelper.documentToFile(doc2, "client_decrypted_vote.xml");
    //XMLViewer.getInstance().addXML("Vote: #3: decrypted (debug)",XMLHelper.documentToString(doc));
    return doc;
  }

  public void vote(Election el, Choice ch)
  {
    try
    {
      //alert("Trying to vote...","Election: "+el.getId()+" -> Choice: "+ch.getId());

      // login to the ballotsigner
      BallotSigner bs = getBallotSignerPort();
      String login_info = bs.login("id", "xyz"); // TODO: dummy login
      System.out.println("[vote] login returned: "+ login_info);

      String zz1 = bs.getZZ1();
      zz1 = zz1.concat(UUID.randomUUID().toString()); // double length of ZZ1 with random content

      // make <vote id=".."><election id=".."><choice id=".."/></election></vote>
      Document doc = this.generateVoteDocument(el, ch, zz1);
      

      // send vote to ballot signer and verify signature
      String xml_vote = XMLHelper.documentToString(doc);
      String signed_vote = bs.getVoteSigned(xml_vote);
      XMLViewer.getInstance().addXML("Vote: #4: signed vote",signed_vote);

      Key ballotSignerPK = KeyHelper.getDSAKeyFromBase64(bs.getPublicKey(el.getId()),true);
      Document doc_signed_vote = XMLHelper.parseXML(signed_vote);
      boolean valid = XMLSignature.validate(doc_signed_vote, null, ballotSignerPK);
      if(!valid)
      {
        System.err.println("[vote] BallotSigner serves invalid Signature for Vote!!!");
        alert("[vote] Error: Invalid Signature","BallotSigner serves invalid Signature for Vote!!!");
        return; // stop voting
      }
      else
      {
        System.out.println("[vote] Got valid Signature from BallotSigner for the Vote. :-)");
      }

      // receive signed rejection code from ballot signer and verify signature
      String rejectionCode = bs.getRejectionSigned();
      XMLViewer.getInstance().addXML("Vote: #5: rejection code",rejectionCode);
      Document doc_signed_rejection = XMLHelper.parseXML(rejectionCode);
      valid = XMLSignature.validate(doc_signed_rejection, null, ballotSignerPK);
      if(!valid)
      {
        System.err.println("[vote] BallotSigner serves invalid Signature for Rejection Code!!!");
        alert("[vote] Error: Invalid Signature","BallotSigner serves invalid Signature for Rejection Code!!!");
        return; // stop voting
      }
      else
      {
        System.out.println("[vote] Got valid Signature from BallotSigner for the Rejection. :-)");
      }
       
      // store my vote and rejection locally
      XMLHelper.documentToFile(doc_signed_vote, "vote_"+el.getId()+".xml");
      XMLHelper.documentToFile(doc_signed_rejection, "rejection_"+el.getId()+".xml");

      // TODO: send my vote to the random voting server
      VotingServer vs = getVotingServerPort();
      // TODO: the key from the server must not come from himself
      Key votingserverkey = KeyHelper.getKeyFromBase64(vs.getPublicKey(),"DSA",true);
      String finalVote = vs.registerVote(signed_vote);
      XMLViewer.getInstance().addXML("Vote: #6: accepted vote",finalVote);

      Document doc_finalVote = XMLHelper.parseXML(finalVote);
      Node sig_finalVote = doc_finalVote.getElementsByTagName("Signature").item(1);

      if(!XMLSignature.validate(doc_finalVote, sig_finalVote, votingserverkey))
      {
        System.err.println("[vote] Invalid answer from VotingServer");
        alert("[vote] Error: Invalid Signature","Voting Server has invalid signature.");
        return;
      }
      
      // finished: notify user
      alert("Successfull vote","We were able to vote successfully and to verify all signatuers.");
    }
    catch(Exception ex)
    {
      System.err.println("Voting failed: "+ex.toString());
      ex.printStackTrace();
    }

  }

  public void reject(Election el)
  {
    // TODO
  }

  public void verify(Election el)
  {
    // TODO
  }

  /**
   * This method is just for Testing Signature and cryptographic stuff.
   * It should be removed when not needed any more.
   * @param el
   * @param ch
   */
  public void test(Election el, Choice ch)
  {
    System.out.println("test()");
    try {

      KeyPair kp = KeyHelper.GenerateDSAKey();
      KeyHelper.generateKeyStore();
      KeyHelper.storeKey("asf", "asdf", kp.getPrivate());
      KeyHelper.storeKey("qwer", "asdf", kp.getPublic());
      KeyHelper.saveKeyStore("test1","pw");
      KeyHelper.loadKeyStore("test1", "pw");

      if(1!=2)  return;
      SecretKey k = KeyHelper.GenerateSymmetricKey();
      KeyHelper.saveKey(kp, "~key1.key");

      Document doc = XMLHelper.generateDocument();

      Element root = doc.createElement("root");
      doc.appendChild(root);

      root.appendChild(doc.createElement("test"));
      doc.getElementsByTagName("test").item(0).setTextContent("richtig");
      System.out.println(XMLHelper.documentToString(doc));
      XMLViewer.getInstance().addXML("Test: #1",XMLHelper.documentToString(doc));

      doc = XMLSignature.signate(doc, kp.getPrivate(), kp);
      System.out.println(XMLHelper.documentToString(doc)); // works until here - 2012-04-12
      XMLViewer.getInstance().addXML("Test: #2 sign",XMLHelper.documentToString(doc));

      doc.getElementsByTagName("test").item(0).setTextContent("richtig"); // change to force invalid validation

      System.out.println("validate: "+XMLSignature.validate(doc, doc.getDocumentElement(), kp.getPublic()));
    }
    catch (Exception ex)
    {
      System.err.println(ex.toString());
      ex.printStackTrace();
      Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void alert(String title, String msg)
  {
    JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
  }

}
