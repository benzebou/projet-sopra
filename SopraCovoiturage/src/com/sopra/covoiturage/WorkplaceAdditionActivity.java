package com.sopra.covoiturage;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TableLayout;

public class WorkplaceAdditionActivity extends Activity {

	private FacadeView fac;
	private TableLayout table;
	private ArrayList<String> workplace;
	private LayoutInflater inflater;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workplace_management_page);
		// this.fac = new FacadeView(this);
		
		// Initialise le tableau de trajet
		inflater = getLayoutInflater();
		table = new TableLayout(this);
		table = (TableLayout) findViewById(R.id.WorkplaceTable);
		this.workplace = new ArrayList<String>();
		//displayWorkplace();
		
	}
}
