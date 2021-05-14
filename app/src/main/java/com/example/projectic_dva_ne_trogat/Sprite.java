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
    private int currentFrameX = 0;
    private int currentFrameY = 2;
    private double frameTime = 0.4;
    public int size = 300;
    private double timeForCurrentFrame = 0;

    private double x;
    private double y;

    public Sprite(Bitmap bitmap, double x, double y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;

        frames = new ArrayList<>();
        frameWidth = bitmap.getWidth() / 9;
        frameHeight = bitmap.getHeight() / 3;
        for(int i = 0; i < 3; i++){
            frames.add(new ArrayList<>());
            for(int j = 0; j < 9; j++){
                frames.get(i).add(new Rect(j * frameWidth, i * frameHeight, (j + 1) * frameWidth, (i + 1) * frameHeight));
            }
        }
        frames.get(1).remove(8);
        frames.get(1).remove(7);
    }

    public void update(double deltaTime){
        timeForCurrentFrame += deltaTime;
        if (timeForCurrentFrame >= frameTime){
            currentFrameX = (currentFrameX + 1) % frames.get(currentFrameY).size();
            timeForCurrentFrame = 0;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, frames.get(currentFrameY).get(currentFrameX),
                new Rect((int) x, (int) y, (int) x + size, (int) y + size), new Paint());
    }
}
