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

import java.util.ArrayList;
import java.util.List;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    //画笔集
    private List<Paint> paintList = new ArrayList<>();
    //路径集
    private List<Path> pathList = new ArrayList<>();
    //画笔颜色
    private int choseColor = Color.WHITE;
    //画笔尺寸
    private int choseStrokeWidth = 5;
    //画笔类型
    private Paint.Style choseStyle = Paint.Style.STROKE;

    //画笔撤销集
    private List<Paint> revokePaintList = new ArrayList<>();
    //路径撤销集
    private List<Path> revokePathList = new ArrayList<>();



    //画笔
    private Paint paint = new Paint() ;

    //路径
    private Path path = new Path();


    //初始化
    public DrawView(Context context, AttributeSet attrs) {
        super(context,attrs);
        getHolder().addCallback(this);
        //初始化onTouchListener
        setOnTouchListener(this);
    }

    //绘制动作
    //todo:原理，逻辑
    public void draw(){
        Canvas canvas = getHolder().lockCanvas();

        canvas.drawColor(Color.GRAY);
//        canvas.drawPath(path,paint);
        if(paintList!=null&&paintList.size()>0){
            for(int i=0;i<paintList.size();i++){
                canvas.drawPath(pathList.get(i), paintList.get(i));//设置画笔，路径
            }
        }
        getHolder().unlockCanvasAndPost(canvas);
    }

    //改变笔触颜色为黄色
    public void changeToYellow(){
        choseColor = Color.YELLOW;
    }

    //改变笔触宽度为10
    public void setStrokeWidthTo10(){
        choseStrokeWidth = 10;
    }

    //改变笔触样式
    public void setPaintStyle(){
        choseStyle = Paint.Style.FILL_AND_STROKE;
    }

    //清屏
    public void clearAll(){
        pathList.clear();
        paintList.clear();
        choseColor = Color.WHITE;
        draw();
    }

    //撤销画笔动作
    public void revoke(){
        if (paintList.size()>0&&pathList.size()>0){
            revokePaintList.add(paintList.get(pathList.size()-1));
            revokePathList.add(pathList.get(pathList.size()-1));
            paintList.remove(paintList.size()-1);
            pathList.remove(pathList.size()-1);
            draw();}
    }

    //前进画笔动作
    public void forward(){
        if (revokePathList.size()>0&&revokePaintList.size()>0){
            paintList.add(revokePaintList.get(revokePaintList.size()-1));
            pathList.add(revokePathList.get(revokePathList.size()-1));
            revokePaintList.remove(revokePaintList.get(revokePaintList.size()-1));
            revokePathList.remove(revokePathList.get(revokePathList.size()-1));
            draw();

    }
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


    //todo：监听器原理与使用,嵌套
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        paint = new Paint();
        path = new Path();
        paint.setColor(choseColor);
        paint.setStrokeWidth(choseStrokeWidth);
        paint.setStyle(choseStyle);

        switch ( event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.setLastPoint(event.getX(),event.getY());
                pathList.add(path);
                paintList.add(paint);
                draw();
                break;

            case MotionEvent.ACTION_MOVE:
                path.setLastPoint(event.getX(),event.getY());
                pathList.get(pathList.size()-1).lineTo(event.getX(),event.getY());
                draw();
                break;

        }


        return true;
    }
}
