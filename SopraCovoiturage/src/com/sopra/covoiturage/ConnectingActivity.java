package com.sopra.covoiturage;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConnectingActivity extends Activity {
	
	private FacadeView facade;
	private EditText loginText;
	private EditText mdpText;
	
	@Override
	/**
	 * cree la page de connexion
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connecting_page);
		
		loginText = (EditText) findViewById(R.id.loginField);
		mdpText = (EditText) findViewById(R.id.passwordField);
		
		facade = FacadeView.getInstance(this);
		

	}
	
	/**
	 * methode appelee lorsqu'on clique sur le bouton connexion
	 * @param v
	 */
	public void onConnectionButtonClick(View v) {
		if(isNetworkAvailable()) {
			facade.performConnect(loginText.getText().toString().replaceAll("\\s", ""), mdpText.getText().toString(), this);
			//facade.performConnect(loginText.getText().toString().replaceAll("\\s", ""), mdpText.getText().toString()) ;
		}
		else
			Toast.makeText(this, "Connectez-vous à Internet", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * methode appelee lorsqu'on clique sur le bouton s'enregistrer
	 * @param v
	 */
	public void onRegisterButtonClick(View v) {
		if(isNetworkAvailable())
			facade.changeActivity(RegisterActivity.class);
		else
			Toast.makeText(this, "Connectez-vous à Internet", Toast.LENGTH_LONG).show();
			
	}
	
	/**
	 * methode appelee lorsqu'on clique sur mot de passe oublié
	 * @param v
	 */
	public void onPasswordForgottenButtonClick(View v) {
		if(isNetworkAvailable())
			facade.changeActivity(PasswordForgottenActivity.class);
		else
			Toast.makeText(this, "Connectez-vous à Internet", Toast.LENGTH_LONG).show();
		
	}
	
	/**
	 * methode notifiant a l'utilisateur que la connexion a échoué
	 */
	public void notificationConnectionFailure() {
		this.runOnUiThread(new Runnable() {
			  public void run() {
			    Toast.makeText(ConnectingActivity.this, "La connexion a échoué", Toast.LENGTH_LONG).show();
			  }
			});
	}
	
	/**
	 * verifie que la connexion au reseau est active
	 * @return true si le reseau est disponible, false sinon
	 */
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}