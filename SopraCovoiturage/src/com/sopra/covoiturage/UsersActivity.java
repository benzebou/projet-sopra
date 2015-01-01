package com.sopra.covoiturage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class UsersActivity extends Activity {
	private FacadeView facade;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_page);

		facade = FacadeView.getInstance(this);
		
		View menu = findViewById(R.id.menu);
		MenuHandling menuH = new MenuHandling(facade, menu);
		
	}
	
	public void onModifyButtonClick(View v) {
		//facade.performConnect(loginText.getText().toString(), mdpText.getText().toString());
	}
	
	public void onSuppressButtonClick(View v) {
		//facade.changeActivityRegister();
	}
	


}
