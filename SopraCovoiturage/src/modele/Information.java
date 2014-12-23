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
	
}
