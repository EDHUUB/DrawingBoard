package com.xpw.drawingboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button cleanButton;
    private Button changeToYellow;
    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cleanButton = findViewById(R.id.clean_btn);
        drawView = findViewById(R.id.draw);
        changeToYellow = findViewById(R.id.yellow_btn);
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

    }
}