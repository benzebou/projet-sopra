package controller;

import controller.Security;
import controller.RequestType;
import controller.RequestResponses;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import modele.Information;
import modele.Ride;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

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
	 * La reponse est au format JSON
	 *
	 *
	 *
	 */

	//private static String urlRequest = "http://localhost/carpooling/http_post_entry.php";
	//private static String urlRequest = "http://etud.insa-toulouse.fr/~demeyer/http_post_entry.php";
	private static String urlRequest = "http://sopcov.hol.es/http_post_entry.php";

	private static String cookie;

	private static int SYN_REQ = -100;

	private static int ACK_REQ = -100;

	private static String key = "1e2c3d5e9aa658cb";


	/**
	 * Methode permettant la connexion d'un utilisateur au serveur 
	 * @param nickname : login de l'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean[] connectionRequest (String nickname, String password) {
		boolean[] tab = new boolean[2] ; 
		HashMap<String, Object> map = new HashMap<String, Object> () ;
		map.put("loginAdmin", nickname) ;
		map.put("mdp", encryptPassword(password)) ;
		if (connectionAdminRequest(map)) {
			tab[0] = true ;
			tab[1] = true ;
		}
		else { 
			map.remove("loginAdmin") ;
			map.put("loginUser", nickname) ;
			if (connectionUserRequest(map)) {
				tab[0] = true ;
				tab[1] = false ;
			}	
			else {
				tab[0] = false ;
				tab[1] = false ;
			}
		}
		return tab ;
	}

	/**
	 * Methode appelee par "connectionRequest" si l'utilisateur n'est pas un admin
	 * Methode permettant la connexion d'un utilisateur au serveur 
	 * @param map
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	private boolean connectionUserRequest (HashMap<String, Object> map) {
		RequestsParams params = new RequestsParams(RequestType.CONNECT_USER,map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		Log.d("SC", "On attend...");
		try {
			RequestResponses result = task.get();
			Log.d("SC", "C'est fini !");
			if (result.isSuccess()) 
				return true ;
			else 
				return false ;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Methode appelee par "connectionRequest" si l'utilisateur est un administrateur
	 * Methode permettant la connexion d'un administrateur au serveur 
	 * @param map
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	private boolean connectionAdminRequest (HashMap<String, Object> map) {
		RequestsParams params = new RequestsParams(RequestType.CONNECT_ADMIN,map);

		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		Log.d("SC", "On attend...");
		try {
			RequestResponses result = task.get();
			Log.d("SC", "Result code : " + result.getCode());
			if (result.isSuccess()) {
				return true ;
			} else {
				return false ;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Methode permettant a un utilisateur de reinitialiser son mot de passe
	 * @param mail : mail de l'utilisateur
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean passwordForgottenRequest (String mail) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("mail", mail);
		RequestResponses reponse = postRequest(RequestType.PASSWORD_FORGOTTEN,map) ;
		if (reponse.isSuccess()) 
			return true ;
		else 
			return false ;
	}

	/**
	 * methode permettant d'inscrire un nouvel utilisateur
	 * @param info : informations du profil de l'utilisateur
	 * @return int : 0 si la requete s'est bien executee, code d'erreur sinon
	 */
	
	//TODO: Voir dans le catch ce qu'on renvoit 
	public int creationUserRequest (Information info) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("login", info.getLogin()); 
		map.put("mdp", encryptPassword(info.getMdp()));
		map.put("mail", info.getEmail());
		map.put("nom", info.getName());
		map.put("prenom", info.getFirstname());
		map.put("tel", info.getPhone());
		map.put("postal", info.getPostcode());
		map.put("travail", info.getWorkplace());
		map.put("horairesMatin", info.getMorning());
		map.put("horairesSoir", info.getEvening());

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

		RequestsParams params = new RequestsParams(RequestType.REGISTER,map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();
			//RequestResponses reponse = postRequest(RequestType.REGISTER,map) ;
			if (result.isSuccess()) 
				return 0 ;
			else 
				return result.getCode() ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Methode permettant la deconnexion d'un utilisateur au serveur 
	 * @param nickname : login de l'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean disconnectionRequest (String nickname, String password) {
		HashMap<String, Object> map = new HashMap<String, Object> () ;
		map.put("login", nickname) ;
		map.put("mdp", encryptPassword(password)) ;
		RequestResponses reponse = null ;
		reponse = postRequest(RequestType.DISCONNECT,map) ;
		if (reponse.isSuccess()) { 
			cookie = null ;
			return true ;
		}
		else 
			return false ;
	}

	/**
	 * Methode permettant de renvoyer les informations sur l'utilisateur ayant pour login nickname 
	 * @param nickname : login de l'utilisateur 
	 * @return Informations : informations sur l'utilisateur 
	 */
	public Information getProfileInformationRequest(String nickname) {
		// Obtenir les informations d'un profil : nickname (utilisateur � afficher)
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("login", nickname);
		RequestResponses result = null ;
		
		RequestsParams params = new RequestsParams(RequestType.GET_PROFILE_INFORMATIONS,map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		try {
			result = task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// reponse = postRequest(RequestType.GET_PROFILE_INFORMATIONS,map) ;
		if (result.isSuccess()) {
			Information info = new Information();
			result.getData() ;
			info.setLogin((String)result.getData().get("login"));
			info.setEmail((String)result.getData().get("mail"));
			info.setName((String)result.getData().get("nom"));
			info.setFirstname((String)result.getData().get("prenom"));
			info.setMdp((String)result.getData().get("mdp"));
			info.setPhone((String)result.getData().get("tel"));
			info.setWorkplace((String)result.getData().get("travail"));
			info.setPostcode((String)result.getData().get("postal"));
			info.setMorning((String)result.getData().get("horairesMatin"));
			info.setEvening((String)result.getData().get("horairesSoir"));
			if (result.getData().get("conducteur") == "1")
				info.setConducteur(true);
			else
				info.setConducteur(false);
			if (result.getData().get("lundi") == "1")
				info.getDays()[0] = true ;
			else
				info.getDays()[0] = false ;
			if (result.getData().get("mardi") == "1")
				info.getDays()[1] = true ;
			else
				info.getDays()[1] = false ;
			if (result.getData().get("mercredi") == "1")
				info.getDays()[2] = true ;
			else
				info.getDays()[2] = false ;
			if (result.getData().get("jeudi") == "1")
				info.getDays()[3] = true ;
			else
				info.getDays()[3] = false ;
			if (result.getData().get("vendredi") == "1")
				info.getDays()[4] = true ;
			else
				info.getDays()[4] = false ;
			if (result.getData().get("samedi") == "1")
				info.getDays()[5] = true ;
			else
				info.getDays()[5] = false ;
			if (result.getData().get("dimanche") == "1")
				info.getDays()[6] = true ;
			else
				info.getDays()[6] = false ;

			return info;
		}
		else 
			return null ;		
	}

	/**
	 * methode permettant de modifier le profil d'un utilisateur
	 * @param info : informations sur l'utilisateur
	 * @return int : 0 si la requete s'est bien executee, code d'erreur sinon
	 */
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
		map.put("horairesMatin", info.getMorning());
		map.put("horairesSoir", info.getEvening());

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

		RequestResponses result = null ;
		
		RequestsParams params = new RequestsParams(RequestType.MODIFY_PROFILE,map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		try {
			result = task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//reponse = postRequest(RequestType.MODIFY_PROFILE,map) ;
		if (result.isSuccess()) {
			return 0 ;
		}
		else 
			return result.getCode() ;	
	}

	/**
	 * Methode permettant de supprimer un utilisateur
	 * @param nickname : login de l'utilisateur a supprimer
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean removeProfileRequest (String nickname) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("login", nickname);
		RequestResponses reponse = null ;
		reponse = postRequest(RequestType.REMOVE_PROFILE,map) ;
		if (reponse.isSuccess()) {
			return true;
		}
		else 
			return false ;
	}

	/** Methode permettant de recuperer des trajets 
	 * @param postCode : code postal du lieu de depart
	 * @param workplace : lieu de travail (destination)
	 * @return ArrayList<Ride> : liste des trajets
	 */
	public ArrayList<Ride> ridesRequest (String postCode, String workplace) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("postal", postCode);
		map.put("bureau", workplace) ;
		RequestResponses reponse = null ;
		reponse = postRequest(RequestType.SEARCH_RIDE,map) ;
		if (reponse.isSuccess()) {
			ArrayList<Ride> rideList = new ArrayList<Ride> () ;

			// parcours de la HashMap
			for (Entry<String, Object> entry : reponse.getData().entrySet()) {
				LinkedHashMap nMapReponse = (LinkedHashMap) entry.getValue() ;
				// meme horaires
				boolean memeHoraires = false ;
				Ride ride = new Ride () ;
				for (int i=0; i<rideList.size(); i++) {
					if (rideList.get(i).getUserList().size() != 0) {
						if (rideList.get(i).getUserList().get(0).getMorning().equals((String)nMapReponse.get("horairesMatin"))) {
							if (rideList.get(i).getUserList().get(0).getEvening().equals((String)nMapReponse.get("horairesSoir"))) {
								memeHoraires = true ;
								ride = rideList.get(i) ;
								break ;
							}
						}
					}	
				}
				if (memeHoraires) {
					Information user = this.getProfileInformationRequest((String)nMapReponse.get("login")) ;
					ride.getUserList().add(user) ;
				}
				// different horaires de depart : nouveau ride dans la liste
				else {
					Ride nride = new Ride () ;
					Information user = this.getProfileInformationRequest((String)nMapReponse.get("login")) ;
					nride.getUserList().add(user) ;
					rideList.add(nride) ;
				}	
			}
			return rideList ;
		}
		else
			return null ;
	}

	/**
	 * Permet d'ajouter un lieu de travail
	 * @param workplace : lieu de travail a ajouter
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean addWorkplaceRequest(String workplace) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("bureau", workplace);
		RequestResponses reponse = postRequest(RequestType.ADD_WORKPLACE,map) ;
		if (reponse.isSuccess()) {
			return true;
		}
		else 
			return false ;
	}

	/**
	 * Methode permettant de supprimer un lieu de travail
	 * @param workplace : lieu de travail a supprimer
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean deletionWorkplaceRequest (String workplace) {			
		RequestResponses reponseBefore = postRequest(RequestType.GET_LIST_WORKPLACE,null) ;
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
			RequestResponses reponseAfter = postRequest(RequestType.DELETE_WORKPLACE,map) ;
			if (reponseAfter.isSuccess()) {
				return true;
			}
			else 
				return false ;
		}
		else return false ;
	}

	/**
	 * Methode permettant de renvoyer la liste des lieux de travail
	 * @return ArrayList<String> : liste des lieux de travail
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public ArrayList<String> getWorkplacesRequest() throws InterruptedException, ExecutionException {
		RequestsParams params = new RequestsParams(RequestType.GET_LIST_WORKPLACE,null);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result = task.get();

		ArrayList<String> workplaces = new ArrayList<String>() ;
		// parcours de la HashMap
		Map<String, Object> map = result.getData();
		if (map.isEmpty())
			workplaces.add("T'es baisée");
		for (Entry<String, Object> entry : map.entrySet()) {
			String MapReponse = (String) entry.getValue() ;
			workplaces.add(MapReponse) ;
			workplaces.add("Bla");
		}
		return workplaces ;
	}

	/**
	 * Methode permettant de renvoyer la liste des utilisateurs
	 * @return ArrayList<Information> : liste des utilisateurs
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public ArrayList<Information> getUsersRequest() throws InterruptedException, ExecutionException {
		RequestsParams params = new RequestsParams(RequestType.GET_LIST_USERS,null);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result = task.get();

		ArrayList<Information> users = new ArrayList<Information>() ;
		// parcours de la HashMap
		for (Entry<String, Object> entry : result.getData().entrySet()) {
			Information MapReponse = (Information) entry.getValue() ;
			users.add(MapReponse) ;
		}
		return users;
	}

	/**
	 * Permet d'ajouter une commune
	 * @param town : nom de la commune a ajouter
	 * @param postCode : code postal de la commune a ajouter
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean addTownRequest(String town, String postCode) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("code", postCode);
		map.put("commune", town);
		RequestResponses reponse = postRequest(RequestType.ADD_TOWN,map) ;
		if (reponse.isSuccess()) {
			return true;
		}
		else 
			return false ;
	}

	/**
	 * Methode permettant de supprimer une commune
	 * @param postCode : code postal de la commune a supprimer
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean deletionTownRequest(String postCode) {
		RequestResponses reponseBefore = postRequest(RequestType.GET_LIST_TOWN,null) ;
		// parcours de la HashMap
		String id = null ;
		for (Entry<String, Object> entry : reponseBefore.getData().entrySet()) {
			String MapReponse = (String) entry.getKey() ;
			if (MapReponse.equals(postCode)) {
				id = entry.getKey() ;
				break ;
			}
		}	
		if (id != null) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("code", id);
			RequestResponses reponseAfter = postRequest(RequestType.DELETE_TOWN,map) ;
			if (reponseAfter.isSuccess()) {
				return true;
			}
			else 
				return false ;
		}
		else return false ;
	}

	/**
	 * Methode permettant de renvoyer la liste des communes
	 * @return ArrayList<String> : liste des communes
	 */
	public ArrayList<String> getTownListRequest() {
		RequestResponses reponse = postRequest(RequestType.GET_LIST_TOWN,null) ;
		ArrayList<String> townList = new ArrayList<String>() ;
		// parcours de la HashMap
		for (Entry<String, Object> entry : reponse.getData().entrySet()) {
			String MapReponse = (String) entry.getValue() ;
			townList.add(MapReponse) ;
		}
		return townList ;
	}


	/**
	 * Methode permettant renvoyer le number de conducteurs et le nombre de passager
	 * @return String[] requete :
	 * si requete[0] = "-1" : echec et requete[1] = code erreur
	 * sinon requete[0] = nombre total de conducteurs 
	 * 	et requete[1] = nombre total de passagers 
	 */
	public String[] numberDriverAndPassengerRequest () {
		RequestResponses reponse = postRequest(RequestType.GET_STAT_DRIVERS_PASSENGERS,null) ;
		String[] tab = new String[2] ;
		if (reponse.isSuccess()) {
			System.out.println("SUCCES") ;
			tab[0] = (String) reponse.getData().get("drivers") ;
			tab[1] = (String) reponse.getData().get("passengers") ;
		}
		else {
			tab[0] = "-1" ;
			tab[1] = ((Integer)reponse.getCode()).toString() ;
		}
		return tab ;
	}


	/**
	 * Methode permettant de renvoyer le number de conducteurs et le nombre de passager par trajet
	 * @return HashMap<String,String[]>
	 * Key = String : trajet
	 * Value = String[0] : nombre de conducteurs ; String[1] : nombre de passagers 
	 */
	public HashMap<String,String[]> numberDriverAndPassengerPerRideRequest () {
		RequestResponses reponse = postRequest(RequestType.GET_STAT_DRIVERS_PASSENGERS_PER_RIDE,null) ;
		HashMap<String,String[]> requete = new HashMap<String,String[]> () ;
		String[] tab = new String[2] ;
		if (reponse.isSuccess()) {
			// parcours de la HashMap
			for (Entry<String, Object> entry : reponse.getData().entrySet()) {
				LinkedHashMap nMapReponse = (LinkedHashMap) entry.getValue() ;

				/**********************************************************
				 * 				AFFICHAGE POUR TEST
				 **********************************************************/
				System.out.println("RIDE : "+entry.getKey()) ;
				System.out.println("Drivers : "+((Long) nMapReponse.get("drivers")).toString()) ;
				System.out.println("Passengers : "+((Long) nMapReponse.get("passengers")).toString()) ;

				tab[0] = ((Long) nMapReponse.get("drivers")).toString() ;
				tab[1] = ((Long) nMapReponse.get("passengers")).toString() ;
				requete.put(entry.getKey(), tab) ;
			}
		}
		else {
			tab[0] = ((Integer)reponse.getCode()).toString() ;
			tab[1] = ((Integer)reponse.getCode()).toString() ;
			requete.put("-1", tab) ;
		}
		return requete ;
	}

	/**
	 * Methode permettant de renvoyer le nombre total de connexions
	 * @return String[] requete :
	 * si requete[0] = "0" : succes et requete[1] = nombre total de connexions
	 * sinon requete[0] = "-1" : echec et requete[1] = code erreur 
	 */
	public String[] numberConnectionRequest () {
		RequestResponses reponse = postRequest(RequestType.GET_STAT_CONNECTIONS,null) ;
		String number = "-1" ;
		String[] tab = new String[2] ;
		if (reponse.isSuccess()) {
			for (Entry<String, Object> entry : reponse.getData().entrySet()) {
				number = (String) entry.getValue() ;
			}
			tab[0] = "0" ;
			tab[1] = number ;
		}
		else {
			tab[0] = "-1" ;
			tab[1] = ((Integer) reponse.getCode()).toString() ;
		}
		return tab ;
	}

	/**
	 * Methode permettant de renvoyer le nombre total de connexions a une date donnee
	 * @param date : data a laquelle on compte le nombre de connexions
	 * @return String[] requete :
	 * si requete[0] = "0" : succes et requete[1] = nombre total de connexions a la date
	 * sinon requete[0] = "-1" : echec et requete[1] = code erreur
	 */
	public String[] numberConnectionDateRequest (String date) {
		String[] tab = new String[2] ;
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("date", date) ;
		RequestResponses reponse = postRequest(RequestType.GET_STAT_CONNECTIONS,map) ;
		if (reponse.isSuccess()) {
			tab[0] = "0" ;
			tab[1] = (String) reponse.getData().get(date);
		}
		else {
			tab[0] = "-1" ;
			tab[1] = ((Integer) reponse.getCode()).toString() ;
		}
		return tab ;
	}


	/**
	 * Methode permettant de renvoyer le nombre total de connexions depuis une date donnee
	 * @param date : data a partir de laquelle on compte le nombre de connexions
	 * @return String[] requete :
	 * si requete[0] = "0" : succes et requete[1] = nombre total de connexions depuis la date
	 * sinon requete[0] = "-1" : echec et requete[1] = code erreur
	 */
	public String[] numberConnectionSinceRequest (String date) {
		String[] tab = new String[2] ;
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("sinceDate", date) ;
		RequestResponses reponse = postRequest(RequestType.GET_STAT_CONNECTIONS,map) ;
		if (reponse.isSuccess()) {
			tab[0] = "0" ;
			tab[1] = (String) reponse.getData().get(date);
		}
		else {
			tab[0] = "-1" ;
			tab[1] = ((Integer) reponse.getCode()).toString() ;
		}
		return tab ;
	}		



	/**
	 * Methode permettant de renvoyer le nombre total de connexions entre deux dates donnees
	 * @param dateFirst : date de debut de l'intervalle
	 * @param dateLast : date de fin de l'intervalle
	 * @return String[] requete :
	 * si requete[0] = "0" : succes et requete[1] = nombre total de connexions entre les deux dates
	 * sinon requete[0] = "-1" : echec et requete[1] = code erreur
	 */
	public String[] numberConnectionBetweenRequest (String firstDate, String lastDate) {
		String[] tab = new String[2] ;
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("rangeFirst", firstDate) ;
		map.put("rangeLast", lastDate) ;
		RequestResponses reponse = postRequest(RequestType.GET_STAT_CONNECTIONS,map) ;
		if (reponse.isSuccess()) {
			tab[0] = "0" ;
			tab[1] = (String) reponse.getData().get(firstDate);
		}
		else {
			tab[0] = "-1" ;
			tab[1] = ((Integer) reponse.getCode()).toString() ;
		}
		return tab ;
	}	


	/**
	 * Methode permettant de recuperer les parametres d'une requete
	 * @param typeOfRequest : type de la requete
	 * @param map 
	 * @return parametres de la requete sous format "parametres d'url"
	 */
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

		if (typeOfRequest.equals(RequestType.CONNECT_USER) || typeOfRequest.equals(RequestType.CONNECT_ADMIN)){
			Random rand = new Random();
			SYN_REQ = rand.nextInt(1000000)+1;
			ACK_REQ = -1;
			urlParameters += "&SYN_REQ="+SYN_REQ;
		}
		else
		{
			SYN_REQ++;
			urlParameters += "&SYN_REQ="+SYN_REQ+"&ACK_REQ="+ACK_REQ;
		}

		return urlParameters;
	}

	private static Map<String,Object> jsonToMap(String json, boolean firstConnection){
		JSONObject JSONStrings;
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			JSONStrings = new JSONObject(json);
			JSONStrings.toString();
			Iterator<String> it = JSONStrings.keys();
			while(it.hasNext()) {
				String value = it.next();
				Object object = JSONStrings.get(value);
				result.put(value, object);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (firstConnection)
		{
			ACK_REQ = Integer.parseInt(result.get("SYN_REQ").toString());
			if(Integer.parseInt(result.get("ACK_REQ").toString()) != SYN_REQ){
				System.out.println("Attention, les numeros de sequences des requetes cryptes sont differents");
			}
		}
		else
		{
			if(Integer.parseInt(result.get("SYN_REQ").toString()) != ACK_REQ+1 || Integer.parseInt(result.get("ACK_REQ").toString()) != SYN_REQ){
				System.out.println("Attention, les numeros de sequences des requetes cryptes sont differents");
			}
			ACK_REQ++;
		}
		if (result.containsKey("SYN_REQ"))
			result.remove("SYN_REQ");
		if (result.containsKey("ACK_REQ"))
			result.remove("ACK_REQ");
		return result;
	}
	/**
	 * Permet de crypter (hasher) le mot de passe
	 * @param password : le mot de passe a crypte
	 * @return le mot de passe crypte
	 */
	private static String encryptPassword(String password){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());

			byte byteData[] = md.digest();

			//convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return "";
		}
	}

	/**
	 * Unique fonction qui sera appellee : effectue une requete
	 * @param typeOfRequest
	 * @param urlParameters
	 * @return the response in the JSON format
	 */
	public static RequestResponses postRequest(RequestType typeOfRequest, HashMap<String,Object> parameters) {
		Log.d("SC", "postRequest commence");
		String urlParameters = getRequestParameters(typeOfRequest,parameters);
		
		// On encrypte les donnees
		urlParameters = "requestPHP="+Security.encrypt(urlParameters, key);
		
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

			Log.d("SC", "on regarde la r�ponse");
			System.out.println();
			//Get Response  
			try{
				// On teste s'il n'y a pas eut d'erreur pour la requ�te
				if (connection.getResponseCode()/100 != 2){ throw new requestException(connection.getResponseCode());}

				System.out.println("AFTER" + connection.getHeaderFields());

				// S'il y a le champ "Set-Cookie", on r�cup�re le cookie
				if (connection.getHeaderField("Set-Cookie") != null){

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
				System.out.println("Crypted : "+response.toString());
				String responseDecrypted = Security.decrypt(response.toString(), key);
				System.out.println("Decrypted : "+responseDecrypted);
				RequestResponses requestResponse = null;
				if (typeOfRequest.equals(RequestType.CONNECT_USER) || typeOfRequest.equals(RequestType.CONNECT_ADMIN)){
					requestResponse = new RequestResponses(connection.getResponseCode(),true,jsonToMap(responseDecrypted,true));
				}
				else
				{
					requestResponse = new RequestResponses(connection.getResponseCode(),true,jsonToMap(responseDecrypted,false));
				}
				Log.d("SC", "postRequest finit");
				return requestResponse;
			}catch(requestException e){
				Log.d("SC", "postRequest finit avec requestException");
				return new RequestResponses(connection.getResponseCode(),false,null);
			}

		} catch (Exception e) {

			e.printStackTrace();
			Log.d("SC", "postRequest finit avec Exception");
			return new RequestResponses(0,false,null);

		} finally {

			if(connection != null) {
				connection.disconnect(); 
			}
		}
	}

	private class HTTPAsyncTask extends AsyncTask<RequestsParams, Void, RequestResponses> {
		@Override
		protected RequestResponses doInBackground(RequestsParams... arg0) {
			RequestsParams params = arg0[0];
			RequestResponses result = postRequest(params.getTypeOfRequest(), params.getParameters());
			Log.d("SC", "on return la r�ponse");
			return result;
		}

	}

}
