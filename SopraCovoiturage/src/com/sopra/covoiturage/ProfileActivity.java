package com.sopra.covoiturage;

import modele.Information;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	private FacadeView facade;
	private TextView login;
	private TextView name;
	private TextView firstname;
	private TextView pwd;
	private TextView email;
	private TextView phone;
	private TextView town;
	private TextView postCode;
	private TextView workplace;
	private TextView goingTime;
	private TextView returningTime;
	private TextView workingDays;
	private TextView modify;
	private TextView back;
	private TextView conductor;
	private TextView notification;
	private Information info ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_page);
		facade = FacadeView.getInstance(this);
		
		login=(TextView) findViewById(R.id.login);
		name = (TextView) findViewById(R.id.nom);
		firstname = (TextView) findViewById(R.id.prenom);
		pwd = (TextView) findViewById(R.id.mot_de_passe);
		email = (TextView) findViewById(R.id.email);
		phone = (TextView) findViewById(R.id.telephone);
		town = (TextView) findViewById(R.id.commune);
		postCode = (TextView) findViewById(R.id.code_postal);
		workingDays = (TextView) findViewById(R.id.jours);
		conductor = (TextView) findViewById(R.id.conducteur);
		notification = (TextView) findViewById(R.id.notification);	
		goingTime = (TextView) findViewById(R.id.heure_aller);
		returningTime = (TextView) findViewById(R.id.heure_retour);
		workplace = (TextView) findViewById(R.id.lieu_de_travail);	
		modify = (Button) findViewById(R.id.modifier);
		back = (Button) findViewById(R.id.retour);

		
		info = facade.getProfileInformation("toto");
		
		login.setText(info.getLogin());
		name.setText(info.getName());
		firstname.setText(info.getFirstname());
		email.setText(info.getEmail());
		phone.setText(info.getPhone());
		//town.setText(info.getText);
		postCode.setText(info.getPostcode());
		workingDays.setText(info.daysToString());
		if (info.isConducteur())
			conductor.setText("oui");
		else conductor.setText("non");
		goingTime.setText(info.getMorning());
		returningTime.setText(info.getEvening());
		workplace.setText(info.getWorkplace());
		

	}
	
	public void onModificationButtonClick(View v) {
		
	}
	
	public void onRetourButtonClick(View v) {
		
	}
}