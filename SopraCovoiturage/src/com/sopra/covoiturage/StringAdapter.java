package com.sopra.covoiturage;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class StringAdapter extends BaseAdapter {

  // source : http://tutorielandroid.francoiscolin.fr/control.php
  List<String> epreuve;
  LayoutInflater inflater;
  
 /**
	 * Ici le constructeur de l'adapter
	 *
	 * @param le contexte et la liste de Strings
	 */
  public StringAdapter(Context context,List<String> epreuve) {
    inflater = LayoutInflater.from(context);
    this.epreuve = epreuve;
  }
  
  // Fonction generee de base lorsque vous creez un adapter
  // Retourne la taille de l'ArrayList
  public int getCount() {
    return epreuve.size();
  }

  // Fonction generee de base lorsque vous creez un adapter
  // Retourne la position (ligne en cours) 
  public Object getItem(int position) {
    return epreuve.get(position);
  }

  // Fonction generee de base lorsque vous creez un adapter
  // Retourne la position (ligne) d'un element
  public long getItemId(int position) {
    return position;
  }
  
  
  /**
	 * Classe dans laquelle vous declarez les elements
	 * qui vont etre presents sur une ligne;
	 * (ici, elements du fichier ligne_de_la_listview.xml)
	 */
  private class ViewHolder {
    //TextView numElement;
    TextView element;
  }

  // Fonction generee de base lorsque vous creez un adapter
  // Elle va lier la List<String> e la vue (ListView)
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    
    if(convertView == null) {
      holder = new ViewHolder();
      // On lie les elements au fichier ligne_de_la_listview.xml
      convertView = inflater.inflate(R.layout.ligne_de_la_list_view, null);
      // On lie les deux TextView declares precedemment e ceux du xml
      //holder.numElement = (TextView)convertView.findViewById(R.id.numElement);
      holder.element = (TextView)convertView.findViewById(R.id.element);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    // On defini ici le texte que doit contenir chacun des TextView
    // Le premier affichera le numero de l'element (numero de ligne)
    //holder.numElement.setText("" + position);
    // Le second affichera l'element
    holder.element.setText(epreuve.get(position));
    return convertView;
  }

}