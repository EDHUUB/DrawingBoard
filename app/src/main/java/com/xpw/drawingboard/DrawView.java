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

import com.xpw.drawingboard.pojo.Pointer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final String TAG = "测试";
    //画笔
    private Paint paint = new Paint();
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
    //上一个触点x坐标
    private float preX;
    //上一个触点y坐标
    private float preY;
    //画笔类型
    private String paintType;
    //画笔撤销集
    private List<Paint> revokePaintList = new ArrayList<>();
    //路径撤销集
    private List<Path> revokePathList = new ArrayList<>();
    //笔触数量
    private int pointerNum;
    //画布
    private Canvas canvas;
    //橡皮擦画笔
    private Paint eraserPaint = new Paint();


    /**
     * 多指绘图数据集合
     * Integer负责存储pointerId，Path负责存储对应pointer的path
     */
    private Map<Integer, Pointer> pointerMap = new HashMap<>();
    Pointer pointerTemp;

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
                /**
                 * 功能描述：单笔触绘图-down
                 * 逻辑流程：
                 * 0、初始化pointerMap
                 * 1、获取pointerId
                 * 2、创建并初始化Path
                 * 3、将pointerId与Path封装至pointerMap
                 * 5、将pointerMap数据中的path浅拷贝至pathList
                 * 6、将当前paintList添加至paintList
                 * 7、判断当前笔触是否是橡皮擦
                 * 8、draw（）
                 */
                pointerMap = new HashMap<>();
                pointerTemp = new Pointer();
                path = new Path();
                path.moveTo(event.getX(), event.getY());
                //todo:pointerTemp的初始化可以抽出一个方法
                pointerTemp.setId(event.getPointerId(0));
                pointerTemp.setPath(path);
                pointerTemp.setX(event.getX());
                pointerTemp.setY(event.getY());
                pointerMap.put(event.getPointerId(0), pointerTemp);
                pathList.add(pointerMap.get(0).getPath());
                paintList.add(paint);
                //todo:x、y的属性可以封装至eraser类，或者说eraser类和paint类合并
                x = event.getX();
                y = event.getY();
                //todo:isEraser应该也可以封装至eraser类中
                isEraser();
                draw();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                /**
                 * 功能描述：多笔触绘图-down
                 * 逻辑流程：
                 * 1、获取落笔pointerId
                 * 2若pointerMap中无该key，创建并初始化对应的path
                 * 3、将pointerId与path封装至painterMap
                 * 4、将新path浅拷贝至pathList，将当前paint浅拷贝至paintList
                 * 5、draw()
                 */
                pointerNum = event.getPointerCount();
                for (int i = 0; i < pointerNum; i++) {
                    if (!pointerMap.containsKey(event.getPointerId(i))) {
                        path = new Path();
                        pointerTemp = new Pointer();
                        path.moveTo(event.getX(i), event.getY(i));
                        //todo:pointer抽取方法
                        pointerTemp.setId(event.getPointerId(i));
                        pointerTemp.setPath(path);
                        pointerTemp.setX(event.getX(i));
                        pointerTemp.setPreY(event.getY(i));
                        pointerMap.put(event.getPointerId(i), pointerTemp);
                        pathList.add(pointerMap.get(event.getPointerId(i)).getPath());
                        paintList.add(paint);
                    }
                }
                isEraser();
                draw();
                break;

            case MotionEvent.ACTION_MOVE:
                /**
                 * 1、获取落笔数量
                 * 2.1、通过落笔数量，遍历获得落笔id
                 * 2.2、通过落笔id，获取对应的path
                 * 2.3、根据event.get(i)获得当前触碰位置，path.lineTo(x,y)
                 * 2.4、根据id，将pointerMap中对应的path更新
                 * 3、draw()
                 */
                pointerNum = event.getPointerCount();
                for (int i = 0; i < pointerNum; i++) {
                    int id = event.getPointerId(i);
                    pointerMap.get(id).getPath().lineTo(event.getX(i), event.getY(i));
                }
                x = event.getX();
                y = event.getY();
                path.moveTo(event.getX(), event.getY());
                isEraser();
                draw();
                break;

            case MotionEvent.ACTION_POINTER_UP:
                /**
                 * 1、将当前抬起的笔触从pointerMap中删去
                 */
                int pointerId = event.getPointerId(event.getActionIndex());
                pointerMap.remove(pointerId);
                break;

            case MotionEvent.ACTION_UP:
                draw();
                break;

        }


        return true;
    }
}

