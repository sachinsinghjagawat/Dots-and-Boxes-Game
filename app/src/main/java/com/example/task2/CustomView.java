package com.example.task2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    int xAxis, yAxis ,varX , varY, initX, initY , finalX , finalY , x, y ;
    int count = 1 ;
    int scale ;
    Paint mPaint , dirPaint ;
    boolean[][][] pointsConnected ;
    boolean[][][] pointsConnected2 ;
    boolean[][][] pointsConnected3 ;
    Rect[][] rect ;
    Bitmap bitmap ;
    Canvas canvas ;
    int[] startX = new int[50];
    int[] startY= new int[50];
    int[] endX = new int[50];
    int[] endY = new int[50];


    public CustomView(Context context , int xInput , int yInput) {
        super(context);
        xAxis = xInput;
        yAxis = yInput;
        pointsConnected = new boolean[xAxis+1][yAxis+1][2]; // 0 for horizontal and 1 for vertical
        pointsConnected2 = new boolean[xAxis+1][yAxis+1][2]; // 0 for horizontal and 1 for vertical
        pointsConnected3 = new boolean[xAxis+1][yAxis+1][2]; // 0 for horizontal and 1 for vertical

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

        rect = new Rect[xAxis+1][yAxis+1] ;
        for (int j = 1; j <= yAxis; j++) {
            for (int i = 1; i <= xAxis; i++) {
                rect[i][j] = new Rect( (i*scale) - (scale/2) , (j*scale) - (scale/2) , (i*scale) + (scale/2) , (j*scale) + (scale/2));
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_4444 ;
        bitmap = Bitmap.createBitmap(screenWidth , screenWidth ,config);
        canvas = new Canvas(bitmap);



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
//        canvas.drawLine(varX , varY , x ,y ,mPaint);
//        canvas.drawLine( initX , initY , finalX , finalY , mPaint);

        for(int j=1 ; j <= yAxis ; j++){
            for(int i=1 ; i<=xAxis ; i++){
                if( pointsConnected[i][j][0] == true ){
                    canvas.drawLine( i*scale , j*scale , (i+1)*scale , j*scale , mPaint);
                }
                if( pointsConnected[i][j][1] == true ){
                    canvas.drawLine( i*scale , j*scale , (i)*scale , (j+1)*scale , mPaint);
                }
            }
        }

        for(int j=1 ; j <= yAxis ; j++){
            for(int i=1 ; i<=xAxis ; i++){
                if(pointsConnected[i][j][0] == false || pointsConnected[i][j][1] == false) {}
                else if (pointsConnected[i][j+1][0] ==false || pointsConnected[i+1][j][1] == false){}
                else {
                    canvas.drawRect( i*scale , j*scale , (i+1)*scale , (j+1)*scale , mPaint);
                }
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        x = (int) event.getX();
        y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                for (int j = 1; j <= yAxis; j++) {
                    for (int i = 1; i <= xAxis; i++) {
                        if(rect[i][j].contains(x , y) == true){
                            initX = i * scale ;
                            initY = j * scale ;
                            startX[count] = i ;
                            startY[count] = j ;
                            return true ;
                        }
                    }
                }
            }
            case MotionEvent.ACTION_MOVE:{
                x = (int) event.getX();
                y = (int) event.getY();
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
            case MotionEvent.ACTION_UP:{

                for (int j = 1; j <= yAxis; j++) {
                    for (int i = 1; i <= xAxis; i++) {
                        if(rect[i][j].contains(x , y) == true){
                            finalX = i * scale ;
                            finalY = j * scale ;
                            endX[count] = i ;
                            endY[count] = j ;

                            evaluate();
                            return true ;
                        }
                    }
                }
            }
        }

        return  false;
    }

    public void evaluate (){

        if(startX[count] == endX[count] && startY[count] == endY[count]){ return;}
        else if(startX[count] == endX[count] + 1 ){
            pointsConnected[endX[count]][endY[count]][0] = true;
            pointsConnected2[endX[count]][endY[count]][0] = true;
            pointsConnected3[endX[count]][endY[count]][0] = true;

        }else if(startX[count] == endX[count] - 1){
            pointsConnected[startX[count]][endY[count]][0] = true;
            pointsConnected2[startX[count]][endY[count]][0] = true;
            pointsConnected3[startX[count]][endY[count]][0] = true;

        }else if(startY[count] == endY[count] + 1 ){
            pointsConnected[endX[count]][endY[count]][1] = true;
            pointsConnected2[endX[count]][endY[count]][1] = true;
            pointsConnected3[endX[count]][endY[count]][1] = true;

        }else if(startY[count] == endY[count] - 1){
            pointsConnected[endX[count]][startY[count]][1] = true;
            pointsConnected2[endX[count]][startY[count]][1] = true;
            pointsConnected3[endX[count]][startY[count]][1] = true;

        }
    }
}

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        boolean value = super.onTouchEvent(event);
//
//        x = (int) event.getX();
//        y = (int) event.getY();
//        int temp = 500 ;
//
//        if(event.getAction() ==MotionEvent.ACTION_DOWN){
//            for (int j = 1; j <= yAxis; j++) {
//                for (int i = 1; i <= xAxis; i++) {
//                    int temp1 = (x - (i * scale)) * (x - (i * scale));
//                    int temp2 = (y - (j * scale)) * (y - (j * scale));
//                    int temp3 = ((x - ((i+1) * scale)) * (x - ((i+1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // right
//                    int temp4 = ((x - ((i-1) * scale)) * (x - ((i-1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // left
//                    int temp5 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j+1) * scale)) * (y - ((j+1) * scale))) ; // top
//                    int temp6 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j-1) * scale)) * (y - ((j-1) * scale))) ; // bottom
//                    temp = temp1 + temp2;
//                    if (temp <= 400) {
//                        varX = i * scale;
//                        varY = j * scale;
//
////                            return true;
//                    }
//                }
//            }
//        }
//
//        for (int j = 1; j <= yAxis; j++) {
//            for (int i = 1; i <= xAxis; i++) {
//                int temp3 = ((x - ((i+1) * scale)) * (x - ((i+1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // right
//                int temp4 = ((x - ((i-1) * scale)) * (x - ((i-1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // left
//                int temp5 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j+1) * scale)) * (y - ((j+1) * scale))) ; // top
//                int temp6 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j-1) * scale)) * (y - ((j-1) * scale))) ; // bottom
//
////                    return true;
//
//
//                if (temp3 <= 1600)
//                    pointsConnected[i][j][0] = true;
//                if (temp4 <= 1600)
//                    pointsConnected[i - 1][j][0] = true;
//                if (temp5 <= 1600)
//                    pointsConnected[i][j - 1][1] = true;
//                if (temp6 <= 1600)
//                    pointsConnected[i][j][1] = true;
//
//
//                return true;
//            }
//        }
//        return false ;
//    }
//}

//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:{
//                for (int j = 1; j <= yAxis; j++) {
//                    for (int i = 1; i <= xAxis; i++) {
//                        int temp1 = (x - (i * scale)) * (x - (i * scale));
//                        int temp2 = (y - (j * scale)) * (y - (j * scale));
//                        int temp3 = ((x - ((i+1) * scale)) * (x - ((i+1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // right
//                        int temp4 = ((x - ((i-1) * scale)) * (x - ((i-1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // left
//                        int temp5 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j+1) * scale)) * (y - ((j+1) * scale))) ; // top
//                        int temp6 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j-1) * scale)) * (y - ((j-1) * scale))) ; // bottom
//                        temp = temp1 + temp2;
//                        if (temp <= 400) {
//                            varX = i * scale;
//                            varY = j * scale;
//
////                            return true;
//                        }
//                    }
//                }
//            }
////            case MotionEvent.ACTION_MOVE:{
////                x = (int) event.getX();
////                y = (int) event.getY();
////                for (int j = 1; j <= yAxis; j++) {
////                    for (int i = 1; i <= xAxis; i++) {
////                        int temp1 = (x - (i * scale)) * (x - (i * scale));
////                        int temp2 = (y - (j * scale)) * (y - (j * scale));
////                        int temp3 = ((x - ((i+1) * scale)) * (x - ((i+1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // right
////                        int temp4 = ((x - ((i-1) * scale)) * (x - ((i-1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // left
////                        int temp5 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j+1) * scale)) * (y - ((j+1) * scale))) ; // top
////                        int temp6 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j-1) * scale)) * (y - ((j-1) * scale))) ; // bottom
////                        int temp = temp1 + temp2;
////                        if (temp <= 400) {
////                            varX = i * scale;
////                            varY = j * scale;
////
////                            return true;
////                        }
////                    }
////                }
////            }
////            case MotionEvent.ACTION_UP:{
////
////                for (int j = 1; j <= yAxis; j++) {
////                    for (int i = 1; i <= xAxis; i++) {
////                        int temp1 = (x - (i * scale)) * (x - (i * scale));
////                        int temp2 = (y - (j * scale)) * (y - (j * scale));
////                        int temp3 = ((x - ((i+1) * scale)) * (x - ((i+1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // right
////                        int temp4 = ((x - ((i-1) * scale)) * (x - ((i-1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // left
////                        int temp5 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j+1) * scale)) * (y - ((j+1) * scale))) ; // top
////                        int temp6 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j-1) * scale)) * (y - ((j-1) * scale))) ; // bottom
////                        int temp = temp1 + temp2;
////
////
////                        if(temp3 <= 1600)
////                            pointsConnected[i][j][0] = true ;
////                        if(temp4 <= 1600)
////                            pointsConnected[i-1][j][0] = true ;
////                        if(temp5 <= 1600)
////                            pointsConnected[i][j-1][1] = true ;
////                        if(temp6 <= 1600)
////                                pointsConnected[i][j][1] = true ;
////
////
////                        return true;
//////                        if(temp3 <= 800)
//////                            pointsConnected[i][j][0] = true ;
//////                        if(temp4 <= 800)
//////                            pointsConnected[i-1][j][0] = true ;
//////                        if(temp5 <= 800)
//////                            pointsConnected[i][j-1][1] = true ;
//////                        if(temp6 <= 800)
//////                            pointsConnected[i][j][1] = true ;
//////
//////
//////                        return true;
////                    }
////                }
////            }
//
//        }
//        for (int j = 1; j <= yAxis; j++) {
//            for (int i = 1; i <= xAxis; i++) {
//                int temp3 = ((x - ((i+1) * scale)) * (x - ((i+1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // right
//                int temp4 = ((x - ((i-1) * scale)) * (x - ((i-1) * scale))) + ((y - (j * scale)) * (y - (j * scale))) ; // left
//                int temp5 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j+1) * scale)) * (y - ((j+1) * scale))) ; // top
//                int temp6 = ((x - (i * scale)) * (x - (i * scale))) + ((y - ((j-1) * scale)) * (y - ((j-1) * scale))) ; // bottom
//                if (temp <= 400) {
//                    varX = i * scale;
//                    varY = j * scale;
//
////                    return true;
//
//
//                    if (temp3 <= 1600)
//                        pointsConnected[i][j][0] = true;
//                    if (temp4 <= 1600)
//                        pointsConnected[i - 1][j][0] = true;
//                    if (temp5 <= 1600)
//                        pointsConnected[i][j - 1][1] = true;
//                    if (temp6 <= 1600)
//                        pointsConnected[i][j][1] = true;
//                }
//
//                return true;
////                        if(temp3 <= 800)
////                            pointsConnected[i][j][0] = true ;
////                        if(temp4 <= 800)
////                            pointsConnected[i-1][j][0] = true ;
////                        if(temp5 <= 800)
////                            pointsConnected[i][j-1][1] = true ;
////                        if(temp6 <= 800)
////                            pointsConnected[i][j][1] = true ;
////
////
////                        return true;
//            }
//        }
//        return false ;
//    }
//}
