package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection ;

import modele.Information;

public class Controller {

	// private FacadeView facadeView ; 
	
	public Controller () {
		//facadeView = new FacadeView () ;
	}
	
	public void performConnect (String nickname, String password) {
		// URL du serveur
		String ur= "http://localhost/carpooling/http_post_entry.php" ;
		// requete
		String post="login="+nickname+"&mdp="+password ;
		URL url;
		try {
			url = new URL(ur);
			URLConnection  conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(post);
			writer.flush();
			//recuperation du code html
			String reponse=null,ligne = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((ligne = reader.readLine()) != null) {
			        reponse+= ligne.trim()+"\n";
			}
			System.out.print(reponse);

			// traitement a faire suivant la reponse 
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	// Julie : je me suis inspirée de la fonction du dessus, je sais pas vrmt si ca marche..
	public void passwordForgotten (String mail)  {
		// URL du serveur
		String ur= "www.exempl.ma\\index.jsp" ;
		// requete
		String post="mail="+mail ;
		URL url;
		try {
			url = new URL(ur);
			URLConnection  conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(post);
			writer.flush();
			//recuperation du code html
			String reponse=null,ligne = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((ligne = reader.readLine()) != null) {
			        reponse+= ligne.trim()+"\n";
			}
			System.out.print(reponse);

			// traitement a faire suivant la reponse 
					
					
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		Controller con = new Controller () ;
		con.performConnect("user", "test") ;
	}
}