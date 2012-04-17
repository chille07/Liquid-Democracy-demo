/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.security;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author chille
 */
public class KeyHelper
{
  public static KeyStore ks = null; // private?

  public static void writeFile(String fileName, String content) throws FileNotFoundException, IOException
  {
    File file = new File(fileName);
    File folder = new File(file.getParent());
    folder.mkdirs();
    try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
      out.write(content);
    }
  }
  public static String readFile(String fileName) throws FileNotFoundException, IOException
  {
    byte[] buffer = new byte[(int) new File(fileName).length()];
    BufferedInputStream f = null;
    try {
        f = new BufferedInputStream(new FileInputStream(fileName));
        f.read(buffer);
    } finally {
        if (f != null) try { f.close(); } catch (IOException ignored) { }
    }
    return new String(buffer);
  }

  public static String getBase64FromKey(Key rsa) throws Exception
  {
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encode(rsa.getEncoded());
  }

  public static Key getKeyFromBase64(String base64, String algorithm, boolean isPublicKey) throws Exception
  {
    // PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(_private.getEncoded()));    // private: PKCS8EncodedKeySpec
    // public: X509EncodedKeySpec

    BASE64Decoder decoder = new BASE64Decoder();
    byte[] bt = decoder.decodeBuffer(base64);
    KeyFactory kf = KeyFactory.getInstance(algorithm);

    try
    {
      return isPublicKey ? kf.generatePublic(new X509EncodedKeySpec(bt)) : kf.generatePrivate(new X509EncodedKeySpec(bt));
    }
    catch(Exception ex)
    {
      System.err.println("Invalid Key in base64-encoding!");
      ex.printStackTrace();
      // testing:
      //KeyPair kp = KeyHelper.GenerateRSAKey();
      //return KeyHelper.getRSAKeyFromBase64(KeyHelper.getBase64FromRSAKey(kp.getPublic()));
      throw ex;
    }
  }

  public static Key getDSAKeyFromBase64(String base64, boolean isPublic) throws Exception
  {
    return KeyHelper.getKeyFromBase64(base64, "DSA", isPublic);
  }
  public static Key getRSAKeyFromBase64(String base64, boolean isPublic) throws Exception
  {
    return KeyHelper.getKeyFromBase64(base64, "RSA", isPublic);
  }

  protected static String getDefaultKeyStoreType()
  {
    // default type does not support saving public keys.
    //return KeyStore.getDefaultType();

    // should support public keys according to:
    // http://khylo.blogspot.com/2009/12/keytool-keystore-cannot-store-non.html
    return "JCEKS";
  }

  public static void generateKeyStore() throws Exception
  {
    ks = KeyStore.getInstance(getDefaultKeyStoreType());
    ks.load(null);
  }
  

  public static void loadKeyStore(String fileName, String password) throws Exception
  {
    ks = KeyStore.getInstance(getDefaultKeyStoreType());
    try (java.io.FileInputStream fis = new java.io.FileInputStream(fileName)) {
      ks.load(fis, password.toCharArray());
    }
  }
  public static void saveKeyStore(String fileName, String password) throws Exception
  {
    try (java.io.FileOutputStream fos = new java.io.FileOutputStream(fileName)) {
      ks.store(fos, password.toCharArray());
    }
  }

  // http://blog.thilinamb.com/2010/01/how-to-generate-self-signed.html
  public static java.security.cert.X509Certificate[] createDummyCerts4() throws Exception
  {
    Security.addProvider(new BouncyCastleProvider());
    String domainName = "ld.iaik.tugraz.at";

    X509V3CertificateGenerator v3CertGen = new X509V3CertificateGenerator();
    long nr =  new SecureRandom().nextInt();
    if(nr<0)nr*=-1;
    BigInteger bi = BigInteger.valueOf(nr);
    v3CertGen.setSerialNumber(bi);
    v3CertGen.setIssuerDN(new X509Principal("CN=" + domainName + ", OU=None, O=None L=None, C=None"));
    v3CertGen.setNotBefore(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30));
    v3CertGen.setNotAfter(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365*10)));
    v3CertGen.setSubjectDN(new X509Principal("CN=" + domainName + ", OU=None, O=None L=None, C=None"));
    
    
    KeyPair KPair = KeyHelper.GenerateRSAKey();
    v3CertGen.setPublicKey(KPair.getPublic());
    v3CertGen.setSignatureAlgorithm("MD5WithRSAEncryption");

    java.security.cert.X509Certificate PKCertificate = v3CertGen.generateX509Certificate(KPair.getPrivate());
    
    try (FileOutputStream fos = new FileOutputStream("testCert.cert")) {
      fos.write(PKCertificate.getEncoded());
    }


    return  new  java.security.cert.X509Certificate[] { PKCertificate };
  }

 
  public static void storeKey(String alias, String password, Key key) throws Exception
  {
    //KeyHelper.writeFile("./keystore/"+alias, KeyHelper.getBase64FromKey(key));

    java.security.cert.Certificate[] cert =  createDummyCerts4();
    ks.setKeyEntry(alias, key, password.toCharArray(), cert);
  }
  public static Key getKey(String alias, String password) throws Exception
  {
    //KeyHelper.getKeyFromBase64(KeyHelper.readFile(""), "DSA", true);

    // ks must be loaded or generated
    return ks.getKey(alias, password.toCharArray());
  }

  /**
   * Generates an AES Key for symmetric Encryption.
   * @return
   * @throws Exception
   */
  public static SecretKey GenerateSymmetricKey() throws Exception
  {
    String algorithm = "AES";
    KeyGenerator kpg = KeyGenerator.getInstance(algorithm);
    kpg.init(128);
    return kpg.generateKey();
  }

  /**
   * Generates a Key for AsymmetricKeyKryptography
   * @param algorithm f.e. RSA, DSA, ...
   * @param keysize Keysize in bits
   * @return
   * @throws Exception
   */
  public static KeyPair GenerateAsymKey(String algorithm, int keysize) throws Exception
  {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm); 
    kpg.initialize(keysize);
    return kpg.genKeyPair();
  }

  /**
   * Generates a RSA Key with 2048 bits. This should last for approx. 20 years.
   * The Key can be used for KeyEncryption.
   * @return KeyPair with Private and Public Key
   * @throws Exception
   */
  public static KeyPair GenerateRSAKey() throws Exception
  {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    kpg.initialize(2048);
    // must be 512 - 1024 and multiple of 64 for DSA
    // 2048 = secure for approx. 20 years
    // http://www.javamex.com/tutorials/cryptography/rsa_key_length.shtml
    return kpg.genKeyPair();
  }

  /**
   * Generates a DSA Key with 1024 bits. I don't know how secure this really is.
   * It can be used for Digital Signatures.
   * @return KeyPair with Private and Public Key
   * @throws Exception
   */
  public static KeyPair GenerateDSAKey() throws Exception
  {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
    kpg.initialize(1024); // must be 512 - 1024 and multiple of 64 for DSA
    return kpg.genKeyPair();
  }

  /**
   * Saves two BigInteger to a File. May be used for KeyPairs like DSA or RSA.
   * TODO: This was not tested yet!
   * @param fileName
   * @param mod
   * @param exp
   * @throws Exception
   */
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

  /**
   * This should save a private/public key to a file.
   * @param kp
   * @param fileName
   * @throws Exception
   */
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


  /**
   * this should load a private/Public key from a file.
   * @param fileName
   * @param pub
   * @return
   * @throws IOException
   */
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
      RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e); // TODO: evtl RSAPrivateKeySpec sometimes?
      KeyFactory fact = KeyFactory.getInstance("RSA");

      return pub ? fact.generatePublic(keySpec):fact.generatePrivate(keySpec);

    } catch (Exception e) {
      throw new RuntimeException("Spurious serialisation error", e);
    } finally {
      ois.close();
    }
  }
}
