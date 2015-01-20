package com.sopra.covoiturage;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;

public class WorkplaceAdditionActivity extends Activity {

	private FacadeView fac;
	private String newPlace;
	private TableLayout table;
	private ArrayList<String> workplace;
	private LayoutInflater inflater;
	private WorkplaceManagementActivity workMan;
	
	/**
	 * Création de WorkplaceAdditionActivity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workplace_addition_page);
		this.fac = FacadeView.getInstance(this);
		// this.fac.setWorkAdd(this);
		this.workMan = this.fac.getWorkMan();
		
	}
	
	/**
	 * Methode appelée au click sur le bouton ajouter
	 * @param v
	 */
	public void onClickAdd(View v) {
		
		// Demande l'ajout à la base
		this.newPlace = ((EditText) findViewById(R.id.Name)).getText().toString();
		this.fac.addWorkplace(this.newPlace);
		
		//Repasse sur la WorkplaceManagementActivity
		this.finish();
	}
}
