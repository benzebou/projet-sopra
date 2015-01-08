package modele;

import java.util.ArrayList;

public class Ride {


	/**
	 * Liste de user effectuant un meme trajet, a la meme heure
	 */
	private ArrayList<Information> userList ;

	/**
	 * Constructeur par defaut de Ride
	 */
	public Ride () {
		this.userList = new ArrayList<Information>() ;
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
		if (userList.get(0)!= null) {
			String users = new String () ;
			for (int i=0; i<userList.size(); i++) {
				users = users +" " +userList.get(i).getLogin()+" // Conducteur? "+userList.get(i).isConducteur()+"\n" ;
			}
			return "RIDE - depart: "+userList.get(0).getPostcode()+"\narrivee: "+userList.get(0).getPostcode()
					+"\nheureDepartMatin: "+userList.get(0).getMorning()
					+"\nheureDepartSoir: "+userList.get(0).getMorning()+"\nUsers: "+users ;
		}
		else return null ;
	}
	

}

