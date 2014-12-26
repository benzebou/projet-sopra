package modele;

import java.util.ArrayList;

public class Ride {

	/**
	 * Adresse de depart du chemin
	 */
	private String depart ;
	
	/**
	 * Adresse d'arrive du chemin
	 */
	private String arrivee ;
	
	/**
	 * Horaires de depart le matin
	 */
	private String heureDepartMatin ;
	
	/**
	 * Horaires de depart le soir
	 */
	private String heureDepartSoir ;
	
	/**
	 * Liste de user effectuant ce meme trajet, a la meme heure
	 */
	private ArrayList<Information> userList ;

	/**
	 * Constructeur par defaut de Ride
	 */
	public Ride () {
		userList = new ArrayList<Information>() ;
	}
	
	/**
	 * Getter de depart
	 * @return depart
	 */
	public String getDepart() {
		return depart;
	}

	/**
	 * Setter de depart
	 * @param depart : addresse de depart
	 */
	public void setDepart(String depart) {
		this.depart = depart;
	}

	/** 
	 * Getter de arrivee
	 * @return arrivee
	 */
	public String getArrivee() {
		return arrivee;
	}

	/**
	 * Setter de arrivee
	 * @param arrivee : adresse d'arrivee
	 */
	public void setArrivee(String arrivee) {
		this.arrivee = arrivee;
	}

	/**
	 * Getter des horaires de depart le matin
	 * @return heureDepart
	 */
	public String getHeureDepartMatin() {
		return heureDepartMatin;
	}
	
	/**
	 * Setter des horaires de depart
	 * @param heureDepart : horaires de depart
	 */
	public void setHeureDepartMatin(String heureDepart) {
		this.heureDepartMatin = heureDepart;
	}

	/**
	 * Getter des horaires de depart le soir
	 * @return heureDepart
	 */
	public String getHeureDepartSoir() {
		return heureDepartSoir;
	}
	
	/**
	 * Setter des horaires de depart
	 * @param heureDepart : horaires de depart
	 */
	public void setHeureDepartSoir(String heureDepart) {
		this.heureDepartSoir = heureDepart;
	}
	
	/**
	 * Getter de la liste de user
	 * @return userList
	 */
	public ArrayList<Information> getUserList() {
		return userList;
	}

	/**
	 * Setter de la liste de user
	 * @param userList : liste de user
	 */
	public void setUserList(ArrayList<Information> userList) {
		this.userList = userList;
	}
	
	/**
	 * Methode toString() de Ride
	 */
	public String toString() {
		String users = new String () ;
		for (int i=0; i<userList.size(); i++) {
			users = users +" " +userList.get(i).getLogin()+" // Conducteur? "+userList.get(i).isConducteur()+"\n" ;
		}
		return "RIDE - depart: "+depart+"\narrivee: "+arrivee+"\nheureDepartMatin: "+heureDepartMatin
				+"\nheureDepartSoir: "+heureDepartSoir+"\nUsers: "+users ;
	}
	

}

