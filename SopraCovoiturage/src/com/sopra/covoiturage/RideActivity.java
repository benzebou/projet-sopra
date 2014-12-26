package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modele.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class RideActivity extends Activity{
	
	//private FacadeView facade;
	private EditText DepartText;
	private Spinner workplace;
	private FacadeView fac;
	private Spinner conducteur;
	private ArrayList<Ride> rides;
	private Information user;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ride_page);
		
		// Initialisation spinner workplace
		this.workplace = new Spinner(this);
		this.workplace = (Spinner) findViewById(R.id.Arrivee);
	    List<String> listW = new ArrayList<String>();
	    // getWorkplace from the database
	    listW.add("Workplace1");
	    listW.add("Workplace2");
	    listW.add("Workplace3");
	    listW.add("Workplace4");
	    listW.add("Workplace5");
	    listW.add("Workplace6");
	    
        ArrayAdapter<String> dataAdapterW = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listW);
        dataAdapterW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		workplace.setAdapter(dataAdapterW);
		
		
		// Initialisation spinner conducteur
	    this.conducteur = new Spinner(this);
		this.conducteur = (Spinner) findViewById(R.id.Conducteur);
	    List<String> listC = new ArrayList<String>();
	    listC.add("les deux");
	    listC.add("conducteur");
	    listC.add("non conducteur");

        ArrayAdapter<String> dataAdapterC = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listC);
        dataAdapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.conducteur.setAdapter(dataAdapterC);
		
		
		// Initialise le tableau de trajet
		displayBasicRide();

	}
	

	public void onClickSearch(View v) {
		this.workplace = (Spinner) findViewById(R.id.Arrivee);
		this.DepartText = (EditText) findViewById(R.id.Depart);
		
	}
	
	public void onClickConducteur(View v) {
		this.conducteur = (Spinner) findViewById(R.id.Conducteur);
		Object selection = this.conducteur.getSelectedItem();
		displayRide((String) selection);
		
	}
	
	
	public void displayBasicRide() {
		this.rides = fac.performRides(this.user.getPostcode(), this.user.getWorkplace());
		//displayRide("both");
	}
	
	
	public void displayRide(String conducteur) {
		// parcours la liste rides et affiche les trajets dont conducteur correspond
		Iterator i = this.rides.iterator();
		while(i.hasNext()){
			// add a new row with mail and driver?.
		}
	}
	
}
