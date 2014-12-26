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
			System.out.println("CONTROLLER_FACADE : Connexion : r�ussite !\n") ;
			facadeView.processConnected();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Connexion : �chec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : r�ussite !\n") ;
			facadeView.processSendPwdMailOk();
		}
		else  {
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : �chec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Deconnexion user : r�ussite !\n") ;
			facadeView.processUserDisconnected();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Deconnexion user : �chec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Suppression user : r�ussite !\n") ;
			facadeView.confirmModification();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Suppression user : �chec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Suppression user : r�ussite !\n") ;
			facadeView.changeActivityConnecting();
		}
		else {
			System.out.println("CONTROLLER_FACADE : Suppression user : �chec !\n") ;
			facadeView.deletionFailure();
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
			System.out.println("CONTROLLER_FACADE : Rides : r�ussite !\n") ;
			for (int i=0; i<requete.size(); i++) {
				System.out.println(requete.get(i)+"\n") ;
			}
			
		}
		else {
			System.out.println("CONTROLLER_FACADE : Rides : �chec !\n") ;
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
	
	/**
	 * Methode permettant de renvoyer les informations sur l'utilisateur ayant pour login nickname 
	 * @param nickname : login de l'utilisateur 
	 * @return Informations : informations sur l'utilisateur 
	 */
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
	
	/**
	 * Methode utilisee seulement par un administrateur
	 * Permet d'ajouter un lieu de travail
	 * @param workplace : lieu de travail a ajouter
	 */
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
	
	/**
	 * Methode utilisee seulement par un administrateur
	 * Permet de supprimer un lieu de travail
	 * @param workplace : lieu de travail a supprimer
	 */
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
			System.out.println("CONTROLLER_FACADE : Addition town : r�ussite !\n") ;
			ArrayList<String> townList = requests.getTownListRequest() ;
			facadeView.displayTownList(townList);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Addition town : �chec !\n") ;
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
			System.out.println("CONTROLLER_FACADE : Deletion town : r�ussite !\n") ;
			ArrayList<String> townList = requests.getTownListRequest() ;
			facadeView.displayTownList(townList);
		}
		else { 
			System.out.println("CONTROLLER_FACADE : Deletion town : �chec !\n") ;
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

	/**
	 * Methode permettant de renvoyer le number de conducteurs et le nombre de non conducteurs
	 * @return 
	 */
	public int getNumberDriverAndNoDriver () {
		int requete =requests.numberDriverAndNoDriverRequest() ;
		System.out.println("CONTROLLER_FACADE : Number of driver and no driver !\n") ;		
		return requete ;
	}
	
	public int getNumberDriverAndNoDriverPerRide () {
		int requete =requests.numberDriverAndNoDriverPerRideRequest() ;
		System.out.println("CONTROLLER_FACADE : Number of driver and no driver per ride!\n") ;		
		return requete ;
	}
	
	
	/**
	 * Methode permettant de renvoyer le nombre total de connexions
	 * @return int : nombre de connexion 
	 */
	public int getNumberConnection () {
		int requete =requests.numberConnectionRequest() ;
		System.out.println("CONTROLLER_FACADE : Total number of connections!\n") ;		
		return requete ;
	}
	
	/**
	 * Methode permettant de renvoyer le nombre total de connexions a une date donnee
	 * @param date : data a laquelle on compte le nombre de connexions
	 * @return int : nombre total de connexions a la date
	 */
	public int getNumberConnectionDate (String date) {
		int requete =requests.numberConnectionDateRequest(date) ;
		System.out.println("CONTROLLER_FACADE : Number of connections on the "+date+"!\n") ;		
		return requete ;		
	}
	
	/**
	 * Methode permettant de renvoyer le nombre total de connexions depuis une date donnee
	 * @param date : data a partir de laquelle on compte le nombre de connexions
	 * @return int : nombre total de connexions depuis la date
	 */
	public int getNumberConnectionSince (String date) {
		int requete =requests.numberConnectionSinceRequest(date) ;
		System.out.println("CONTROLLER_FACADE : Number of connections since "+date+"!\n") ;		
		return requete ;		
	}	
	
	/**
	 * Methode permettant de renvoyer le nombre total de connexions entre deux dates donnees
	 * @param dateFirst : date de debut de l'intervalle
	 * @param dateLast : date de fin de l'intervalle
	 * @return int : nombre total de connexions entre les deux dates
	 */
	public int getNumberConnectionBetween (String dateFirst, String dateLast) {
		int requete =requests.numberConnectionBetweenRequest(dateFirst, dateLast) ;
		System.out.println("CONTROLLER_FACADE : Number of connections between "+dateFirst+" and "+dateLast+" !\n") ;		
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
		//con.performConnect("user1", "test") ; //fonctionne
		//con.performRegister(info); //fonctionne
		//con.performDisconnect("user1", "test") ; // fonctionne
		con.getProfileInformation("user1");
		//con.performRides("31400", "3"); // fonctionne
		con.addWorkplace("bureau5"); // fonctionne
		//con.deletionWorkplace("bureau3"); // fonctionne
		//con.addTown("Foix", "09000"); // fonctionne
		//con.deletionTown("9000");
		con.getNumberDriverAndNoDriver() ;
	}
}