package com.sopra.covoiturage;

import java.util.ArrayList;
import java.util.Iterator;

import modele.Information;

import com.sopra.covoiturage.FacadeView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class WorkplaceManagementActivity extends Activity {

	private FacadeView fac;
	private StringAdapter adapter;
	private ListView listWorkplace;
	private ArrayList<String> workplace = new ArrayList<String>();
	private String delWorkplace;

	/**
	 * Création de WorkplaceManagementActivity
	 **/
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workplace_management_page);

	}

	/**
	 * Methode pour remplir la liste des workplaces
	 */
	private void fillListView() {
		this.workplace = new ArrayList<String>();
		this.workplace = this.fac.getWorkplaces();
		adapter = new StringAdapter(getApplicationContext(), this.workplace);
		// On dit a la ListView de se remplir via cet adapter
		this.listWorkplace.setAdapter(adapter);
		/*
		 * Si vos donnees changent, penser a utiliser la fonction
		 * adapter.notifyDataSetChanged(); qui aura pour effet de notifier le
		 * changement de donnees et de recharger la liste automatiquement.
		 */
		adapter.notifyDataSetChanged();
		Log.d("Lulu", "On passe dan fillListView" + this.fac.getWorkplaces().toString());
	}

	/**
	 * Methode appelée lorsque l'on revient sur l'activité Workplace Management
	 */
	public void onResume() {
		super.onResume();
		this.fac = FacadeView.getInstance(this);
		// Initialise la list de trajet
		listWorkplace = (ListView) findViewById(R.id.WorkplaceTable);
		fillListView();
		registerForContextMenu(listWorkplace);
		Log.d("Lulu", "On passe par le onResume");

	}

	/**
	 * Methode pour créer l'ouverture d'une fenetre lors d'un click long
	 */
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.WorkplaceTable) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(this.workplace.get(info.position));
		}
		String[] menuItems = getResources().getStringArray(R.array.menuSuppr);
		for (int i = 0; i < menuItems.length; i++) {
			menu.add(Menu.NONE, i, i, menuItems[i]);
		}
	}

	public void suppressionFailure() {
		Toast.makeText(this, "Echec Suppression", Toast.LENGTH_SHORT);
	}

	public void addFailure() {
		Toast.makeText(this, "Echec Suppression", Toast.LENGTH_SHORT);
	}

	/**
	 * Methode définissant ce que font les options de la fenetre ouverte lors du click long sur un workplace
	 */
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		String[] menuItems = getResources().getStringArray(R.array.menuSuppr);
		String menuItemName = menuItems[menuItemIndex];
		String listItemName = this.workplace.get(info.position);
		if (menuItemName.equals("Supprimer")) {
			this.fac.deletionWorkplace(listItemName);
			this.workplace.remove(info.position);
			Log.d("Lulu", workplace.toString());
			
			adapter.notifyDataSetChanged();
		}
		return true;
	}

	/**
	 * fonction appelée lors du click sur le bouton ajouter
	 * @param v
	 */
	public void onClickAdd(View v) {
		this.fac.changeActivity(WorkplaceAdditionActivity.class);
	}

	/**
	 * getter du champ FacadeView : fac
	 * @return FacadeView
	 */
	public FacadeView getFac() {
		return fac;
	}

	/**
	 * setter du champ FacadeView : fac
	 * @param fac
	 */
	public void setFac(FacadeView fac) {
		this.fac = fac;
	}

	/**
	 * getter du champ StringAdapter : adapter
	 * @return
	 */
	public StringAdapter getAdapter() {
		return this.adapter;
	}

	/**
	 * getter du champ String : delWorkplace
	 * @return
	 */
	public String getDelWorkplace() {
		return delWorkplace;
	}

	/**
	 * setter du champ String : delWorkplace
	 * @param delWorkplace
	 */
	public void setDelWorkplace(String delWorkplace) {
		this.delWorkplace = delWorkplace;
	}

}