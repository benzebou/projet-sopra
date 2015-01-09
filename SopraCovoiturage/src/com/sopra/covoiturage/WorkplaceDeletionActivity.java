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

public class WorkplaceDeletionActivity extends Activity {
	
	private FacadeView fac;
	private TextView title;
	private String button;
	private Button bDel;
	private TableLayout table;
	private ArrayList<String> workplace;
	private LayoutInflater inflater;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workplace_management_page);
		this.fac = FacadeView.getInstance(this);
		this.fac.setWorkDel(this);
		
		//Change le titre
		title = (TextView) findViewById(R.id.Title);
		title.setText("Delete Workplace : ");
		
		//Rend les bouton delete invisible
		bDel = (Button) findViewById(R.id.BDelete);
		bDel.setVisibility(4);
		
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
			TableRow tr = (TableRow) inflater.inflate(R.layout.table_deletion, null); 
			//tr.findViewById(R.id.Delete)).setText((String) workplace.get(i);
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
	
	public void onClickDelete(View v) {
		button = ((Button) findViewById(R.id.Delete)).getText().toString();
		this.fac.deletionWorkplace(button);
		this.fac.changeActivity(WorkplaceManagementActivity.class);
	}
	
}