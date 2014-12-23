package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.ParseException;
import org.json.JSONObject;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;


public class Requests {

	/**
	 * 
	 * @author alexandre
	 * Une requ�te HTTP POST est effectuee en utilisant la methode postRequest()
	 * Cette methode prend deux parametres, un type de requete et une hashmap contenant les parametres
	 * Remarque : les types de requetes sont ceux de la classe "requestJava"
	 * Parametres a fournir pour chaque requete :
	 *  CONNECT_USER : HashMap contenant les parametres (login,mdp) (exemple voir requete 1)
		CONNECT_ADMIN : HashMap contenant les parametres (login,mdp) (exemple voir requete 2)
		PASSWORD_FORGOTTEN : HashMap contenant les parametres (mail) (exemple voir requete 3)
		DISCONNECT : HashMap contenant les parametres (login,mdp) (exemple voir requete 4)
		REGISTER : HashMap contenant les parametres (...) (exemple voir requete 5)
		GET_PROFILE_INFORMATIONS : HashMap contenant les parametres (login) (exemple voir requete 6)
		MODIFY_PROFILE : HashMap contenant les parametres (...) (exemple voir requete 7)
		REMOVE_PROFILE : HashMap contenant les parametres (login) (exemple voir requete 8)
		SEARCH_RIDE : HashMap contenant les parametres (code,commune) (exemple voir requete 9)
		ADD_WORKPLACE : HashMap contenant les parametres (bureau) (exemple voir requete 10)
		GET_LIST_WORKPLACE : HashMap contenant aucun parametre (exemple voir requete 11)
		DELETE_WORKPLACE : HashMap contenant les parametres (login,mdp) (exemple voir requete 1)
		ADD_TOWN : HashMap contenant les parametres (code,commune) (exemple voir requete 1)
		GET_LIST_TOWN : HashMap contenant aucun parametres (exemple voir requete 1)
		DELETE_TOWN : HashMap contenant les parametres (code) (exemple voir requete 1)
		*
		*
		* La reponse est au format 
		*
		*
		*
	 */
	public class httpRequest {

		private String urlRequest = "http://127.0.0.1/public/http_post_entry.php";
		//private static String urlRequest = "http://etud.insa-toulouse.fr/~demeyer/http_post_entry.php";
		
		private String cookie;
		
		public void main(String[] args) throws IOException, requestException {
			
			// Connexion d'un utilisateur : login + mdp
			//HashMap<String,String> connectionParameters = new HashMap<String,String>();
			//connectionParameters.put("loginUser", "user2");
			//connectionParameters.put("mdp", "test");
			//System.out.println("/***** Requ�te 1 : Connexion d'un utilisateur *****/");
			//System.out.println(postRequest(requestType.CONNECT_USER,connectionParameters));
			
			// Connexion d'un administrateur : login + mdp
			HashMap<String,String> connectionParameters2 = new HashMap<String,String>();
			connectionParameters2.put("loginAdmin", "admin1");
			connectionParameters2.put("mdp", "sopra");
			System.out.println("/***** Requ�te 2 : Connexion d'un administrateur *****/");
			System.out.println(postRequest(RequestType.CONNECT_ADMIN,connectionParameters2));
			
			// Mot de passe oubli� : mail
			//HashMap<String,String> connectionParameters3 = new HashMap<String,String>();
			//connectionParameters3.put("mail", "user1@test.fr");
			//System.out.println("/***** Requ�te 3 : Mot de passe perdu *****/");
			//Map<String,String> m = postRequest(requestType.PASSWORD_FORGOTTEN,connectionParameters3);
			//System.out.println(m);
			
			// A VERIFIER
			// Deconnexion d'un utilisateur/administrateur : login + mdp (pour s'assurer que c'est bien lui)
			//HashMap<String,String> connectionParameters4 = new HashMap<String,String>();
			//connectionParameters4.put("login", "user2");
			//connectionParameters4.put("mdp", "test");
			//System.out.println("/***** Requ�te 4 : Deconnexion *****/");
			//System.out.println(postRequest(requestType.DISCONNECT,connectionParameters4));
			
			// Creation d'un utilisateur
			/*HashMap<String,String> connectionParameters5 = new HashMap<String,String>();
			connectionParameters5.put("login", "user2");
			connectionParameters5.put("mdp", "test");
			connectionParameters5.put("mail", "test@test.fr");
			connectionParameters5.put("nom", "a");
			connectionParameters5.put("prenom", "b");
			connectionParameters5.put("tel", "");
			connectionParameters5.put("postal", "31400");
			connectionParameters5.put("travail", "1");
			connectionParameters5.put("horairesMatin", "09:30");
			connectionParameters5.put("horairesSoir", "10:10");
			connectionParameters5.put("lundi", "1");
			connectionParameters5.put("mardi", "0");
			connectionParameters5.put("mercredi", "0");
			connectionParameters5.put("jeudi", "0");
			connectionParameters5.put("vendredi", "0");
			connectionParameters5.put("samedi", "1");
			connectionParameters5.put("dimanche", "1");
			connectionParameters5.put("conducteur", "0");*/
			//System.out.println("/***** Requ�te 5 : Creation de profil *****/");
			//System.out.println(postRequest(requestType.REGISTER,connectionParameters5));
			
			// Obtenir les informations d'un profil : login (utilisateur � afficher)
			//HashMap<String,String> connectionParameters6 = new HashMap<String,String>();
			//connectionParameters6.put("login", "user2");
			//System.out.println("/***** Requ�te 6 : Obtenir les informations d'un profil *****/");
			//System.out.println(postRequest(requestType.GET_PROFILE_INFORMATIONS,connectionParameters6));
			
			// Modifier les informations d'un profil
			/*HashMap<String,String> connectionParameters7 = new HashMap<String,String>();
			connectionParameters7.put("login", "user2");
			connectionParameters7.put("mdp", "test");
			connectionParameters7.put("mail", "azerty@test.fr");
			connectionParameters7.put("nom", "a");
			connectionParameters7.put("prenom", "b");
			connectionParameters7.put("tel", "");
			connectionParameters7.put("postal", "31400");
			connectionParameters7.put("travail", "1");
			connectionParameters7.put("horairesMatin", "09:30");
			connectionParameters7.put("horairesSoir", "10:10");
			connectionParameters7.put("lundi", "1");
			connectionParameters7.put("mardi", "0");
			connectionParameters7.put("mercredi", "0");
			connectionParameters7.put("jeudi", "0");
			connectionParameters7.put("vendredi", "0");
			connectionParameters7.put("samedi", "1");
			connectionParameters7.put("dimanche", "1");
			connectionParameters7.put("conducteur", "0");*/
			//System.out.println("/***** Requ�te 7 : Modifier les informations d'un profil *****/");
			//System.out.println(postRequest(requestType.MODIFY_PROFILE,connectionParameters7));
			
			// Supprimer un profil : login (utilisateur � supprimer)
			//HashMap<String,String> connectionParameters8 = new HashMap<String,String>();
			//connectionParameters8.put("login", "user2");
			//System.out.println("/***** Requ�te 8 : Suppression d'un profil *****/");
			//System.out.println(postRequest(requestType.REMOVE_PROFILE,connectionParameters8));

			// Rechercher les trajets : code postal + lieu de travail (l'un ou l'autre ou les deux)
			//HashMap<String,String> connectionParameters9 = new HashMap<String,String>();
			//connectionParameters9.put("postal", "31400");
			//connectionParameters9.put("travail", "3");
			//System.out.println("/***** Requ�te 9 : Suppression d'un profil *****/");
			//System.out.println(postRequest(requestType.SEARCH_RIDE,connectionParameters9));
			
			// Ajouter un lieu de travail : nom du bureau
			//HashMap<String,String> connectionParameters10 = new HashMap<String,String>();
			//connectionParameters10.put("bureau", "bureau4");
			//System.out.println("/***** Requ�te 10 : Ajouter un lieu de travail *****/");
			//System.out.println(postRequest(requestType.ADD_WORKPLACE,connectionParameters10));
			
			// Recuperation des lieux de travail
			//System.out.println("/***** Requ�te 11 : Recuperation des lieux de travail *****/");
			//String s = postRequest(requestType.GET_LIST_WORKPLACE,null);
			//System.out.println(s);
			
			// Suppression d'un lieu de travail : nom du bureau
			//HashMap<String,String> connectionParameters12 = new HashMap<String,String>();
			//connectionParameters12.put("idBureau", "4");
			//System.out.println("/***** Requ�te 12 : Suppression d'un lieu de travail *****/");
			//System.out.println(postRequest(requestType.DELETE_WORKPLACE,connectionParameters12));
			
			// Ajouter une commune : code + commune
			//HashMap<String,String> connectionParameters13 = new HashMap<String,String>();
			//connectionParameters13.put("code", "25000");
			//connectionParameters13.put("commune", "Besan�on");
			//System.out.println("/***** Requ�te 13 : Ajouter une commune *****/");
			//System.out.println(postRequest(requestType.ADD_TOWN,connectionParameters13));
			
			// Recuperation des codes postaux (avec chaque commune respective)
			System.out.println("/***** Requ�te 14 : Recuperation des lieux de travail *****/");
			RequestReponses response = postRequest(RequestType.GET_LIST_TOWN,null);
			System.out.println(response.toString());
			
			//Suppression d'une commune : code 
			//HashMap<String,String> connectionParameters15 = new HashMap<String,String>();
			//connectionParameters15.put("code", "25000");
			//System.out.println("/***** Requ�te 15 : Suppression d'une commune *****/");
			//System.out.println(postRequest(requestType.DELETE_TOWN,connectionParameters15));
			
			
		}
		
		private String getRequestParameters(RequestType typeOfRequest, HashMap<String,String> parameters){
			
			String urlParameters = "request=" + typeOfRequest.toString().toLowerCase();
			
			if (parameters != null){
				
				// We fetch parameters in an iterator
				Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
				
				// We add parameters
				while(it.hasNext()){
					Entry<String,String> couple = it.next();
					urlParameters += "&" + couple.getKey() + "=" + couple.getValue();
				}
				
			}
			
			return urlParameters;
		}
		
		@SuppressWarnings("unchecked")
		private HashMap<String,String> jsonToMap(String json){
			JSONObject parser = new JSONObject();
			  CursorFactory containerFactory = new CursorFactory(){
			    public List<String> creatArrayContainer() {
			      return new LinkedList<String>();
			    }

			    public Map<String,String> createObjectContainer() {
			      return new LinkedHashMap<String,String>();
			    }

				@Override
				public Cursor newCursor(SQLiteDatabase db,
						SQLiteCursorDriver masterQuery, String editTable,
						SQLiteQuery query) {
					// TODO Auto-generated method stub
					return null;
				}
			                        
			  };
			    Map<String,String> result = null;            
			  try{
				  Object obj = parser.parse(json, containerFactory);
				  if (obj instanceof LinkedList){
					  LinkedList<LinkedHashMap<String,String>> list = (LinkedList<LinkedHashMap<String,String>>)obj;
					  result = new HashMap<String,String>();
					  for (int i = 0; i < list.size(); i++){
						  LinkedHashMap<String,String> lhm = list.get(i);
						  Iterator<Entry<String, String>> it = lhm.entrySet().iterator();
						  Entry<String, String> entry = it.next();
						  String id = entry.getValue();
						  entry = it.next();
						  String value = entry.getValue();
						  result.put(id, value);
					  }
				  }
				  else
				  {
					  result = (Map<String,String>)obj;
				  }
			  }
			  catch(ParseException pe){
			    System.out.println("Erreur lors du decodage du format JSON : "+pe);
			  }
			  return (HashMap<String,String>)result;
		}
		
		/**
		 * Unique fonction qui sera appellee
		 * @param typeOfRequest
		 * @param urlParameters
		 * @return the response in the JSON format
		 */
		public RequestReponses postRequest(RequestType typeOfRequest, HashMap<String,String> parameters) {
		    String urlParameters = getRequestParameters(typeOfRequest,parameters);
			URL url;
			HttpURLConnection connection = null; 
		     
		    try {
		      //Create connection
		      url = new URL(urlRequest);
		      
		      connection = (HttpURLConnection)url.openConnection();
		      
		      connection.setRequestMethod("POST");
		      connection.setRequestProperty("Content-Type", 
		           "application/x-www-form-urlencoded");

		      connection.setRequestProperty("Content-Length", "" + 
		               Integer.toString(urlParameters.getBytes().length)); 

		      connection.setUseCaches (false);
		      connection.setDoInput(true);
		      connection.setDoOutput(true);
		      
		      // On verifie l'etat du cookie
		      if (cookie!=null){
		    	  connection.setRequestProperty("Cookie", cookie);
		    	  System.out.println("BEFORE" + connection.getRequestProperties());
		      }
		      // sinon c'est la premiere fois qu'on se connecte, on recuperera un cookie
		      
		      System.out.println(urlParameters);
		      
		      //Send request
		      DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
		      wr.writeBytes (urlParameters);
		      wr.flush ();
		      wr.close ();

		      //Get Response  
		      try{
		    	  // On teste s'il n'y a pas eut d'erreur pour la requ�te
			      if (connection.getResponseCode()/100 != 2){ throw new requestException(connection.getResponseCode());}
			      
			      System.out.println("AFTER" + connection.getHeaderFields());
			      
			      // S'il y a le champ "Set-Cookie", on r�cup�re le cookie
			      if (connection.getHeaderField("Set-Cookie") != null){
				      System.out.println(connection.getHeaderField("Set-Cookie"));
				      cookie = connection.getHeaderField("Set-Cookie");
			      }
			      else{
			    	  // Sinon on v�rifie l'�tat du cookie
			      }
			      
			      InputStream is = connection.getInputStream();
			      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			      String line;
			      StringBuffer response = new StringBuffer(); 
			      while((line = rd.readLine()) != null) {
			        response.append(line);
			        response.append('\r');
			      }
			      rd.close();
			      return new RequestReponses(connection.getResponseCode(),true,jsonToMap(response.toString()));
		      }catch(requestException e){
		    	  return new RequestReponses(connection.getResponseCode(),false,null);
		      }

		    } catch (Exception e) {

		      e.printStackTrace();
		      return new RequestReponses(0,false,null);

		    } finally {
		    	
		      if(connection != null) {
		        connection.disconnect(); 
		      }
		    }
		}
}
}