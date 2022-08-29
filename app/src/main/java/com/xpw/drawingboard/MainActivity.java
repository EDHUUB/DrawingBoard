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
    private Button revokeButton;
    private Button forwardButton;
    private Button eraserButton;
    private DrawView drawView;
    private Button testLine;
    private Button testLineDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cleanButton = findViewById(R.id.clean_btn);
        drawView = findViewById(R.id.draw);
        changeToYellow = findViewById(R.id.yellow_btn);
        setStrokeWidthTo10 = findViewById(R.id.stroke_width_btn);
        setPaintStyle = findViewById(R.id.style_btn);
        eraserButton = findViewById(R.id.point_eraser_btn);
        revokeButton = findViewById(R.id.revoke_btn);
        forwardButton = findViewById(R.id.forward_btn);
        testLine = findViewById(R.id.test_line_btn);
        testLineDemo = findViewById(R.id.test_line_demo_btn);


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

        eraserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.eraser();
            }
        });

        revokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.revoke();
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.forward();
            }
        });

        testLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.testLine();
            }
        });

        testLineDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.testLineDemo();
            }
        });

    }
}