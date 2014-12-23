package modele;

import java.util.ArrayList;

public class Information {

	private String login ;
	
	private String mdp ;
	
	private String email ;
	
	private String name ;
	
	private String firstname ;
	
	private String phone ;
	
	private String postcode ;
	
	private String workplace ;
	
	//private ArrayList<> schedule ; 
	
	private ArrayList<Boolean> days ;
	
	private boolean conducteur ;

	public Information() {
		
	}

	public String getLogin() {
		return login;
	}
	
	public String getMdp() {
		return mdp ;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getPhone() {
		return phone;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getWorkplace() {
		return workplace;
	}

	public ArrayList<Boolean> getDays() {
		return days;
	}

	public boolean isConducteur() {
		return conducteur;
	}
	
}
