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
 * ConnectingActivity est la premi�re activit� de l'application.
 * L'utilisateur peut se connecter, s'inscrire ou aller sur la page de mot de passe oubli�.
 */
public class ConnectingActivity extends Activity {
	
	/**
     * La facade, permettant la communication avec le controller
     * @see FacadeView
     */
	private FacadeView facade;
	
	/**
     * La vue associ�e au login
     */
	private EditText loginText;
	
	/**
     * La vue associ�e au mot de passe
     */
	private EditText mdpText;
	
	@Override
	
	/**
     * M�thode d'instanciation de l'activit�
     * @param savedInstanceState
     */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Chargement du layout
		setContentView(R.layout.connecting_page);
		
		// R�cup�ration des champs texte
		loginText = (EditText) findViewById(R.id.loginField);
		mdpText = (EditText) findViewById(R.id.passwordField);
		
		// Cr�ation de la fa�ade
		facade = FacadeView.getInstance(this);	
	}
	
	/**
	 * M�thode appel�e lorsqu'on clique sur le bouton "Valider",
	 * tente de connecter l'utilisateur
	 * @param v Le bouton cliqu�
	 */
	public void onConnectionButtonClick(View v) {
		// Si l'utilisateur a acc�s � internet
		if(isNetworkAvailable()) {
			// On tente la connexion
			facade.performConnect(loginText.getText().toString().replaceAll("\\s", ""), mdpText.getText().toString()) ;
		}
		else
			Toast.makeText(this, "Connectez-vous � Internet", Toast.LENGTH_LONG).show(); // Sinon on pr�vient l'utilisateur
	}
	
	/**
	 * M�thode appel�e lorsqu'on clique sur le bouton "S'inscrire",
	 * ouvre l'activit� d'inscription
	 * @param v Le bouton cliqu�
	 */
	public void onRegisterButtonClick(View v) {
		// Si l'utilisateur a acc�s � internet
		if(isNetworkAvailable())
			facade.changeActivity(RegisterActivity.class); // On ouvre la nouvelle activit�
		else
			Toast.makeText(this, "Connectez-vous � Internet", Toast.LENGTH_LONG).show(); // Sinon on pr�vient l'utilisateur
			
	}
	
	/**
	 * M�thode appel�e lorsqu'on clique sur "Mot de passe oubli�",
	 * ouvre l'activit� du mot de passe oubli�
	 * @param v Le bouton cliqu�
	 */
	public void onPasswordForgottenButtonClick(View v) {
		// Si l'utilisateur a acc�s � internet
		if(isNetworkAvailable())
			facade.changeActivity(PasswordForgottenActivity.class); // On ouvre la nouvelle activit�
		else
			Toast.makeText(this, "Connectez-vous � Internet", Toast.LENGTH_LONG).show(); // Sinon on pr�vient l'utilisateur
		
	}
	
	/**
	 * M�thode notifiant a l'utilisateur que la connexion a �chou�
	 */
	public void notificationConnectionFailure() {
		this.runOnUiThread(new Runnable() {
			  public void run() {
			    Toast.makeText(ConnectingActivity.this, "La connexion a �chou�", Toast.LENGTH_LONG).show();
			  }
			});
	}
	
	/**
	 * V�rifie que la connexion au r�seau est activ�
	 * @return true si le reseau est disponible, false sinon
	 */
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}