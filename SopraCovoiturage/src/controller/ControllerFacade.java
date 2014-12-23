package controller;


import com.sopra.covoiturage.FacadeView;

import modele.Information;

public class ControllerFacade {

	private static ControllerFacade singleton ;
	
	 private FacadeView facadeView ; 
	 
	 private Requests requests ;
	
	private ControllerFacade () {
		facadeView = new FacadeView () ;
		requests = new Requests () ;
	}
	
	public static ControllerFacade getInstance () {
		if (singleton == null) {
			singleton = new ControllerFacade() ;
		}
		return singleton ;
	}
	
	public void performConnect (String nickname, String password) {
		boolean requete =requests.connectionRequest(nickname, password) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Connexion : r�ussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Connexion : �chec !\n") ;
	}
	
	
	public void passwordForgotten (String mail)  {
		boolean requete =requests.passwordForgottenRequest(mail) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : r�ussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : �chec !\n") ;
	}
	
	public void performDisconnect (String nickname, String password) {
		boolean requete =requests.disconnectionRequest(nickname, password) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Deconnexion user : r�ussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Deconnexion user : �chec !\n") ;	
	}
	
	public void performProfileModification (Information info) {
		
	}
	
	public void performDeletion () {
		
	}
	
	public void performRides (int postcode, String workplace) {
		
	}
	
	public void performRegister (Information info) {
		boolean requete =requests.creationUserRequest(info) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Creation user : r�ussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Creation user : �chec !\n") ;
	}
	
	public void processUserDisconnected() { 	
	}
	
	public void changeActivityReport () {
		
	}
	
	public void changeActivityAccountModificationPage () {
		
	}
	
	 
	public Information getProfileInformation (String nickname) {
		Information requete =requests.getProfileInformation(nickname) ;
		if (requete!= null) {
			System.out.println("CONTROLLER_FACADE : getProfileInformation : r�ussite !\n") ;
			return requete ; 
		}
		else {
			System.out.println("CONTROLLER_FACADE : getProfileInformation : �chec !\n") ;
			return null;
		}

	}
	
	public static void main (String argv[]) {	
		ControllerFacade con = null ;
		con = ControllerFacade.getInstance() ;
		Boolean[] days = new Boolean[]{true,true,true,true,true,false,false};
		Information info = new Information("user100", "1234", "user@monmail.fr", "smith",
				"john", "0561665522", "31400", "1",
				days, true);
		con.performConnect("user1", "test") ; //fonctionne
		//con.performRegister(info); //fonctionne
		//con.performDisconnect("user1", "test") ; // fonctionne
		con.getProfileInformation("user1");
	}
}