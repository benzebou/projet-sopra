package controller;


import java.util.ArrayList;

import com.sopra.covoiturage.FacadeView;

import modele.Information;
import modele.Ride;

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
			System.out.println("CONTROLLER_FACADE : Connexion : réussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Connexion : échec !\n") ;
	}
	
	
	public void passwordForgotten (String mail)  {
		boolean requete =requests.passwordForgottenRequest(mail) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : réussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : échec !\n") ;
	}
	
	public void performDisconnect (String nickname, String password) {
		boolean requete =requests.disconnectionRequest(nickname, password) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Deconnexion user : réussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Deconnexion user : échec !\n") ;	
	}
	
	public void performProfileModification (Information info) {
		boolean requete =requests.profileModificationRequest(info) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Suppression user : réussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Suppression user : échec !\n") ;
	}
	
	public void performDeletion (String nickname) {
		boolean requete =requests.removeProfileRequest(nickname) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Suppression user : réussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Suppression user : échec !\n") ;
	}
	
	public ArrayList<Ride> performRides (String postcode, String workplace) {
		ArrayList<Ride> requete =requests.ridesRequest(postcode, workplace) ;
		if (requete != null) {
			System.out.println("CONTROLLER_FACADE : Rides : réussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Rides : échec !\n") ;
		return requete;
	}
	
	public void performRegister (Information info) {
		boolean requete =requests.creationUserRequest(info) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Creation user : réussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Creation user : échec !\n") ;
	}
	
	public void processUserDisconnected(String nickname, String password) { 	
		boolean requete =requests.disconnectionRequest(nickname, password) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Disconnection user : réussite !\n") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Disconnection user : échec !\n") ;
	}
	
	public void changeActivityReport () {
		
	}
	
	public void changeActivityAccountModificationPage () {
		
	}
	
	 
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
		con.performRides("31400", "3");
	}
}