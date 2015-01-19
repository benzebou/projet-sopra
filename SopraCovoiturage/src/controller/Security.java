package controller;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import android.util.*;

public class Security {
	
	/**
	 * Permet de crypter une cha�ne de caract�res � l'aide de la cl�
	 * La m�thode de cryptage est en AES sur une cl� de 128 bits (soit 16 caract�res)
	 * @param input : cha�ne � crypter
	 * @param key : cl� de cryptage (qui sert �galement au d�cryptage)
	 * @return la cha�ne "input" crypt�e
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
	 * Permet de d�crypter une cha�ne de caract�res � l'aide de la cl�
	 * La m�thode de d�cryptage est en AES sur une cl� de 128 bits (soit 16 caract�res)
	 * @param input : cha�ne � d�crypter
	 * @param key : cl� de d�cryptage (qui sert �galement au cryptage)
	 * @return la cha�ne "input" d�crypt�e
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
