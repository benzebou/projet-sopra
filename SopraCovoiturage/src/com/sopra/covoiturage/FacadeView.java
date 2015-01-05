package com.sopra.covoiturage;

import java.util.ArrayList;

import modele.Information;
import modele.Ride;
import android.app.Activity;
import android.content.Intent;
import controller.ControllerFacade;

public class FacadeView {
	
	/**
	 * Singleton de la classe FacadeView
	 */
	private static FacadeView singleton ;
	
	private ControllerFacade controller;
	private Activity firstActivity;
	private RideActivity searchRide;
	
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
	

	
	// Constructeur pour les test de ride Activity, a enlever
	public FacadeView(RideActivity activity) {
		controller = ControllerFacade.getInstance(this);
		searchRide = activity;
		
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
			
		firstActivity.startActivity(i); 
	}
	

	
	/* DISCONNECTION */
	public void performDisconnect(String nickname, String password) {
		controller.performDisconnect(nickname, password);
	}
	public void processUserDisconnected() {
		
	}
	
	public void processUserNotDisconnected () {
		
	}
	
	/* REGISTRATION */
	public void changeActivityRegister() {
		Intent i = new Intent(firstActivity, RegisterActivity.class);
		firstActivity.startActivity(i); 
	}
	
	
	/*PROFILE*/
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
	
	
	
	
	public void processNotConnected() {
		
	}
	
	public void processSendPwdMailOk () {
		
	}

	public void processSendPwdMailFailure () {
		
	}
	
	public void changeActivityProfile () {
		Intent i = new Intent(firstActivity, ProfileActivity.class);
		firstActivity.startActivity(i); 
	}
	
	public void registrationFailed (int codeErreur) {
		
	}
	
	public void confirmModification () {
		
	}
	
	public void modificationFailed (int codeErreur) {
		
	}
	
	public void changeActivityConnecting () {
		
	}
	
	public void deletionFailure () {
		
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
	public ArrayList<String> displayWorkplaces() {
		return this.controller.getWorkplaces();
	}
	
	public void erreurAddWorkplace () {
		
	}
	
	public void erreurDeletionWorkplace() {
		
	}

	public void displayTownList(ArrayList<String> townList) {

	}

	public void erreurAddTown() {

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
		setLogin(login);
	}
	

	/**
	 * Methode permettant de renvoyer les infos correspondantes au user
	 * @return Information : infos correspondantes au user
	 */
	public Information getUserInfo() {
		return controller.getUserInfo();
	}

	/**
	 * Methode permettant de mettre à jouruserInfo
	 */
	public void setUserInfo(Information userInfo) {
		setUserInfo(userInfo);
	}
	
}

