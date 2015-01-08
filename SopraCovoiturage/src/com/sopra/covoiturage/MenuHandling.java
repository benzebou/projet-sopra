package com.sopra.covoiturage;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MenuHandling implements OnClickListener {
	public static final int RESULT_CLOSE_ALL = -1;
	
	FacadeView facade;
	View menu;
	Activity activity;
	TextView profileText;
	TextView disconnectionText;
	
	public MenuHandling(FacadeView facade, Activity activity, View menu) {
		this.facade = facade;
		this.menu = menu;
		
		Log.d("SC", "MenuHandling created");
		
		this.profileText = (TextView) menu.findViewById(R.id.profileText);
		this.profileText.setOnClickListener(this);
		this.disconnectionText = (TextView) menu.findViewById(R.id.disconnectionText);
		this.disconnectionText.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		if(arg0.equals(disconnectionText)) {
			facade.performDisconnect();
			activity.finishActivity(RESULT_CLOSE_ALL);
			
		} else if (arg0.equals(profileText)) {
			facade.changeActivity(ProfileActivity.class);
		}
		
		
	}

}
