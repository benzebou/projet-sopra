package com.sopra.covoiturage;

import modele.Information;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminProfileActivity extends Activity{
	private FacadeView facade;
	private TextView login;
	private TextView name;
	private TextView firstname;
	private TextView email;
	private TextView phone;
	private TextView modify;
	private TextView back;
	private Information info ;
	
	/**
	 * Crée la page de consultation du profile admin
	 * @param savedInstanceState
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_profile_page);
		facade = FacadeView.getInstance(this);
		login=(TextView) findViewById(R.id.login);
		email = (TextView) findViewById(R.id.email);
		modify = (Button) findViewById(R.id.modifier);
		back = (Button) findViewById(R.id.retour);


		info = facade.getAdminInformation(facade.getLogin());

		login.setText(info.getLogin());
		email.setText(info.getEmail());
	}
	
	/**
	 * Renvoit à la page de modification du profile utilisateur
	 * @param v vue de l'application
	 */
	public void onModifierButtonClick(View v) {
		facade.changeActivity(ProfileAdminModificationActivity.class);
	}

	/**
	 * Renvoit à la page précédente
	 * @param v vue de l'application
	 */
	public void onRetourButtonClick(View v) {
		this.finish();
	}
	

}
