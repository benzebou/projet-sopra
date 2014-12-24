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

		private static String urlRequest = "http://localhost/carpooling/http_post_entry.php";
		//private static String urlRequest = "http://etud.insa-toulouse.fr/~demeyer/http_post_entry.php";
		
		private static String cookie;
		
		
		public boolean connectionRequest (String nickname, String password) {
			HashMap<String, Object> map = new HashMap<String, Object> () ;
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
		
		
		private boolean connectionUserRequest (HashMap<String, Object> map) {
			RequestReponses reponse = null ;
			reponse = postRequest(RequestType.CONNECT_USER,map) ;
			if (reponse.isSuccess()) {
				return true ; }
			else 
				return false ;
		}

		private boolean connectionAdminRequest (HashMap<String, Object> map) {
			RequestReponses reponse = postRequest(RequestType.CONNECT_ADMIN,map) ;
			if (reponse.isSuccess()) 
				return true ;
			else 
				return false ;
		}
		
		
		public boolean passwordForgottenRequest (String mail) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("mail", mail);
			RequestReponses reponse = postRequest(RequestType.PASSWORD_FORGOTTEN,map) ;
			if (reponse.isSuccess()) 
				return true ;
			else 
				return false ;
		}
		
		
		public int creationUserRequest (Information info) {
			HashMap<String,Object> map = new HashMap<String,Object>();
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
				return 0 ;
			else 
				return reponse.getCode() ;
			}
		
		
		// pas de difference entre admin et user?
		public boolean disconnectionRequest (String nickname, String password) {
			HashMap<String, Object> map = new HashMap<String, Object> () ;
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
			// Obtenir les informations d'un profil : login (utilisateur � afficher)
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("login", nickname);
			RequestReponses reponse = null ;
			reponse = postRequest(RequestType.GET_PROFILE_INFORMATIONS,map) ;
			if (reponse.isSuccess()) {
				Information info = new Information();
				reponse.getData() ;
				info.setLogin((String)reponse.getData().get("login"));
				info.setEmail((String)reponse.getData().get("mail"));
				info.setName((String)reponse.getData().get("nom"));
				info.setFirstname((String)reponse.getData().get("prenom"));
				info.setMdp((String)reponse.getData().get("mdp"));
				info.setPhone((String)reponse.getData().get("tel"));
				info.setWorkplace((String)reponse.getData().get("travail"));
				info.setPostcode((String)reponse.getData().get("postal"));
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
		
		
		public int profileModificationRequest (Information info) {
			HashMap<String,Object> map = new HashMap<String,Object>();
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
				return 0 ;
			}
			else 
				return reponse.getCode() ;	
		}
		
		
		public boolean removeProfileRequest (String nickname) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("login", nickname);
			RequestReponses reponse = null ;
			reponse = postRequest(RequestType.REMOVE_PROFILE,map) ;
			if (reponse.isSuccess()) {
				return true;
			}
			else 
				return false ;
		}

		
		public ArrayList<Ride> ridesRequest (String postCode, String workplace) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("postal", postCode);
			map.put("bureau", workplace) ;
			RequestReponses reponse = null ;
			reponse = postRequest(RequestType.SEARCH_RIDE,map) ;
			if (reponse.isSuccess()) {
				ArrayList<Ride> rideList = new ArrayList<Ride> () ;

				// parcours de la HashMap
				for (Entry<String, Object> entry : reponse.getData().entrySet()) {
					LinkedHashMap nMapReponse = (LinkedHashMap) entry.getValue() ;
					Ride nride = new Ride () ;
					nride.setDepart((String)nMapReponse.get("postal"));
					nride.setArrivee((String)nMapReponse.get("travail"));
					nride.setHeureDepart((String)nMapReponse.get("horairesMatin"));
					if (nMapReponse.get("conducteur") == "1")
						nride.getUserList().put((String)nMapReponse.get("login"), true) ;
					else 
						nride.getUserList().put((String)nMapReponse.get("login"), false) ;
					rideList.add(nride) ;
				}
				
				return rideList ;
			}
			else
				return null ;
		}
		

		public boolean addWorkplaceRequest(String workplace) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("bureau", workplace);
			RequestReponses reponse = postRequest(RequestType.ADD_WORKPLACE,map) ;
			if (reponse.isSuccess()) {
				return true;
			}
			else 
				return false ;
		}
		
		public boolean deletionWorkplaceRequest (String workplace) {			
			RequestReponses reponseBefore = postRequest(RequestType.GET_LIST_WORKPLACE,null) ;
			// parcours de la HashMap
			String id = null ;
			for (Entry<String, Object> entry : reponseBefore.getData().entrySet()) {
				String MapReponse = (String) entry.getValue() ;
				if (MapReponse.equals(workplace)) {
					id = entry.getKey() ;
					break ;
				}
			}	
			if (id != null) {
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("idBureau", id);
				RequestReponses reponseAfter = postRequest(RequestType.DELETE_WORKPLACE,map) ;
				if (reponseAfter.isSuccess()) {
					return true;
				}
				else 
					return false ;
			}
			else return false ;
		}
		
		public ArrayList<String> getWorkplacesRequest() {
			RequestReponses reponse = postRequest(RequestType.GET_LIST_WORKPLACE,null) ;
			ArrayList<String> workplaces = new ArrayList<String>() ;
			// parcours de la HashMap
			for (Entry<String, Object> entry : reponse.getData().entrySet()) {
				String MapReponse = (String) entry.getValue() ;
				workplaces.add(MapReponse) ;
			}
			return workplaces ;
		}
		
		public static void main(String[] args) throws IOException, requestException {	

		
			// Ajouter une commune : code + commune
			//HashMap<String,String> connectionParameters13 = new HashMap<String,String>();
			//connectionParameters13.put("code", "25000");
			//connectionParameters13.put("commune", "Besan�on");
			//System.out.println("/***** Requ�te 13 : Ajouter une commune *****/");
			//System.out.println(postRequest(requestType.ADD_TOWN,connectionParameters13));
			
			//Suppression d'une commune : code 
			//HashMap<String,String> connectionParameters15 = new HashMap<String,String>();
			//connectionParameters15.put("code", "25000");
			//System.out.println("/***** Requ�te 15 : Suppression d'une commune *****/");
			//System.out.println(postRequest(requestType.DELETE_TOWN,connectionParameters15));
			
		}
		
		private static String getRequestParameters(RequestType typeOfRequest, HashMap<String, Object> map){
			String urlParameters = "request=" + typeOfRequest.toString().toLowerCase();
			if (map != null){
				
				// We fetch parameters in an iterator
				Iterator<Entry<String, Object>> it = map.entrySet().iterator();
				
				// We add parameters
				while(it.hasNext()){
					Entry<String,Object> couple = it.next();
					urlParameters += "&" + couple.getKey() + "=" + couple.getValue();
				}
			}
			return urlParameters;
		}
		
		@SuppressWarnings("unchecked")
		private static Map<String,Object> jsonToMap(String json){
			JSONParser parser = new JSONParser();
			  ContainerFactory containerFactory = new ContainerFactory(){
			    public List<String> creatArrayContainer() {
			      return new LinkedList<String>();
			    }

			    public Map<String,String> createObjectContainer() {
			      return new LinkedHashMap<String,String>();
			    }
			                        
			  };
			    Map<String,Object> result = null;
			    
			  try{
				  Object obj = parser.parse(json, containerFactory);
				  if (obj instanceof LinkedList){
					  
					  LinkedList<LinkedHashMap<String,Object>> list = (LinkedList<LinkedHashMap<String,Object>>)obj;
					  result = new HashMap<String,Object>();
					  for (int i = 0; i < list.size(); i++){
						  LinkedHashMap<String,Object> lhm = list.get(i);
						  Iterator<Entry<String, Object>> it = lhm.entrySet().iterator();
						  Entry<String, Object> entry = it.next();
						  String id = (String)entry.getValue();
						  entry = it.next();
						  Object value = entry.getValue();
						  result.put(id, value);
					  }
				  }
				  else
				  {
					  result = (Map<String,Object>)obj;
				  }
			  }
			  catch(ParseException pe){
			    System.out.println("Erreur lors du decodage du format JSON : "+pe+", veuillez v�rifier le message re�u par l'application dans la m�thode postRequest avant de transformer la r�ponse JSON en classe");
			  }
			  return (Map<String,Object>)result;
		}
		
		/**
		 * Unique fonction qui sera appellee
		 * @param typeOfRequest
		 * @param urlParameters
		 * @return the response in the JSON format
		 */
		public static RequestReponses postRequest(RequestType typeOfRequest, HashMap<String, Object> map) {
		    String urlParameters = getRequestParameters(typeOfRequest,map);
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
