package com.sopra.covoiturage;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;

public class WorkplaceAdditionActivity extends Activity {

	private FacadeView fac;
	private TableLayout table;
	private ArrayList<String> workplace;
	private LayoutInflater inflater;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workplace_addition_page);
		this.fac = FacadeView.getInstance(this);
		
		
	}
	
	public void onClickAdd(View v) {
		// Demande l'ajout à la base
		
		//Repasse sur la WorkplaceManagementActivity
		this.fac.changeActivity(WorkplaceManagementActivity.class);
	}
}
