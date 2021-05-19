package com.example.projectic_dva_ne_trogat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class Sprite{
    private Bitmap bitmap;
    private ArrayList<ArrayList<Rect>> frames;
    private int frameWidth = 110;
    private int frameHeight = 200;
    public int currentFrameX = 0;
    public int currentFrameY = 2; //линия анимации (0 - идёт вправо, 1 - только для смерти, 2 - идёт влево)
    public double frameTime = 1;
    public int size = 600;
    private double timeForCurrentFrame = 0;

    public double x;
    public double y;

    private double velocityX;

    public double getVX() {
        return velocityX;
    }

    public void setVX(double velocityX) {
        this.velocityX = velocityX;
    }

    public Sprite(Bitmap bitmap, double x, double y, double velocityX) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;

        frames = new ArrayList<>();
        frameWidth = bitmap.getWidth() / 8;
        frameHeight = bitmap.getHeight() / 3;
        for(int i = 0; i < 3; i++){
            frames.add(new ArrayList<>());
            for(int j = 0; j < 8; j++){
                frames.get(i).add(new Rect(j * frameWidth, i * frameHeight, (j + 1) * frameWidth, (i + 1) * frameHeight));
            }
        }
        frames.get(1).remove(7);
//        frames.get(1).remove(6);
    }

    public void update(double deltaTime, int ms){
        timeForCurrentFrame += deltaTime;

        if (velocityX > 0){
            currentFrameY = 2;
        }else{
            currentFrameY = 0;
        }

        if (timeForCurrentFrame >= frameTime){
            currentFrameX = (currentFrameX + 1) % frames.get(currentFrameY).size();
            timeForCurrentFrame = 0;
        }

        x = x + velocityX * ms/1000.0;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, frames.get(currentFrameY).get(currentFrameX),
                new Rect((int)x, (int)y, (int)x + size, (int)y + size), new Paint());
        if (x <= 0 || x >= canvas.getWidth() - size){
            velocityX = -velocityX;
        }
    }
}
