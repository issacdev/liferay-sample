package com.dbs.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class CipherHelper {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public String encrypt(String value){
		try{
			Cipher cipher;
			
			Key key = getKey();
			
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			
			return new String (Base64.encode(cipher.doFinal(value.getBytes("UTF-8"))));
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public String decrypt(String value){
		try{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			Key key = getKey();

			cipher.init(Cipher.DECRYPT_MODE,key,cipher.getParameters());
			byte[] byteDecryptedText = cipher.doFinal(Base64.decode(value.getBytes("UTF-8")));
			return new String(byteDecryptedText);
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public Key getKey() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException{
		MessageDigest digester = MessageDigest.getInstance("MD5");
		String key = "Abcd1234";
		char[] password = key.toCharArray();
		for (int i = 0; i < password.length; i++) {
		digester.update((byte) password[i]);
		}
		byte[] passwordData = digester.digest();

		Key secretkey = new SecretKeySpec(passwordData, "AES");
		
		return secretkey;
	}
}