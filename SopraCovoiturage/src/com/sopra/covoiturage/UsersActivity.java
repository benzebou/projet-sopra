package com.sopra.covoiturage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UsersActivity extends Activity {
	private FacadeView facade;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_page);

		facade = FacadeView.getInstance(this);
	}
	
	public void onModifyButtonClick(View v) {
		//facade.performConnect(loginText.getText().toString(), mdpText.getText().toString());
	}
	
	public void onSuppressButtonClick(View v) {
		//facade.changeActivityRegister();
	}
}
