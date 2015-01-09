package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.Iterator;


import com.sopra.covoiturage.FacadeView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class WorkplaceManagementActivity extends Activity {
	
	private FacadeView fac;
	private String button;
	private TableLayout table;
	private ArrayList<String> workplace;
	private LayoutInflater inflater;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workplace_management_page);
		this.fac = FacadeView.getInstance(this);
		this.fac.setWorkMan(this);
		
		// Initialise le tableau de trajet
		inflater = getLayoutInflater();
		table = new TableLayout(this);
		table = (TableLayout) findViewById(R.id.WorkplaceTable);
		this.workplace = new ArrayList<String>();
		displayWorkplace();
		
	}

	
	private void displayWorkplace() {
		resetRides();
	    this.workplace = this.fac.getWorkplaces();
		for (int i = 0 ; i<workplace.size() ;i++) {
			TableRow tr = (TableRow) inflater.inflate(R.layout.table_workplace_management, null); 
			((TextView) tr.findViewById(R.id.Workplace)).setText((String) workplace.get(i));
			tr.findViewById(R.id.Delete).setTag((String) workplace.get(i));
			table.addView(tr);
		}
	}
	
	
	private void resetRides() {
		int count = table.getChildCount();
		for (int i = 1; i < count; i++) {
			View child = table.getChildAt(i);
			if (child instanceof TableRow)
				((ViewGroup) child).removeAllViews();
		}
	}


	public void onClickAdd(View v) {
		this.fac.changeActivity(WorkplaceAdditionActivity.class);	
	}
	
	public void onClickDelete(View v) {
		// Recupere le tag du bouton
		button = ((Button) findViewById(R.id.Delete)).getTag().toString();
		// Si le temps : fenetre de confirmation de la deletion
		
		// envoie une demande de suppression du workplace correspondant
		this.fac.deletionWorkplace(button);
		// Re-affiche les workplaces
		displayWorkplace();
	}
	
}