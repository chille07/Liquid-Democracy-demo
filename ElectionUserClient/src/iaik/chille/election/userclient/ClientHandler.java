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
import iaik.chille.security.XMLHelper;
import iaik.chille.security.XMLSignature;
import java.security.KeyPair;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.znerd.xmlenc.XMLEncoder;

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
    alert("Trying to vote...","Election: "+el.getId()+" -> Choice: "+ch.getId());


    // TODO: check local if voted already


    // am i logged in to the ballot signer? (login?)
    BallotSigner_Service bs_s = new BallotSigner_Service(); // java.lang.NoClassDefFoundError
    BallotSigner bs = bs_s.getBallotSignerPort();
    String login_info = bs.login("id", "anything");
    alert("login_info", login_info);

    // TODO: encrypt my vote and send to the ballot signer
    String signed_vote = bs.getVoteSigned("encrypted vote with some information");
    alert("signed_vote", signed_vote);

    //  XMLSignatureFactory (javax.xml.crypto)
    // Encrypt: XMLENC
    XMLEncoder enc;
    

    // TODO: store my vote locally


    // send my vote to the random voting server


  }

  public void reject(Election el)
  {
    // TODO
  }

  public void verify(Election el)
  {
    // TODO
  }

  public void test(Election el, Choice ch)
  {
    System.out.println("test()");
    try {
      KeyPair kp = KeyHelper.GenerateKeyEncryptionKey();
      SecretKey k = KeyHelper.GenerateSymmetricKey();
      KeyHelper.saveKey(kp, "~key1.key");

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      Document doc = dbf.newDocumentBuilder().newDocument();
      //Element root = doc.createElement("root");
      //Element root = doc.createElementNS("http://chille.iaik.tugraz.at/somewhat","root");
      Element root = doc.createElementNS("","root");


      doc.appendChild(root);
      root.appendChild(doc.createElement("test"));
      doc.getElementsByTagName("test").item(0).setTextContent("richtig");
      System.out.println(XMLHelper.documentToString(doc));

      doc = XMLSignature.signate(doc, kp.getPrivate(), kp);
      System.out.println(XMLHelper.documentToString(doc)); // works until here - 2012-04-12

      doc.getElementsByTagName("test").item(0).setTextContent("richtig");

      System.out.println("validate: "+XMLSignature.validate(doc, doc.getDocumentElement(), kp.getPublic())); // TODO
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
