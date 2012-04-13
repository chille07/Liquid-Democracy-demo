/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.election.userclient;

import iaik.chille.ballotsignerclient.BallotSigner;
import iaik.chille.ballotsignerclient.BallotSigner_Service;
import iaik.chille.elections.common.Choice;
import iaik.chille.elections.common.Election;
import iaik.chille.security.KeyHelper;
import iaik.chille.security.XMLEncrypt;
import iaik.chille.security.XMLHelper;
import iaik.chille.security.XMLSignature;
import java.security.KeyPair;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    
  }


  public void vote(Election el, Choice ch)
  {
    try{
      alert("Trying to vote...","Election: "+el.getId()+" -> Choice: "+ch.getId());

      // TODO: check local if voted already


      // am i logged in to the ballot signer? (login?)
      BallotSigner_Service bs_s = new BallotSigner_Service();
      BallotSigner bs = bs_s.getBallotSignerPort();
      String login_info = bs.login("id", "anything"); // TODO: dummy login
      System.out.println("[vote] login returned: "+ login_info);


      String zz1 = "[ballotserver_random]"; // TODO: request ZZ1(1) from BallotSigner
      zz1 = zz1.concat("[local_random]"); // TODO: generate ZZ1(2) locally


      // TODO: make <Vote>
      Document doc = XMLHelper.generateDocument();
      Element vote = doc.createElement("vote");
      vote.setAttribute("id", zz1);
      Element election = doc.createElement("election");
      election.setAttribute("id", el.getId());
      Element choice = doc.createElement("choice");
      choice.setAttribute("id", ch.getId());

      doc.appendChild(vote);
      vote.appendChild(election);
      election.appendChild(choice);

      // TODO: encrypt my vote (content of vote only)
      SecretKey aesKey = KeyHelper.GenerateSymmetricKey();
      KeyPair rsaKey = KeyHelper.GenerateRSAKey();
      doc = XMLEncrypt.encryptAES(aesKey,rsaKey.getPublic(),doc,vote,false);
      alert("encrypted_vote",XMLHelper.documentToString(doc));

      doc = XMLEncrypt.decryptAES(doc, rsaKey.getPrivate());
      alert("decrypted_vote",XMLHelper.documentToString(doc));
              /*
               *
  public static Document encryptAES(
          Key symmetricKey,
          Key keyEncryptionKey,
          Document document,
          Element elementToEncrypt,
          boolean encryptContentsOnly
          ) throws Exception
               */

      // TODO: send vote to ballot signer

      String xml_vote = XMLHelper.documentToString(doc);
      String signed_vote = bs.getVoteSigned(xml_vote);
      alert("signed_vote", signed_vote);

      // TODO: get rejection vote

      // TODO: verify signed_vote's signature

      // TODO: store my vote locally


      // TODO: send my vote to the random voting server
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
      KeyPair kp = KeyHelper.GenerateKeyEncryptionKey();
      SecretKey k = KeyHelper.GenerateSymmetricKey();
      KeyHelper.saveKey(kp, "~key1.key");

      Document doc = XMLHelper.generateDocument();

      Element root = doc.createElement("root");
      doc.appendChild(root);

      root.appendChild(doc.createElement("test"));
      doc.getElementsByTagName("test").item(0).setTextContent("richtig");
      System.out.println(XMLHelper.documentToString(doc));

      doc = XMLSignature.signate(doc, kp.getPrivate(), kp);
      System.out.println(XMLHelper.documentToString(doc)); // works until here - 2012-04-12

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
