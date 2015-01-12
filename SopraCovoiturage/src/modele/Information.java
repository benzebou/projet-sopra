package modele;


public class Information {

	/**
	 * Login du user : unique
	 */
	private String login ;

	/**
	 * Mot de passe du user
	 */
	private String mdp ;

	/**
	 * Adresse mail du user
	 */
	private String email ;

	/**
	 * Nom du user
	 */
	private String name ;

	/**
	 * Prenom du user
	 */
	private String firstname ;

	/**
	 * Numero de telephone du user
	 */
	private String phone ;

	/**
	 * Code postal de l'adresse postale du user
	 */
	private String postcode ;

	/**
	 * Adresse de travail du user
	 */
	private String workplace ;

	/**
	 * Horaires de depart le matin : schedule[0]
	 * Horaires de depart le soir : schedule[1]
	 */
	private String [] schedule ; 

	/**
	 * Tableau indiquant si le user effectue son trajet indique le jour X
	 * Exemple : si days[0] est vrai, alors le user effectue son trajet le lundi
	 */
	private Boolean[] days ;

	/**
	 * Boolean indiquant si le user est conducteur ou non
	 */
	private boolean conducteur ;

	
	/**
	 * Boolean indiquant si le user vaut est notifie par l'ajout d'un nouveau trajet qui correspond a celui du user
	 */
	private boolean notifie ;
	
	
	/**
	 * Constructeur par defaut de Information
	 */
	public Information() {
		schedule = new String[2] ;
		days = new Boolean[7] ;
	}

	/**
	 * Constructeur de Information avec parametres
	 * @param login : login du user
	 * @param mdp : mot de passe du user
	 * @param email : adresse mail du user
	 * @param name : nom du user 
	 * @param firstname : prenom du user
	 * @param phone : numero de telephone du user
	 * @param postcode : code postale du user
	 * @param workplace : adresse de travail du user
	 * @param schedule : horaires de depart le matin et le soir
	 * @param days : tableau de boolean indiquant si oui ou non le trajet est effectue un jour X
	 * @param conducteur : indiquant si le user est conducteur ou non
	 * @param notifie : indiquant si le user veut etre notifie
	 */
	public Information(String login, String mdp, String email, String name,
			String firstname, String phone, String postcode, String workplace, String[] schedule,
			Boolean[] days, boolean conducteur, boolean notifie) {
		super();
		this.login = login;
		this.mdp = mdp;
		this.email = email;
		this.name = name;
		this.firstname = firstname;
		this.phone = phone;
		this.postcode = postcode;
		this.workplace = workplace;
		this.schedule = schedule ;
		this.days = days;
		this.conducteur = conducteur;
		this.notifie = notifie ;
	}
	
	/**
	 * Constructeur de Information avec parametres
	 * @param login : login du user
	 * @param mdp : mot de passe du user
	 * @param email : adresse mail du user
	 */
	public Information(String login, String mdp, String email) {
		super();
		this.login = login;
		this.mdp = mdp;
		this.email = email;
	}

	/**
	 * Setter du login
	 * @param login : login du user
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Setter du mot de passe
	 * @param mdp : mot de passe du user
	 */
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	/** 
	 * Setter de l'adresse mail du user
	 * @param email : adresse mail du user
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Setter du nom du user
	 * @param name : nom du user
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** 
	 * Setter du prenom du user
	 * @param firstname : prenom du user
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/** 
	 * Setter du numero de telephone du user
	 * @param phone : numero de telephone du user
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Setter du code postal du user
	 * @param postcode : code postal
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * Setter de l'adresse de travail du user
	 * @param workplace : adresse de travail
	 */
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	/**
	 * Setter des horaires de depart du matin
	 * @param morning : horaires de depart le matin
	 */
	public void setMorning (String morning) {
		this.schedule[0] = morning ;
	}

	/**
	 * Setter des horaires de depart du soir
	 * @param evening : horaires de depart le soir
	 */
	public void setEvening (String evening) {
		this.schedule[1] = evening ;
	}

	/** 
	 * Setter du tableau de jours
	 * @param days : tableau de boolean indiquant les jours o� le user effectue le trajet
	 */
	public void setDays(Boolean[] days) {
		this.days = days;
	}

	/**
	 * Setter de conducteur
	 * @param conducteur : boolean indiquant si le user est conducteur ou non
	 */
	public void setConducteur(boolean conducteur) {
		this.conducteur = conducteur;
	}
	
	/**
	 * Setter de notifie
	 * @param notifie : boolean indiquant si le user veut etre notifie ou non
	 */
	public void setNotifie (boolean notifie) {
		this.notifie = notifie ;
	}

	/** 
	 * Getter du login
	 * @return login
	 */
	public String getLogin() {
		return login;
	}

	/** 
	 * Getter du mot de passe
	 * @return mdp
	 */
	public String getMdp() {
		return mdp ;
	}

	/** 
	 * Getter de l'adresse mail 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/** 
	 * Getter du nom
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/** 
	 * Getter du prenom
	 * @return firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Getter du numero de telephone
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Getter du code postal
	 * @return postcode 
	 */
	public String getPostcode() {
		return postcode;
	}

	/** 
	 * Getter de l'adresse de travail
	 * @return Workplace
	 */
	public String getWorkplace() {
		return workplace;
	}

	/**
	 * Getter des horaires du matin
	 * @return schedule[0]
	 */
	public String getMorning() {
		return schedule[0] ;
	}

	/** 
	 * Getter des horaires du soir
	 * @return schedule[1]
	 */
	public String getEvening() {
		return schedule[1] ;
	}

	/** 
	 * Getter du tableau de jours
	 * @return days
	 */
	public Boolean[] getDays() {
		return days;
	}

	/** 
	 * Methode indiquant si le user est un conducteur
	 * @return vrai si le user est un conducteur ; faux sinon
	 */
	public boolean isConducteur() {
		return conducteur;
	}
	
	/**
	 * Methode indiquant si le user veut etre notifie
	 * @return vrai si le user veut etre notifie ; faux sinon
	 */
	public boolean isNotifie () {
		return notifie ;
	}

	/**
	 * Methode toString() de Information
	 */
	public String toString () {
		return "Login: "+login+"\nMdp: "+mdp+"\nMail: "+email+"\nNom: "+name+"\nPrenom: "+firstname+
				"\nTel: "+phone+"\nCodePostal: "+postcode+"\nTravail: "+workplace+"\nTime morning: "+schedule[0]
						+"\nTime evening: "+schedule[1]+"\nConducteur? : "+conducteur+"\nNotification? : "+notifie
						+"\nlundi? : "+days[0]+"\nmardi? : "+days[1]+"\nmercredi? : "+days[2]
						+"\njeudi? : "+days[3]+"\nvendredi? : "+days[4]+"\nsamedi? : "+days[5]+"\ndimanche? : "+days[6];
	}

	/**
	 * Methode toString() de l'attribut days
	 */
	public String daysToString(){
		String res="";
		for (int i=0;i<7;i++){
			if (days[i]){
				if (i==0) {
					if(!isLast(i))
						res+= "lundi, ";
					else
						res+= "lundi. ";
				} else if (i==1) {
					if(!isLast(i))
						res+= "mardi, ";
					else
						res+= "mardi. ";
				} else if (i==2) {
					if(!isLast(i))
						res+= "mercredi, ";
					else
						res+= "mercredi. ";
				} else if (i==3) {
					if(!isLast(i))
						res+= "jeudi, ";
					else
						res+= "jeudi. ";
				} else if (i==4) {
					if(!isLast(i))
						res+= "vendredi, ";
					else
						res+= "vendredi. ";
				} else if (i==5) {
					if(!isLast(i))
						res+= "samedi, ";
					else
						res+= "samedi. ";
				} else if (i==6) {
					res+= "dimanche.";
				}
			}

		}

		return res;	
	}
	
	public boolean isLast(int i) {
		boolean last = true;
		if(i < 6) {
			for (int j=i+1;j<7;j++){
				if(days[j]) {
					last = false;
					break;
				}
			}
		}
		return last;
	}
}
