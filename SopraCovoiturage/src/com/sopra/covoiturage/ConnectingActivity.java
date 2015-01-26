package com.sopra.covoiturage;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ConnectingActivity est la premiere activite de l'application.
 * L'utilisateur peut se connecter, s'inscrire ou aller sur la page de mot de passe oublie.
 */
public class ConnectingActivity extends Activity {
	
	/**
     * La facade, permettant la communication avec le controller
     * @see FacadeView
     */
	private FacadeView facade;
	
	/**
     * La vue associee au login
     */
	private EditText loginText;
	
	/**
     * La vue associee au mot de passe
     */
	private EditText mdpText;
	
	@Override
	
	/**
     * Methode d'instanciation de l'activite
     * @param savedInstanceState
     */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Chargement du layout
		setContentView(R.layout.connecting_page);
		
		// Recuperation des champs texte
		loginText = (EditText) findViewById(R.id.loginField);
		mdpText = (EditText) findViewById(R.id.passwordField);
		
		// Creation de la faeade
		facade = FacadeView.getInstance(this);	
	}
	
	/**
	 * Methode appelee lorsqu'on clique sur le bouton "Valider",
	 * tente de connecter l'utilisateur
	 * @param v Le bouton clique
	 */
	public void onConnectionButtonClick(View v) {
		// Si l'utilisateur a acces e internet
		if(isNetworkAvailable()) {
			// On tente la connexion
			facade.performConnect(loginText.getText().toString().replaceAll("\\s", ""), mdpText.getText().toString()) ;
		}
		else
			Toast.makeText(this, "Connectez-vous e Internet", Toast.LENGTH_LONG).show(); // Sinon on previent l'utilisateur
	}
	
	/**
	 * Methode appelee lorsqu'on clique sur le bouton "S'inscrire",
	 * ouvre l'activite d'inscription
	 * @param v Le bouton clique
	 */
	public void onRegisterButtonClick(View v) {
		// Si l'utilisateur a acces e internet
		if(isNetworkAvailable())
			facade.changeActivity(RegisterActivity.class); // On ouvre la nouvelle activite
		else
			Toast.makeText(this, "Connectez-vous e Internet", Toast.LENGTH_LONG).show(); // Sinon on previent l'utilisateur
			
	}
	
	/**
	 * Methode appelee lorsqu'on clique sur "Mot de passe oublie",
	 * ouvre l'activite du mot de passe oublie
	 * @param v Le bouton clique
	 */
	public void onPasswordForgottenButtonClick(View v) {
		// Si l'utilisateur a acces e internet
		if(isNetworkAvailable())
			facade.changeActivity(PasswordForgottenActivity.class); // On ouvre la nouvelle activite
		else
			Toast.makeText(this, "Connectez-vous e Internet", Toast.LENGTH_LONG).show(); // Sinon on previent l'utilisateur
		
	}
	
	/**
	 * Methode notifiant a l'utilisateur que la connexion a echoue
	 */
	public void notificationConnectionFailure() {
		this.runOnUiThread(new Runnable() {
			  public void run() {
			    Toast.makeText(ConnectingActivity.this, "La connexion a echoue", Toast.LENGTH_LONG).show();
			  }
			});
	}
	
	/**
	 * Verifie que la connexion au reseau est active
	 * @return true si le reseau est disponible, false sinon
	 */
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}