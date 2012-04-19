/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.election.userclient;

import java.util.HashMap;

/**
 * Please only use this class for the demo!!!
 * @author chille
 */
public class DataStorage
{

  private static HashMap<String,String> ht = new HashMap<>();
  public static void setData(String key, String data)
  {
    ht.put(key, data);
  }
  public static String getData(String key)
  {
    return ht.get(key);
  }
}
