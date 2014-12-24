package modele;


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
	
	private Boolean[] days ;
	
	private boolean conducteur ;

	public Information() {
		days = new Boolean[7] ;
	}
	
	public Information(String login, String mdp, String email, String name,
			String firstname, String phone, String postcode, String workplace,
			Boolean[] days, boolean conducteur) {
		super();
		this.login = login;
		this.mdp = mdp;
		this.email = email;
		this.name = name;
		this.firstname = firstname;
		this.phone = phone;
		this.postcode = postcode;
		this.workplace = workplace;
		this.days = days;
		this.conducteur = conducteur;
	}


	public void setLogin(String login) {
		this.login = login;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public void setDays(Boolean[] days) {
		this.days = days;
	}

	public void setConducteur(boolean conducteur) {
		this.conducteur = conducteur;
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

	public Boolean[] getDays() {
		return days;
	}

	public boolean isConducteur() {
		return conducteur;
	}
	
	public String toString () {
		return "Login: "+login+"\nMdp: "+mdp+"\nMail: "+email+"\nNom: "+name+"\nPrenom: "+firstname+
				"\nTel: "+phone+"\nCodePostal: "+postcode+"\nTravail: "+workplace+"\nConducteur? : "
				+conducteur+"\nlundi? : "+days[0]+"\nmardi? : "+days[1]+"\nmercredi? : "+days[2]
				+"\njeudi? : "+days[3]+"\nvendredi? : "+days[4]+"\nsamedi? : "+days[5]+"\ndimanche? : "+days[6];
	}
	
}
