package com.example.projectic_dva_ne_trogat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class CatRoomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    float x;
    float y;

    public Canvas canvas;
    float vx = (float)0.1; //скорость уменьшения состояния
    float vUst = (float)0.1; //отдельная переменная для заполнения состояния усталости
    float xg = 0; //голод
    float xn = 0; //настроение
    float xu = 0; //усталость

    private DrawThread drawThread;

    public CatRoomSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(20);
//
//        x++;
//        canvas.drawRect((float)x,200,300,500,paint);
//
//        invalidate();
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();

            if(vUst > 0 && x > 0 && x < (int)(canvas.getWidth() * 0.331) && y > (int)(canvas.getHeight() * 0.85) && y < canvas.getHeight() ) {
                xg = xg - (float)(canvas.getWidth() * 0.01);
                if(xg < 0 ) xg = 0;
                xn = xn - (float)(canvas.getWidth() * 0.0023);
                if(xn < 0) xn = 0;
//                vx = (float)-0.5;
            }
            else if(vUst > 0 && x > (int)(canvas.getWidth() * 0.332) && x < (int)(canvas.getWidth() * 0.661) && y > (int)(canvas.getHeight() * 0.85) && y < canvas.getHeight()) {
                xn = xn - (float)(canvas.getWidth() * 0.01);
                if(xn < 0) xn = 0;
            }
            else if(x > (int)(canvas.getWidth() * 0.662) && x < canvas.getWidth() && y > (int)(canvas.getHeight() * 0.85) && y < canvas.getHeight()) {
//                xu = xu - (float)(canvas.getWidth() * 0.01);
//                if(xu < 0) xu = 0;
//                xn = xn - (float)(canvas.getWidth() * 0.005);
//                if(xn < 0) xn = 0;
                vUst = (float)-0.1;
            }
            else if(x > (int)(canvas.getWidth() * 0.91) && x < (int)(canvas.getWidth() * 0.96) && y > (int)(canvas.getHeight() * 0.01) && y < (int)canvas.getHeight() * 0.05){
                System.exit(0);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(), getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
    class DrawThread extends Thread{
        SurfaceHolder surfaceHolder;
        public DrawThread(Context context, SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;

        }

        @Override
        public void run() {
            Paint wallPaint = new Paint();  wallPaint.setColor(Color.rgb(182, 231, 23));    wallPaint.setStyle(Paint.Style.FILL);
            Paint ceilingPaint = new Paint();   ceilingPaint.setColor(Color.rgb(220, 220, 220));    ceilingPaint.setStyle(Paint.Style.FILL);
            Paint palki = new Paint();  palki.setColor(Color.BLACK);    palki.setStrokeWidth(7);    palki.setStyle(Paint.Style.FILL);
            Paint floorPaint = new Paint(); floorPaint.setColor(Color.rgb(186, 123, 86));   floorPaint.setStyle(Paint.Style.FILL);
            Paint gameBg = new Paint(); gameBg.setColor(Color.WHITE);   gameBg.setStyle(Paint.Style.FILL);
            Paint facets = new Paint(); facets.setColor(Color.BLACK);   facets.setStrokeWidth(7);   facets.setStyle(Paint.Style.STROKE);
            Paint tooxt = new Paint();  tooxt.setColor(Color.BLACK);    tooxt.setTextSize(40);  tooxt.setStrokeWidth(20);
            Paint backTOback = new Paint(); backTOback.setColor(Color.BLACK);   backTOback.setStrokeWidth(100); backTOback.setTextSize(90);
            Bitmap window = BitmapFactory.decodeResource(getResources(), R.drawable.window);
            Paint forStatus = new Paint(); forStatus.setColor(Color.GREEN); forStatus.setStyle(Paint.Style.FILL); forStatus.setStrokeWidth(7);

            boolean running = true;
            while (running) {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    try {
                        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), wallPaint);
                        canvas.drawRect(0, 0, canvas.getWidth(), (int)(canvas.getHeight() * 0.06), ceilingPaint);
                        canvas.drawRect(0, (int)(canvas.getHeight() * 0.06), canvas.getWidth(), (int)(canvas.getHeight() * 0.11), gameBg);

                        float widthRect = (float)(canvas.getWidth() * 0.331) - xg;
                        if(widthRect > 0 && vUst > 0) xg = xg + vx;
                        else xg = (float)(xg + (vx * 0.01));
                        canvas.drawRect(0, (float) (canvas.getHeight() * 0.06), widthRect, (float) (canvas.getHeight() * 0.11), forStatus);  //отрисовка уровня состояния голода

                        float widthRect2 = (float)(canvas.getWidth() * 0.661) - xn;
                        if(widthRect2 > (float)(canvas.getWidth() * 0.331) && vUst > 0) xn = xn + vx;
                        else xn = (float)(xn + (vx * 0.01));
                        canvas.drawRect((float)(canvas.getWidth() * 0.332), (float) (canvas.getHeight() * 0.06), widthRect2, (float) (canvas.getHeight() * 0.11), forStatus); //отрисовка уровня состояния настроения

                        float widthRect3 = (float)(canvas.getWidth()) - xu;
                        if(widthRect3 > (float)(canvas.getWidth() * 0.661) || vUst < 0) xu = xu + vUst;
                        if(xu < 0) vUst = (float)0.1;
                        canvas.drawRect((float)(canvas.getWidth() * 0.662), (float) (canvas.getHeight() * 0.06), widthRect3, (float) (canvas.getHeight() * 0.11), forStatus); //отрисовка уровня состояния усталости

                        canvas.drawLine(0, (float)(canvas.getHeight() * 0.06), canvas.getWidth(), (float)(canvas.getHeight() * 0.06), palki);
                        canvas.drawRect(0, (int)(canvas.getWidth() * 1.07), canvas.getWidth(), canvas.getHeight(), floorPaint);
                        canvas.drawLine(0, (float)(canvas.getHeight() * 0.68), canvas.getWidth(), (float)(canvas.getHeight() * 0.68), palki);
                        canvas.drawBitmap(window, new Rect(0, 0, window.getWidth(), window.getHeight()),
                                new Rect((int)(canvas.getWidth() * 0.02), (int)(canvas.getHeight() * 0.15), (int)(canvas.getWidth() * 0.225), (int)(canvas.getHeight() * 0.35)), wallPaint);
                        canvas.drawLine(0, (float)(canvas.getHeight() * 0.11), canvas.getWidth(), (float)(canvas.getHeight() * 0.11), palki);
                        canvas.drawLine((int)(canvas.getWidth() * 0.331), (float)(canvas.getHeight() * 0.06), (int)(canvas.getWidth() * 0.331), (float)(canvas.getHeight() * 0.11), palki);
                        canvas.drawLine((int)(canvas.getWidth() * 0.661), (float)(canvas.getHeight() * 0.06), (int)(canvas.getWidth() * 0.661), (float)(canvas.getHeight() * 0.11), palki);
                        canvas.drawRect((int)(canvas.getWidth() * 0.331), (int)(canvas.getHeight() * 0.01), (int)(canvas.getWidth() * 0.661), (int)(canvas.getHeight() * 0.05), facets);  //место таймера
                        canvas.drawRect(0, (int)(canvas.getHeight() * 0.85), canvas.getWidth(), canvas.getHeight(), ceilingPaint);
                        canvas.drawRect(0, (int)(canvas.getHeight() * 0.85), (int)(canvas.getWidth() * 0.331), canvas.getHeight(), facets);  //первая  кнопка
                        canvas.drawRect((int)(canvas.getWidth() * 0.331), (int)(canvas.getHeight() * 0.85), (int)(canvas.getWidth() * 0.661), canvas.getHeight(), facets);  //вторая кнопка
                        canvas.drawRect((int)(canvas.getWidth() * 0.661), (int)(canvas.getHeight() * 0.85), canvas.getWidth(), canvas.getHeight(), facets);  //третья кнопка
                        canvas.drawText("Накормить", (float)(canvas.getWidth() * 0.07), (float)(canvas.getHeight() * 0.935), tooxt);
                        canvas.drawText("Развлечь", (float)(canvas.getWidth() * 0.428), (float)(canvas.getHeight() * 0.935), tooxt);
                        canvas.drawText("Спать", (float)(canvas.getWidth() * 0.785), (float)(canvas.getHeight() * 0.935), tooxt);
                        canvas.drawText("X", (float)(canvas.getWidth() * 0.915), (float)(canvas.getHeight() * 0.0455), backTOback);

                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
