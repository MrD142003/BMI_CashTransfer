package com.example.project_class;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CashTransfer extends AppCompatActivity {
    EditText edtMoney_Insert;
    Spinner spinnerMoneyTypes;
    Button btnTransfer;
    TextView txtMoney_Result, txtMoney_Type;
    Map<String, Double> moneyTypes;
    ArrayList<String> arrMoneyTypes;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_transfer);
        InitWidget();
        setMoneyTypes();
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double vietnamdong = Double.parseDouble(edtMoney_Insert.getText().toString());
                String moneyType=spinnerMoneyTypes.getSelectedItem().toString();
                double result = vietnamdong/moneyTypes.get(moneyType);
                DecimalFormat dF = new DecimalFormat("#.##");
                txtMoney_Result.setText(dF.format(result));
                txtMoney_Type.setText(moneyType);
            }
        });
    }

    private void setMoneyTypes(){
        moneyTypes = new HashMap<>();
        moneyTypes.put("EURO",26914.52);
        moneyTypes.put("USD",24720.00);
        moneyTypes.put("NDT",3435.34);
        Set<String> set = moneyTypes.keySet();
        arrMoneyTypes = new ArrayList<>();
        arrMoneyTypes.addAll(set);
        adapter = new ArrayAdapter<>(CashTransfer.this, android.R.layout.simple_spinner_dropdown_item,arrMoneyTypes);
        spinnerMoneyTypes.setAdapter(adapter);
    }
    private void InitWidget() {
        edtMoney_Insert = findViewById(R.id.edtMoney_Insert);
        spinnerMoneyTypes = findViewById(R.id.spinnerMoney_Types);
        btnTransfer = findViewById(R.id.btnTransfer);
        txtMoney_Result = findViewById(R.id.txtMoney_Result);
        txtMoney_Type = findViewById(R.id.txtMoney_Type);
    }
}