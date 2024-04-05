package com.example.project_class;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Intent_01 extends AppCompatActivity {
    TextView txt;
    Button btn;
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if(o.getResultCode() == 200){
                        Intent data = o.getData();
                        txt.setTextColor(data.getIntExtra("color", Color.BLACK));
                        txt.setTextSize(data.getIntExtra("size", 12));
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent01);
        InitWidget();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //truyền sang Intent02
                Intent intent = new Intent(Intent_01.this, Intent_02.class);
                //startActivity(intent); // không lấy giá trị trả về
                //startActivityForResult(intent,100);
                intent.putExtra("color",txt.getCurrentTextColor());
                intent.putExtra("size",Math.round(txt.getTextSize() / getResources().getDisplayMetrics().scaledDensity));
                launcher.launch(intent);
            }
        });
    }

    private void InitWidget() {
        txt = findViewById(R.id.txtText);
        btn = findViewById(R.id.btnSettings);
    }
}