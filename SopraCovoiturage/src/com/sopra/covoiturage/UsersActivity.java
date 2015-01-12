package com.sopra.covoiturage;

import java.util.ArrayList;

import modele.Information;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
		fillUserArrayList();
		fillListView();
		registerForContextMenu(userList);
	}

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

	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		String[] menuItems = getResources().getStringArray(R.array.menu);
		String menuItemName = menuItems[menuItemIndex];
		String listItemName = userArrayList.get(info.position);
		if (menuItemName.equals("Modifier"))
			modifyUser(listItemName);
		else if (menuItemName.equals("Supprimer"))
			deleteUser(listItemName);
		return true;
	}

	private void deleteUser(String listItemName) {
		// TODO Auto-generated method stub
		
	}

	private void modifyUser(String listItemName) {
		// TODO Auto-generated method stub
		
	}

	private void fillListView() {
		StringAdapter adapter = new StringAdapter(getApplicationContext(),
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

	private void fillUserArrayList() {
		ArrayList<Information> infoList = facade.getUsers();
		for (Information i : infoList) {
			userArrayList.add(i.getFirstname() + " " + i.getName());
		}
	}

	public void onWorkplaceButtonClick(View v) {
		facade.changeActivity(WorkplaceManagementActivity.class);
	}

	public void onReportButtonClick(View v) {
		facade.changeActivity(ReportActivity.class);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case MenuHandling.RESULT_CLOSE_ALL:
			setResult(MenuHandling.RESULT_CLOSE_ALL);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
