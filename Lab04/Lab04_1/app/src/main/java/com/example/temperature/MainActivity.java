package com.example.temperature;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edtFarenheit, edtCelsius;
    Button btnConvertFtoC, btnConvertCtoF, btnClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        edtFarenheit = (EditText) findViewById(R.id.txtFar);
        edtCelsius = (EditText) findViewById(R.id.txtCel);
        btnConvertFtoC = (Button) findViewById(R.id.btnFar);
        btnConvertCtoF = (Button) findViewById(R.id.btnCel);
        btnClear = (Button) findViewById(R.id.btnClear);


        btnConvertFtoC.setOnClickListener(v -> {
            DecimalFormat dcf = new DecimalFormat("#.00");
            String doF = edtFarenheit.getText() + "";
            int F = Integer.parseInt(doF);
            edtCelsius.setText("" + dcf.format((F - 32) * 5 / 9));

        });
        btnConvertCtoF.setOnClickListener(v -> {
            DecimalFormat dcf = new DecimalFormat("#.00");
            String doC = edtCelsius.getText() + "";
            int C = Integer.parseInt(doC);
            edtFarenheit.setText("" + dcf.format((C * 9 / 5) + 32));
        });
        btnClear.setOnClickListener(v -> {
            edtFarenheit.setText("");
            edtCelsius.setText("");
        });
    }
}