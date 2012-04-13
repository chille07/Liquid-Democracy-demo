/*
 * Hillebold Christoph
 * 28.02.2012
 */
package iaik.chille.election.userclient;

import iaik.chille.elections.common.Elections;
import iaik.chille.elections.electionserverclient.ElectionProvider;
import iaik.chille.elections.electionserverclient.ElectionProvider_Service;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author chille
 */
public class ClientMain {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    System.out.println("\n\n");
    System.out.println("-- Starting Election Client --");
    try
    {
      ElectionProvider_Service eps = new ElectionProvider_Service();
      ElectionProvider ep = eps.getElectionProviderPort();

      System.out.println("Connecting to Service and requesting Version.");
      System.out.println(ep.getVersion());
      System.out.println("End of Program.");

      String xml = ep.getElectionInformation();

      try
      {
        Elections els = null;
        JAXBContext ctx = JAXBContext.newInstance(Elections.class.getPackage().getName());
        Unmarshaller unms = ctx.createUnmarshaller();
        StringReader sr = new StringReader(xml);
        els = (Elections) unms.unmarshal(sr);

        System.out.println("So viele Elemente sind drinnen: "+els.getElection().size());
        System.out.println(xml);

        // Start GUI
        MainFrame2 mf2 = new MainFrame2(els);
        mf2.setVisible(true);
      }
      catch(JAXBException ex)
      {
        System.err.println("The Given Message was no XML-Data: \n\n");
        System.err.println(xml+"\n\n");
        System.err.println(ex.getMessage());
      }
    }
    catch(com.sun.xml.internal.ws.client.ClientTransportException ex)
    {
      System.err.println("## WebService is not started or not reachable. ##");
      System.err.println(ex.getMessage());

      MainFrame2 mf2 = new MainFrame2(null);
      mf2.setVisible(true);
    }
    catch(Exception ex)
    {
      System.err.println("## Other error occured. ##");
      System.err.println(ex.getMessage());
    }

  }
}
