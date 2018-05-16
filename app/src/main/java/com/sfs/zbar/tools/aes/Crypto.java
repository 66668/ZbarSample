 package com.sfs.zbar.tools.aes;

 import android.annotation.SuppressLint;
 import android.util.Base64;

 import java.io.UnsupportedEncodingException;
 import java.security.GeneralSecurityException;
 import java.security.SecureRandom;
 import java.security.spec.KeySpec;

 import javax.crypto.Cipher;
 import javax.crypto.SecretKey;
 import javax.crypto.SecretKeyFactory;
 import javax.crypto.spec.IvParameterSpec;
 import javax.crypto.spec.PBEKeySpec;
 import javax.crypto.spec.SecretKeySpec;

 public class Crypto {

     public static final String PBKDF2_DERIVATION_ALGORITHM = "PBKDF2WithHmacSHA1";
     public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
     public static final String CIPHER_ALGORITHM_DEFAULT = "AES";
     private static String DELIMITER = "]";
     private static int KEY_LENGTH = 256;
     private static int ITERATION_COUNT = 1000;
     private static final int PKCS5_SALT_LENGTH = 8;
     @SuppressLint("TrulyRandom")
     private static SecureRandom random = new SecureRandom();

     private Crypto() {
     }

     public static SecretKey deriveKeyPbkdf2(byte[] salt, String password) {
         try {
             KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
             SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF2_DERIVATION_ALGORITHM);
             byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
             SecretKey result = new SecretKeySpec(keyBytes, "AES");
             return result;
         } catch (GeneralSecurityException e) {
             throw new RuntimeException(e);
         }
     }

     public static byte[] generateIv(int length) {
         byte[] b = new byte[length];
         random.nextBytes(b);
         return b;
     }

     public static byte[] generateSalt() {
         byte[] b = new byte[PKCS5_SALT_LENGTH];
         random.nextBytes(b);
         return b;
     }

     public static String encrypt(String plaintext, SecretKey key, byte[] salt) {
         try {
             Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

             byte[] iv = generateIv(cipher.getBlockSize());
             IvParameterSpec ivParams = new IvParameterSpec(iv);
             cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
             byte[] cipherText = cipher.doFinal(plaintext.getBytes("UTF-8"));

             if (salt != null) {
                 return String.format("%s%s%s%s%s", toBase64(salt), DELIMITER, toBase64(iv), DELIMITER,
                         toBase64(cipherText));
             }
             return String.format("%s%s%s", toBase64(iv), DELIMITER, toBase64(cipherText));
         } catch (GeneralSecurityException e) {
             throw new RuntimeException(e);
         } catch (UnsupportedEncodingException e) {
             throw new RuntimeException(e);
         }
     }

     public static String toHex(byte[] bytes) {
         StringBuffer buff = new StringBuffer();
         for (byte b : bytes) {
             buff.append(String.format("%02X", b));
         }
         return buff.toString();
     }

     public static String toBase64(byte[] bytes) {
         return Base64.encodeToString(bytes, Base64.NO_WRAP);
     }

     public static byte[] fromBase64(String base64) {
         return Base64.decode(base64, Base64.NO_WRAP);
     }

     public static String decrypt(byte[] cipherBytes, SecretKey key, byte[] iv) {
         try {
             Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
             IvParameterSpec ivParams = new IvParameterSpec(iv);
             cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
             byte[] plaintext = cipher.doFinal(cipherBytes);
             String plainrStr = new String(plaintext, "UTF-8");
             return plainrStr;
         } catch (GeneralSecurityException e) {
             throw new RuntimeException(e);
         } catch (UnsupportedEncodingException e) {
             throw new RuntimeException(e);
         }
     }

     public static String decryptPbkdf2(String ciphertext, String password) {
         String[] fields = ciphertext.split(DELIMITER);
         if (fields.length != 3) {
             throw new IllegalArgumentException("Invalid encypted text format");
         }
         byte[] salt = fromBase64(fields[0]);
         byte[] iv = fromBase64(fields[1]);
         byte[] cipherBytes = fromBase64(fields[2]);
         SecretKey key = deriveKeyPbkdf2(salt, password);
         return decrypt(cipherBytes, key, iv);
     }

     public static byte[] decryptBytePbkdf2(String ciphertext, String password) {

         try {

             String[] fields = ciphertext.split(DELIMITER);
             if (fields.length != 3) {
                 throw new IllegalArgumentException("Invalid encypted text format");
             }
             byte[] salt = fromBase64(fields[0]);
             byte[] iv = fromBase64(fields[1]);
             byte[] cipherBytes = fromBase64(fields[2]);
             SecretKey key = deriveKeyPbkdf2(salt, password);

             Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
             IvParameterSpec ivParams = new IvParameterSpec(iv);
             cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
             byte[] plaintext = cipher.doFinal(cipherBytes);
             return plaintext;
         } catch (GeneralSecurityException e) {
             throw new RuntimeException(e);
         }

     }

     public static byte[] encryptByte(byte[] plaintext, SecretKey key, byte[] salt) {
         try {
             Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

             byte[] iv = generateIv(cipher.getBlockSize());
             IvParameterSpec ivParams = new IvParameterSpec(iv);
             cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
             byte[] cipherText = cipher.doFinal(plaintext);

             if (salt != null) {
                 return String.format("%s%s%s%s%s", toBase64(salt), DELIMITER, toBase64(iv), DELIMITER,
                         toBase64(cipherText)).getBytes("UTF-8");
             }
             return String.format("%s%s%s", toBase64(iv), DELIMITER, toBase64(cipherText)).getBytes("UTF-8");
         } catch (GeneralSecurityException e) {
             throw new RuntimeException(e);
         } catch (UnsupportedEncodingException e) {
             throw new RuntimeException(e);
         }
     }
 }
