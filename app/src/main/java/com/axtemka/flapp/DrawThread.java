package com.axtemka.flapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

    private double gravity =  0.2;
    private boolean youLose = false;
    public int score = 0;

    private SurfaceHolder surfaceHolder;
    private volatile boolean running = true;
    private Paint backgroundPaint = new Paint();
    private Sprite player;
    private Sprite pipeUp;
    private Sprite pipeDown;

    private int towardPointX;
    private int towardPointY;
    {
        backgroundPaint.setColor(Color.BLUE);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }
    public DrawThread(Context context, SurfaceHolder surfaceHolder) {

        Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.birdup);
        Rect firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        player = new Sprite(20, 0, 0, 10, firstFrame, b, 0.5);

        b = BitmapFactory.decodeResource(context.getResources(), R.drawable.toweruup);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        pipeUp = new Sprite(20, 0, -5, 0, firstFrame, b, 0);

        b = BitmapFactory.decodeResource(context.getResources(), R.drawable.towerddown);
        firstFrame = new Rect(0, 0, b.getWidth(), b.getHeight());
        pipeDown = new Sprite(20, 0, -5, 0, firstFrame, b, 0);

        this.surfaceHolder = surfaceHolder;
    }
    public void requestStop() {
        running = false;
    }
    @Override
    public void run() {

        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    if (youLose){
                        canvas.drawARGB(250, 0, 0, 0); // заливаем цветом
                        Paint p = new Paint();
                        p.setAntiAlias(true);
                        p.setTextSize(55.0f);
                        p.setColor(Color.WHITE);
                        canvas.drawText("You Lose", canvas.getWidth() - 600, canvas.getHeight() - 500, p);
                        p.setColor(Color.WHITE);
                        String txt = "Вы набрали\n" + Integer.toString(score) + " очков";
                        canvas.drawText( txt, canvas.getWidth() - 820, canvas.getHeight() - 1300, p);
                        canvas.drawText("Click to play this game", canvas.getWidth() - 820, canvas.getHeight() - 600, p);}
                    else{
                        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
                        canvas.drawBitmap(player.bitmap, (float) player.getX(), (float) player.getY(), backgroundPaint);
                        canvas.drawBitmap(pipeDown.bitmap, (float) pipeDown.getX(), (float) pipeDown.getY(), backgroundPaint);
                        canvas.drawBitmap(pipeUp.bitmap, (float) pipeUp.getX(), (float) pipeUp.getY(), backgroundPaint);
                        drawScore(canvas);
                        updatePositions(canvas);
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    public void updatePositions(Canvas canvas){
        player.update();
        pipeUp.update();
        pipeDown.update();

        if (pipeDown.getX() < -pipeDown.getFrameWidth()) {
            teleportEnemy(canvas);
            Score();
        }
        if (pipeDown.intersect(player)) {
            youLose = true;
        }
        if (pipeUp.getX() < -pipeUp.getFrameWidth()) {
            teleportEnemy(canvas);
        }
        if (pipeUp.intersect(player)) {
            youLose = true;
        }

    }

    public void changeBirdY(){
        player.setVy(-10);
    }

    private void drawScore(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        canvas.drawText(String.valueOf(score), 50, 100, paint);
    }

    public void Score() {
        score++;
    }

    private void teleportEnemy (Canvas canvas) {
        pipeDown.setX(canvas.getWidth());
        pipeDown.setY((Math.random() * 1520) + 650);

        pipeUp.setX(canvas.getWidth());
        pipeUp.setY(pipeDown.getY() - pipeUp.getFrameHeight() - 600);
    }
}