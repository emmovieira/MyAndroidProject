package com.emmovieira.measures;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.widget.TextView;

public class ListaActivity extends Activity {
	
	String filename = Environment.getExternalStorageDirectory().getPath() + "/measures.txt";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TextView teste = new TextView(this);
//        teste.setText("isso é um teste");
        
        setContentView(R.layout.list);

//        TableLayout tabelaDados = (TableLayout)  findViewById(R.id.tabeladados);
        
	}   
    
 
	public void Alerta (String Title, String Message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(Title);
		builder.setMessage(Message);
		builder.setPositiveButton("OK", null);
		AlertDialog dialog = builder.show();

		// Must call show() prior to fetching text view
		TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
		messageView.setGravity(Gravity.CENTER);
	}

    
}