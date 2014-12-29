package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.List;

import modele.Information;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends Activity  {

	private FacadeView facade;
	private EditText login;
	private EditText nom;
	private EditText prenom;
	private EditText mdp;
	private EditText email;
	private EditText telephone;
	private EditText commune;
	private EditText codePostal;
	private Spinner lieuDeTravail;
	private Spinner heureAller;
	private Spinner heureRetour;
	private CheckBox lundi;
	private CheckBox mardi;
	private CheckBox mercredi;
	private CheckBox jeudi;
	private CheckBox vendredi;
	private CheckBox samedi;
	private CheckBox dimanche;
	private CheckBox conducteur;
	private CheckBox notification;
	private Button inscrire;
	private Button annuler;

	private Boolean[] days = {true,true,true,true,true,false,false};
	private boolean estConducteur;
	private boolean estNotif;

	private Information info ;



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_page);
		login=(EditText) findViewById(R.id.login);
		nom = (EditText) findViewById(R.id.nom);
		prenom = (EditText) findViewById(R.id.prenom);
		mdp = (EditText) findViewById(R.id.mot_de_passe);
		email = (EditText) findViewById(R.id.email);
		telephone = (EditText) findViewById(R.id.telephone);
		commune = (EditText) findViewById(R.id.commune);
		codePostal = (EditText) findViewById(R.id.code_postal);
		lundi = (CheckBox) findViewById(R.id.lundi);
		mardi = (CheckBox) findViewById(R.id.mardi);
		mercredi = (CheckBox) findViewById(R.id.mercredi);
		jeudi = (CheckBox) findViewById(R.id.jeudi);
		vendredi =(CheckBox) findViewById(R.id.vendredi);
		samedi = (CheckBox) findViewById(R.id.samedi);
		dimanche = (CheckBox) findViewById(R.id.dimanche);
		conducteur = (CheckBox) findViewById(R.id.conducteur);
		notification = (CheckBox) findViewById(R.id.notification);	
		inscrire = (Button) findViewById(R.id.inscrire);
		annuler = (Button) findViewById(R.id.annuler);	

		//to do
		/*		lieuDeTravail = (Spinner) findViewById(R.id.lieu_de_travail);	
		List<String> list = new ArrayList<String>();
		list = facade.getWorkplaces();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lieuDeTravail.setAdapter(adapter);*/


		// Initialisation spinner heure aller
		heureAller = new Spinner(this);
		heureAller = (Spinner) findViewById(R.id.heure_aller);	
		InitHeure(this.heureAller);

		// Initialisation spinner heure retour
		heureRetour = new Spinner(this);
		heureRetour = (Spinner) findViewById(R.id.heure_retour);
		InitHeure(this.heureRetour);
		estNotif = false;
		estConducteur = false;
	}

	/**
	 * Aimed to set to the good values the attributes linked to checkboxes
	 * @param v view o the application
	 */
	public void onCheckBoxClicked(View v){

		if(v.getId()==R.id.lundi){
			if(lundi.isChecked())
				days[0]=true;
			else days[0]=false;
		}else if(v.getId()==R.id.mardi){
			if(mardi.isChecked())
				days[1]=true;
			else days[1]=false;
		}else if(v.getId()==R.id.mercredi){
			if(mercredi.isChecked())
				days[2]=true;
			else days[2]=false;
		}else if(v.getId()==R.id.jeudi){
			if(jeudi.isChecked())
				days[3]=true;
			else days[3]=false;
		}else if(v.getId()==R.id.vendredi){
			if(vendredi.isChecked())
				days[4]=true;
			else days[4]=false;
		}else if(v.getId()==R.id.samedi){
			if(samedi.isChecked())
				days[5]=true;
			else days[5]=false;
		}else if(v.getId()==R.id.dimanche){
			if(dimanche.isChecked())
				days[6]=true;
			else days[6]=false;
		}else if(v.getId()==R.id.conducteur){
			if(conducteur.isChecked())
				estConducteur=true;
			else estConducteur=false;
		}else if(v.getId()==R.id.notification){
			if(notification.isChecked())
				estNotif=true;
			else estNotif=false;
		}
	}

	/**
	 * Send the informations collected in the view to the FaçadeView
	 * @param v view o the application
	 */
	public void onInscrireButtonClick(View v) {
		String[] horaires = new String[2] ;
		horaires[0] = heureAller.getSelectedItem().toString();
		horaires[1] = heureRetour.getSelectedItem().toString();

		if (login.getText().toString().equals("") || mdp.getText().toString().equals("") || 
				email.getText().toString().equals("")|| nom.getText().toString().equals("")||
				prenom.getText().toString().equals("")||telephone.getText().toString().equals("")||
				codePostal.getText().toString().equals("") || lieuDeTravail.getTag().toString().equals("")){

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

			// set title
			alertDialogBuilder.setTitle("Inscription ratée");

			// set dialog message
			alertDialogBuilder
			.setMessage("Veuillez remplir tous les champs demandés ")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					dialog.cancel();
				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}else{

			this.info = new Information(login.getText().toString() ,mdp.getText().toString(),
					email.getText().toString(),nom.getText().toString(),prenom.getText().toString(), 
					telephone.getText().toString(), codePostal.getText().toString(),
					lieuDeTravail.getTag().toString(),horaires,days, estConducteur);


		}
	}


	/**
	 * Send back to the connecting page
	 * @param v view o the application
	 */
	public void onAnnulerButtonClick(View v) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle("Inscription ratée");

		// set dialog message
		alertDialogBuilder
		.setMessage("Voulez vous quittez la page?")
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity and open Connecting activity
				RegisterActivity.this.finish();
				facade.changeActivityConnecting();
			}
		})

		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	/**
	 * Fonction d'initialisation des spinner heure aller et heure retour
	 * @param spin
	 */
	private void InitHeure(Spinner spin) {
		String heure;
		List<String> list = new ArrayList<String>();

		for(int i=7; i< 20; i++) {
			for(int j=0; j<4; j++) {
				if (j==0){
					heure = i + ":00";
				} else {
					heure = i + ":" + j*15;
				}
				list.add(heure);
			}
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(dataAdapter);
	}
}
