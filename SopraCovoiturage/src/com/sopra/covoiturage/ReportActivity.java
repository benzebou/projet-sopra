package com.sopra.covoiturage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ReportActivity extends Activity {
	
	private FacadeView facade;
	
	private TextView nbConnexions;
	private TextView nbConducteurs;
	private TextView nbNonConducteurs;
	// TODO : liste users par trajet
	
	/**
	 * Cree la page de consultation des rapports admin
	 * @param savedInstanceState
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_page);
		facade = FacadeView.getInstance(this);
		nbConnexions =(TextView) findViewById(R.id.nb_connexion);
		nbConducteurs =(TextView) findViewById(R.id.nb_conducteurs);
		nbNonConducteurs =(TextView) findViewById(R.id.nb_non_conducteurs);
		
		String[] res = facade.getNumberConnection();
		if (res[0] != "-1") 
			nbConnexions.setText(res[1]);
		
		res = facade.getNumberDriverAndPassenger();
		if (res[0] != "-1") {
			nbConducteurs.setText(res[0]);
			nbNonConducteurs.setText(res[1]);
		}
		

	}

}
