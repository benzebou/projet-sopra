package com.sopra.covoiturage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ConnectingActivity extends Activity {
	
	private FacadeView facade;
	private EditText loginText;
	private EditText mdpText;
	private TextView passwordForgottenText ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connecting_page);
		
		loginText = (EditText) findViewById(R.id.loginField);
		mdpText = (EditText) findViewById(R.id.passwordField);
		
		facade = FacadeView.getInstance(this);
		
		passwordForgottenText = (TextView)findViewById(R.id.passwordForgottenField);
		passwordForgottenText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("SC", "Password forgotten");
                    facade.changeActivityPasswordForgotten();
                } catch (Exception except) {
                    Log.e("SC","Ooops"+except.getMessage());
                }
            }});

	}
	
	public void onConnectionButtonClick(View v) {
		facade.performConnect(loginText.getText().toString(), mdpText.getText().toString());
	}
	
	public void onRegisterButtonClick(View v) {
		facade.changeActivityRegister();
	}
	
	public void onPasswordForgottenClick(View v) {
		Log.d("SC","passwordForgotten");
		facade.changeActivityPasswordForgotten();
	}
	

}