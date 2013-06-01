package com.emmovieira.measures;

import com.emmovieira.measures.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources ressources = getResources(); 
		TabHost tabHost = getTabHost(); 
		
		// Entrada tab
		Intent intentEntrada = new Intent().setClass(this, EntradaActivity.class);
		TabSpec tabSpecEntrada = tabHost
			.newTabSpec("Entrada")
			.setIndicator("", ressources.getDrawable(R.drawable.icon_clipboard_config))
			.setContent(intentEntrada);

		// Grafico tab
		Intent intentGrafico = new Intent().setClass(this, GraficoActivity.class);
		TabSpec tabSpecGrafico = tabHost
			.newTabSpec("Grafico")
			.setIndicator("", ressources.getDrawable(R.drawable.icon_graph_config))
			.setContent(intentGrafico);
		
		// Lista tab
		Intent intentLista = new Intent().setClass(this, ListaActivity.class);
		TabSpec tabSpecLista = tabHost
			.newTabSpec("Lista")
			.setIndicator("", ressources.getDrawable(R.drawable.icon_list_config))
			.setContent(intentLista);
		
		// add all tabs 
		tabHost.addTab(tabSpecEntrada);
		tabHost.addTab(tabSpecGrafico);
		tabHost.addTab(tabSpecLista);
		
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);
	}

}