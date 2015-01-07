package com.sopra.covoiturage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordForgottenActivity extends Activity  {
	
	private FacadeView facade;
	private EditText mailText;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_forgotten_page);
		
		mailText = (EditText) findViewById(R.id.mailField);
		
		facade = FacadeView.getInstance(this);
		facade.setPwActivity(this);

	}
	
	public void onPasswordForgottenButtonClick(View v) {
		facade.performPasswordForgotten(mailText.getText().toString());
	}
	
	public void notificationSendPwdMailOk() {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, "Votre nouveau mot de passe a été envoyé", duration);
		toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 0, 0);
		toast.show();		
	}
	
	public void notificationSendPwdMailFailure() {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, "Echec de l'envoie du nouveau mot de passe", duration);
		toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 0, 0);
		toast.show();	
	}
	
	public void notificationEmptyPwdMail() {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, "Erreur : le champs Email est vide", duration);
		toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 0, 0);
		toast.show();	
	}
	
	

}
