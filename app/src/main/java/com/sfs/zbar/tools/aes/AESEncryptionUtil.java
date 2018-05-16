package com.sfs.zbar.tools.aes;

import android.content.Context;

import com.sfs.zbar.tools.CommonTools;
import com.sfs.zbar.tools.Logg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class AESEncryptionUtil {
	
	public static final String CIPHER_ALGORITHM = "PBEWITHSHA256AND128BITAES-CBC-BC";
	public static final String ARITH_CHOICE = "PBKDF2WithHmacSHA1";
	private static final int BUF_SIZE = 8192;

	
	/**
	 * 加密可序列化对象
	 * @param source
	 * @param pwd
	 * @return
	 */
	public static Serializable encryptObject(Serializable source, String pwd) {

		try {
			Cipher cipher = getEncryptCipher(pwd);
			return new SealedObject(source, cipher);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	
	/**
	 * 计算加密方式。
	 * @param pwd
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 */
	private static Cipher getEncryptCipher(String pwd) throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Logg.v("wzcp.randy","getEncryptCipher 密码："+pwd);
	    if (null==pwd) {
	    	Logg.v("wzcp.randy","getEncryptCipher 密码==null");
	        pwd = CommonTools.getUUID();
        }else{
            Logg.v("wzcp.randy","getEncryptCipher 密码！=null");
        }
		char[] passPherase = pwd.toCharArray();
		byte[] salt = pwd.getBytes();

		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 20);
		PBEKeySpec pbeKeySpec = new PBEKeySpec(passPherase, salt, 1024, 128);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(CIPHER_ALGORITHM);
		SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParamSpec);
		return cipher;
	}

	/**
	 * 解密可序列化对象，解密完毕以后可以直接强制性转换成Model使用。
	 * @param o
	 * @param key
	 * @return
	 */
	public static Serializable decryptObject(Serializable o, String pwd) {
		Serializable result = null;
		try {

			Cipher cipher = getDecryptCipher(pwd);
			result = (Serializable)((SealedObject)o).getObject(cipher);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 获得解密方式
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 */
	private static Cipher getDecryptCipher(String pwd) throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		char[] passPherase = pwd.toCharArray();
		byte[] salt = pwd.getBytes();
		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 20);
		PBEKeySpec pbeKeySpec = new PBEKeySpec(passPherase, salt, 1024, 128);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(CIPHER_ALGORITHM);
		SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, pbeParamSpec);
		return cipher;
	}

	/**
	 * 加密普通的字符串
	 * @param plaintext
	 * @param password
	 * @return
	 */
	public static String encryptString(String plaintext, String password) {
		byte[] salt = Crypto.generateSalt();
		String result = Crypto.encrypt(plaintext, Crypto.deriveKeyPbkdf2(salt, password), salt);
		return result;
	}

	/**
	 * 解密普通的字符串
	 * @param ciphertext
	 * @param password
	 * @return
	 */
	public static String decryptString(String ciphertext, String password) {
		String result = Crypto.decryptPbkdf2(ciphertext, password);
		return result;
	}

	/**
	 * 加密byte字节流
	 * @param p12
	 * @param encrpytFile
	 * @param password
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static void encrypt(byte[] p12, File encrpytFile, String password) throws IOException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException,
			InvalidAlgorithmParameterException {
		FileOutputStream fos = new FileOutputStream(encrpytFile);
		CipherOutputStream cos = new CipherOutputStream(fos, getEncryptCipher(password));
		cos.write(p12, 0, p12.length);
		cos.flush();
		cos.close();
		fos.close();
	}

	/**
	 * 解密byte字节流
	 * @param encryptedFile
	 * @param password
	 * @param context
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static byte[] decryptByte(File encryptedFile, String password, Context context) throws IOException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException,
			InvalidAlgorithmParameterException {
		FileInputStream fis = null;
		try {
			fis = context.openFileInput(encryptedFile.getName());
			byte[] buffer = new byte[BUF_SIZE];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int amtRead = fis.read(buffer, 0, BUF_SIZE);
			while (amtRead > 0) {
				baos.write(buffer, 0, amtRead);
				amtRead = fis.read(buffer, 0, BUF_SIZE);
			}
			byte[] encryptedBytes = baos.toByteArray();
			String encryptedString = new String(encryptedBytes, "UTF-8");
			return Crypto.decryptBytePbkdf2(encryptedString, password);
		} catch (Exception e) {
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}