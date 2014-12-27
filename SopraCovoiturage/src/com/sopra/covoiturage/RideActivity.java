package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modele.Information;
import modele.Ride;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
	private Spinner conducteur;
	//private Spinner heure;
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
		
		// Initialisation spinner workplace
		this.workplace = new Spinner(this);
		this.workplace = (Spinner) findViewById(R.id.Arrivee);
	    ArrayList<String> listW = new ArrayList<String>();
	    this.fac.displayWorkplaces(listW);
	    /*listW.add("Workplace1");
	    listW.add("Workplace2");
	    listW.add("Workplace3");
	    listW.add("Workplace4");
	    listW.add("Workplace5");
	    listW.add("Workplace6");*/
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
		
		
	/*	// Initialisation spinner heure
		this.heure = new Spinner(this);
		this.heure = (Spinner) findViewById(R.id.Heure);
	    List<String> listH = new ArrayList<String>();

        ArrayAdapter<String> dataAdapterH = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listH);
        dataAdapterH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.heure.setAdapter(dataAdapterH);
		*/
		
		// Initialise le tableau de trajet
		inflater = getLayoutInflater();
		table = new TableLayout(this);
		table = (TableLayout) findViewById(R.id.Trajets);
		displayBasicRide();
	}
	
	/**
	 * Execution de cette fonction lorsque le user clique sur search.
	 * @param v
	 */
	public void onClickSearch(View v) {
		this.workplace = (Spinner) findViewById(R.id.Arrivee);
		this.departText = (EditText) findViewById(R.id.Depart);
		Object selection = this.conducteur.getSelectedItem();	
		fac.performRides(departText.getText().toString(), workplace.toString());
		displayRide(selection.toString());		
	}
	
	/**
	 * Execution de cette fonction lorsque le user change le choix du spinner conducteur.
	 * @param v
	 */
	public void onClickConducteur(View v) {
		this.conducteur = (Spinner) findViewById(R.id.Conducteur);
		Object selection = this.conducteur.getSelectedItem();
		displayRide(selection.toString());	
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
	public void displayRide(String conducteur) {
		// parcours la liste rides et affiche les trajets dont "conducteur" correspond
		Iterator r = this.rides.iterator();
		while(r.hasNext()){
			// add a new row with mail and driver?.
			Iterator info = ((ArrayList<Ride>) r.next()).iterator();
			while(info.hasNext())
			if (conducteur == "non conducteur" && ((Information)info.next()).isConducteur() == false ) {
				TableRow tr = (TableRow) inflater.inflate(R.layout.table_search, null); 
				
				((TextView) tr.findViewById(R.id.MailUser)).setText(((Information) info.next()).getEmail());
				((TextView) tr.findViewById(R.id.CondUser)).setText("Non");
				((TextView) tr.findViewById(R.id.HeureAller)).setText(((Information) info.next()).getMorning());
				((TextView) tr.findViewById(R.id.HeureRetour)).setText(((Information) info.next()).getEvening());
				
				
				table.addView(tr);
			}
			else if (conducteur == "conducteur" && ((Information)info.next()).isConducteur() == true) {
				TableRow tr = (TableRow) inflater.inflate(R.layout.table_search, null); 
				
				((TextView) tr.findViewById(R.id.MailUser)).setText(((Information) info.next()).getEmail());
				((TextView) tr.findViewById(R.id.CondUser)).setText("Oui");
				((TextView) tr.findViewById(R.id.HeureAller)).setText(((Information) info.next()).getMorning());
				((TextView) tr.findViewById(R.id.HeureRetour)).setText(((Information) info.next()).getEvening());
				
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
				
				table.addView(tr);
			}
		}
	}
	
	/**
	 * setter de l'attribut rides. 
	 * @param rides
	 */
	public void setRides(ArrayList<Ride> rides) {
		this.rides = rides;
	}
	
}
