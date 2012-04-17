/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.security;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chille
 */
public class PropertyHandler
{
  private static PropertyHandler _instance = null;
  public static PropertyHandler getInstance()
  {
    if(_instance == null)
      _instance = new PropertyHandler("connection.properties");
    return _instance;
  }
  public static PropertyHandler getInstance(String fileName)
  {
    if(_instance == null)
      _instance = new PropertyHandler(fileName);
    return _instance;
  }


  private Properties properties=null;

  private PropertyHandler(String fileName)
  {
    try
    {
      properties = new Properties();
      InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
      if(is==null)
        is = new FileInputStream(fileName);
      if(is==null)
      {
        System.err.println("Property File '"+fileName+"' not found.");
        return;
      }
      
      BufferedInputStream stream = new BufferedInputStream(is);
      try
      {
        properties.load(stream);
      }
      finally
      {
        if(stream != null)
        stream.close();
      }
    }
    catch (IOException ex)
    {
      System.err.println("Property File '"+fileName+"' error.");
      Logger.getLogger(PropertyHandler.class.getName()).log(Level.SEVERE, null, ex);

      File test = new File(".");
      System.err.println("*** WE ARE HERE: "+test.getAbsolutePath()+" ***");
    }
  }

  public String get(String key)
  {
    return properties.getProperty(key);
  }
  
  public String get(String key, String defaultvalue)
  {
    return properties.getProperty(key,defaultvalue);
  }
}
