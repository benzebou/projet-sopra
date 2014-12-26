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
	 * Methode permettant la connexion d'un utilisateur au serveur 
	 * @param nickname : login de l'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 */
	public void performConnect (String nickname, String password) {
		boolean requete =requests.connectionRequest(nickname, password) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Connexion : réussite !\n") ;
			facadeView.processConnected();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Connexion : échec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : réussite !\n") ;
			facadeView.processSendPwdMailOk();
		}
		else  {
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : échec !\n") ;
			facadeView.processSendPwdMailFailure();
		}
	}
	
	/**
	 * Methode permettant la deconnexion d'un utilisateur au serveur 
	 * @param nickname : login de l'utilisateur
	 * @param password : mot de passe de l'utilisateur
	 */
	public void performDisconnect (String nickname, String password) {
		boolean requete =requests.disconnectionRequest(nickname, password) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Deconnexion user : réussite !\n") ;
			facadeView.processUserDisconnected();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Deconnexion user : échec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Suppression user : réussite !\n") ;
			facadeView.confirmModification();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Suppression user : échec !\n") ;
			facadeView.modificationFailed(requete);
		}
	}
	
	/**
	 * Methode utilisee seulement par un administrateur
	 * Permet de supprimer un utilisateur
	 * @param nickname : login de l'utilisateur a supprimer
	 */
	public void performDeletion (String nickname) {
		boolean requete =requests.removeProfileRequest(nickname) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Suppression user : réussite !\n") ;
			facadeView.changeActivityConnecting();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Suppression user : échec !\n") ;
			facadeView.deletionFailure();
		}
	}
	
	/**
	 * Methode permettant de recuperer des trajets 
	 * @param postcode : code postal du lieu de depart
	 * @param workplace : lieu de travail (destination)
	 */
	public void performRides (String postcode, String workplace) {
		ArrayList<Ride> requete =requests.ridesRequest(postcode, workplace) ;
		if (requete != null) {
			System.out.println("CONTROLLER_FACADE : Rides : réussite !\n") ;
			for (int i=0; i<requete.size(); i++) {
				System.out.println(requete.get(i)+"\n") ;
			}
			
		}
		else {
			System.out.println("CONTROLLER_FACADE : Rides : échec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Creation user : réussite !\n") ;
			facadeView.changeActivityProfile();
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Creation user : échec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : getProfileInformation : réussite !\n") ;
			System.out.println(requete) ;
			return requete ;
		}
		else {
			System.out.println("CONTROLLER_FACADE : getProfileInformation : échec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Addition workplace : réussite !\n") ;
			ArrayList<String> workplaces = requests.getWorkplacesRequest() ;
			facadeView.displayWorkplaces(workplaces);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Addition workplace : échec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Deletion workplace : réussite !\n") ;
			ArrayList<String> workplaces = requests.getWorkplacesRequest() ;
			facadeView.displayWorkplaces(workplaces);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Deletion workplace : échec !\n") ;
			facadeView.erreurDeletionWorkplace();
		}		
	}
	
	/**
	 * Methode permettant de renvoyer la liste des lieux de travail
	 * @return ArrayList<String> : liste des lieux de travail
	 */
	public ArrayList<String> getWorkplaces() {
		return requests.getWorkplacesRequest() ;
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
			System.out.println("CONTROLLER_FACADE : Addition town : réussite !\n") ;
			ArrayList<String> townList = requests.getTownListRequest() ;
			facadeView.displayTownList(townList);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Addition town : échec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Deletion town : réussite !\n") ;
			ArrayList<String> townList = requests.getTownListRequest() ;
			facadeView.displayTownList(townList);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Deletion town : échec !\n") ;
			facadeView.erreurAddTown();
		}		
	}
	
	/**
	 * Methode permettant de renvoyer la liste des communes
	 * @return ArrayList<String> : liste des communes
	 */
	public ArrayList<String> getTownList() {
		return requests.getTownListRequest() ;
	}

	
	public int getNumberDriverAndNoDriver () {
		int requete =requests.numberDriverAndNoDriverRequest() ;
		System.out.println("CONTROLLER_FACADE : Number of driver and no driver !\n") ;		
		return requete ;
	}
	
	public static void main (String argv[]) {	
		ControllerFacade con = null ;
		con = ControllerFacade.getInstance() ;
		String [] schedule = new String[]{"10:30", "18:00"} ;
		Boolean[] days = new Boolean[]{true,true,true,true,true,false,false};
		Information info = new Information("user100", "1234", "user@monmail.fr", "smith",
				"john", "0561665522", "31400", "3", schedule,days, true);
		con.performConnect("admin1", "sopra") ; //fonctionne
		con.performConnect("user1", "test") ; //fonctionne
		//con.performRegister(info); //fonctionne
		con.performDisconnect("user1", "test") ; // fonctionne
		con.getProfileInformation("user1");
		//con.performRides("31400", "3"); // fonctionne
		con.addWorkplace("bureau5"); // fonctionne
		//con.deletionWorkplace("bureau3"); // fonctionne
		//con.addTown("Foix", "09000"); // fonctionne
		//con.deletionTown("9000");
		con.getNumberDriverAndNoDriver() ;
	}
}