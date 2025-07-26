package com.example.bmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtHeight, edtWeight, edtBMI, edtDiagnosis;
    Button btnBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        edtName = findViewById(R.id.edt_Name);
        edtHeight = findViewById(R.id.edt_Height);
        edtWeight = findViewById(R.id.edt_Weight);
        edtBMI = findViewById(R.id.edt_Result);
        edtDiagnosis = findViewById(R.id.edt_diagnosis);
        btnBMI = findViewById(R.id.btn_BMI);

        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double heightCm = Double.parseDouble(edtHeight.getText().toString());
                    double weight = Double.parseDouble(edtWeight.getText().toString());
                    double height = heightCm / 100;
                    double bmi = weight / Math.pow(height, 2);

                    String diagnosis;
                    if (bmi < 18) {
                        diagnosis = "Bạn gầy";
                    } else if (bmi < 25) {
                        diagnosis = "Bạn bình thường";
                    } else if (bmi < 30) {
                        diagnosis = "Bạn béo phì độ 1";
                    } else if (bmi < 35) {
                        diagnosis = "Bạn béo phì độ 2";
                    } else {
                        diagnosis = "Bạn béo phì độ 3";
                    }

                    DecimalFormat dcf = new DecimalFormat("#.0");
                    edtBMI.setText(dcf.format(bmi));
                    edtDiagnosis.setText(diagnosis);

                } catch (NumberFormatException e) {
                    edtBMI.setText("Lỗi nhập");
                    edtDiagnosis.setText("Vui lòng nhập đúng số");
                }
            }
        });
    }
}
