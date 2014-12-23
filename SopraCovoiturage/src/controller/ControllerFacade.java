package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection ;
import java.util.HashMap;

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
			System.out.println("CONTROLLER_FACADE : Connexion : réussite !") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Connexion : échec !") ;
	}
	
	
	public void passwordForgotten (String mail)  {
		boolean requete =requests.passwordForgottenRequest(mail) ;
		if (requete) {
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : réussite !") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Recuperation MDP : échec !") ;
	}
	
	public void performDisconnect () {
		
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
			System.out.println("CONTROLLER_FACADE : Creation user : réussite !") ;
		}
		else 
			System.out.println("CONTROLLER_FACADE : Creation user : échec !") ;
	}
	
	public void processUserDisconnected () {
		
	}
	
	public void changeActivityReport () {
		
	}
	
	public void changeActivityAccountModificationPage () {
		
	}
	
	 
	public Information getProfileInformation () {
		return null ; 
	}
	
	public static void main (String argv[]) {	
		ControllerFacade con = null ;
		con = ControllerFacade.getInstance() ;
		con.performConnect("user1", "test") ;
	}
}