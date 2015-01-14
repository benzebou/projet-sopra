package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.List;

import modele.Information;
import modele.Ride;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RideActivity extends Activity {

	private FacadeView fac;
	private Spinner postCode;
	private String selPostCode;
	private Spinner workplace;
	private String selWorkplace;
	private Spinner driver;
	private String seldriver = "Les deux";
	private Spinner morning;
	private String selmorning;
	private Spinner evening;
	private String selevening;
	private ArrayList<Ride> rides;
	private Information user;
	private TableLayout table;
	private LayoutInflater inflater;

	/**
	 * Creation de RideActivity.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ride_page);
		this.fac = FacadeView.getInstance(this);
		this.fac.setSearchRide(this);
		
		this.user = fac.getUserInfo();
		if(this.user == null)
			this.user = fac.getProfileInformation(fac.getLogin());

		View menu = findViewById(R.id.menu);
		MenuHandling menuH = new MenuHandling(fac, this, menu);

		// Initialisation Spinner code postal
		this.postCode = new Spinner(this);
		this.postCode = (Spinner) findViewById(R.id.code_postal);
		ArrayList<String> listP = new ArrayList<String>();
		listP = this.fac.getPostcodeList();
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listP);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		postCode.setAdapter(dataAdapter);

		// Initialisation spinner workplace
		this.workplace = new Spinner(this);
		this.workplace = (Spinner) findViewById(R.id.Arrivee);
		ArrayList<String> listW = new ArrayList<String>();
		listW = this.fac.getWorkplaces();
		Log.d("Lulu", "Affichage des workplaces");
		ArrayAdapter<String> dataAdapterW = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listW);
		dataAdapterW
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		workplace.setAdapter(dataAdapterW);

		// Initialisation spinner driver
		this.driver = new Spinner(this);
		this.driver = (Spinner) findViewById(R.id.Conducteur);
		List<String> listC = new ArrayList<String>();
		listC.add("les deux");
		listC.add("conducteur");
		listC.add("non conducteur");
		this.seldriver = "les deux";

		final ArrayAdapter<String> dataAdapterC = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listC);
		dataAdapterC
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.driver.setAdapter(dataAdapterC);
		this.driver.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				setSelCond(getCond().getSelectedItem().toString());
				displayRide();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		// Initialisation spinner heure aller
		this.morning = new Spinner(this);
		this.morning = (Spinner) findViewById(R.id.ChoixAller);
		InitHeure(this.morning);

		// Initialisation spinner heure evening
		this.evening = new Spinner(this);
		this.evening = (Spinner) findViewById(R.id.ChoixRetour);
		InitHeure(this.evening);

		// Initialise le tableau de trajet
		inflater = getLayoutInflater();
		table = new TableLayout(this);
		table = (TableLayout) findViewById(R.id.Trajets);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		displayBasicRide();
	}

	/**
	 * Execution de cette fonction lorsque le user clique sur search.
	 * 
	 * @param v
	 */
	public void onClickSearch(View v) {
		this.postCode = (Spinner) findViewById(R.id.code_postal);
		this.selPostCode = this.postCode.getSelectedItem().toString();
		this.workplace = (Spinner) findViewById(R.id.Arrivee);
		this.selWorkplace = this.workplace.getSelectedItem().toString();
		this.morning = (Spinner) findViewById(R.id.ChoixAller);
		this.selmorning = this.morning.getSelectedItem().toString();
		this.evening = (Spinner) findViewById(R.id.ChoixRetour);
		this.selevening = this.evening.getSelectedItem().toString();
		fac.performRides(selPostCode, selWorkplace);
		displayRide();
	}

	/**
	 * Affichage des trajets correspondant aux parametres du profil du user à
	 * l'ouverture de la page.
	 */
	public void displayBasicRide() {
		Log.d("SC", "display Basic Ride : " + this.user.getWorkplace());
		fac.performRides(this.user.getPostcode(), this.user.getWorkplace());
		this.user =  fac.getUserInfo();
		this.selmorning = this.user.getMorning();
		this.selevening = this.user.getEvening();
		displayRide();
	}
	
	public void onButtonSearchClick(View v) {
		View searchView = findViewById(R.id.search);
		ImageView buttonSearch = (ImageView)findViewById(R.id.buttonSearch);
		
		if(searchView.getVisibility() == View.GONE) {
			searchView.setVisibility(View.VISIBLE);
			buttonSearch.setImageResource(R.drawable.ic_menu_less);
		} else {
			searchView.setVisibility(View.GONE);
			buttonSearch.setImageResource(R.drawable.ic_menu_more);
			
		}
	}

	/**
	 * Affichage des trajets.
	 * 
	 * @param driver
	 */
	public void displayRide() {
		String selCond = this.seldriver;
		resetRides();
		if (rides != null) {
			// parcours la liste rides et affiche les trajets dont "conducteur"
			// correspond
			for (int i = 0; i < rides.size(); i++) {
				for (int j = 0; j < rides.get(i).getUserList().size(); j++) {
					Information info = rides.get(i).getUserList().get(j);

					// add a new row with mail and driver?.
					if ((selCond.equals("non conducteur"))
							&& (info.isConducteur() == false)) {
						TableRow tr = (TableRow) inflater.inflate(
								R.layout.table_search, null);
						Log.d("lulu", "on passe dans la boucle");

						((TextView) tr.findViewById(R.id.MailUser))
								.setText(info.getEmail());
						((TextView) tr.findViewById(R.id.CondUser))
								.setText("Non");

						if (this.selmorning.equals(info.getMorning()))
							((TextView) tr.findViewById(R.id.HeureAller))
									.setTextColor(Color.parseColor("#de002d"));

						if (this.selevening.equals(info.getEvening()))
							((TextView) tr.findViewById(R.id.HeureRetour))
									.setTextColor(Color.parseColor("#de002d"));

						((TextView) tr.findViewById(R.id.HeureAller))
								.setText(info.getMorning());
						((TextView) tr.findViewById(R.id.HeureRetour))
								.setText(info.getEvening());

						table.addView(tr);
					} else if ((selCond.equals("conducteur"))
							&& (info.isConducteur() == true)) {
						TableRow tr = (TableRow) inflater.inflate(
								R.layout.table_search, null);
						Log.d("lulu", "on passe dans la boucle 2");

						((TextView) tr.findViewById(R.id.MailUser))
								.setText(info.getEmail());
						((TextView) tr.findViewById(R.id.CondUser))
								.setText("Oui");
						((TextView) tr.findViewById(R.id.HeureAller))
								.setText(info.getMorning());
						((TextView) tr.findViewById(R.id.HeureRetour))
								.setText(info.getEvening());

						if (this.selmorning.equals(info.getMorning()))
						((TextView) tr.findViewById(R.id.HeureAller))
								.setTextColor(Color.parseColor("#de002d"));

						if (this.selevening.equals(info.getEvening()))
							((TextView) tr.findViewById(R.id.HeureRetour))
									.setTextColor(Color.parseColor("#de002d"));

						table.addView(tr);
					} else if ((selCond.equals("les deux"))) {
						TableRow tr = (TableRow) inflater.inflate(
								R.layout.table_search, null);
						Log.d("lulu", "2:" + info.getMorning());
						Log.d("lulu", "3:" + this.selmorning);
						((TextView) tr.findViewById(R.id.MailUser))
								.setText(info.getEmail());
						Log.d("lulu", "on passe dans la boucle 3");

						if (info.isConducteur() == true)
							((TextView) tr.findViewById(R.id.CondUser))
									.setText("Oui");
						else
							((TextView) tr.findViewById(R.id.CondUser))
									.setText("Non");

						((TextView) tr.findViewById(R.id.HeureAller))
								.setText(info.getMorning());
						((TextView) tr.findViewById(R.id.HeureRetour))
								.setText(info.getEvening());

						if (this.selmorning.equals(info.getMorning()))
							((TextView) tr.findViewById(R.id.HeureAller))
									.setTextColor(Color.parseColor("#de002d"));

						if (this.selevening.equals(info.getEvening()))
							((TextView) tr.findViewById(R.id.HeureRetour))
									.setTextColor(Color.parseColor("#de002d"));

						table.addView(tr);
					}
				}
			}
		}
	}

	private void resetRides() {
		int count = table.getChildCount();
		for (int i = 1; i < count; i++) {
			View child = table.getChildAt(i);
			if (child instanceof TableRow)
				((ViewGroup) child).removeAllViews();
		}
	}

	/**
	 * Fonction d'initialisation des spinner heure aller et heure retour
	 * 
	 * @param spin
	 */
	private void InitHeure(Spinner spin) {
		String heure, h;
		List<String> list = new ArrayList<String>();

		for (int i = 7; i < 20; i++) {
			if(i < 10)
				heure = "0" + i + ":00";
			else
				heure = i + ":00";
			for (int j = 1; j < 5; j++) {
				list.add(heure);
				if( i < 10)
					heure = "0" + i + ":" + j * 15;
				else
					heure = i + ":" + j * 15;
			}
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(dataAdapter);
	}
	
	
	public void onClickMail(View v) {
		
	}

	/**
	 * setter de l'attribut rides.
	 * 
	 * @param rides
	 */
	public void setRides(ArrayList<Ride> rides) {
		this.rides = rides;
	}

	public void setSelCond(String selCond) {
		this.seldriver = selCond;
	}

	public Spinner getCond() {
		return this.driver;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch(resultCode)
	    {
	    case RESULT_OK:
	        finish();
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}

}
