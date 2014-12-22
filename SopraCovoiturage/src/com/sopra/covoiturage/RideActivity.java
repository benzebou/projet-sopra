package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class RideActivity extends Activity{
	
	//private FacadeView facade;
	private EditText DepartText;
	private Spinner spinner;
	private FacadeView fac;
	private boolean conducteur;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ride_page);
		
		Spinner spinner = new Spinner(this);
		spinner = (Spinner) findViewById(R.id.Arrivee);
	    List<String> list = new ArrayList<String>();
	    // getWorkplace from the database
	    list.add("Workplace1");
	    list.add("Workplace2");
	    list.add("Workplace3");
	    list.add("Workplace4");
	    list.add("Workplace5");
	    list.add("Workplace6");
	    
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
         
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         
		spinner.setAdapter(dataAdapter);
		
		ArrayList<String> rides = getBasicRide();
		
		
	}
	
	
	public void onCheckboxClicked(View view) {
		conducteur = ! conducteur;
	}
	
	
	public void onClickSearch(View v) {
		spinner = (Spinner) findViewById(R.id.Arrivee);
		DepartText = (EditText) findViewById(R.id.Depart);
		
	}
	
	
	public ArrayList<String> getBasicRide() {
		return null;//fac.performRides(/*User.Postcode, User.workplace*/);
	}
	
	
}
