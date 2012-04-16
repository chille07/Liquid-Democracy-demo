/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.election.userclient;

import iaik.chille.security.XMLHelper;
import java.util.Vector;
import org.w3c.dom.*;

/**
 *
 * @author chille
 */
public class XMLStruct extends Vector
{
  private static int count=0;
  public XMLStruct(String header, String xml)
  {
    this.header = header;
    this.xml = xml;
    nr=count++;

    this.add("-- Begin --");
    try{
      Document doc = XMLHelper.parseXML(xml);
      work(doc.getFirstChild(),"", false);
    }
    catch(Exception ex)
    {
      this.add(ex.toString());
      this.add(this.xml);
    }
    this.add("-- End --");
  }
  public int nr;
  public String header;
  public String xml;



  private void work(Node e, String depth, boolean node)
  {
    if(e.getLocalName()==null)
      return;

    String text = depth + (node?"   ":"-> ") + e.getLocalName();
    if(e.getChildNodes().getLength()==1)
    {
      if(e.getChildNodes().item(0).getLocalName()==null)
      {
        text += " -> " + e.getTextContent();
      }
    }
    this.add(text);
    
    // show parameters
    NamedNodeMap nnm = e.getAttributes();
    if(nnm!=null)
    {
      for(int i=0;i<nnm.getLength();i++)
      {
        Node n = nnm.item(i);
        work(n,depth+" | ", true);
      }
    }

    // Do Recursively:
    NodeList nl = e.getChildNodes();
    for(int i=0;i<nl.getLength();i++)
    {
      Node n = nl.item(i);
      work(n,depth+" | ", false);
    }
  }
  

  @Override
  public String toString()
  {
    return getHeader();
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getXml() {
    return xml;
  }

  public void setXml(String xml) {
    this.xml = xml;
  }
}