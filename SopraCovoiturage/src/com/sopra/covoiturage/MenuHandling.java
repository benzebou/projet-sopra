package com.sopra.covoiturage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MenuHandling implements OnClickListener {
	FacadeView facade;
	View menu;
	
	TextView profileText;
	TextView disconnectionText;
	
	public MenuHandling(FacadeView facade, View menu) {
		this.facade = facade;
		this.menu = menu;
		
		this.profileText = (TextView) menu.findViewById(R.id.profileText);
		profileText.setOnClickListener(this);
		this.disconnectionText = (TextView) menu.findViewById(R.id.disconnectionText);
	}

	@Override
	public void onClick(View arg0) {
		//if(arg0 == disconnectionText) 
			//facade.performDisconnect();
		
	}

}
