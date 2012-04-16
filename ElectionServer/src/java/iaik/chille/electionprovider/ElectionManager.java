/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.electionprovider;

import iaik.chille.electionprovider.db.DBChoice;
import iaik.chille.electionprovider.db.DBElection;
import iaik.chille.electionprovider.jaxb.Choice;
import iaik.chille.electionprovider.jaxb.Election;
import iaik.chille.electionprovider.jaxb.ObjectFactory;
import iaik.chille.electionprovider.jpa.DBChoiceJpaController;
import iaik.chille.electionprovider.jpa.DBElectionJpaController;
import iaik.chille.security.KeyHelper;
import java.security.KeyPair;
import java.util.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author chille
 */
public class ElectionManager
{
  private ElectionManager()
  {

  }

  private static ElectionManager _instance = null;
  public static ElectionManager getInstance()
  {
    if(_instance == null)
      _instance = new ElectionManager();
    return _instance;
  }

  /*
   *       <p>
        Frage: <input type="text" name="question" size="25" />
        Titel: <input type="text" name="title" size="25" />
        URL: <input type="text" name="url" size="25" />
        </p>

        <p>
          <b>Antwort 1:</b>
        Antwort: <input type="text" name="1_answer" size="25" />
        Details: <input type="text" name="1_detail" size="25" />
        URL: <input type="text" name="1_url" size="25" />
        </p>

        <p>
          <b>Antwort 2:</b>
        Antwort: <input type="text" name="2_answer" size="25" />
        Details: <input type="text" name="2_detail" size="25" />
        URL: <input type="text" name="2_url" size="25" />
   */
  public Election addElection(javax.servlet.http.HttpServletRequest r) throws Exception
  {
    // election: question, title, url
    // antwort: 1_answer, 1_detail, 1_url
    // antwort: 2_answer, 2_detail, 2_url
    ObjectFactory of = new ObjectFactory();

    Election el = of.createElection();
    el.setId(UUID.randomUUID().toString());
    el.setQuestion(r.getParameter("question"));
    el.setTitle(r.getParameter("title"));
    el.setUrl(r.getParameter("url"));

    KeyPair kp = KeyHelper.GenerateRSAKey();

    // PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(_private.getEncoded()));
    // private: PKCS#8

    // Bas64 encoded
    //BASE64Encoder encoder = new BASE64Encoder();
    System.err.println("enc1: "+KeyHelper.getBase64FromKey(kp.getPrivate()));
    System.err.println("enc2: "+KeyHelper.getBase64FromKey(kp.getPublic()));
    el.setPrivateKey(KeyHelper.getBase64FromKey(kp.getPrivate()));
    el.setPublicKey(KeyHelper.getBase64FromKey(kp.getPublic()));
    
    try{
      // TODO: define begin/end date
      GregorianCalendar cal = new GregorianCalendar( 2012, Calendar.DECEMBER, 22 );
      XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar( cal );
      el.setValidFrom(xmlCal);
      el.setValidTo(xmlCal);
    }
    catch(DatatypeConfigurationException ex)
    {
      ex.printStackTrace();
      return null;
    }
    // ..
    {
      Choice choise1 = of.createChoice();
      choise1.setId(UUID.randomUUID().toString());
      choise1.setAnswer(r.getParameter("1_answer"));
      choise1.setDetail(r.getParameter("1_detail"));
      choise1.setUrl(r.getParameter("1_url"));
      el.getChoice().add(choise1);
    }
    {
      Choice choise1 = of.createChoice();
      choise1.setId(UUID.randomUUID().toString());
      choise1.setAnswer(r.getParameter("2_answer"));
      choise1.setDetail(r.getParameter("2_detail"));
      choise1.setUrl(r.getParameter("2_url"));
      el.getChoice().add(choise1);
    }
    insertElectionInformation(el);
    
    return el;
  }

    /**
   * Stores a Election to the Database
   * @param el
   * @throws Exception
   */
  public void insertElectionInformation(Election el) throws Exception
  {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ElectionServerPU"); 
    DBChoiceJpaController cc = new DBChoiceJpaController(emf);
    DBElectionJpaController ec = new DBElectionJpaController(emf);
    DBElection el2 = new DBElection(el);
    List <DBChoice>cl2 = el2.getChoice();
    for(int i=0;i<cl2.size();i++)
    {
      cc.create(cl2.get(i));
    }
    ec.create(el2);
  }

  /**
   * Lists all Elections
   * @return
   */
  public List<Election> findElections() throws DatatypeConfigurationException
  {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ElectionServerPU");
    DBElectionJpaController ec = new DBElectionJpaController(emf);
    List<DBElection> l1 = ec.findDBElectionEntities();
    List<Election> l2 = new ArrayList<Election>();
    for(int i=0;i<l1.size();i++)
    {
      Election el = l1.get(i).convert();
      //el.setPrivateKey("--cencored--"); // TODO: comment in
      l2.add(el);
    }
    return l2;
  }


}
