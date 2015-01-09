package com.sopra.covoiturage;

import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReportActivity extends Activity {
	
	private FacadeView facade;
	
	private TextView nbConnexions;
	private TextView nbConducteurs;
	private TextView nbNonConducteurs;

	private TableLayout table;
	private LayoutInflater inflater;
	
	/**
	 * Cree la page de consultation des rapports admin
	 * @param savedInstanceState
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_page);
		
		View menu = findViewById(R.id.menu);
		MenuHandling menuH = new MenuHandling(facade, this, menu);
		
		facade = FacadeView.getInstance(this);
		nbConnexions =(TextView) findViewById(R.id.nb_connexion);
		nbConducteurs =(TextView) findViewById(R.id.nb_conducteurs);
		nbNonConducteurs =(TextView) findViewById(R.id.nb_non_conducteurs);
		
		// Initialise le tableau des utilisateurs par trajet
		inflater = getLayoutInflater();
		table = new TableLayout(this);
		table = (TableLayout) findViewById(R.id.Trajets);
		
		// requete pour recuperer le nb de connexions
		String[] res = facade.getNumberConnection();
		if (res[0] != "-1") 
			nbConnexions.setText(res[1]);
		
		// requete pour recuperer le nb de conducteurs et passagers
		res = facade.getNumberDriverAndPassenger();
		if (res[0] != "-1") {
			nbConducteurs.setText(res[0]);
			nbNonConducteurs.setText(res[1]);
		}
		
		fillTableNumberUsersPerRide();
		
	}

	/**
	 * Remplir le tableau avec le resultat de la requete getNumberDriverAndPassengerPerRide()
	 */
	private void fillTableNumberUsersPerRide() {
		HashMap<String,String[]> map = facade.getNumberDriverAndPassengerPerRide();
		// Key = String : trajet
		// Value = String[0] : nombre de conducteurs ; String[1] : nombre de passagers 
		if (map != null)
		{
			for(Entry<String, String[]> entry : map.entrySet()) {
				String cle = entry.getKey();
				String[] valeur = entry.getValue();
				
				TableRow tr = (TableRow) inflater.inflate(R.layout.table_report, null);
				((TextView) tr.findViewById(R.id.Trajet)).setText(cle);
				((TextView) tr.findViewById(R.id.Nb_conducteurs)).setText(valeur[0]);
				((TextView) tr.findViewById(R.id.Nb_passagers)).setText(valeur[1]);
				
				table.addView(tr);
			}
		}
	}

}
