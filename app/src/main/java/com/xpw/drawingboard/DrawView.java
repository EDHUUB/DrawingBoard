package com.xpw.drawingboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final String TAG = "测试";
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
    //当前触点x坐标
    private float x;
    //当前触点x坐标
    private float y;
    //画笔类型
    private String paintType;

    //画笔撤销集
    private List<Paint> revokePaintList = new ArrayList<>();
    //路径撤销集
    private List<Path> revokePathList = new ArrayList<>();

    //画布
    private Canvas canvas;

    //橡皮擦画笔
    private Paint eraserPaint = new Paint();
    //上一个触碰点位置


    //画笔
    private Paint paint = new Paint();

    //路径
    private Path path = new Path();


    //初始化
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        //初始化onTouchListener
        setOnTouchListener(this);
    }

    //画笔绘制动作
    //todo:原理，逻辑
    public void draw() {
        canvas = getHolder().lockCanvas();

        canvas.drawColor(Color.GRAY);
        if (paintList != null && paintList.size() > 0) {
            for (int i = 0; i < paintList.size(); i++) {
                canvas.drawPath(pathList.get(i), paintList.get(i));//设置画笔，路径
            }
        }

        getHolder().unlockCanvasAndPost(canvas);
    }

    //改变笔触颜色为黄色
    public void changeToYellow() {
        paintType = "paint";
        choseColor = Color.YELLOW;
    }

    //改变笔触宽度为10
    public void setStrokeWidthTo10() {
        paintType = "paint";
        choseStrokeWidth = 10;
    }

    //改变笔触样式
    public void setPaintStyle() {
        paintType = "paint";
    }

    //点状橡皮擦
    public void eraser() {
        choseColor = Color.GRAY;
        paintType = "eraser";
    }

    //跟随圆点
    public void followPoint() {
        canvas = getHolder().lockCanvas();
        eraserPaint.setColor(Color.WHITE);
        eraserPaint.setAlpha(50);
        //todo 橡皮擦属性的设置抽取
        eraserPaint.setStrokeWidth(40);
        canvas.drawCircle(x, y, 40, eraserPaint);
        getHolder().unlockCanvasAndPost(canvas);


    }

    //清屏
    public void clearAll() {
        pathList.clear();
        paintList.clear();
        choseColor = Color.WHITE;
        draw();
    }

    //撤销画笔动作
    public void revoke() {
        if (paintList.size() > 0 && pathList.size() > 0) {
            revokePaintList.add(paintList.get(pathList.size() - 1));
            revokePathList.add(pathList.get(pathList.size() - 1));
            paintList.remove(paintList.size() - 1);
            pathList.remove(pathList.size() - 1);
            draw();
        }
    }

    //前进画笔动作
    public void forward() {
        if (revokePathList.size() > 0 && revokePaintList.size() > 0) {
            paintList.add(revokePaintList.get(revokePaintList.size() - 1));
            pathList.add(revokePathList.get(revokePathList.size() - 1));
            revokePaintList.remove(revokePaintList.get(revokePaintList.size() - 1));
            revokePathList.remove(revokePathList.get(revokePathList.size() - 1));
            draw();

        }
    }

    //判断是否是橡皮擦
    public void isEraser() {
        if (paintType == "eraser") {
            followPoint();
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

        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                path.setLastPoint(event.getX(), event.getY());
                x = event.getX();
                y = event.getY();
                pathList.add(path);
                paintList.add(paint);
                isEraser();
                draw();
                break;

            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                path.setLastPoint(event.getX(), event.getY());
                pathList.get(pathList.size() - 1).lineTo(event.getX(), event.getY());
                isEraser();
                draw();
                break;
            case MotionEvent.ACTION_UP:
                draw();
                break;

        }


        return true;
    }
}

