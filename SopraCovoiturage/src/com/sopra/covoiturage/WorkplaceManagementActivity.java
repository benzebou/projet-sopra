package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.Iterator;


import com.sopra.covoiturage.FacadeView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class WorkplaceManagementActivity extends Activity {
	
	private FacadeView fac;
	private TableLayout table;
	private ArrayList<String> workplace;
	private LayoutInflater inflater;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workplace_management_page);
		this.fac = FacadeView.getInstance(this);
		
		// Initialise le tableau de trajet
		inflater = getLayoutInflater();
		table = new TableLayout(this);
		table = (TableLayout) findViewById(R.id.WorkplaceTable);
		this.workplace = new ArrayList<String>();
		displayWorkplace();
		
	}

	
	private void displayWorkplace() {
	    this.workplace = this.fac.getWorkplaces();
		Iterator r = this.workplace.iterator();
		while(r.hasNext()){
			TableRow tr = (TableRow) inflater.inflate(R.layout.table_workplace_management, null); 
			((TextView) tr.findViewById(R.id.Workplace)).setText((String) r.next());
			tr.findViewById(R.id.Delete).setTag((String) r.next());
			table.addView(tr);
		}
	}
	

	
	public void onClickAdd(View v) {
		this.fac.changeActivityWorkAdd();
	}
	
	public void onClickDelete(View v) {
		
	}
	
}