/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.security;

import java.io.*;
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
  public static Document parseXML(String xml) throws ParserConfigurationException, SAXException, IOException
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

  public static void documentToFile(Document doc, String filename) throws Exception
  {
    System.out.println("[xml] export document to '"+filename+"'.");
    
    TransformerFactory factory = TransformerFactory.newInstance();
    Transformer transformer = factory.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

    DOMSource src = new DOMSource(doc);
    FileOutputStream fs = new FileOutputStream(filename, false);
    try
    {
      StreamResult sr = new StreamResult(fs);
      transformer.transform(src, sr);
    }
    finally
    {
      fs.close();
    }
  }

  public static Document fileToDocument(String filename) throws Exception
  {
    System.out.println("[xml] read document from '"+filename+"'.");
    
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    DocumentBuilder db = factory.newDocumentBuilder();
    try
    (FileInputStream sr = new FileInputStream(filename))
    {
      Document document = db.parse(sr);
      return document;
    }
    
  }

  public static Document generateDocument() throws ParserConfigurationException
  {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    return dbf.newDocumentBuilder().newDocument();
  }

}
