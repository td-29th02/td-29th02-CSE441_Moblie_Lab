package com.example.demor_cyclefile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Subactivity extends AppCompatActivity {

    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subactivity);

        btnOk = findViewById(R.id.btnok);

        btnOk.setOnClickListener(view -> {
            // Kết thúc activity này và quay về activity trước đó
            finish();
        });
    }
}
