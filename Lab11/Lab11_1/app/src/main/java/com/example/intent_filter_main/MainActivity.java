package com.example.intent_filter_main;

import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intent_filter_main.R;

public class MainActivity extends AppCompatActivity {
    Button btnopen;
    EditText edtlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtlink = findViewById(R.id.editTextWebsite);
        btnopen = findViewById(R.id.buttonShowWebPage);

        btnopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://" + edtlink.getText().toString()));
                startActivity(intent);
            }
        });
    }
}