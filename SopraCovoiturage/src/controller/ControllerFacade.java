package controller;


import java.util.ArrayList;

import com.sopra.covoiturage.FacadeView;

import modele.Information;
import modele.Ride;

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
	
	/** 
	 * Constructeur par defaut de ControllerFacade
	 */
	private ControllerFacade () {
		facadeView = new FacadeView () ;
		requests = new Requests () ;
	}
	
	/**
	 * Permet de recuperer l'instance de ControllerFacade
	 * @return singleton
	 */
	public static ControllerFacade getInstance () {
		if (singleton == null) {
			singleton = new ControllerFacade() ;
		}
		return singleton ;
	}
	
	/**
	 * Getter de requests
	 * @return requests
	 */
	private Requests getRequests() {
		return requests ;
	}
	
	/**
	 * Methode permettant la connexion d'un utilisateur au serveur 
	 * @param nickname : login de l'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 */
	public void performConnect (String nickname, String password) {
		boolean requete =requests.connectionRequest(nickname, password) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Connexion : r�ussite !\n") ;
			facadeView.processConnected();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Connexion : �chec !\n") ;
			facadeView.processNotConnected();
		}
	}
	
	
	public void passwordForgotten (String mail)  {
		boolean requete =requests.passwordForgottenRequest(mail) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : r�ussite !\n") ;
			facadeView.processSendPwdMailOk();
		}
		else  {
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : �chec !\n") ;
			facadeView.processSendPwdMailFailure();
		}
	}
	
	public void performDisconnect (String nickname, String password) {
		boolean requete =requests.disconnectionRequest(nickname, password) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Deconnexion user : r�ussite !\n") ;
			facadeView.processUserDisconnected();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Deconnexion user : �chec !\n") ;
			facadeView.processUserNotDisconnected();
		}
	}
	
	public void performProfileModification (Information info) {
		int requete =requests.profileModificationRequest(info) ;
		if (requete == 0) {
			System.out.println("CONTROLLER_FACADE : Suppression user : r�ussite !\n") ;
			facadeView.confirmModification();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Suppression user : �chec !\n") ;
			facadeView.modificationFailed(requete);
		}
	}
	
	public void performDeletion (String nickname) {
		boolean requete =requests.removeProfileRequest(nickname) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Suppression user : r�ussite !\n") ;
			facadeView.changeActivityConnecting();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Suppression user : �chec !\n") ;
			facadeView.deletionFailure();
		}
	}
	
	public void performRides (String postcode, String workplace) {
		ArrayList<Ride> requete =requests.ridesRequest(postcode, workplace) ;
		if (requete != null) {
			System.out.println("CONTROLLER_FACADE : Rides : r�ussite !\n") ;
			System.out.println(requete.get(0)) ;
		}
		else {
			System.out.println("CONTROLLER_FACADE : Rides : �chec !\n") ;
		}
		facadeView.processRides(requete);
	}
	
	public void performRegister (Information info) {
		int requete =requests.creationUserRequest(info) ;
		if (requete == 0) {
			System.out.println("CONTROLLER_FACADE : Creation user : r�ussite !\n") ;
			facadeView.changeActivityProfile();
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Creation user : �chec !\n") ;
			facadeView.registrationFailed(requete);
		}
	}
	
	
	public void changeActivityReport () {
		
	}
	
	public void changeActivityAccountModificationPage () {
		
	}
	
	 
	public Information getProfileInformation (String nickname) {
		Information requete =requests.getProfileInformationRequest(nickname) ;
		if (requete!= null) {
			System.out.println("CONTROLLER_FACADE : getProfileInformation : r�ussite !\n") ;
			System.out.println(requete) ;
			return requete ;
		}
		else {
			System.out.println("CONTROLLER_FACADE : getProfileInformation : �chec !\n") ;
			return null;
		}
	}
	
	public void addWorkplace (String workplace) {
		boolean requete =requests.addWorkplaceRequest(workplace) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Addition workplace : r�ussite !\n") ;
			ArrayList<String> workplaces = requests.getWorkplacesRequest() ;
			facadeView.displayWorkplaces(workplaces);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Addition workplace : �chec !\n") ;
			facadeView.erreurAddWorkplace();
		}	
	}
	
	public void deletionWorkplace (String workplace) {
		boolean requete =requests.deletionWorkplaceRequest(workplace) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Deletion workplace : r�ussite !\n") ;
			ArrayList<String> workplaces = requests.getWorkplacesRequest() ;
			facadeView.displayWorkplaces(workplaces);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Deletion workplace : �chec !\n") ;
			facadeView.erreurDeletionWorkplace();
		}		
	}
	
	public static void main (String argv[]) {	
		ControllerFacade con = null ;
		con = ControllerFacade.getInstance() ;
		Boolean[] days = new Boolean[]{true,true,true,true,true,false,false};
		Information info = new Information("user100", "1234", "user@monmail.fr", "smith",
				"john", "0561665522", "31400", "1",
				days, true);
		con.performConnect("admin1", "sopra") ; //fonctionne
		//con.performConnect("user1", "test") ; //fonctionne
		//con.performRegister(info); //fonctionne
		//con.performDisconnect("user1", "test") ; // fonctionne
		//con.getProfileInformation("user1");
		//con.performRides("31400", "3"); // fonctionne avec un ride en tout cas / a tester avec plus
		//con.addWorkplace("bureau3"); // fonctionne
		con.deletionWorkplace("bureau3"); // fonctionne
	}


	

}