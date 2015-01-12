package com.sopra.covoiturage;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConnectingActivity extends Activity {
	
	private FacadeView facade;
	private EditText loginText;
	private EditText mdpText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connecting_page);
		
		loginText = (EditText) findViewById(R.id.loginField);
		mdpText = (EditText) findViewById(R.id.passwordField);
		
		facade = FacadeView.getInstance(this);
		

	}
	
	public void onConnectionButtonClick(View v) {
		if(isNetworkAvailable())
			facade.performConnect(loginText.getText().toString().replaceAll("\\s", ""), mdpText.getText().toString());
		else
			Toast.makeText(this, "Connectez-vous à Internet", Toast.LENGTH_LONG).show();
	}
	
	public void onRegisterButtonClick(View v) {
		if(isNetworkAvailable())
			facade.changeActivity(RegisterActivity.class);
		else
			Toast.makeText(this, "Connectez-vous à Internet", Toast.LENGTH_LONG).show();
			
	}
	
	public void onPasswordForgottenButtonClick(View v) {
		if(isNetworkAvailable())
			facade.changeActivity(PasswordForgottenActivity.class);
		else
			Toast.makeText(this, "Connectez-vous à Internet", Toast.LENGTH_LONG).show();
		
	}
	
	public void notificationConnectionFailure() {
		Toast.makeText(this, "La connexion a échoué", Toast.LENGTH_LONG).show();
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}