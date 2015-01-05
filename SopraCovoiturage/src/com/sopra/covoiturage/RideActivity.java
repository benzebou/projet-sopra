package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modele.Information;
import modele.Ride;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RideActivity extends Activity{
	
	private FacadeView fac;
	private EditText departText;
	private Spinner workplace;
	private String selWorkplace;
	private Spinner conducteur;
	private String selConducteur = "both";
	private Spinner aller;
	private String selAller;
	private Spinner retour;
	private String selRetour;
	private ArrayList<Ride> rides;
	private Information user;
	private TableLayout table;
	private LayoutInflater inflater;
		
	
	/**
	 * Creation de RideActivity.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ride_page);
		this.fac = new FacadeView(this);
		
		// Initialisation spinner workplace
		this.workplace = new Spinner(this);
		this.workplace = (Spinner) findViewById(R.id.Arrivee);
	    ArrayList<String> listW = new ArrayList<String>();
	    listW = this.fac.getWorkplaces();

        ArrayAdapter<String> dataAdapterW = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listW);
        dataAdapterW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		workplace.setAdapter(dataAdapterW);
		
		
		// Initialisation spinner conducteur
	    this.conducteur = new Spinner(this);
		this.conducteur = (Spinner) findViewById(R.id.Conducteur);
	    List<String> listC = new ArrayList<String>();
	    listC.add("les deux");
	    listC.add("conducteur");
	    listC.add("non conducteur");

        final ArrayAdapter<String> dataAdapterC = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listC);
        dataAdapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.conducteur.setAdapter(dataAdapterC);
		this.conducteur.setOnItemSelectedListener(new OnItemSelectedListener() { 
           @Override 
           public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
               //this.selConducteur = this.conducteur.getSelectedItem().toString();	
               //displayRide(this.selConducteur);	 
           }

           @Override
           public void onNothingSelected(AdapterView<?> arg0) {
           }
         });
		
		// Initialisation spinner heure aller
		this.aller = new Spinner(this);
		this.aller = (Spinner) findViewById(R.id.ChoixAller);
		InitHeure(this.aller);
		
		// Initialisation spinner heure retour
		this.retour = new Spinner(this);
		this.retour = (Spinner) findViewById(R.id.ChoixRetour);
		InitHeure(this.retour);
		
		
		// Initialise le tableau de trajet
		inflater = getLayoutInflater();
		table = new TableLayout(this);
		table = (TableLayout) findViewById(R.id.Trajets);
		//displayBasicRide();
	}
	
	/**
	 * Execution de cette fonction lorsque le user clique sur search.
	 * @param v
	 */
	public void onClickSearch(View v) {
		this.workplace = (Spinner) findViewById(R.id.Arrivee);
		this.selWorkplace = this.workplace.getSelectedItem().toString();
		this.departText = (EditText) findViewById(R.id.Depart);	
		this.aller = (Spinner) findViewById(R.id.ChoixAller);
		this.selAller = this.aller.getSelectedItem().toString();
		this.retour = (Spinner) findViewById(R.id.ChoixRetour);
		this.selRetour = this.retour.getSelectedItem().toString();
		fac.performRides(departText.getText().toString(), selWorkplace);
		displayRide(this.selConducteur);		
	}
	
	/**
	 * Execution de cette fonction lorsque le user change le choix du spinner conducteur.
	 * @param v
	 */
	public void onClickConducteur(View v) {
		this.conducteur = (Spinner) findViewById(R.id.Conducteur);
		this.selConducteur = this.conducteur.getSelectedItem().toString();	
		displayRide(this.selConducteur);	
	}
	
	/**
	 * Affichage des trajets correspondant aux parametres du profil du user à l'ouverture de la page.
	 */
	public void displayBasicRide() {
		fac.performRides(this.user.getPostcode(), this.user.getWorkplace());
		displayRide("both");
	}
	
	/**
	 * Affichage des trajets.
	 * @param conducteur
	 */
	public void displayRide(String selCond) {
		// parcours la liste rides et affiche les trajets dont "conducteur" correspond
		Iterator r = this.rides.iterator();
		while(r.hasNext()){
			// add a new row with mail and driver?.
			Iterator info = ((ArrayList<Ride>) r.next()).iterator();
			while(info.hasNext())
			if (selCond == "non conducteur" && ((Information)info.next()).isConducteur() == false ) {
				TableRow tr = (TableRow) inflater.inflate(R.layout.table_search, null); 
				
				((TextView) tr.findViewById(R.id.MailUser)).setText(((Information) info.next()).getEmail());
				((TextView) tr.findViewById(R.id.CondUser)).setText("Non");
				((TextView) tr.findViewById(R.id.HeureAller)).setText(((Information) info.next()).getMorning());
				((TextView) tr.findViewById(R.id.HeureRetour)).setText(((Information) info.next()).getEvening());
				
				if (this.selAller == ((Information) info.next()).getMorning())
					((TextView) tr.findViewById(R.id.HeureAller)).setTextColor(Color.parseColor("#de002d"));

				if (this.selRetour == ((Information) info.next()).getEvening())
					((TextView) tr.findViewById(R.id.HeureRetour)).setTextColor(Color.parseColor("#de002d"));
				
				table.addView(tr);
			}
			else if (selCond == "conducteur" && ((Information)info.next()).isConducteur() == true) {
				TableRow tr = (TableRow) inflater.inflate(R.layout.table_search, null); 
				
				((TextView) tr.findViewById(R.id.MailUser)).setText(((Information) info.next()).getEmail());
				((TextView) tr.findViewById(R.id.CondUser)).setText("Oui");
				((TextView) tr.findViewById(R.id.HeureAller)).setText(((Information) info.next()).getMorning());
				((TextView) tr.findViewById(R.id.HeureRetour)).setText(((Information) info.next()).getEvening());
				
				if (this.selAller == ((Information) info.next()).getMorning())
					((TextView) tr.findViewById(R.id.HeureAller)).setTextColor(Color.parseColor("#de002d"));

				if (this.selRetour == ((Information) info.next()).getEvening())
					((TextView) tr.findViewById(R.id.HeureRetour)).setTextColor(Color.parseColor("#de002d"));
				
				table.addView(tr);
			}
			else {
				TableRow tr = (TableRow) inflater.inflate(R.layout.table_search, null); 
				
				((TextView) tr.findViewById(R.id.MailUser)).setText(((Information) info.next()).getEmail());
				
				if (((Information) info.next()).isConducteur() == true)
					((TextView) tr.findViewById(R.id.CondUser)).setText("Oui");
				else
					((TextView) tr.findViewById(R.id.CondUser)).setText("Non");
			
				((TextView) tr.findViewById(R.id.HeureAller)).setText(((Information) info.next()).getMorning());
				((TextView) tr.findViewById(R.id.HeureRetour)).setText(((Information) info.next()).getEvening());
				
				if (this.selAller == ((Information) info.next()).getMorning())
					((TextView) tr.findViewById(R.id.HeureAller)).setTextColor(Color.parseColor("#de002d"));

				if (this.selRetour == ((Information) info.next()).getEvening())
					((TextView) tr.findViewById(R.id.HeureRetour)).setTextColor(Color.parseColor("#de002d"));
				
				table.addView(tr);
			}
		}
	}
	
	/**
	 * Fonction d'initialisation des spinner heure aller et heure retour
	 * @param spin
	 */
	private void InitHeure(Spinner spin) {
	    String heure;
		List<String> list = new ArrayList<String>();
	    
		for(int i=7; i< 20; i++) {
			heure = i + ":00";
	    	for(int j=1; j<5; j++) {
	    		list.add(heure);	
	    		heure = i + ":" + j*15;
	    	}
	    }
	    
	    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(dataAdapter);
	}
	
	/**
	 * setter de l'attribut rides. 
	 * @param rides
	 */
	public void setRides(ArrayList<Ride> rides) {
		this.rides = rides;
	}
	
}
