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

public class Custom2View extends View {

    int xAxis, yAxis ;
    float left, right, top, bottom ;
    int xTemp, yTemp , padding, dx, dy ;
    Paint[] mPaint ;

    public Custom2View(Context context , int x ,int y , int z) {
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

        mPaint = new Paint[11] ;
        mPaint[1].setColor(Color.BLUE);
        mPaint[2].setColor(Color.RED);
        mPaint[3].setColor(Color.GREEN);
        mPaint[4].setColor(Color.GRAY);
        mPaint[5].setColor(Color.MAGENTA);
        mPaint[6].setColor(Color.LTGRAY);
        mPaint[7].setColor(Color.BLACK);
        mPaint[8].setColor(Color.YELLOW);
        mPaint[9].setColor(Color.CYAN);
        mPaint[10].setColor(Color.DKGRAY);

        mPaint[1].setAntiAlias(true);
        mPaint[2].setAntiAlias(true);
        mPaint[3].setAntiAlias(true);
        mPaint[4].setAntiAlias(true);
        mPaint[5].setAntiAlias(true);
        mPaint[6].setAntiAlias(true);
        mPaint[7].setAntiAlias(true);
        mPaint[8].setAntiAlias(true);
        mPaint[9].setAntiAlias(true);
        mPaint[10].setAntiAlias(true);




    }

    public Custom2View(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
