package com.xpw.drawingboard.pojo;

import android.graphics.Path;

import lombok.Data;

@Data
public class Pointer {

    private int id;
    private Path path;
    private float x;
    private float y;
    private float preX;
    private float preY;




}
