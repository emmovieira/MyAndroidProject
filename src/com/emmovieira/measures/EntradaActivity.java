package com.emmovieira.measures;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.emmovieira.measures.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.R.array;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EntradaActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.entrada);
	
        setCurrentDateOnView();
		addListenerOnEditText();
		carregaDados();
    }

	private TextView tvDisplayDate;
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 999;
    private EditText data;
    private EditText altura;
	private EditText peso;
	private EditText bmi;
	private EditText cintura;
	private EditText gordura;
	ArrayList<String> listOfString = new ArrayList<String>();
	String[] listUltimosDados;

    private void carregaDados() {
		// TODO Auto-generated method stub

    	String filename = Environment.getExternalStorageDirectory().getPath() + "/measures.txt";
    	listOfString.clear();
    	
    	try {
			File myFile = new File(filename);
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(
					new InputStreamReader(fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
				listOfString.add(aDataRow);
			}
//			Alerta("Dados lidos: " + listOfString.size(),aBuffer);	
			myReader.close();
			Toast.makeText(getBaseContext(), 
					"Done reading SD " + filename,
					Toast.LENGTH_SHORT).show();

			//Alerta("Dado lido",listOfString.get(listOfString.size()-1));
			data 	= (EditText) findViewById(R.id.tvDate);
			altura 	= (EditText)  findViewById(R.id.altura);
			peso 	= (EditText) findViewById(R.id.peso);
			bmi 	= (EditText) findViewById(R.id.bmi);
			cintura = (EditText) findViewById(R.id.cintura);
			gordura = (EditText) findViewById(R.id.gordura);
			
			// Realiza o split do array e atualiza cada campo da entrada exceto a data
			listUltimosDados = listOfString.get(listOfString.size()-1).split(";");
			altura.setText(listUltimosDados[1]);
			peso.setText(listUltimosDados[2]);
			bmi.setText(listUltimosDados[3]);
			cintura.setText(listUltimosDados[4]);
			gordura.setText(listUltimosDados[5]);
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		
	}


	// display current date
	public void setCurrentDateOnView() {
 
		tvDisplayDate = (EditText) findViewById(R.id.tvDate);
 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		// set current date into textview
		tvDisplayDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append(" "));
 
 
	}
 
	public void addListenerOnEditText() {
		 
		tvDisplayDate = (TextView) findViewById(R.id.tvDate);
 
		tvDisplayDate.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
				showDialog(DATE_DIALOG_ID);
 
			}
 
		});
 
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
 
			// set selected date into textview
			tvDisplayDate.setText(new StringBuilder().append(month + 1)
			   .append("-").append(day).append("-").append(year)
			   .append(" "));
 
		}
	};
    
	public void CalcResult(View view) {
		Float flt_altura = null;
		Float flt_peso = null;
		Float flt_resultado = (float) 0;
		
		try {
			data 	= (EditText) findViewById(R.id.tvDate);
			altura 	= (EditText)  findViewById(R.id.altura);
			peso 	= (EditText) findViewById(R.id.peso);
			bmi 	= (EditText) findViewById(R.id.bmi);
			cintura = (EditText) findViewById(R.id.cintura);
			gordura = (EditText) findViewById(R.id.gordura);

			
			flt_altura = Float.parseFloat(altura.getText().toString());
			
			flt_peso = Float.parseFloat(peso.getText().toString());
			
			flt_resultado = (flt_peso/(flt_altura * flt_altura));
			
		} catch (NumberFormatException e) {
			System.err.println("Erro NumberFormatException :"
                    +  e.getMessage());
			Alerta("Altura/Peso", "Informe valores numéricos positivos");
		} catch (NullPointerException e) {
			System.err.println("Erro NullPointerException: "
                    +  e.getMessage());
		} catch (Exception e) {
			System.err.println("Erro: "
                    +  e.getMessage());
		} finally {
			bmi.setText(flt_resultado.toString());
			GravaArquivo(view);
		}
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
	
	public void GravaArquivo (View view) {
		String filename = Environment.getExternalStorageDirectory().getPath() + "/measures.txt";
		String string = data.getText().toString();
		string += ";" + altura.getText().toString();
		string += ";" + peso.getText().toString();
		string += ";" + bmi.getText().toString();
		string += ";" + cintura.getText().toString();
		string += ";" + gordura.getText().toString();
		string += "\n";

		try {
			File myFile = new File(filename);
			myFile.delete();
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = 
									new OutputStreamWriter(fOut);

			//ordena o array para gravação
			Collections.sort(listOfString);
			
			for (int i = 0; i < listOfString.size(); i++) {
				myOutWriter.write(listOfString.get(i) + "\n");
				
			}
			myOutWriter.write(string);
			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(),
					"Done writing SD " + filename,
					Toast.LENGTH_SHORT).show();
			carregaDados();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		
	}
	public void DeletaArquivo (View view) {
		String filename = Environment.getExternalStorageDirectory().getPath() + "/measures.txt";

		try {
			File myFile = new File(filename);
			myFile.delete();
			Toast.makeText(getBaseContext(),
					"Arquivo " + filename + " excluído!",
					Toast.LENGTH_SHORT).show();
			listOfString.clear();
			carregaDados();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		
	}
}