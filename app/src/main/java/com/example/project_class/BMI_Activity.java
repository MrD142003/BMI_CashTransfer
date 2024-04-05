package com.example.project_class;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BMI_Activity extends AppCompatActivity {
    EditText edtWeight, edtHeight;
    TextView txtBMIState, txtBMIResult;
    Button btnResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        Initwidget();
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int weight = Integer.parseInt(edtWeight.getText().toString());
                int height = Integer.parseInt(edtHeight.getText().toString());
                BMI_Calculator bmi = new BMI_Calculator(weight, height);
                DecimalFormat dF = new DecimalFormat("#.##");
                txtBMIResult.setText("Your BMI: " + dF.format(bmi.calc()));
                txtBMIState.setText(bmi.BMIresult());
            }
        });
    }
    private void Initwidget(){
        edtWeight = findViewById(R.id.edtWeight);
        edtHeight = findViewById(R.id.edtHeight);
        txtBMIState = findViewById(R.id.txtBMIState);
        txtBMIResult = findViewById(R.id.txtBMIResult);
        btnResult = findViewById(R.id.btnBMIResult);
    }
}