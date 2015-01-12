package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.HashMap;

import modele.Information;
import modele.Ride;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import controller.ControllerFacade;

public class FacadeView {
	
	/**
	 * Singleton de la classe FacadeView
	 */
	private static FacadeView singleton ;
	
	private ControllerFacade controller;
	private Activity firstActivity;
	private RideActivity searchRide;
	private WorkplaceManagementActivity workMan;
	private WorkplaceAdditionActivity workAdd;
	private PasswordForgottenActivity pwActivity;
	private RegisterActivity rActivity;
	private UsersActivity uActivity;
	
	private boolean admin;
	
	
	private FacadeView(Activity activity) {
		controller = ControllerFacade.getInstance(this);
		firstActivity = activity;	
	}

	/**
	 * Permet de recuperer l'instance de FacadeView
	 * @return singleton
	 */
	public static FacadeView getInstance (Activity activity) {
		if (singleton == null) {
			singleton = new FacadeView(activity) ;
		}
		return singleton ;
	}
	
	
	public void changeActivity(Class activity) {
		Intent i = new Intent(firstActivity, activity);
		firstActivity.startActivityForResult(i, 1);
	}
	
	public void changeActivityReport(Activity activity) {
		Intent i = new Intent(activity, ReportActivity.class);
		activity.startActivityForResult(i, 1);
	}

	
	/* CONNECTION */
	public void performConnect(String nickname, String password) {
		controller.performConnect(nickname, password);
	}
	public void processConnected(boolean admin) {
		Intent i;
		if(admin) 
			i = new Intent(firstActivity, UsersActivity.class); 
		else 
			i = new Intent(firstActivity, RideActivity.class);
			
		firstActivity.startActivityForResult(i, 1); 
	}
	

	public void changeActivityProfile(Activity activity) {
		Intent i;
		if(this.admin) 
			i = new Intent(activity, AdminProfileActivity.class); 
		else 
			i = new Intent(activity, ProfileActivity.class);
			
		activity.startActivityForResult(i, 1); 
	}
	
	
	
	/* DISCONNECTION */
	public void performDisconnect() {
		controller.performDisconnect();
	}
	public void processUserDisconnected() {
		
	}
	
	public void processUserNotDisconnected () {
		
	}
	
	
	/**
	 * methode permettant d'inscrire un nouvel utilisateur
	 * @param info : informations du profil de l'utilisateur
	 */
	public void performRegister (Information info){
		controller.performRegister(info);
	}
	
	public void performPasswordForgotten (String mail) {
		controller.passwordForgotten(mail);
	}
	
	/*PROFILE*/
	
	/**
	 * Methode utilisee seulement par un administrateur
	 * Permet de supprimer un utilisateur
	 * @param nickname : login de l'utilisateur a supprimer
	 */
	public void performDeletion (String nickname) {
		controller.performDeletion(nickname);
	}
	/**
	 * methode permettant de modifier le profil d'un utilisateur
	 * @param info : informations sur l'utilisateur
	 */
	public void performProfileModification (Information info) {
		controller.performProfileModification(info);
	}
	
	/**
	 * Methode permettant de renvoyer les informations sur l'utilisateur ayant pour login nickname 
	 * @param nickname : login de l'utilisateur 
	 * @return Informations : informations sur l'utilisateur 
	 */
	public Information getProfileInformation (String nickname) {
		return controller.getProfileInformation (nickname);
	}
	
	/**
	 * Methode permettant de renvoyer la liste des communes
	 * @return ArrayList<String> : liste des communes
	 */
	public ArrayList<String> getPostcodeList() {
		return controller.getPostcodeList() ;
	}
	
	
	
	public void processNotConnected() {
		((ConnectingActivity)firstActivity).notificationConnectionFailure();
	}
	
	public void processSendPwdMailOk () {
		if (getPwActivity() == null)
			Log.d("SC","Erreur processSendPwdMailOk");
		else 
			getPwActivity().notificationSendPwdMailOk();		
	}

	public void processSendPwdMailFailure () {
		if (getPwActivity() == null)
			Log.d("SC","Erreur processSendPwdMailFailure");
		else 
			getPwActivity().notificationSendPwdMailFailure();		
	}
	
	public void notificationRegisterFailure(){
		if (getPwActivity() != null)
			getrActivity().notificationRegisterFailure();	
	}
	
	public void registrationFailed (int codeErreur) {
		
	}
	
	public void confirmModification () {
		
	}
	
	public void modificationFailed (int codeErreur) {
		
	}
	
	public void deletionFailure () {
		UsersActivity uActivity = this.getuActivity();
		if (uActivity != null)
			uActivity.notificationDeletionFailure();
	}
	
	/**
	 * Methode demandant au controller de chercher les trajets partant de postcode pour aller à workplace
	 * @param postcode
	 * @param workplace
	 */
	public void performRides(String postcode, String workplace) {
		this.controller.performRides(postcode, workplace);
	}
	
	/**
	 * Methode initialisation l'attribut ride de l'activite Ride.
	 * @param listOfRides
	 */
	public void processRides (ArrayList<Ride> listOfRides) {
		this.searchRide.setRides(listOfRides);
	}
	
	/**
	 * initialise le parametre listOfWorkplaces en faisant appel a la methode getWorkplace du controller
	 * @param listOfWorkplaces
	 */
	public void displayWorkplaces(HashMap<String, String> workplace) {
	}
	
	/**
	 * Demande au controller d'ajouter un workplace en base
	 * @param workplace
	 */
	public void addWorkplace(String workplace) {
		this.controller.addWorkplace(workplace);
	}
	
	public void deletionWorkplace(String workplace) {
		this.controller.deletionWorkplace(workplace);
	}
	
	
	
	public void erreurAddWorkplace () {
		
	}
	
	public void erreurDeletionWorkplace() {
		
	}

	public void displayTownList(ArrayList<String> townList) {

	}

	public void erreurAddTown() {

	}
	
	public ArrayList<Information> getUsers() {
		return controller.getUsers();
	}
	
	
	/**
	 * Methode permettant renvoyer le number de conducteurs et le nombre de passager
	 * @return String[] requete :
	 * si requete[0] = "-1" : echec et requete[1] = code erreur
	 * sinon requete[0] = nombre total de conducteurs 
	 * 	et requete[1] = nombre total de passagers 
	 */
	public String[] getNumberDriverAndPassenger() {
		String[] res = this.controller.getNumberDriverAndPassenger();
		return res;
	}
	
	/**
	 * Methode permettant de renvoyer le nombre total de connexions
	 * @return String[] requete :
	 * si requete[0] = "0" : succes et requete[1] = nombre total de connexions
	 * sinon requete[0] = "-1" : echec et requete[1] = code erreur 
	 */
	public String[] getNumberConnection() {
		String[] res = this.controller.getNumberConnection();
		return res;
	}
	
	/**
	 * Methode permettant de renvoyer le number de conducteurs et le nombre de passager par trajet
	 * @return HashMap<String,String[]>
	 * Key = String : trajet
	 * Value = String[0] : nombre de conducteurs ; String[1] : nombre de passagers 
	 */
	public HashMap<String,String[]> getNumberDriverAndPassengerPerRide () {
		HashMap<String,String[]> res = this.controller.getNumberDriverAndPassengerPerRide();
		return res;
	}
	
	/**
	 * Methode permettant de renvoyer la liste des lieux de travail
	 * @return ArrayList<String> : liste des lieux de travail
	 */
	public ArrayList<String> getWorkplaces() {
		return controller.getWorkplaces();
	}
	
	/**
	 * Methode permettant de renvoyer le login de l'utilisateur
	 * @return String : login de l'utilisateur
	 */
	public String getLogin() {
		return controller.getLogin();
	}

	/**
	 * Methode permettant de mettre à jour le login
	 */
	public void setLogin(String login) {
		controller.setLogin(login);
	}
	

	/**
	 * Methode permettant de renvoyer les infos correspondantes au user
	 * @return Information : infos correspondantes au user
	 */
	public Information getUserInfo() {
		return controller.getUserInfo();
	}

	/**
	 * Methode permettant de mettre à jour userInfo
	 * @param userInfo
	 */
	public void setUserInfo(Information userInfo) {
		controller.setUserInfo(userInfo);
	}

	
	public void setrActivity(RegisterActivity rActivity) {
		this.rActivity = rActivity;
	}

	public RegisterActivity getrActivity() {
		return rActivity;
	}


	
	/**
	 * 
	 * @return
	 */
	public PasswordForgottenActivity getPwActivity() {
		return pwActivity;
	}

	/**
	 * Methode permettant de mettre à jour PwActivity
	 * @param pwActivity
	 */
	public void setPwActivity(PasswordForgottenActivity pwActivity) {
		this.pwActivity = pwActivity;
	}

	/**
	 * Methode permettant de mettre à jour searchRide
	 * @param sRide
	 */
	public void setSearchRide(RideActivity sRide) {
		this.searchRide = sRide;
	}	

	/**
	 * Methode permettant de mettre à jour workAdd
	 * @param workAdd
	 */
	public void setWorkAdd(WorkplaceAdditionActivity workAdd) {
		this.workAdd = workAdd;
	}

	/**
	 * Methode permettant de mettre à jour workMan
	 * @param workMan
	 */
	public void setWorkMan(WorkplaceManagementActivity workMan) {
		this.workMan = workMan;
	}

	public UsersActivity getuActivity() {
		return uActivity;
	}

	public void setuActivity(UsersActivity uActivity) {
		this.uActivity = uActivity;
	}
	
}

