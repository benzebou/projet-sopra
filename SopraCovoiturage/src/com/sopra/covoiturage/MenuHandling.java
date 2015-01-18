package com.sopra.covoiturage;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * MenuHandling permet d'afficher le menu et de g�rer les boutons associ�s : Profil et D�connexion.
 */
public class MenuHandling implements OnClickListener {
	public static final int RESULT_CLOSE_ALL = -1;
	
	/**
     * La facade, permettant la communication avec le controller
     * @see FacadeView
     */
	FacadeView facade;
	
	/**
     * La vue associ�e au menu
     */
	View menu;
	
	/**
     * L'activit� qui a cr�� le menu
     */
	Activity activity;
	
	/**
     * Le bouton "Profil"
     */
	TextView profileText;
	
	/**
     * Le bouton "D�connexion"
     */
	TextView disconnectionText;
	
	/**
     * Cr�ation du menu
     * @param facade 
     * @param activity L'activit� cr�ant le menu
     * @param menu La vue contenant le layout du menu
     */
	public MenuHandling(FacadeView facade, Activity activity, View menu) {
		// R�cup�ration des objets
		this.facade = facade;
		this.menu = menu;
		this.activity = activity;
		
		// R�cup�ration des boutons 
		this.profileText = (TextView) menu.findViewById(R.id.profileText);
		this.profileText.setOnClickListener(this);
		this.disconnectionText = (TextView) menu.findViewById(R.id.disconnectionText);
		this.disconnectionText.setOnClickListener(this);
	}

	/**
     * M�thode d�clench�e lorsqu'on clique sur l'un des boutons
     * Si on clique sur profil, on ouvre l'activit� montrant le profil de l'utilisateur
     * Si on clique sur d�connexion, on d�connecte l'utilisateur
     * @param arg0 Bouton cliqu�
     */
	@Override
	public void onClick(View arg0) {
		// Si on a cliqu� sur d�connexion
		if(arg0.equals(disconnectionText)) {
			facade.performDisconnect(); // On d�connecte l'utilisateur
			Intent i = new Intent();
			activity.setResult(MenuHandling.RESULT_CLOSE_ALL, i); // On pr�vient l'activit� pr�c�dente qu'il faut se quitter
			activity.finish(); // On quitte cette activit�
		} else if (arg0.equals(profileText)) { // Si on a cliqu� sur profil
			facade.setProfileLogin(facade.getLogin()); // On pr�vient qu'on veut voir le profil de l'utilisateur
			facade.changeActivityProfile(activity); // On ouvre une nouvelle activit�
		}
		
		
	}

}
