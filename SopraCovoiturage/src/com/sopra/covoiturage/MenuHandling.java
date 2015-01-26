package com.sopra.covoiturage;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * MenuHandling permet d'afficher le menu et de gerer les boutons associes : Profil et Deconnexion.
 */
public class MenuHandling implements OnClickListener {
	public static final int RESULT_CLOSE_ALL = -1;
	
	/**
     * La facade, permettant la communication avec le controller
     * @see FacadeView
     */
	FacadeView facade;
	
	/**
     * La vue associee au menu
     */
	View menu;
	
	/**
     * L'activite qui a cree le menu
     */
	Activity activity;
	
	/**
     * Le bouton "Profil"
     */
	TextView profileText;
	
	/**
     * Le bouton "Deconnexion"
     */
	TextView disconnectionText;
	
	/**
     * Creation du menu
     * @param facade 
     * @param activity L'activite creant le menu
     * @param menu La vue contenant le layout du menu
     */
	public MenuHandling(FacadeView facade, Activity activity, View menu) {
		// Recuperation des objets
		this.facade = facade;
		this.menu = menu;
		this.activity = activity;
		
		// Recuperation des boutons 
		this.profileText = (TextView) menu.findViewById(R.id.profileText);
		this.profileText.setOnClickListener(this);
		this.disconnectionText = (TextView) menu.findViewById(R.id.disconnectionText);
		this.disconnectionText.setOnClickListener(this);
	}

	/**
     * Methode declenchee lorsqu'on clique sur l'un des boutons
     * Si on clique sur profil, on ouvre l'activite montrant le profil de l'utilisateur
     * Si on clique sur deconnexion, on deconnecte l'utilisateur
     * @param arg0 Bouton clique
     */
	@Override
	public void onClick(View arg0) {
		// Si on a clique sur deconnexion
		if(arg0.equals(disconnectionText)) {
			facade.performDisconnect(); // On deconnecte l'utilisateur
			Intent i = new Intent();
			activity.setResult(MenuHandling.RESULT_CLOSE_ALL, i); // On previent l'activite precedente qu'il faut se quitter
			activity.finish(); // On quitte cette activite
		} else if (arg0.equals(profileText)) { // Si on a clique sur profil
			facade.setProfileLogin(facade.getLogin()); // On previent qu'on veut voir le profil de l'utilisateur
			facade.changeActivityProfile(activity); // On ouvre une nouvelle activite
		}
		
		
	}

}
