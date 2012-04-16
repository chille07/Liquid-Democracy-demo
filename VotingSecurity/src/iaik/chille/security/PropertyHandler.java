/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.security;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
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
      _instance = new PropertyHandler();
    return _instance;
  }
  private Properties properties=null;

  private PropertyHandler()
  {
    try
    {
      properties = new Properties();
      
      try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream("connection.properties"))) {
        properties.load(stream);
        stream.close();
      }


    }
    catch (IOException ex)
    {
      Logger.getLogger(PropertyHandler.class.getName()).log(Level.SEVERE, null, ex);
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
