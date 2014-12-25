package controller;

import java.util.HashMap;
import java.util.Map;

public class RequestReponses {

	/**
	 * Code de la reponse
	 */
	private int code;
	
	/**
	 * Boolean indiquant si la reponse est un succes ou non
	 */
	private boolean success;
	
	/**
	 * Map contenant les donnees de la reponse
	 */
	private Map<String,Object> data;
		
	/**
	 * Constructeur avec parametres
	 * @param code : code de la reponse
	 * @param success : boolean indiquant le succes ou l'echec de la requete
	 * @param map : donnee de la reponse
	 */
	public RequestReponses(int code, boolean success, Map<String, Object> map){
		this.code = code;
		this.success = success;
		if (map == null)
			this.data = new HashMap<String,Object>();
		else 
			this.data = map ;
	}

	/** 
	 * Getter du code
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Methode indiquant si la requete est un succes
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}
	
	/**
	 * Getter de la map des donnee
	 * @return map
	 */
	public Map<String, Object> getData() {
		return data;
	}
		
	/**
	 * Methode toString() de la classe RequestReponses
	 */
	public String toString(){
		return "Server code : "+code+"\nRequest successful ? : "+success+"\nData received : "+data;
	}
}
