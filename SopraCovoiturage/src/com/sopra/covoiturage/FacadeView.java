package com.sopra.covoiturage;

import java.util.ArrayList;

import controller.ControllerFacade;
import modele.Ride;

public class FacadeView {
	
	private ControllerFacade controller;
	
	
	//////////////////////////////
	//	   For Ride Activity    //
	//////////////////////////////
	
	public ArrayList<Ride> performRides(String postcode, String workplace) {
		return this.controller.performRides(postcode, workplace);
	}
	
	public void processConnected () {
		
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
}

