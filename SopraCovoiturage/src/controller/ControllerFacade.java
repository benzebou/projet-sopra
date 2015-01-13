package controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import modele.Information;
import modele.Ride;
import android.util.Log;

import com.sopra.covoiturage.FacadeView;

public class ControllerFacade {

	/**
	 * Singleton de la classe ControllerFacade
	 */
	private static ControllerFacade singleton ;
	
	/**
	 * Field vers la classe FacadeView
	 * Relation : composition
	 */
	private FacadeView facadeView ; 
	
	/** 
	 * Field vers la classe Requests
	 * Relation : composition
	 */
	private Requests requests ;
	
	private String login;
	
	private Information userInfo;
	
	/** 
	 * Constructeur par defaut de ControllerFacade
	 * @param facade La FacadeView
	 */
	private ControllerFacade (FacadeView facade) {
		facadeView = facade;
		requests = new Requests () ;
	}
	
	/**
	 * Permet de recuperer l'instance de ControllerFacade
	 * @return singleton
	 */
	public static ControllerFacade getInstance (FacadeView facade) {
		if (singleton == null) {
			singleton = new ControllerFacade(facade) ;
		}
		return singleton ;
	}
	
	// TESTS UNIQUEMENT
	private ControllerFacade () {
		requests = new Requests () ;
	}
	public static ControllerFacade getInstance () {
		if (singleton == null) {
			singleton = new ControllerFacade() ;
		}
		return singleton ;
	}
	

	/**
	 * Methode permettant la connexion d'un utilisateur au serveur 
	 * @param nickname : login de l'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 */
	public void performConnect (String nickname, String password) {
		boolean[] requete =requests.connectionRequest(nickname, password) ;
		if (requete[0]) {
			System.out.println("CONTROLLER_FACADE : Connexion : reussite !\n") ;
			this.setLogin(nickname);
			facadeView.processConnected(requete[1]);
		}
		else {
			System.out.println("CONTROLLER_FACADE : Connexion : echec !\n") ;
			facadeView.processNotConnected();
		}
	}
	
	/**
	 * Methode permettant a un utilisateur de reinitialiser son mot de passe
	 * @param mail : mail de l'utilisateur
	 */
	public void passwordForgotten (String mail)  {
		boolean requete =requests.passwordForgottenRequest(mail) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : reussite !\n") ;
			facadeView.processSendPwdMailOk();
		}
		else  {
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : echec !\n") ;
			facadeView.processSendPwdMailFailure();
		}
	}
	
	/**
	 * Methode permettant la deconnexion d'un utilisateur au serveur 
	 * @param nickname : login de l'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 */
	public void performDisconnect () {
		boolean requete = requests.disconnectionRequest() ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Deconnexion user : reussite !\n") ;
			facadeView.processUserDisconnected();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Deconnexion user : echec !\n") ;
			facadeView.processUserNotDisconnected();
		}
	}
	
	/**
	 * methode permettant de modifier le profil d'un utilisateur
	 * @param info : informations sur l'utilisateur
	 */
	public void performProfileModification (Information info) {
		int requete =requests.profileModificationRequest(info) ;
		if (requete == 0) {
			System.out.println("CONTROLLER_FACADE : Modification user : reussite !\n") ;
			facadeView.modificationSuccess();
			//facadeView.confirmModification();
			//on met Ã  jour nos infos 
			userInfo= info;	
		}
		else {
			System.out.println("CONTROLLER_FACADE : Modification user : echec !\n") ;
			facadeView.modificationFailed(requete);
		}
	}
	
	/**
	 * methode permettant de modifier le profil d'un administrateur
	 * @param info : informations sur l'administrateur
	 */
	public void performAdminProfileModificationRequest (Information info){
		int requete =requests.adminProfileModificationRequest(info) ;
		if (requete == 0) {
			System.out.println("CONTROLLER_FACADE : Modification admin : reussite !\n") ;
			facadeView.changeActivity(com.sopra.covoiturage.AdminProfileActivity.class);
			//facadeView.confirmModification();
			//on met Ã  jour nos infos 
			userInfo= info;	
		}
		else {
			System.out.println("CONTROLLER_FACADE : Modification admin : echec !\n") ;
			facadeView.modificationFailed(requete);
		}
	}
	
	/**
	 * Methode utilisee seulement par un administrateur
	 * Permet de supprimer un utilisateur
	 * @param nickname : login de l'utilisateur a supprimer
	 * return boolean : 1 si l'utilisateur a été supprimé, 0 sinon
	 */
	public boolean performDeletion (String nickname) {
		boolean requete =requests.removeProfileRequest(nickname) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Suppression user : reussite !\n") ;
			return true;
			// TODO : Il faut arrï¿½ter toutes les activitï¿½s
		}
		else {
			System.out.println("CONTROLLER_FACADE : Suppression user : echec !\n") ;
			facadeView.deletionFailure();
			return false;
		}
	}
	
	/**
	 * Methode permettant de recuperer des trajets 
	 * @param postcode : code postal du lieu de depart
	 * @param workplace : lieu de travail (destination)
	 */
	public void performRides (String postcode, String workplace) {
		ArrayList<Ride> requete = requests.ridesRequest(postcode, workplace) ;
		if (requete != null) {
			System.out.println("CONTROLLER_FACADE : Rides : reussite !\n") ;
			for (int i=0; i<requete.size(); i++) {
				System.out.println(requete.get(i)+"\n") ;
			}
			
		}
		else {
			System.out.println("CONTROLLER_FACADE : Rides : echec !\n") ;
		}
		facadeView.processRides(requete);
	}
	
	/**
	 * methode permettant d'inscrire un nouvel utilisateur
	 * @param info : informations du profil de l'utilisateur
	 */
	public void performRegister (Information info) {
		int requete =requests.creationUserRequest(info) ;
		if (requete == 0) {
			System.out.println("CONTROLLER_FACADE : Creation user : reussite !\n") ;
			facadeView.changeActivity(com.sopra.covoiturage.ConnectingActivity.class);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Creation user : echec !\n") ;
			facadeView.registrationFailed(requete);
		}
	}
	
	
	public void changeActivityReport () {
		
	}
	
	public void changeActivityAccountModificationPage () {
		
	}
	
	/**
	 * Methode permettant de renvoyer les informations sur l'utilisateur ayant pour login nickname 
	 * @param nickname : login de l'utilisateur 
	 * @return Informations : informations sur l'utilisateur 
	 */
	public Information getProfileInformation (String nickname) {
		Information requete =requests.getProfileInformationRequest(nickname) ;
		if (requete!= null) {
			System.out.println("CONTROLLER_FACADE : getProfileInformation : reussite !\n") ;
			System.out.println(requete) ;
			return requete ;
		}
		else {
			System.out.println("CONTROLLER_FACADE : getProfileInformation : echec !\n") ;
			return null;
		}
	}
	
	/**
	 * Methode permettant de renvoyer les informations sur l'admin ayant pour login nickname 
	 * @param nickname : login de l'admin 
	 * @return Informations : informations sur l'admin 
	 */
	public Information getAdminInformationRequest(String nickname) {
		Information requete =requests.getAdminInformationRequest(nickname) ;
		if (requete!= null) {
			System.out.println("CONTROLLER_FACADE : getAdminInformationRequest : reussite !\n") ;
			System.out.println(requete) ;
			return requete ;
		}
		else {
			System.out.println("CONTROLLER_FACADE : getAdminInformationRequest : echec !\n") ;
			return null;
		}
	}
	
	/**
	 * Methode utilisee seulement par un administrateur
	 * Permet d'ajouter un lieu de travail
	 * @param workplace : lieu de travail a ajouter
	 */
	public void addWorkplace (String workplace) {
		boolean requete =requests.addWorkplaceRequest(workplace) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Addition workplace : reussite !\n") ;
			Log.d("SC", "CONTROLLER_FACADE : Addition workplace : reussite !\n");
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Addition workplace : echec !\n") ;
			facadeView.erreurAddWorkplace();
		}	
	}
	
	/**
	 * Methode utilisee seulement par un administrateur
	 * Permet de supprimer un lieu de travail
	 * @param workplace : lieu de travail a supprimer
	 */
	public void deletionWorkplace (String workplace) {
		boolean requete =requests.deletionWorkplaceRequest(workplace) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Deletion workplace : reussite !\n") ;
			HashMap<String, String> workplaces = requests.getWorkplacesRequest() ;
			facadeView.displayWorkplaces(workplaces);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Deletion workplace : echec !\n") ;
			facadeView.erreurDeletionWorkplace();
		}		

	}
	
	/**
	 * Methode permettant de renvoyer la liste des lieux de travail
	 * @return ArrayList<String> : liste des lieux de travail
	 */
	public ArrayList<String> getWorkplaces() {
		ArrayList<String> res = new ArrayList<String>();
		HashMap<String, String> workplaces = requests.getWorkplacesRequest();
		Iterator<Entry<String, String>> it = workplaces.entrySet().iterator();
		while(it.hasNext())
			res.add(it.next().getValue());

		return res;
	}
	
	/**
	 * Methode permettant de renvoyer la liste des utilisateurs
	 * @return ArrayList<Information> : liste des utilisateurs
	 */
	public ArrayList<Information> getUsers() {
		ArrayList<Information> res = null;
		res = requests.getUsersRequest() ;
		return res;
	}
	
	/**
	 * Methode utilisee seulement par un administrateur
	 * Permet d'ajouter une commune
	 * @param town : nom de la commune a ajouter
	 * @param code : code postal de la commune a ajouter
	 */
	public void addTown (String town, String code) {
		boolean requete =requests.addTownRequest(town, code) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Addition town : reussite !\n") ;
			ArrayList<String> townList = requests.getPostcodeListRequest() ;
			facadeView.displayTownList(townList);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Addition town : echec !\n") ;
			facadeView.erreurAddTown();
		}	
	}
	
	
	/**
	 * Methode utilisee seulement par un administrateur
	 * Permet de supprimer une commune
	 * @param code : code postal de la commune a supprimer
	 */
	public void deletionTown (String code) {
		boolean requete =requests.deletionTownRequest(code) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Deletion town : reussite !\n") ;
			ArrayList<String> townList = requests.getPostcodeListRequest() ;
			facadeView.displayTownList(townList);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Deletion town : echec !\n") ;
			facadeView.erreurAddTown();
		}		
	}
	
	/**
	 * Methode permettant de renvoyer la liste des communes
	 * @return ArrayList<String> : liste des communes
	 */
	public ArrayList<String> getPostcodeList() {
		/*
		ArrayList <String> postcodes = requests.getPostcodeListRequest() ;
		for (int i=0 ; i< postcodes.size() ; i++ ) {
			System.out.println(postcodes.get(i)) ;
		}
		*/
		return requests.getPostcodeListRequest() ;
	}

	/**
	 * Methode permettant renvoyer le number de conducteurs et le nombre de passager
	 * @return String[] requete :
	 * si requete[0] = "-1" : echec et requete[1] = code erreur
	 * sinon requete[0] = nombre total de conducteurs 
	 * 	et requete[1] = nombre total de passagers 
	 */
	public String[] getNumberDriverAndPassenger () {
		String[] requete =requests.numberDriverAndPassengerRequest() ;
		if (requete[0].equals("-1")) {
			System.out.println("CONTROLLER_FACADE : Echec - erreur "+requete[1]+"\n") ;
		}
		else
			System.out.println("CONTROLLER_FACADE : Number of driver :"+requete[1]+"\n and passenger : "+requete[1]+"\n") ;
		return requete ;
	}
	
	/**
	 * Methode permettant de renvoyer le number de conducteurs et le nombre de passager par trajet
	 * @return HashMap<String,String[]>
	 * Key = String : trajet
	 * Value = String[0] : nombre de conducteurs ; String[1] : nombre de passagers 
	 */
	public HashMap<String,String[]> getNumberDriverAndPassengerPerRide () {
		HashMap<String,String[]> requete =requests.numberDriverAndPassengerPerRideRequest() ;
		System.out.println("CONTROLLER_FACADE : Number of driver and passenger per ride!\n") ;		
		return requete ;
	}
	
	
	/**
	 * Methode permettant de renvoyer le nombre total de connexions
	 * @return String[] requete :
	 * si requete[0] = "0" : succes et requete[1] = nombre total de connexions
	 * sinon requete[0] = "-1" : echec et requete[1] = code erreur 
	 */
	public String[] getNumberConnection () {
		String[] requete =requests.numberConnectionRequest() ;
		if (requete[0].equals("0")) {
			System.out.println("CONTROLLER_FACADE : Total number of connections : "+requete[1]+"\n") ;		
		}
		else {
			System.out.println("CONTROLLER_FACADE : Echec - erreur "+requete[1]+"\n") ;
		}
			return requete ;
	}
	
	/**
	 * Methode permettant de renvoyer le nombre total de connexions a une date donnee
	 * @param date : data a laquelle on compte le nombre de connexions
	 * @return String[] requete :
	 * si requete[0] = "0" : succes et requete[1] = nombre total de connexions a la date
	 * sinon requete[0] = "-1" : echec et requete[1] = code erreur
	 */
	public String[] getNumberConnectionDate (String date) {
		String[] requete =requests.numberConnectionDateRequest(date) ;
		if (requete[0].equals("0")) {
			System.out.println("CONTROLLER_FACADE : Number of connections on the "+date+" : "+requete[1]+"\n") ;		
		}
		else {
			System.out.println("CONTROLLER_FACADE : Echec - erreur "+requete[1]+"\n") ;
		}		
		return requete ;	
	}
	
	/**
	 * Methode permettant de renvoyer le nombre total de connexions depuis une date donnee
	 * @param date : data a partir de laquelle on compte le nombre de connexions
	 * @return String[] requete :
	 * si requete[0] = "0" : succes et requete[1] = nombre total de connexions depuis la date
	 * sinon requete[0] = "-1" : echec et requete[1] = code erreur
	 */
	public String[] getNumberConnectionSince (String date) {
		String[] requete =requests.numberConnectionSinceRequest(date) ;		
		if (requete[0].equals("0")) {
			System.out.println("CONTROLLER_FACADE : Number of connections since "+date+" : "+requete[1]+"\n") ;		
		}
		else {
			System.out.println("CONTROLLER_FACADE : Echec - erreur "+requete[1]+"\n") ;
		}		
		return requete ;		
	}	
	
	/**
	 * Methode permettant de renvoyer le nombre total de connexions entre deux dates donnees
	 * @param dateFirst : date de debut de l'intervalle
	 * @param dateLast : date de fin de l'intervalle
	 * @return String[] requete :
	 * si requete[0] = "0" : succes et requete[1] = nombre total de connexions entre les deux dates
	 * sinon requete[0] = "-1" : echec et requete[1] = code erreur
	 */
	public String[] getNumberConnectionBetween (String dateFirst, String dateLast) {
		String[] requete =requests.numberConnectionBetweenRequest(dateFirst, dateLast) ;
		if (requete[0].equals("0")) {
			System.out.println("CONTROLLER_FACADE : Number of connections between "+dateFirst+" and "+dateLast+" : "+requete[1]+"\n") ;		
		}
		else {
			System.out.println("CONTROLLER_FACADE : Echec - erreur "+requete[1]+"\n") ;
		}		
		return requete ;		
	}	
	/**
	 * Methode permettant de renvoyer le login de l'utilisateur
	 * @return String : login de l'utilisateur
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Methode permettant de mettre Ã  jour le login
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Methode permettant de renvoyer les infos correspondantes au user
	 * @return Information : infos correspondantes au user
	 */
	public Information getUserInfo() {
		return userInfo;
	}

	/**
	 * Methode permettant de mettre Ã  jouruserInfo
	 */
	public void setUserInfo(Information userInfo) {
		this.userInfo = userInfo;
	}
	
	
	public static void main (String argv[]) {	
		ControllerFacade con = null ;
		con = ControllerFacade.getInstance() ;
		String [] schedule = new String[]{"10:30", "18:00"} ;
		Boolean[] days = new Boolean[]{true,true,true,true,true,false,false};
		Information info = new Information("user100", "1234", "user@monmail.fr", "smith",
				"john", "0561665522", "31400", "3", schedule,days, true, true);
		//con.performConnect("admin1", "sopra") ; //fonctionne
		//con.performConnect("user1", "test") ; //fonctionne
		//con.performRegister(info); //fonctionne
		//con.performDisconnect("user1", "test") ; // fonctionne
		//con.getProfileInformation("user1");
		//con.performRides("31400", "3"); // fonctionne
		//con.addWorkplace("bureau5"); // fonctionne
		//con.deletionWorkplace("bureau3"); // fonctionne
		//con.addTown("Foix", "09000"); // fonctionne
		//con.deletionTown("9000"); // fonctionne
		//con.getNumberDriverAndPassenger() ; // fonctionne
		//con.getNumberDriverAndPassengerPerRide() ; // fonctionne
		//con.getNumberConnection() ; // fonctionne
		//con.getNumberConnectionDate("2014-12-26") ; // fonctionne
		//con.getNumberConnectionSince("2014-12-26") ; // fonctionne
		//con.getNumberConnectionBetween("2014-12-26", "2014-12-26") ; // fonctionne
		con.getPostcodeList() ;
	}


}