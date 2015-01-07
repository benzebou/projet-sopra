package com.sopra.covoiturage;

import android.app.Activity;
import android.os.Bundle;

public class ReportActivity extends Activity {
	
	private FacadeView facade;
	
	/**
	 * Cree la page de consultation des rapports admin
	 * @param savedInstanceState
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_page);
		facade = FacadeView.getInstance(this);
	}

}
