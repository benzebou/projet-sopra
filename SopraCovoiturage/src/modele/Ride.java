package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Ride {

	private String depart ;
	
	private String arrivee ;
	
	private String heureDepart ;
	
	private ArrayList<Information> userList ;

	public Ride () {
		userList = new ArrayList<Information>() ;
	}
	
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getArrivee() {
		return arrivee;
	}

	public void setArrivee(String arrivee) {
		this.arrivee = arrivee;
	}

	public String getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(String heureDepart) {
		this.heureDepart = heureDepart;
	}

	public ArrayList<Information> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<Information> userList) {
		this.userList = userList;
	}
	
	public String toString() {
		String users = new String () ;
		for (int i=0; i<userList.size(); i++) {
			users = users +" " +userList.get(i).getLogin()+" // Conducteur? "+userList.get(i).isConducteur()+"\n" ;
		}
		return "RIDE - depart: "+depart+"\narrivee: "+arrivee+"\nheureDepart: "+heureDepart
				+"\nUsers: "+users ;
	}
	
	public boolean contains(String nHeureDepart) {
		if (this.heureDepart == nHeureDepart)
			return true ;
		else return false ;
	}
}

