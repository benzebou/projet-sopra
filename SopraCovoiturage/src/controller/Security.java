package controller;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import android.util.*;

public class Security {
	
	/**
	 * Permet de crypter une chaîne de caractères à l'aide de la clé
	 * La méthode de cryptage est en AES sur une clé de 128 bits (soit 16 caractères)
	 * @param input : chaîne à crypter
	 * @param key : clé de cryptage (qui sert également au décryptage)
	 * @return la chaîne "input" cryptée
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
	 * Permet de décrypter une chaîne de caractères à l'aide de la clé
	 * La méthode de décryptage est en AES sur une clé de 128 bits (soit 16 caractères)
	 * @param input : chaîne à décrypter
	 * @param key : clé de décryptage (qui sert également au cryptage)
	 * @return la chaîne "input" décryptée
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
