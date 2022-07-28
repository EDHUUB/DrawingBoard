package com.xpw.drawingboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button cleanButton;
    private Button changeToYellow;
    private Button setStrokeWidthTo10;
    private Button setPaintStyle;
    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cleanButton = findViewById(R.id.clean_btn);
        drawView = findViewById(R.id.draw);
        changeToYellow = findViewById(R.id.yellow_btn);
        setStrokeWidthTo10 = findViewById(R.id.stroke_width_btn);
        setPaintStyle = findViewById(R.id.style_btn);


        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.clearAll();
            }
        });

        changeToYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.changeToYellow();
            }
        });

        setStrokeWidthTo10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setStrokeWidthTo10();
            }
        });

        setPaintStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setPaintStyle();
            }
        });

    }
}