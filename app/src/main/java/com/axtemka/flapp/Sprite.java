package com.axtemka.flapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Sprite {
    public Bitmap bitmap;
    private int frameWidth;
    private int frameHeight;
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    private double gravity = 0;
    private int padding;

    public Sprite(double x, double y, double velocityX, double velocityY,  Rect initialFrame, Bitmap bitmap, double gravity) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.bitmap = bitmap;
        this.gravity = gravity;
        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();
        this.padding = 20;
    }

    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public int getFrameWidth() {
        return frameWidth;
    }
    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }
    public int getFrameHeight() {
        return frameHeight;
    }
    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }
    public double getVx() {
        return velocityX;
    }
    public void setVx(double velocityX) {
        this.velocityX = velocityX;
    }
    public double getVy() {
        return velocityY;
    }
    public void setVy(double velocityY) {
        this.velocityY = velocityY;
    }


    public void update () {

        x = x + velocityX;
        y = y + velocityY + gravity;

        velocityY = velocityY + gravity;
    }

    public Rect getBoundingBoxRect () {
        return new Rect((int)x+padding, (int)y+padding, (int)(x + frameWidth - 2 * padding), (int)(y + frameHeight - 2 * padding));
    }

    public void draw (Canvas canvas) {
        Paint p = new Paint();
        Rect destination = new Rect((int)x, (int)y, (int)(x + frameWidth), (int)(y + frameHeight));
        canvas.drawBitmap(bitmap, new Rect(), destination, p);
    }

    public boolean intersect (Sprite s) {
        return getBoundingBoxRect().intersect(s.getBoundingBoxRect());
    }

    public double getX() {
        return x;
    }
}
