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
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ride_page);
		
		Spinner spinner = new Spinner(this);
		spinner = (Spinner) findViewById(R.id.Arrivee);
	    List<String> list = new ArrayList<String>();
	    list.add("Android");
	    list.add("Java");
	    list.add("Spinner Data");
	    list.add("Spinner Adapter");
	    list.add("Spinner Example");
	    
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
         
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         
		spinner.setAdapter(dataAdapter);
	    
	}
	
	
	public void search(View view) {
		spinner = (Spinner) findViewById(R.id.Arrivee);
		DepartText = (EditText) findViewById(R.id.Depart);
	}
	
	public void onCheckboxClicked(View view) {
		 
	}
}
