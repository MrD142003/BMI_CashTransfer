package com.example.project_class;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class Intent_02 extends AppCompatActivity {
    RadioGroup rtdG;
    SeekBar seekBar;
    Button btn;
    RadioButton rtdRed,rtdGreen,rtdBlue;
    TextView txtSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent02);
        InitWidget();
        //trả về intent01
        Intent intent1 = getIntent();
        int color = intent1.getIntExtra("color", Color.BLACK);
        int size = intent1.getIntExtra("size", 12);
        preColorAndSize(color,size);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = Color.BLACK;
                if (rtdG.getCheckedRadioButtonId() == R.id.rtdRed) {
                    color = Color.RED;
                } else if (rtdG.getCheckedRadioButtonId() == R.id.rtdGreen) {
                    color = Color.GREEN;
                } else if (rtdG.getCheckedRadioButtonId() == R.id.rtdBlue) {
                    color = Color.BLUE;
                }

                int size = seekBar.getProgress();
                Intent intent = new Intent();
                intent.putExtra("color", color);
                intent.putExtra("size", size);
                setResult(200, intent);
                finish();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtSize.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void preColorAndSize(int color,int size) {
        if (color == Color.RED) {
            rtdG.check(rtdRed.getId());
        } else if (color == Color.GREEN) {
            rtdG.check(rtdGreen.getId());
        } else if (color == Color.BLUE) {
            rtdG.check(rtdBlue.getId());
        }
        txtSize.setText(size+"");
        seekBar.setProgress(size);
    }
    private void InitWidget() {
        rtdG = (RadioGroup) findViewById(R.id.rtdColors);
        seekBar = (SeekBar) findViewById(R.id.sbTextSize);
        btn = (Button) findViewById(R.id.btnApply);
        rtdRed = (RadioButton) findViewById(R.id.rtdRed);
        rtdBlue= (RadioButton) findViewById(R.id.rtdBlue);
        rtdGreen = (RadioButton) findViewById(R.id.rtdGreen);
        txtSize = (TextView) findViewById(R.id.txtSize);
    }
}