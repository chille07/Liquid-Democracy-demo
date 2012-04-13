/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.security;

import java.io.*;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author chille
 */
public class KeyHelper
{
  KeyHelper()
  {
    
  }
  public static SecretKey GenerateSymmetricKey() throws Exception
  {
    String algorithm = "AES";
    KeyGenerator kpg = KeyGenerator.getInstance(algorithm);
    kpg.init(128);
    return kpg.generateKey();
  }

  public static KeyPair GenerateKeyEncryptionKey() throws Exception
  {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA"); // was RSA
    kpg.initialize(1024); // must be 512 - 1024 and multiple of 64 for DSA
    // 2048 = secure for approx. 20 years
    // http://www.javamex.com/tutorials/cryptography/rsa_key_length.shtml
    return kpg.genKeyPair();
  }

  private static void saveToFile(String fileName, BigInteger mod, BigInteger exp)
          throws Exception
  {
    ObjectOutputStream oout = new ObjectOutputStream(
      new BufferedOutputStream(new FileOutputStream(fileName)));
    try
    {
      oout.writeObject(mod);
      oout.writeObject(exp);
    }
    catch (Exception e)
    {
      throw new IOException("Unexpected error", e);
    }
    finally
    {
      oout.close();
    }
  }


  public static void saveKey(KeyPair kp, String fileName) throws Exception
  {
    if(1==1) return;
    KeyFactory fact = KeyFactory.getInstance("DSA");
    RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
    RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);

    saveToFile(fileName+"~public.key", pub.getModulus(), pub.getPublicExponent());
    saveToFile(fileName+"~private.key", priv.getModulus(), priv.getPrivateExponent());
    //kp.getPrivate();
  }

  public static Key readKeyFromFile(String fileName, boolean pub) throws IOException
  {
    //InputStream in = ServerConnection.class.getResourceAsStream(keyFileName);
    InputStream in = new FileInputStream(fileName);

    ObjectInputStream ois =
      new ObjectInputStream(new BufferedInputStream(in));
    try
    {
      BigInteger m = (BigInteger) ois.readObject();
      BigInteger e = (BigInteger) ois.readObject();
      RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
      KeyFactory fact = KeyFactory.getInstance("RSA");

      return pub ? fact.generatePublic(keySpec):fact.generatePrivate(keySpec);

    } catch (Exception e) {
      throw new RuntimeException("Spurious serialisation error", e);
    } finally {
      ois.close();
    }
  }
}
