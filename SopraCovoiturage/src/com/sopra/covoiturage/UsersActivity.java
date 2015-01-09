package com.sopra.covoiturage;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class UsersActivity extends Activity {
	private FacadeView facade;
	private ListView userList;
	ArrayList<String> userArrayList = new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_page);

		facade = FacadeView.getInstance(this);
		
		View menu = findViewById(R.id.menu);
		MenuHandling menuH = new MenuHandling(facade, this, menu);
		
		userList = (ListView) findViewById(R.id.usersTable);
		
	}
	
	public void onModifyButtonClick(View v) {
		//facade.performConnect(loginText.getText().toString(), mdpText.getText().toString());
	}
	
	public void onSuppressButtonClick(View v) {
		//facade.changeActivityRegister();
	}
	
	public void onWorkplaceButtonClick(View v) {
		facade.changeActivity(WorkplaceManagementActivity.class);
	}
	
	public void onReportButtonClick(View v) {
		facade.changeActivity(ReportActivity.class);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch(resultCode)
	    {
	    case MenuHandling.RESULT_CLOSE_ALL:
	        setResult(MenuHandling.RESULT_CLOSE_ALL);
	        finish();
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}
}
