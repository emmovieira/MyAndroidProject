package com.emmovieira.measures;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GraficoActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is Apple tab");
        setContentView(R.layout.grafico);
    }
}