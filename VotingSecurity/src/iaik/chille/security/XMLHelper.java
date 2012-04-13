/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author chille
 */
public class XMLHelper
{
  public static Document parseFile(String xml) throws ParserConfigurationException, SAXException, IOException
  {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    DocumentBuilder db = factory.newDocumentBuilder();

    ByteArrayInputStream sr = new ByteArrayInputStream(xml.getBytes());
    try
    {
      Document document = db.parse(sr);
      return document;
    }
    finally{
      sr.close();
    }
  }

  public static String documentToString(Document doc) throws TransformerConfigurationException, TransformerException, IOException
  {
    //return doc.getDocumentElement().toString();

    TransformerFactory factory = TransformerFactory.newInstance();
    Transformer transformer = factory.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

    DOMSource src = new DOMSource(doc);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try
    {
      StreamResult sr = new StreamResult(baos);
      transformer.transform(src, sr);

      String result = baos.toString();
      return result;
    }
    finally
    {
      baos.close();
    }
  }

  public static Document generateDocument() throws ParserConfigurationException
  {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    return dbf.newDocumentBuilder().newDocument();
  }

}
