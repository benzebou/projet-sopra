package com.sopra.covoiturage;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * MenuHandling permet d'afficher le menu et de gérer les boutons associés : Profil et Déconnexion.
 */
public class MenuHandling implements OnClickListener {
	public static final int RESULT_CLOSE_ALL = -1;
	
	/**
     * La facade, permettant la communication avec le controller
     * @see FacadeView
     */
	FacadeView facade;
	
	/**
     * La vue associée au menu
     */
	View menu;
	
	/**
     * L'activité qui a créé le menu
     */
	Activity activity;
	
	/**
     * Le bouton "Profil"
     */
	TextView profileText;
	
	/**
     * Le bouton "Déconnexion"
     */
	TextView disconnectionText;
	
	/**
     * Création du menu
     * @param facade 
     * @param activity L'activité créant le menu
     * @param menu La vue contenant le layout du menu
     */
	public MenuHandling(FacadeView facade, Activity activity, View menu) {
		// Récupération des objets
		this.facade = facade;
		this.menu = menu;
		this.activity = activity;
		
		// Récupération des boutons 
		this.profileText = (TextView) menu.findViewById(R.id.profileText);
		this.profileText.setOnClickListener(this);
		this.disconnectionText = (TextView) menu.findViewById(R.id.disconnectionText);
		this.disconnectionText.setOnClickListener(this);
	}

	/**
     * Méthode déclenchée lorsqu'on clique sur l'un des boutons
     * Si on clique sur profil, on ouvre l'activité montrant le profil de l'utilisateur
     * Si on clique sur déconnexion, on déconnecte l'utilisateur
     * @param arg0 Bouton cliqué
     */
	@Override
	public void onClick(View arg0) {
		// Si on a cliqué sur déconnexion
		if(arg0.equals(disconnectionText)) {
			facade.performDisconnect(); // On déconnecte l'utilisateur
			Intent i = new Intent();
			activity.setResult(MenuHandling.RESULT_CLOSE_ALL, i); // On prévient l'activité précédente qu'il faut se quitter
			activity.finish(); // On quitte cette activité
		} else if (arg0.equals(profileText)) { // Si on a cliqué sur profil
			facade.setProfileLogin(facade.getLogin()); // On prévient qu'on veut voir le profil de l'utilisateur
			facade.changeActivityProfile(activity); // On ouvre une nouvelle activité
		}
		
		
	}

}
