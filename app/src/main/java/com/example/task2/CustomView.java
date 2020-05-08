package com.example.task2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    int xAxis, yAxis ;
    float left, right, top, bottom ;
    int xTemp, yTemp , padding, dx, dy ;
    Paint mPaint ;


    public CustomView(Context context , int x , int y) {
        super(context);
        xAxis = x-1;
        yAxis = y-1;
        padding = 10 ;
        dx = 10 ;
        dy = 10 ;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels -40 ;
        xTemp = screenWidth / xAxis ;
        yTemp = xTemp ;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);


    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw (Canvas canvas){
        super.onDraw(canvas);

        for(int i=0 ; i <= yAxis ; i++){
            for(int j=0 ; j<=xAxis ; j++){
                canvas.drawCircle( dx , dy , 10 , mPaint);
                invalidate();
                dx += xTemp;
            }
            dy += yTemp ;
        }



    }
}
