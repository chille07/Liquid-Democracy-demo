/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.electionprovider;

import iaik.chille.electionprovider.jaxb.Elections;
import iaik.chille.electionprovider.jaxb.ObjectFactory;
import java.io.StringWriter;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

 // org.datanucleus.enhancer.tools.EnhancerTask


/**
 *
 * @author chille
 */
@WebService(serviceName = "ElectionProvider")
public class ElectionProvider {

  /**
   * This is a sample web service operation
   */
  @WebMethod(operationName = "hello")
  public String hello(@WebParam(name = "name") String txt) {
    return "Hello " + txt + " !";
  }

  /**
   *
   */
  @WebMethod(operationName = "getVersion")
  public String getVersion()
  {
    return "1.0.1 alpha";
  }



  /**
   * Web service operation
   */
  @WebMethod(operationName = "getElectionInformation")
  public String getElectionInformation()
  {
    try
    {
      ObjectFactory of = new ObjectFactory();
      Elections els = of.createElections();
      
      // read elections from database and add to xml:
      els.getElection().addAll(ElectionManager.getInstance().findElections());

      // ...
      {
      /*
        Election el = of.createElection();
        el.setId(UUID.randomUUID().toString());
        el.setQuestion("blubb");
        el.setTitle("asdf");
        el.setUrl("");
        try{
          GregorianCalendar cal = new GregorianCalendar( 1976, Calendar.DECEMBER, 22 );
          XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar( cal );
          el.setValidFrom(xmlCal);
          el.setValidTo(xmlCal);
        }
        catch(DatatypeConfigurationException ex)
        {
          ex.printStackTrace();
          return ex.toString();
        }
        // ..
        {
          Choice choise1 = of.createChoice();
          choise1.setAnswer("answer");
          choise1.setDetail("detail");
          choise1.setResult(3);
          choise1.setUrl("asdf");
          choise1.setId(UUID.randomUUID().toString());
          el.getChoice().add(choise1);
        }
        insertElectionInformation(el);
        */
        //els.getElection().add(el);
      }

      try
      {
        JAXBContext ctx = JAXBContext.newInstance(els.getClass().getPackage().getName());
        Marshaller ms = ctx.createMarshaller();
        ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        ms.marshal(els, sw);
        String s = sw.toString();
        System.out.println(s);
        return s;
      }
      catch(JAXBException ex)
      {
        ex.printStackTrace();
        return ex.toString();
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return ex.toString();
    }
  
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getVotingServers")
  public String getVotingServers() {
    return "asdf";
  }
}
