package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import modele.Information;
import modele.Ride;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Requests {

	/**
	 * 
	 * @author alexandre
	 * Une requête HTTP POST est effectuee en utilisant la methode postRequest()
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

		private static String urlRequest = "http://localhost/carpooling/http_post_entry.php";
		//private static String urlRequest = "http://etud.insa-toulouse.fr/~demeyer/http_post_entry.php";
		
		private static String cookie;
		
		
		public boolean connectionRequest (String nickname, String password) {
			HashMap<String, String> map = new HashMap<String, String> () ;
			map.put("loginAdmin", nickname) ;
			map.put("mdp", password) ;
			if (connectionAdminRequest(map))
				return true ;
			else { 
				map.remove("loginAdmin") ;
				map.put("loginUser", nickname) ;
				if (connectionUserRequest(map)) 
					return true ;
				else return false ;
			}
		}
		
		
		private boolean connectionUserRequest (HashMap<String, String> map) {
			RequestReponses reponse = null ;
			reponse = postRequest(RequestType.CONNECT_USER,map) ;
			if (reponse.isSuccess()) 
				return true ;
			else 
				return false ;
		}

		private boolean connectionAdminRequest (HashMap<String, String> map) {
			RequestReponses reponse = postRequest(RequestType.CONNECT_ADMIN,map) ;
			if (reponse.isSuccess()) 
				return true ;
			else 
				return false ;
		}
		
		
		public boolean passwordForgottenRequest (String mail) {
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("mail", mail);
			RequestReponses reponse = postRequest(RequestType.PASSWORD_FORGOTTEN,map) ;
			if (reponse.isSuccess()) 
				return true ;
			else 
				return false ;
		}
		
		
		public boolean creationUserRequest (Information info) {
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("login", info.getLogin()); 
			map.put("mdp", info.getMdp());
			map.put("mail", info.getEmail());
			map.put("nom", info.getName());
			map.put("prenom", info.getFirstname());
			map.put("tel", info.getPhone());
			map.put("postal", info.getPostcode());
			map.put("travail", info.getWorkplace());
			// TODO A MODIFIER
			map.put("horairesMatin", "09:30");
			map.put("horairesSoir", "10:10");
			
			if (info.getDays()[0]) 
				map.put("lundi", "1");
			else
				map.put("lundi", "0");
			if (info.getDays()[1]) 
				map.put("mardi", "1");
			else 
				map.put("mardi", "0");
			if (info.getDays()[2]) 
				map.put("mercredi", "1");
			else 
				map.put("mercredi", "0");
			if (info.getDays()[3]) 
				map.put("jeudi", "1");
			else 
				map.put("jeudi", "0");
			if (info.getDays()[4]) 
				map.put("vendredi", "1");
			else 
				map.put("vendredi", "0");
			if (info.getDays()[5]) 
				map.put("samedi", "1");
			else 
				map.put("samedi", "0");
			if (info.getDays()[6]) 
				map.put("dimanche", "1");
			else 
				map.put("dimanche", "0");
			
			if (info.isConducteur()) 
				map.put("conducteur", "1");
			else 
				map.put("conducteur", "0");
			
			RequestReponses reponse = postRequest(RequestType.REGISTER,map) ;
			if (reponse.isSuccess()) 
				return true ;
			else 
				return false ;
			}
		
		
		// pas de difference entre admin et user?
		public boolean disconnectionRequest (String nickname, String password) {
			HashMap<String, String> map = new HashMap<String, String> () ;
			map.put("login", nickname) ;
			map.put("mdp", password) ;
			RequestReponses reponse = null ;
			reponse = postRequest(RequestType.DISCONNECT,map) ;
			if (reponse.isSuccess()) 
				return true ;
			else 
				return false ;
		}
		
		// renvoyer les infos ?
		public Information getProfileInformationRequest(String nickname) {
			// Obtenir les informations d'un profil : login (utilisateur à afficher)
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("login", nickname);
			RequestReponses reponse = null ;
			reponse = postRequest(RequestType.GET_PROFILE_INFORMATIONS,map) ;
			if (reponse.isSuccess()) {
				Information info = new Information();
				reponse.getData() ;
				info.setLogin(reponse.getData().get("login"));
				info.setEmail(reponse.getData().get("mail"));
				info.setName(reponse.getData().get("nom"));
				info.setFirstname(reponse.getData().get("prenom"));
				info.setMdp(reponse.getData().get("mdp"));
				info.setPhone(reponse.getData().get("tel"));
				info.setWorkplace(reponse.getData().get("travail"));
				info.setPostcode(reponse.getData().get("postal"));
				if (reponse.getData().get("conducteur") == "1")
					info.setConducteur(true);
				else
					info.setConducteur(false);
				if (reponse.getData().get("lundi") == "1")
					info.getDays()[0] = true ;
				else
					info.getDays()[0] = false ;
				if (reponse.getData().get("mardi") == "1")
					info.getDays()[1] = true ;
				else
					info.getDays()[1] = false ;
				if (reponse.getData().get("mercredi") == "1")
					info.getDays()[2] = true ;
				else
					info.getDays()[2] = false ;
				if (reponse.getData().get("jeudi") == "1")
					info.getDays()[3] = true ;
				else
					info.getDays()[3] = false ;
				if (reponse.getData().get("vendredi") == "1")
					info.getDays()[4] = true ;
				else
					info.getDays()[4] = false ;
				if (reponse.getData().get("samedi") == "1")
					info.getDays()[5] = true ;
				else
					info.getDays()[5] = false ;
				if (reponse.getData().get("dimanche") == "1")
					info.getDays()[6] = true ;
				else
					info.getDays()[6] = false ;
				
				return info;
			}
			else 
				return null ;		
		}
		
		
		public boolean profileModificationRequest (Information info) {
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("login", info.getLogin()); 
			map.put("mdp", info.getMdp());
			map.put("mail", info.getEmail());
			map.put("nom", info.getName());
			map.put("prenom", info.getFirstname());
			map.put("tel", info.getPhone());
			map.put("postal", info.getPostcode());
			map.put("travail", info.getWorkplace());
			// TODO A MODIFIER
			map.put("horairesMatin", "09:30");
			map.put("horairesSoir", "10:10");
			
			if (info.getDays()[0]) 
				map.put("lundi", "1");
			else
				map.put("lundi", "0");
			if (info.getDays()[1]) 
				map.put("mardi", "1");
			else 
				map.put("mardi", "0");
			if (info.getDays()[2]) 
				map.put("mercredi", "1");
			else 
				map.put("mercredi", "0");
			if (info.getDays()[3]) 
				map.put("jeudi", "1");
			else 
				map.put("jeudi", "0");
			if (info.getDays()[4]) 
				map.put("vendredi", "1");
			else 
				map.put("vendredi", "0");
			if (info.getDays()[5]) 
				map.put("samedi", "1");
			else 
				map.put("samedi", "0");
			if (info.getDays()[6]) 
				map.put("dimanche", "1");
			else 
				map.put("dimanche", "0");
			
			if (info.isConducteur()) 
				map.put("conducteur", "1");
			else 
				map.put("conducteur", "0");
			
			RequestReponses reponse = null ;
			reponse = postRequest(RequestType.MODIFY_PROFILE,map) ;
			if (reponse.isSuccess()) {
				return true;
			}
			else 
				return false ;	
		}
		
		
		public boolean removeProfileRequest (String nickname) {
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("login", nickname);
			RequestReponses reponse = null ;
			reponse = postRequest(RequestType.REMOVE_PROFILE,map) ;
			if (reponse.isSuccess()) {
				return true;
			}
			else 
				return false ;
		}

		// List de ride?
		public ArrayList<Ride> ridesRequest (String postCode, String workplace) {
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("postal", postCode);
			map.put("bureau", workplace) ;
			RequestReponses reponse = null ;
			reponse = postRequest(RequestType.SEARCH_RIDE,map) ;
			if (reponse.isSuccess()) {
				ArrayList<Ride> rideList = new ArrayList<Ride> () ;
				// parcours de la HashMap
				//rideList.add(reponse.getData().get()) ;
				return rideList ;
			}
			else
				return null ;
		}
		
		// Rechercher les trajets : code postal + lieu de travail (l'un ou l'autre ou les deux)
		//HashMap<String,String> connectionParameters9 = new HashMap<String,String>();
		//connectionParameters9.put("postal", "31400");
		//connectionParameters9.put("travail", "3");
		//System.out.println("/***** Requête 9 : Suppression d'un profil *****/");
		//System.out.println(postRequest(requestType.SEARCH_RIDE,connectionParameters9));
		
		public static void main(String[] args) throws IOException, requestException {	

		
			// Ajouter un lieu de travail : nom du bureau
			//HashMap<String,String> connectionParameters10 = new HashMap<String,String>();
			//connectionParameters10.put("bureau", "bureau4");
			//System.out.println("/***** Requête 10 : Ajouter un lieu de travail *****/");
			//System.out.println(postRequest(requestType.ADD_WORKPLACE,connectionParameters10));
			
			// Recuperation des lieux de travail
			//System.out.println("/***** Requête 11 : Recuperation des lieux de travail *****/");
			//String s = postRequest(requestType.GET_LIST_WORKPLACE,null);
			//System.out.println(s);
			
			// Suppression d'un lieu de travail : nom du bureau
			//HashMap<String,String> connectionParameters12 = new HashMap<String,String>();
			//connectionParameters12.put("idBureau", "4");
			//System.out.println("/***** Requête 12 : Suppression d'un lieu de travail *****/");
			//System.out.println(postRequest(requestType.DELETE_WORKPLACE,connectionParameters12));
			
			// Ajouter une commune : code + commune
			//HashMap<String,String> connectionParameters13 = new HashMap<String,String>();
			//connectionParameters13.put("code", "25000");
			//connectionParameters13.put("commune", "Besançon");
			//System.out.println("/***** Requête 13 : Ajouter une commune *****/");
			//System.out.println(postRequest(requestType.ADD_TOWN,connectionParameters13));
			
			// Recuperation des codes postaux (avec chaque commune respective)
			System.out.println("/***** Requête 14 : Recuperation des lieux de travail *****/");
			RequestReponses response = postRequest(RequestType.GET_LIST_TOWN,null);
			System.out.println(response.toString());
			
			//Suppression d'une commune : code 
			//HashMap<String,String> connectionParameters15 = new HashMap<String,String>();
			//connectionParameters15.put("code", "25000");
			//System.out.println("/***** Requête 15 : Suppression d'une commune *****/");
			//System.out.println(postRequest(requestType.DELETE_TOWN,connectionParameters15));
			
			
		}
		
		private static String getRequestParameters(RequestType typeOfRequest, HashMap<String,String> parameters){
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
		private static HashMap<String,String> jsonToMap(String json){
			JSONParser parser = new JSONParser();
			  ContainerFactory containerFactory = new ContainerFactory(){
			    public List<String> creatArrayContainer() {
			      return new LinkedList<String>();
			    }

			    public Map<String,String> createObjectContainer() {
			      return new LinkedHashMap<String,String>();
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
		public static RequestReponses postRequest(RequestType typeOfRequest, HashMap<String,String> parameters) {
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
		    	  // On teste s'il n'y a pas eut d'erreur pour la requête
			      if (connection.getResponseCode()/100 != 2){ throw new requestException(connection.getResponseCode());}
			      
			      System.out.println("AFTER" + connection.getHeaderFields());
			      
			      // S'il y a le champ "Set-Cookie", on récupère le cookie
			      if (connection.getHeaderField("Set-Cookie") != null){
				      System.out.println(connection.getHeaderField("Set-Cookie"));
				      cookie = connection.getHeaderField("Set-Cookie");
			      }
			      else{
			    	  // Sinon on vérifie l'état du cookie
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
			      String a = response.toString();
			      System.out.println(a);
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
