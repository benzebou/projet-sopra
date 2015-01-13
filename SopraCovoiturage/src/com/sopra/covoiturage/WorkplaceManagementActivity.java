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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workplace_management_page);
		this.fac = FacadeView.getInstance(this);
		this.fac.setWorkMan(this);

		// Initialise le tableau de trajet
		/*
		 * inflater = getLayoutInflater(); table = new TableLayout(this); table
		 * = (TableLayout) findViewById(R.id.WorkplaceTable); this.workplace =
		 * new ArrayList<String>(); displayWorkplace();
		 */

		listWorkplace = (ListView) findViewById(R.id.WorkplaceTable);
		fillListView();
		registerForContextMenu(listWorkplace);
	}

	private void fillListView() {
		this.workplace = this.fac.getWorkplaces();
		adapter = new StringAdapter(getApplicationContext(),
				this.workplace);
		// On dit a la ListView de se remplir via cet adapter
		this.listWorkplace.setAdapter(adapter);
		/*
		 * Si vos donnees changent, penser a utiliser la fonction
		 * adapter.notifyDataSetChanged(); qui aura pour effet de notifier le
		 * changement de donnees et de recharger la liste automatiquement.
		 */
		adapter.notifyDataSetChanged();
	}

	public void onResume() {
		super.onResume();
		this.listWorkplace = null;
		this.workplace = null;
		this.listWorkplace = (ListView) findViewById(R.id.WorkplaceTable);
		fillListView();
		registerForContextMenu(listWorkplace);
		Log.d("Lulu", "On passe par le onResume");
	}
	
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
	
	public void suppressionFailure(){
		Toast.makeText(this, "Echec Suppression", Toast.LENGTH_SHORT);
	}
	
	public void addFailure(){
		Toast.makeText(this, "Echec Suppression", Toast.LENGTH_SHORT);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		String[] menuItems = getResources().getStringArray(R.array.menuSuppr);
		String menuItemName = menuItems[menuItemIndex];
		String listItemName = this.workplace.get(info.position);
		if(menuItemName.equals("Supprimer")) {
			this.fac.deletionWorkplace(listItemName);
			this.workplace.remove(info.position);
			adapter.notifyDataSetChanged();
		}
		return true;
	}

	
	public void onClickAdd(View v) {
		this.fac.changeActivity(WorkplaceAdditionActivity.class);
	}

	public FacadeView getFac() {
		return fac;
	}

	public void setFac(FacadeView fac) {
		this.fac = fac;
	}

	public StringAdapter getAdapter() {
		return this.adapter;
	}
	
	public String getDelWorkplace() {
		return delWorkplace;
	}
	
	public void AddWorkplace() {
		this.workplace = this.fac.getWorkplaces();
	}

	public void setDelWorkplace(String delWorkplace) {
		this.delWorkplace = delWorkplace;
	}

}