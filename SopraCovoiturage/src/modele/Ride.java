package modele;

import java.util.HashMap;
import java.util.Map.Entry;

public class Ride {

	private String depart ;
	
	private String arrivee ;
	
	private String heureDepart ;
	
	private HashMap<String, Boolean> userList ;

	public Ride () {
		userList = new HashMap<String, Boolean>() ;
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

	public HashMap<String, Boolean> getUserList() {
		return userList;
	}

	public void setUserList(HashMap<String, Boolean> userList) {
		this.userList = userList;
	}
	
	public String toString() {
		String users = new String () ;
		for (Entry<String, Boolean> entry : userList.entrySet()) {
			users = users +" " +entry.getKey()+" // Conducteur? "+entry.getValue()+"\n" ;
		}
		return "RIDE - depart: "+depart+"\narrivee: "+arrivee+"\nheureDepart: "+heureDepart
				+"\nUsers: "+users ;
	}
}

