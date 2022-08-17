package com.xpw.drawingboard.pojo;

import android.graphics.Path;

import lombok.Data;

@Data
/**
 * 1、id用于存储pointerId，用于后期查询目标pointer的属性，如preX、X
 * 2、path记录pointer的path，由于在move事件中，只被Path new一次出来的path会被不断更改存储地址，所以需要一个中间变量存储path数值
 * 3、x记录pointer当前的x坐标
 * 4、y记录pointer当前的y坐标
 * 5、preX记录pointer之前的x坐标，配合x属性实现贝塞尔曲线和字迹功能
 * 6、preY记录pointer之前的Y坐标,配合y属性实现贝塞尔曲线和字迹功能
 */
public class Pointer {

    private int id;
    private Path actualPath = new Path();
    private Path slowPath = new Path();
    private float actualX;
    private float actualY;
    private float slowX = -1;
    private float slowY = -1;
}
