package com.sopra.covoiturage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PasswordForgottenActivity extends Activity  {
	
	private FacadeView facade;
	private EditText mailText;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_forgotten_page);
		
		mailText = (EditText) findViewById(R.id.mailField);
		
		facade = FacadeView.getInstance(this);

	}
	
	public void onPasswordForgottenButtonClick(View v) {
		facade.performPasswordForgotten(mailText.getText().toString());
	}
	
	

}
