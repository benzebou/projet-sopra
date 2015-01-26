package controller;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import android.util.*;

public class Security {
	
	/**
	 * Permet de crypter une chaene de caracteres e l'aide de la cle
	 * La methode de cryptage est en AES sur une cle de 128 bits (soit 16 caracteres)
	 * @param input : chaene e crypter
	 * @param key : cle de cryptage (qui sert egalement au decryptage)
	 * @return la chaene "input" cryptee
	 */
	public static String encrypt(String input, String key){
	  byte[] crypted = null;
	  try{
	    SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.ENCRYPT_MODE, skey);
	      crypted = cipher.doFinal(input.getBytes());
	    }catch(Exception e){
	    	System.out.println(e.toString());
	    }
	    return new String(Base64.encode(crypted,Base64.DEFAULT));
	}

	/**
	 * Permet de decrypter une chaene de caracteres e l'aide de la cle
	 * La methode de decryptage est en AES sur une cle de 128 bits (soit 16 caracteres)
	 * @param input : chaene e decrypter
	 * @param key : cle de decryptage (qui sert egalement au cryptage)
	 * @return la chaene "input" decryptee
	 */
	public static String decrypt(String input, String key){
	    byte[] output = null;
	    try{
	      SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.DECRYPT_MODE, skey);
	      output = cipher.doFinal(Base64.decode(input,Base64.DEFAULT));
	    }catch(Exception e){
	      System.out.println(e.toString());
	    }
	    return new String(output);
	}	
}
