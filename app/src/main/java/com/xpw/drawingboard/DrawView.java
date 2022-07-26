package com.xpw.drawingboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    //画笔
    private Paint paint = new Paint();

    //路径
    private Path path = new Path();


    //初始化
    public DrawView(Context context, AttributeSet attrs) {
        super(context,attrs);
        getHolder().addCallback(this);
        paint.setColor(Color.WHITE);
        paint.setTextSize(10);
        paint.setStyle(Paint.Style.STROKE);
        //初始化onTouchListener
        setOnTouchListener(this);
    }

    //绘制动作
    //todo:原理，逻辑
    public void draw(){
        Canvas canvas = getHolder().lockCanvas();

        canvas.drawColor(Color.GRAY);
        canvas.drawPath(path,paint);

        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


    //todo：监听器原理与使用
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch ( event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(),event.getY());
                draw();
                break;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(),event.getY());
                draw();
                break;

        }


        return true;
    }
}
