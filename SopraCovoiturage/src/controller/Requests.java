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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modele.Information;
import modele.Ride;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class Requests {

	/**
	 * 
	 * @author alexandre
	 * Une requï¿½te HTTP POST est effectuee en utilisant la methode postRequest()
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
	
	private HashMap<String, String> mapWorkplaces = new HashMap<String, String>();


	/**
	 * Methode permettant la connexion d'un utilisateur au serveur 
	 * @param nickname : login de l'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean[] connectionRequest (String nickname, String password) {
		cookie = null;
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
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
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
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
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
		
		RequestsParams params = new RequestsParams(RequestType.PASSWORD_FORGOTTEN,map);

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
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * methode permettant d'inscrire un nouvel utilisateur
	 * @param info : informations du profil de l'utilisateur
	 * @return int : 0 si la requete s'est bien executee, code d'erreur sinon
	 */
	
	
	public int creationUserRequest (Information info) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("login", info.getLogin()); 
		map.put("mdp", encryptPassword(info.getMdp()));
		map.put("mail", info.getEmail());
		map.put("nom", info.getName());
		map.put("prenom", info.getFirstname());
		map.put("tel", info.getPhone());
		map.put("postal", info.getPostcode());
		
		// recuperation de l'ID du workplace
		String id = null ;
		Iterator<Entry<String, String>> it = mapWorkplaces.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> workplace = it.next();
			if(workplace.getValue().contentEquals(info.getWorkplace()))
				id = workplace.getKey();
				break;
		}
		
		map.put("travail", id); // TODO : Vraiment travail le nom de l'attribut ?
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

		RequestsParams params = new RequestsParams(RequestType.REGISTER, map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();
			if (result.isSuccess()) 
				return 0 ;
			else 
				return result.getCode() ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		} catch (ExecutionException e) {
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
	public boolean disconnectionRequest () {
		RequestResponses result = null ;
		RequestsParams params = new RequestsParams(RequestType.DISCONNECT, null);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		try {
			result = task.get();
			if (result.isSuccess()) { 
				cookie = null ;
				return true ;
			}
			else 
				return false ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Methode permettant de renvoyer les informations sur l'utilisateur ayant pour login nickname 
	 * @param nickname : login de l'utilisateur 
	 * @return Informations : informations sur l'utilisateur 
	 */
	public Information getProfileInformationRequest(String nickname) {
		// Obtenir les informations d'un profil : nickname (utilisateur ï¿½ afficher)
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("login", nickname);
		
		RequestResponses result;
		RequestsParams params = new RequestsParams(RequestType.GET_PROFILE_INFORMATIONS,map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		
		try {
			result = task.get();
			
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
			} else 
				return null ;	
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
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

		RequestResponses result;
		RequestsParams params = new RequestsParams(RequestType.MODIFY_PROFILE,map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		try {
			result = task.get();
	
			if (result.isSuccess()) 
				return 0 ;
			else 
				return result.getCode() ;	
		
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Methode permettant de supprimer un utilisateur
	 * @param nickname : login de l'utilisateur a supprimer
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean removeProfileRequest (String nickname) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("login", nickname);
		
		RequestsParams params = new RequestsParams(RequestType.REMOVE_PROFILE, map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();			
			if (result.isSuccess()) 
				return true;
			else 
				return false ;
		
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return false;
		}
	}

	/** Methode permettant de recuperer des trajets 
	 * @param postCode : code postal du lieu de depart
	 * @param workplace : lieu de travail (destination)
	 * @return ArrayList<Ride> : liste des trajets
	 */
	public ArrayList<Ride> ridesRequest (String postCode, String workplace) {
		System.out.println("REQUEST RIDE : "+postCode+" --> "+workplace) ;
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("postal", postCode);
		
		// recuperation de l'ID du workplace
		String id = null ;
		Iterator<Entry<String, String>> it = mapWorkplaces.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> currentWorkplace = it.next();
			if(currentWorkplace.getValue().contentEquals(workplace))
				id = currentWorkplace.getKey();
				break;
		}
		map.put("travail", id);
		
		RequestsParams params = new RequestsParams(RequestType.SEARCH_RIDE, map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();
	
			if (result.isSuccess()) {
				ArrayList<Ride> rideList = new ArrayList<Ride> () ;
	
				// parcours de la HashMap
				for (Entry<String, Object> entry : result.getData().entrySet()) {
					HashMap nMapReponse = (HashMap) entry.getValue() ;
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
						Information info = new Information();
						info.setLogin((String)nMapReponse.get("login"));
						info.setEmail((String)nMapReponse.get("mail"));
						info.setName((String)nMapReponse.get("nom"));
						info.setFirstname((String)nMapReponse.get("prenom"));
						info.setMdp((String)nMapReponse.get("mdp"));
						info.setPhone((String)nMapReponse.get("tel"));
						info.setWorkplace((String)nMapReponse.get("travail"));
						info.setPostcode((String)nMapReponse.get("postal"));
						info.setMorning((String)nMapReponse.get("horairesMatin"));
						info.setEvening((String)nMapReponse.get("horairesSoir"));
						if (nMapReponse.get("conducteur") == "1")
							info.setConducteur(true);
						else
							info.setConducteur(false);
						if (nMapReponse.get("lundi") == "1")
							info.getDays()[0] = true ;
						else
							info.getDays()[0] = false ;
						if (nMapReponse.get("mardi") == "1")
							info.getDays()[1] = true ;
						else
							info.getDays()[1] = false ;
						if (nMapReponse.get("mercredi") == "1")
							info.getDays()[2] = true ;
						else
							info.getDays()[2] = false ;
						if (nMapReponse.get("jeudi") == "1")
							info.getDays()[3] = true ;
						else
							info.getDays()[3] = false ;
						if (nMapReponse.get("vendredi") == "1")
							info.getDays()[4] = true ;
						else
							info.getDays()[4] = false ;
						if (nMapReponse.get("samedi") == "1")
							info.getDays()[5] = true ;
						else
							info.getDays()[5] = false ;
						if (nMapReponse.get("dimanche") == "1")
							info.getDays()[6] = true ;
						else
							info.getDays()[6] = false ;
						
						ride.getUserList().add(info) ;
					}
					// different horaires de depart : nouveau ride dans la liste
					else {
						Ride nride = new Ride () ;
						Information info = new Information();
						info.setLogin((String)nMapReponse.get("login"));
						info.setEmail((String)nMapReponse.get("mail"));
						info.setName((String)nMapReponse.get("nom"));
						info.setFirstname((String)nMapReponse.get("prenom"));
						info.setMdp((String)nMapReponse.get("mdp"));
						info.setPhone((String)nMapReponse.get("tel"));
						info.setWorkplace((String)nMapReponse.get("travail"));
						info.setPostcode((String)nMapReponse.get("postal"));
						info.setMorning((String)nMapReponse.get("horairesMatin"));
						info.setEvening((String)nMapReponse.get("horairesSoir"));
						if (nMapReponse.get("conducteur") == "1")
							info.setConducteur(true);
						else
							info.setConducteur(false);
						if (nMapReponse.get("lundi") == "1")
							info.getDays()[0] = true ;
						else
							info.getDays()[0] = false ;
						if (nMapReponse.get("mardi") == "1")
							info.getDays()[1] = true ;
						else
							info.getDays()[1] = false ;
						if (nMapReponse.get("mercredi") == "1")
							info.getDays()[2] = true ;
						else
							info.getDays()[2] = false ;
						if (nMapReponse.get("jeudi") == "1")
							info.getDays()[3] = true ;
						else
							info.getDays()[3] = false ;
						if (nMapReponse.get("vendredi") == "1")
							info.getDays()[4] = true ;
						else
							info.getDays()[4] = false ;
						if (nMapReponse.get("samedi") == "1")
							info.getDays()[5] = true ;
						else
							info.getDays()[5] = false ;
						if (nMapReponse.get("dimanche") == "1")
							info.getDays()[6] = true ;
						else
							info.getDays()[6] = false ;
						nride.getUserList().add(info) ;
						rideList.add(nride) ;
					}	
				}
				return rideList ;
			}
			else
				return null ;
		
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Permet d'ajouter un lieu de travail
	 * @param workplace : lieu de travail a ajouter
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean addWorkplaceRequest(String workplace) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("bureau", workplace);
		
		RequestsParams params = new RequestsParams(RequestType.ADD_WORKPLACE, map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();
				
			if (result.isSuccess()) {
				return true;
			}
			else 
				return false ;
		
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Methode permettant de supprimer un lieu de travail
	 * @param workplace : lieu de travail a supprimer
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean deletionWorkplaceRequest (String workplace) {			
		// recuperation de l'ID du workplace
		String id = null ;
		Iterator<Entry<String, String>> it = mapWorkplaces.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> currentWorkplace = it.next();
			if(currentWorkplace.getValue().contentEquals(workplace))
				id = currentWorkplace.getKey();
				break;
		}

		if (id != null) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("idBureau", id);
			RequestsParams params = new RequestsParams(RequestType.DELETE_WORKPLACE, map);
			HTTPAsyncTask task = new HTTPAsyncTask();
			task.execute(params);
			RequestResponses result;
			try {
				result = task.get();
				if (result.isSuccess()) {
					return true;
				}
				else 
					return false ;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			} catch (ExecutionException e) {
				e.printStackTrace();
				return false;
			}
		} else 
			return false ;
	}

	/**
	 * Methode permettant de renvoyer la liste des lieux de travail
	 * @return ArrayList<String> : liste des lieux de travail
	 */
	public HashMap<String, String> getWorkplacesRequest() {
		RequestsParams params = new RequestsParams(RequestType.GET_LIST_WORKPLACE,null);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();

			// parcours de la HashMap
			for (Entry<String, Object> entry : result.getData().entrySet()) {
				String value = (String) entry.getValue();
				String key = (String) entry.getKey();
				this.mapWorkplaces.put(key, value) ;
			}
			return mapWorkplaces ;
		
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Methode permettant de renvoyer la liste des utilisateurs
	 * @return ArrayList<Information> : liste des utilisateurs
	 */
	public ArrayList<Information> getUsersRequest() {
		RequestsParams params = new RequestsParams(RequestType.GET_LIST_USERS,null);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();
			
			ArrayList<Information> users = new ArrayList<Information>() ;
			// parcours de la HashMap
			for (Entry<String, Object> entry : result.getData().entrySet()) {
				HashMap<String,String> nMapReponse = (HashMap) entry.getValue() ;
				Information info = new Information() ;
				info.setLogin((String)nMapReponse.get("login"));
				info.setEmail((String)nMapReponse.get("mail"));
				info.setName((String)nMapReponse.get("nom"));
				info.setFirstname((String)nMapReponse.get("prenom"));
				info.setMdp((String)nMapReponse.get("mdp"));
				info.setPhone((String)nMapReponse.get("tel"));
				info.setWorkplace((String)nMapReponse.get("travail"));
				info.setPostcode((String)nMapReponse.get("postal"));
				info.setMorning((String)nMapReponse.get("horairesMatin"));
				info.setEvening((String)nMapReponse.get("horairesSoir"));
				if (nMapReponse.get("conducteur") == "1")
					info.setConducteur(true);
				else
					info.setConducteur(false);
				if (nMapReponse.get("lundi") == "1")
					info.getDays()[0] = true ;
				else
					info.getDays()[0] = false ;
				if (nMapReponse.get("mardi") == "1")
					info.getDays()[1] = true ;
				else
					info.getDays()[1] = false ;
				if (nMapReponse.get("mercredi") == "1")
					info.getDays()[2] = true ;
				else
					info.getDays()[2] = false ;
				if (nMapReponse.get("jeudi") == "1")
					info.getDays()[3] = true ;
				else
					info.getDays()[3] = false ;
				if (nMapReponse.get("vendredi") == "1")
					info.getDays()[4] = true ;
				else
					info.getDays()[4] = false ;
				if (nMapReponse.get("samedi") == "1")
					info.getDays()[5] = true ;
				else
					info.getDays()[5] = false ;
				if (nMapReponse.get("dimanche") == "1")
					info.getDays()[6] = true ;
				else
					info.getDays()[6] = false ;
				users.add(info) ;
			}
			return users;
		
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
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
		
		RequestsParams params = new RequestsParams(RequestType.ADD_TOWN, map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();
			if (result.isSuccess()) {
				return true;
			} else 
				return false ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Methode permettant de supprimer une commune
	 * @param postCode : code postal de la commune a supprimer
	 * @return boolean : true si la requete s'est bien executee, false sinon
	 */
	public boolean deletionTownRequest(String postCode) {
		
		RequestsParams paramsTown = new RequestsParams(RequestType.GET_LIST_TOWN, null);
		HTTPAsyncTask taskTown = new HTTPAsyncTask();
		taskTown.execute(paramsTown);
		RequestResponses resultTown;
		String id = null ;
		try {
			resultTown = taskTown.get();

			// parcours de la HashMap
			for (Entry<String, Object> entry : resultTown.getData().entrySet()) {
				String MapReponse = (String) entry.getKey() ;
				if (MapReponse.equals(postCode)) {
					id = entry.getKey() ;
					break ;
				}
			}	
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		if (id != null) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("code", id);
			RequestsParams params = new RequestsParams(RequestType.DELETE_TOWN, map);
			HTTPAsyncTask task = new HTTPAsyncTask();
			task.execute(params);
			RequestResponses result;
			try {
				result = task.get();
				if (result.isSuccess()) 
					return true;
				else 
					return false ;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			} catch (ExecutionException e) {
				e.printStackTrace();
				return false;
			}
		}
		else 
			return false ;
	}

	/**
	 * Methode permettant de renvoyer la liste des communes
	 * @return ArrayList<String> : liste des communes
	 */
	public ArrayList<String> getPostcodeListRequest() {
		RequestsParams params = new RequestsParams(RequestType.GET_LIST_TOWN, null);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();
			
			ArrayList<String> postcodeList = new ArrayList<String>() ;
			// parcours de la HashMap
			for (Entry<String, Object> entry : result.getData().entrySet()) {
				String MapReponse = (String) entry.getKey() ;
				postcodeList.add(MapReponse) ;
			}
			return postcodeList ;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Methode permettant renvoyer le number de conducteurs et le nombre de passager
	 * @return String[] requete :
	 * si requete[0] = "-1" : echec et requete[1] = code erreur
	 * sinon requete[0] = nombre total de conducteurs 
	 * 	et requete[1] = nombre total de passagers 
	 */
	public String[] numberDriverAndPassengerRequest () {
		RequestsParams params = new RequestsParams(RequestType.GET_STAT_DRIVERS_PASSENGERS,null);
		String[] tab = new String[2] ;
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		Log.d("SC", "On attend...");
		try {
			RequestResponses result = task.get();
			Log.d("SC", "C'est fini !");
			if (result.isSuccess()) {
				System.out.println("SUCCES") ;
				tab[0] = (String) result.getData().get("drivers") ;
				tab[1] = (String) result.getData().get("passengers") ;
			}
			else {
				tab[0] = "-1" ;
				tab[1] = ((Integer)result.getCode()).toString() ;
			}
			return tab ;
		} catch (InterruptedException e) {
			tab[0] = "-1" ;
			tab[1] = "Interrupted Exception" ;
			return tab;
		} catch (ExecutionException e) {
			tab[0] = "-1" ;
			tab[1] = "Execution Exception" ;
			return tab;
		}
		
	}


	/**
	 * Methode permettant de renvoyer le number de conducteurs et le nombre de passager par trajet
	 * @return HashMap<String,String[]>
	 * Key = String : trajet
	 * Value = String[0] : nombre de conducteurs ; String[1] : nombre de passagers 
	 */
	public HashMap<String,String[]> numberDriverAndPassengerPerRideRequest () {
		RequestsParams params = new RequestsParams(RequestType.GET_STAT_DRIVERS_PASSENGERS_PER_RIDE, null);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		HashMap<String,String[]> requete = new HashMap<String,String[]> () ;
		
		try {
			result = task.get();
		
			
			if (result.isSuccess()) {
				// parcours de la HashMap
				for (Entry<String, Object> entry : result.getData().entrySet()) {
					HashMap<String,Object> nMapReponse = (HashMap<String,Object>) entry.getValue() ;
					String[] tab = new String[2] ;
					/**********************************************************
					 * 				AFFICHAGE POUR TEST
					 **********************************************************/
					Log.d("SC", "RIDE : "+entry.getKey()) ;
					Log.d("SC", "Drivers : "+((Integer) nMapReponse.get("drivers")).toString()) ;
					Log.d("SC", "Passengers : "+((Integer) nMapReponse.get("passengers")).toString()) ;
	
					tab[0] = ((Integer) nMapReponse.get("drivers")).toString() ;
					tab[1] = ((Integer) nMapReponse.get("passengers")).toString() ;
					requete.put(entry.getKey(), tab) ;
				}
			}
			else {
				String[] tab = new String[2] ;
				tab[0] = ((Integer)result.getCode()).toString() ;
				tab[1] = ((Integer)result.getCode()).toString() ;
				requete.put("-1", tab) ;
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
			String[] tab = new String[2] ;
			tab[0] = "Interrupted Exception" ;
			tab[1] = "Interrupted Exception" ;
			requete.put("-1", tab) ;
		} catch (ExecutionException e) {
			String[] tab = new String[2] ;
			e.printStackTrace();
			tab[0] = "Execution Exception" ;
			tab[1] = "Execution Exception" ;
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
		RequestsParams params = new RequestsParams(RequestType.GET_STAT_CONNECTIONS,null);
		Integer number = 0 ;
		String[] tab = new String[2] ;
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		Log.d("SC", "On attend...");
		
		try {
			RequestResponses result = task.get();
			Log.d("SC", "C'est fini !");
			if (result.isSuccess()) {
				for (Entry<String, Object> entry : result.getData().entrySet()) {
					number += Integer.parseInt((String) entry.getValue()) ;
				}
				tab[0] = "0" ;
				tab[1] = number.toString() ;
			}
			else {
				tab[0] = "-1" ;
				tab[1] = ((Integer) result.getCode()).toString() ;
			}
			return tab ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			tab[0] = "-1" ;
			tab[1] = "Interrupted Exception" ;
		} catch (ExecutionException e) {
			e.printStackTrace();
			tab[0] = "-1" ;
			tab[1] = "Execution Exception" ;
		}
		return tab;
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
		
		RequestsParams params = new RequestsParams(RequestType.GET_STAT_CONNECTIONS, map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();
				
			if (result.isSuccess()) {
				tab[0] = "0" ;
				tab[1] = (String) result.getData().get(date);
			}
			else {
				tab[0] = "-1" ;
				tab[1] = ((Integer) result.getCode()).toString() ;
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
			tab[0] = "-1" ;
			tab[1] = "Interruption Exception" ;
		} catch (ExecutionException e) {
			e.printStackTrace();
			tab[0] = "-1" ;
			tab[1] = "Execution Exception" ;
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
		
		RequestsParams params = new RequestsParams(RequestType.GET_STAT_CONNECTIONS, map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();
				
			if (result.isSuccess()) {
				tab[0] = "0" ;
				tab[1] = (String) result.getData().get(date);
			}
			else {
				tab[0] = "-1" ;
				tab[1] = ((Integer) result.getCode()).toString() ;
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
			tab[0] = "-1" ;
			tab[1] = "Interruption Exception" ;
		} catch (ExecutionException e) {
			e.printStackTrace();
			tab[0] = "-1" ;
			tab[1] = "Execution Exception" ;
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
		
		RequestsParams params = new RequestsParams(RequestType.GET_STAT_CONNECTIONS, map);
		HTTPAsyncTask task = new HTTPAsyncTask();
		task.execute(params);
		RequestResponses result;
		try {
			result = task.get();
				
			if (result.isSuccess()) {
				tab[0] = "0" ;
				tab[1] = (String) result.getData().get(firstDate);
			}
			else {
				tab[0] = "-1" ;
				tab[1] = ((Integer) result.getCode()).toString() ;
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
			tab[0] = "-1" ;
			tab[1] = "Interruption Exception" ;
		} catch (ExecutionException e) {
			e.printStackTrace();
			tab[0] = "-1" ;
			tab[1] = "Execution Exception" ;
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
		if (typeOfRequest == RequestType.GET_LIST_WORKPLACE || typeOfRequest == RequestType.GET_LIST_TOWN){
			System.out.println("Requête : " + urlParameters);
			return urlParameters;
		}
		else{
			if (map != null){
	
				// We fetch parameters in an iterator
				Iterator<Entry<String, Object>> it = map.entrySet().iterator();
	
				// We add parameters
				while(it.hasNext()){
					Entry<String,Object> couple = it.next();
					urlParameters += "&" + couple.getKey() + "=" + couple.getValue();
				}
			}
	
			if (typeOfRequest.equals(RequestType.CONNECT_USER) || typeOfRequest.equals(RequestType.CONNECT_ADMIN) || typeOfRequest.equals(RequestType.REGISTER) 
					|| typeOfRequest.equals(RequestType.PASSWORD_FORGOTTEN)){
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
		}

		System.out.println("Requête : " + urlParameters);
		return urlParameters;
	}

	private static Map<String,Object> jsonToMap(String json, boolean firstConnection){
		JSONObject JSONStrings;
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			JSONStrings = new JSONObject(json);
			JSONArray names = JSONStrings.names();
			for (int i = 0; i < names.length(); i++){
				String name = names.get(i).toString();
				System.out.println("JSON : " + i + " : " + JSONStrings.get(name));
				// On verifie si l'objet n'est pas un format JSON (comme pour la recherche d'un trajet par exemple), si c'est le cas on modifie cela
				if (isJSONValid(JSONStrings.get(name).toString()))
				{
					// On a donc un format JSON que l'on doit convertir
					String jsonToConvert = JSONStrings.get(name).toString();
					Map<String, Object> result2 = jsonToMap(jsonToConvert,false);
					result.put(name, result2);
				}
				else
				{
					result.put(name, JSONStrings.get(name));
				}
			}
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
		
		if (result.containsKey("SYN_REQ") || result.containsKey("ACK_REQ")){
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
		}
		
		System.out.println("Résultat après décodage du JSON : "+result.toString());
		return result;
	}
	
	/**
	 * Permet de tester si une chaine de caracteres est au format JSON
	 * @param test : la chaine a teste
	 * @return vrai si la chaine est au format JSON, faux sinon
	 */
	public static boolean isJSONValid(String test) {
	    try {
	        new JSONObject(test);
	    } catch (JSONException ex) {
	        // edited, to include @Arthur's comment
	        // e.g. in case JSONArray is valid as well...
	        try {
	            new JSONArray(test);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
	
	/**
	 * Permet de crypter (hasher) le mot de passe
	 * @param password : le mot de passe a crypte
	 * @return le mot de passe crypte
	 */
	private static String encryptPassword(String password){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(password.getBytes());

			byte byteData[] = md.digest();

			char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
			 StringBuffer buf = new StringBuffer();
			 for (int j=0; j<byteData.length; j++) {
			    buf.append(hexDigit[(byteData[j] >> 4) & 0x0f]);
			    buf.append(hexDigit[byteData[j] & 0x0f]);
			 }

			System.out.println("Mot de passe crypte : "+buf.toString().toLowerCase());
			return buf.toString().toLowerCase();

		} catch (NoSuchAlgorithmException e) {
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
				System.out.println("BEFORE : " + connection.getRequestProperties());
			}
			// sinon c'est la premiere fois qu'on se connecte, on recuperera un cookie

			System.out.println(urlParameters);

			//Send request
			DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
			wr.writeBytes (urlParameters);
			wr.flush ();
			wr.close ();

			Log.d("SC", "on regarde la rï¿½ponse");
			System.out.println();
			//Get Response  
			try{
				// On teste s'il n'y a pas eut d'erreur pour la requï¿½te
				if (connection.getResponseCode()/100 != 2){ throw new requestException(connection.getResponseCode());}

				System.out.println("AFTER" + connection.getHeaderFields());

				// S'il y a le champ "Set-Cookie", on rï¿½cupï¿½re le cookie
				if (connection.getHeaderField("Set-Cookie") != null){

					cookie = connection.getHeaderField("Set-Cookie");

				}
				else{
					// Sinon on vï¿½rifie l'ï¿½tat du cookie
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
			Log.d("SC", "on return la rï¿½ponse");
			return result;
		}

	}

}
