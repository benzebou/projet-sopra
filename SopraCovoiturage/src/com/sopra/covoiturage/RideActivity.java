package com.sopra.covoiturage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class RideActivity extends Activity{
	
	//private FacadeView facade;
	private EditText DepartText;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ride_page);
		
		DepartText = (EditText) findViewById(R.id.Depart);
	}
	
}
