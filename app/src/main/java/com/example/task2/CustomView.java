package com.example.task2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    int xAxis, yAxis ,varX , varY, x, y ;
    int scale ;
    Paint mPaint , dirPaint ;
    boolean[][] pointsConnected ;


    public CustomView(Context context , int xInput , int yInput) {
        super(context);
        xAxis = xInput;
        yAxis = yInput;
        pointsConnected = new boolean[xAxis][yAxis];
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels ;
        scale = screenWidth / (xAxis + 1) ;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);

        dirPaint = new Paint();
        dirPaint.setAntiAlias(true);
        dirPaint.setColor(getResources().getColor(R.color.highlight));


    }

    @Override
    protected void onDraw (Canvas canvas){
        super.onDraw(canvas);

        for(int j=1 ; j <= yAxis ; j++){
            for(int i=1 ; i<=xAxis ; i++){
                canvas.drawCircle( i * scale , j * scale , 20 , mPaint);
            }
        }

        postInvalidate();
        if( varX > (scale*xAxis) || varY > (scale*yAxis) ){}
        else if (varY < scale || varX < scale){}
        else {
            canvas.drawCircle(varX + scale, varY, 25, dirPaint);
            canvas.drawCircle(varX - scale, varY, 25, dirPaint);
            canvas.drawCircle(varX, varY + scale, 25, dirPaint);
            canvas.drawCircle(varX, varY - scale, 25, dirPaint);
            postInvalidate();
        }
        canvas.drawLine( varX , varY , x , y , mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        x = (int) event.getX();
        y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                return true ;
            }
            case MotionEvent.ACTION_MOVE:{
                for (int j = 1; j <= yAxis; j++) {
                    for (int i = 1; i <= xAxis; i++) {
                        int temp1 = (x - (i * scale)) * (x - (i * scale));
                        int temp2 = (y - (j * scale)) * (y - (j * scale));
                        int temp = temp1 + temp2;
                        if (temp <= 400) {
                            varX = i * scale;
                            varY = j * scale;
                            return true;
                        }
                    }
                }
            }

        }
        return false ;
    }
}
