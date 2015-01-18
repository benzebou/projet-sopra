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
 * ConnectingActivity est la première activité de l'application.
 * L'utilisateur peut se connecter, s'inscrire ou aller sur la page de mot de passe oublié.
 */
public class ConnectingActivity extends Activity {
	
	/**
     * La facade, permettant la communication avec le controller
     * @see FacadeView
     */
	private FacadeView facade;
	
	/**
     * La vue associée au login
     */
	private EditText loginText;
	
	/**
     * La vue associée au mot de passe
     */
	private EditText mdpText;
	
	@Override
	
	/**
     * Méthode d'instanciation de l'activité
     * @param savedInstanceState
     */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Chargement du layout
		setContentView(R.layout.connecting_page);
		
		// Récupération des champs texte
		loginText = (EditText) findViewById(R.id.loginField);
		mdpText = (EditText) findViewById(R.id.passwordField);
		
		// Création de la façade
		facade = FacadeView.getInstance(this);	
	}
	
	/**
	 * Méthode appelée lorsqu'on clique sur le bouton "Valider",
	 * tente de connecter l'utilisateur
	 * @param v Le bouton cliqué
	 */
	public void onConnectionButtonClick(View v) {
		// Si l'utilisateur a accès à internet
		if(isNetworkAvailable()) {
			// On tente la connexion
			facade.performConnect(loginText.getText().toString().replaceAll("\\s", ""), mdpText.getText().toString()) ;
		}
		else
			Toast.makeText(this, "Connectez-vous à Internet", Toast.LENGTH_LONG).show(); // Sinon on prévient l'utilisateur
	}
	
	/**
	 * Méthode appelée lorsqu'on clique sur le bouton "S'inscrire",
	 * ouvre l'activité d'inscription
	 * @param v Le bouton cliqué
	 */
	public void onRegisterButtonClick(View v) {
		// Si l'utilisateur a accès à internet
		if(isNetworkAvailable())
			facade.changeActivity(RegisterActivity.class); // On ouvre la nouvelle activité
		else
			Toast.makeText(this, "Connectez-vous à Internet", Toast.LENGTH_LONG).show(); // Sinon on prévient l'utilisateur
			
	}
	
	/**
	 * Méthode appelée lorsqu'on clique sur "Mot de passe oublié",
	 * ouvre l'activité du mot de passe oublié
	 * @param v Le bouton cliqué
	 */
	public void onPasswordForgottenButtonClick(View v) {
		// Si l'utilisateur a accès à internet
		if(isNetworkAvailable())
			facade.changeActivity(PasswordForgottenActivity.class); // On ouvre la nouvelle activité
		else
			Toast.makeText(this, "Connectez-vous à Internet", Toast.LENGTH_LONG).show(); // Sinon on prévient l'utilisateur
		
	}
	
	/**
	 * Méthode notifiant a l'utilisateur que la connexion a échoué
	 */
	public void notificationConnectionFailure() {
		this.runOnUiThread(new Runnable() {
			  public void run() {
			    Toast.makeText(ConnectingActivity.this, "La connexion a échoué", Toast.LENGTH_LONG).show();
			  }
			});
	}
	
	/**
	 * Vérifie que la connexion au réseau est activé
	 * @return true si le reseau est disponible, false sinon
	 */
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}