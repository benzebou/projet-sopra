package com.sopra.covoiturage;

import java.util.ArrayList;

import modele.Information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class UsersActivity extends Activity {
	private FacadeView facade;
	private ListView userList;
	ArrayList<String> userArrayList = new ArrayList<String>();
	StringAdapter adapter;

	@Override
	/**
	 * Cree la page d'acceuil d'un admin
	 * @param savedInstanceState
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_page);

		facade = FacadeView.getInstance(this);
		facade.setuActivity(this);

		View menu = findViewById(R.id.menu);
		MenuHandling menuH = new MenuHandling(facade, this, menu);

		userList = (ListView) findViewById(R.id.usersTable);
		fillUserArrayList();
		fillListView();
		registerForContextMenu(userList);
	}

	/**
	 * methode appelee lorsqu'on clique longtemps sur un element de la liste
	 */
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.usersTable) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(userArrayList.get(info.position));
			String[] menuItems = getResources().getStringArray(R.array.menu);
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	/**
	 * methode appelée lorsqu'on clique sur un element du menu contextuel
	 */
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		String[] menuItems = getResources().getStringArray(R.array.menu);
		String menuItemName = menuItems[menuItemIndex];
		String listItemName = userArrayList.get(info.position);
		String nickname = getLoginFromList(listItemName);
		if (menuItemName.equals("Modifier"))
			modifyUser(nickname);
		else if (menuItemName.equals("Supprimer"))
			deleteUser(nickname);
		return true;
	}

	/**
	 * supprimer un utilisateur
	 * @param nickname
	 */
	private void deleteUser(String nickname) {
		boolean requeteReussie = facade.performDeletion(nickname);
		if (requeteReussie) {
			String listName = getListNameFromLogin(nickname);
			this.userArrayList.remove(listName);
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * modifier un utilisateur
	 * @param nickname
	 */
	private void modifyUser(String nickname) {
		facade.setModificationLogin(nickname);
		facade.changeActivity(ProfileModificationActivity.class);
	}
	
	/**
	 * methode notifiant que la suppression a echoue
	 */
	public void notificationDeletionFailure() {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, "Echec de la suppression", duration);
		toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 0, 0);
		toast.show();	
	}


	/**
	 * methode permettant de remplir la listView contenant tous les utilisateurs
	 */
	private void fillListView() {
		adapter = new StringAdapter(getApplicationContext(),
				userArrayList);
		// On dit à la ListView de se remplir via cet adapter
		userList.setAdapter(adapter);
		/*
		 * Si vos données changent, penser à utiliser la fonction
		 * adapter.notifyDataSetChanged(); qui aura pour effet de notifier le
		 * changement de données et de recharger la liste automatiquement.
		 */
		adapter.notifyDataSetChanged();

	}

	/**
	 * methode permettant de remplir l'arrayList des logins 
	 */
	private void fillUserArrayList() {
		ArrayList<Information> infoList = facade.getUsers();
		for (Information i : infoList) {
			userArrayList.add(i.getFirstname() + " " + i.getName()+" : "+i.getLogin());
			//userArrayList.add(i.getLogin());
		}
	}
	
	/**
	 * recupère le login d'un utilisateur a partir de la listView : nom+prenom : login
	 * @param element : ligne de la listView concernée
	 * @return login
	 */
	private String getLoginFromList(String element) {
		int debut = element.indexOf(":");
		String login = element.substring(debut + 2);
		return login;
	}
	
	/**
	 * recupère a partir du login la ligne complete de la listView
	 * @param login
	 * @return nom+prenom : login
	 */
	private String getListNameFromLogin (String login) {
		String listname = null;
		for (String s : userArrayList){
			if (s.contains(": "+login) == true)
				listname = s;
		}
			
		return listname;
	}

	/**
	 * methode appelee lorsqu'on clique sur le bouton gestion des lieux de travail
	 * @param v
	 */
	public void onWorkplaceButtonClick(View v) {
		facade.changeActivity(WorkplaceManagementActivity.class);
	}

	/**
	 * methode appelee lorsqu'on clique sur le bouton generation des rapports
	 * @param v
	 */
	public void onReportButtonClick(View v) {
		facade.changeActivityReport(this);
	}

	@Override
	/**
	 * methode permettant de fermer l'activity
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case MenuHandling.RESULT_CLOSE_ALL:
			setResult(MenuHandling.RESULT_CLOSE_ALL);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
