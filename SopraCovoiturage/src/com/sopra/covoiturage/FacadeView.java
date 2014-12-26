package com.sopra.covoiturage;

import java.util.ArrayList;

import modele.Ride;
import android.app.Activity;
import android.content.Intent;
import controller.ControllerFacade;

public class FacadeView {
	
	private ControllerFacade controller;
	private ConnectingActivity firstActivity;
	
	public FacadeView(ConnectingActivity activity) {
		controller = ControllerFacade.getInstance();
		firstActivity = activity;
		
	}
	
	/* CONNECTION */
	public void performConnect(String nickname, String password) {
		controller.performConnect(nickname, password);
	}
	
	
	//////////////////////////////
	//	   For Ride Activity    //
	//////////////////////////////
	
	public void performRides(String postcode, String workplace) {
		this.controller.performRides(postcode, workplace);
	}
	
	public void processConnected() {
		Intent i = new Intent(firstActivity, RideActivity.class);
		firstActivity.startActivity(i); 
	}
	
	public void processNotConnected() {
		
	}
	
	public void processSendPwdMailOk () {
		
	}

	public void processSendPwdMailFailure () {
		
	}
	
	public void processUserDisconnected() {
		
	}
	
	public void processUserNotDisconnected () {
		
	}
	
	public void changeActivityProfile () {
		
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
	
	public void processRides (ArrayList<Ride> listOfRides) {
		
	}
	
	public void displayWorkplaces(ArrayList<String> listOfWorkplaces) {
		
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
	
}

