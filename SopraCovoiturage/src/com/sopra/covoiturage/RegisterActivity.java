package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.List;

import modele.Information;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity  {

	private FacadeView facade;
	private EditText login;
	private EditText name;
	private EditText firstname;
	private EditText pwd;
	private EditText pwdVerif;
	private EditText email;
	private EditText phone;
	private Spinner postCode;
	private Spinner workplace;
	private Spinner goingTime;
	private Spinner returningTime;
	private CheckBox monday;
	private CheckBox tuesday;
	private CheckBox wednesday;
	private CheckBox thursday;
	private CheckBox friday;
	private CheckBox saturday;
	private CheckBox sunday;
	private CheckBox conductor;
	private CheckBox notification;
	private Button apply;
	private Button cancel;

	private Boolean[] days = {true,true,true,true,true,false,false};
	private boolean estConducteur;
	private boolean estNotif;

	private Information info ;


	/**
	 * Crée la page d'enregistrement d'un nouvel utilisateur
	 * @param savedInstanceState
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_page);
		facade = FacadeView.getInstance(this);
		facade.setrActivity(this);

		login=(EditText) findViewById(R.id.login);
		name = (EditText) findViewById(R.id.nom);
		firstname = (EditText) findViewById(R.id.prenom);
		pwd = (EditText) findViewById(R.id.mot_de_passe);
		pwdVerif = (EditText) findViewById(R.id.mdp2);
		email = (EditText) findViewById(R.id.email);
		phone = (EditText) findViewById(R.id.telephone);
		monday = (CheckBox) findViewById(R.id.lundi);
		tuesday = (CheckBox) findViewById(R.id.mardi);
		wednesday = (CheckBox) findViewById(R.id.mercredi);
		thursday = (CheckBox) findViewById(R.id.jeudi);
		friday =(CheckBox) findViewById(R.id.vendredi);
		saturday = (CheckBox) findViewById(R.id.samedi);
		sunday = (CheckBox) findViewById(R.id.dimanche);
		conductor = (CheckBox) findViewById(R.id.conducteur);
		notification = (CheckBox) findViewById(R.id.notification);	
		apply = (Button) findViewById(R.id.inscrire);
		cancel = (Button) findViewById(R.id.annuler);	


		// Initialisation spinner workplace
		this.workplace = new Spinner(this);
		this.workplace = (Spinner) findViewById(R.id.lieu_de_travail);
		ArrayList<String> listW = new ArrayList<String>();
		listW = this.facade.getWorkplaces();
		ArrayAdapter<String> dataAdapterW = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listW);
		dataAdapterW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		workplace.setAdapter(dataAdapterW);

		// Initialisation Spinner code postal
		this.postCode = new Spinner(this);
		this.postCode = (Spinner) findViewById(R.id.code_postal);
		ArrayList<String> list = new ArrayList<String>();
		list = this.facade.getPostcodeList();
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		postCode.setAdapter(dataAdapter);


		// Initialisation spinner heure aller
		goingTime = new Spinner(this);
		goingTime = (Spinner) findViewById(R.id.heure_aller);	
		InitHeure(this.goingTime);

		// Initialisation spinner heure retour
		returningTime = new Spinner(this);
		returningTime = (Spinner) findViewById(R.id.heure_retour);
		InitHeure(this.returningTime);
		estNotif = false;
		estConducteur = false;
	}

	/**
	 * Permet de donner les bonnes valeurs au attibuts correspondant à la checkbox
	 * @param v vue de l'application
	 */
	public void onCheckBoxClicked(View v){

		if(v.getId()==R.id.lundi){
			if(monday.isChecked())
				days[0]=true;
			else days[0]=false;
		}else if(v.getId()==R.id.mardi){
			if(tuesday.isChecked())
				days[1]=true;
			else days[1]=false;
		}else if(v.getId()==R.id.mercredi){
			if(wednesday.isChecked())
				days[2]=true;
			else days[2]=false;
		}else if(v.getId()==R.id.jeudi){
			if(thursday.isChecked())
				days[3]=true;
			else days[3]=false;
		}else if(v.getId()==R.id.vendredi){
			if(friday.isChecked())
				days[4]=true;
			else days[4]=false;
		}else if(v.getId()==R.id.samedi){
			if(saturday.isChecked())
				days[5]=true;
			else days[5]=false;
		}else if(v.getId()==R.id.dimanche){
			if(sunday.isChecked())
				days[6]=true;
			else days[6]=false;
		}else if(v.getId()==R.id.conducteur){
			if(conductor.isChecked())
				estConducteur=true;
			else estConducteur=false;
		}else if(v.getId()==R.id.notification){
			if(notification.isChecked())
				estNotif=true;
			else estNotif=false;
		}
	}

	/**
	 * Envoie les informations saisies si elles sont bonne vers la base de donnée
	 *  @param v vue de l'application
	 */
	public void onInscrireButtonClick(View v) {
		String[] horaires = new String[2] ;
		horaires[0] = goingTime.getSelectedItem().toString();
		horaires[1] = returningTime.getSelectedItem().toString();
		boolean pwdOk = pwd.getText().toString().equals(pwdVerif.getText().toString());

		/** Si toutes les infos on bien été rentrées on envoit le nouvel utilisateur */
		if (login.getText().toString().equals("") || !pwdOk || pwd.getText().toString().equals("")|| pwdVerif.getText().toString().equals("")|| 
				email.getText().toString().equals("")|| name.getText().toString().equals("")||
				firstname.getText().toString().equals("")||phone.getText().toString().equals("")||
				postCode.getSelectedItem().toString().equals("") || workplace.getSelectedItem().toString().equals("")){

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			// set le titre
			alertDialogBuilder.setTitle("Echec de l'inscription");
			if (!pwdOk){
				alertDialogBuilder
				.setMessage("Mot de passe invalide ");
			}
			else {
				alertDialogBuilder
				.setMessage("Veuillez remplir tous les champs demandes ");			
			}

			// set le message du dialogue
			alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// si le boutton est clicker, ferme seulement la box
					dialog.cancel();
				}
			});

			// crée la alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// l'affiche
			alertDialog.show();
		}else{


			this.info = new Information(login.getText().toString() ,pwd.getText().toString(),
					email.getText().toString(),name.getText().toString(),firstname.getText().toString(), 
					phone.getText().toString(), postCode.getSelectedItem().toString(),
					workplace.getSelectedItem().toString(),horaires,days, estConducteur, estNotif);
			facade.performRegister(info, this);
			facade.setLogin(login.getText().toString());


		}
	}


	/**
	 * Renvoit à la page de connection
	 * @param v vue de l'application
	 */
	public void onAnnulerButtonClick(View v) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set le titre
		alertDialogBuilder.setTitle("Echec de l'inscription");

		// set le message du dialogue
		alertDialogBuilder
		.setMessage("Voulez vous quittez la page?")
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// si le boutton est cliquer fermer l'activity actuelle
				// et ouvre la Connecting activity
				RegisterActivity.this.finish();
			}
		})

		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// si le boutton est clicker, ferme seulement la box
				dialog.cancel();
			}
		});

		// crée la alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// l'affiche
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


	/**
	 * Fonction permettant de notifier lorsque l'enregistrement ne c'est pas déroulé comme prévu
	 */
	public void notificationRegisterFailure() {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, "Echec de l'inscription, veuillez vérifier les informations saisie", duration);
		toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 0, 0);
		toast.show();	
	}
}