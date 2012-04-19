/*
 * Hillebold Christoph
 * 28.02.2012
 */
package iaik.chille.election.userclient;

import com.sun.xml.internal.ws.client.ClientTransportException;
import iaik.chille.electionclient.ElectionProvider;
import iaik.chille.electionclient.ElectionProvider_Service;
import iaik.chille.electionclient.jaxb.Elections;
import iaik.chille.security.PropertyHandler;
import java.io.StringReader;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author chille
 */
public class ClientMain
{

  public static void alert(String title, String msg)
  {
    JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
  }
  public static void loadElections()
  {
    try
    {
      PropertyHandler p = PropertyHandler.getInstance();

      // following wrong results in "CM4" error: ... is not a valid service
      QName ELECTIONPROVIDER_QNAME = new QName(p.get("ELECTIONPROVIDER_QNAME",
              "http://electionprovider.chille.iaik/"), "ElectionProvider");

      // following wrong results in "CM4" error: failed to access the wsdl at: ...
      URL url = new URL(p.get("ELECTIONPROVIDER_WSDL",
              "http://localhost:8084/ElectionServer/ElectionProvider?wsdl"));

      ElectionProvider_Service eps = new ElectionProvider_Service(url, ELECTIONPROVIDER_QNAME);
      ElectionProvider ep = eps.getElectionProviderPort();

      // following wrong results in "CM2" error: Webservice is not started or not reachable
      ((BindingProvider)ep).getRequestContext().put(
              BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
              p.get("ELECTIONPROVIDER_URL","http://localhost:8084/ElectionServer/ElectionProvider"));

      System.out.println("Connecting to ElectionProvider and requesting Version.");
      System.out.println("Version is "+ep.getVersion()+".");

      // Request Election information in xml Format
      String xml = ep.getElectionInformation();
      XMLViewer.getInstance().addXML("Election Information",xml);

      try
      {
        Elections els = null;
        JAXBContext ctx = JAXBContext.newInstance(Elections.class.getPackage().getName());
        Unmarshaller unms = ctx.createUnmarshaller();
        StringReader sr = new StringReader(xml);
        els = (Elections) unms.unmarshal(sr);

        System.out.println("Count of elements: "+els.getElection().size());
        //System.out.println(xml); // DEBUG

        // Start GUI
        MainFrame2 mf2 = new MainFrame2(els);
        mf2.setVisible(true);
      }
      catch(JAXBException ex)
      {
        alert("Parsing Error","CM1: I received invalid xml-data.\nMaybe we need an update.");
        System.err.println("The Given Message was no XML-Data: \n\n");
        System.err.println(xml+"\n\n");
        System.err.println(ex.getMessage());
      }
    }
    catch(ClientTransportException ex)
    {
      alert("Connection Error","CM2: Webservice is not started or not reachable.");
      System.err.println("## WebService is not started or not reachable. ##");
      System.err.println(ex.getMessage());

      // just for testing
      // TODO: maybe remove following 3 lines in real application.
      alert("Test","CM3: For testing reasons i will start the program.");
      MainFrame2 mf2 = new MainFrame2(null);
      mf2.setVisible(true);
    }
    catch(Exception ex)
    {
      alert("Unexpected Error","CM4: A strange error occured: "+ex.getMessage());
      System.err.println("## Other error occured. ##");
      System.err.println(ex.getMessage());
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    System.out.flush();
    System.err.flush();
    System.out.println("\n");
    System.out.println("-- Opening Login Window --");

    LoginFrame lf = new LoginFrame(null, true); // modular window
    lf.setVisible(true);
    DataStorage.setData("username", lf.getUsername());
    DataStorage.setData("password", lf.getPassword());

    System.out.println("-- Starting Election Client --");
    ClientMain.loadElections();
    
  }


}
