package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.Iterator;

import com.sopra.covoiturage.FacadeView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
	private String delWorkplace;
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
		this.workplace = null;
		this.workplace = this.fac.getWorkplaces();
		for (int i = 0; i < workplace.size(); i++) {
			TableRow tr = (TableRow) inflater.inflate(
					R.layout.table_workplace_management, null);
			((TextView) tr.findViewById(R.id.Workplace))
					.setText((String) workplace.get(i));
			table.addView(tr);
		}		
		Log.d("Lulu", workplace.toString());
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

	public void onClickWorkplace(View v) {
		this.delWorkplace = ((TextView) findViewById(R.id.Workplace)).getText().toString();
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set le titre
		alertDialogBuilder.setTitle("Suppression de " + this.delWorkplace );

		// set le message du dialogue
		alertDialogBuilder
				.setMessage("Voulez vous supprimer " + this.delWorkplace + "?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								getFac().deletionWorkplace(getDelWorkplace());
								displayWorkplace();
							}
						})

				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// si le boutton est clicker, ferme seulement la box
						dialog.cancel();
					}
				});

		// crée la alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// l'affiche
		alertDialog.show();
	}

	public void onClickRefresh(View v) {
		displayWorkplace();
	}

	public FacadeView getFac() {
		return fac;
	}

	public void setFac(FacadeView fac) {
		this.fac = fac;
	}

	public String getDelWorkplace() {
		return delWorkplace;
	}

	public void setDelWorkplace(String delWorkplace) {
		this.delWorkplace = delWorkplace;
	}
	
	
	
	
}