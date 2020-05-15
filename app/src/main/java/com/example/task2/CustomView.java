package com.example.task2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    int xAxis, yAxis ,initX, initY , finalX , finalY , x, y  , numberOfPlayers ;
    int player = 1 ;
    int count = 1 ;
    int chance = 1 ;
    int hv = 0 ;
    int a , b , c , d ;
    int scale ;
    Paint mPaint , dirPaint, linePaint , boxPaint ;
    boolean[][][][] pointsConnected ;
    boolean[][][] pointsConnected2 ;
    boolean[][][] pointsConnected3 ;
    boolean[][][][][] relation ;
    Rect[][] rect ;
    Bitmap bitmap ;
    Canvas canvas ;
    int[] startX = new int[50];
    int[] startY= new int[50];
    int[] endX = new int[50];
    int[] endY = new int[50];
    Color[] mColor = new Color[10] ;
    private SoundPool soundPool ;
    private int sound1 , sound2 ;


    public CustomView(Context context , int xInput , int yInput , int p) {
        super(context);
        xAxis = xInput;
        yAxis = yInput;
        numberOfPlayers = p ;
        pointsConnected = new boolean[xAxis+1][yAxis+1][2][11]; // 0 for horizontal and 1 for vertical
        pointsConnected2 = new boolean[xAxis+1][yAxis+1][2]; // 0 for horizontal and 1 for vertical
        pointsConnected3 = new boolean[xAxis+1][yAxis+1][11]; // 0 for horizontal and 1 for vertical
        relation = new boolean[xAxis+1][yAxis+1][xAxis+1][yAxis+1][11] ;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels ;
        scale = screenWidth / (xAxis + 1) ;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);

        boxPaint = new Paint();
        boxPaint.setAntiAlias(true);
        boxPaint.setColor(Color.BLACK);

        dirPaint = new Paint();
        dirPaint.setAntiAlias(true);
        dirPaint.setColor(getResources().getColor(R.color.highlight));

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(13);
        linePaint.setColor(getResources().getColor(R.color.highlight));

        rect = new Rect[xAxis+1][yAxis+1] ;
        for (int j = 1; j <= yAxis; j++) {
            for (int i = 1; i <= xAxis; i++) {
                rect[i][j] = new Rect( (i*scale) - (scale/2) , (j*scale) - (scale/2) , (i*scale) + (scale/2) , (j*scale) + (scale/2));
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else {
            soundPool = new SoundPool(6 , AudioManager.STREAM_MUSIC , 0);
        }
//        sound1 = soundPool.load(CustomView.this , R.raw.line , 1);
//        sound2 = soundPool.load(this , R.raw.box  ,1);
//        Bitmap.Config config = Bitmap.Config.ARGB_4444 ;
//        bitmap = Bitmap.createBitmap(screenWidth , screenWidth ,config);
//        canvas = new Canvas(bitmap);





    }

    @Override
    protected void onDraw (Canvas canvas){
        super.onDraw(canvas);

        postInvalidate();

        for(int j=1 ; j <= yAxis ; j++){
            for(int i=1 ; i<=xAxis ; i++){
                for(int k=1 ; k<=10 ; k++) {
                    if(pointsConnected3[i][j][k] == true) {
                        colorBox(k);
                        canvas.drawRect(i * scale, j * scale, (i + 1) * scale, (j + 1) * scale, boxPaint);
                    }
                }
            }
        }

        for(int j=1 ; j <= yAxis ; j++){
            for(int i=1 ; i<=xAxis ; i++){
                for(int k=1 ; k<=10 ; k++) {
                    if (pointsConnected[i][j][0][k] == true) {
                        color(k);
                        canvas.drawLine(i * scale, j * scale, (i + 1) * scale, j * scale, linePaint);
                    }
                    if (pointsConnected[i][j][1][k] == true) {
                        color(k);
                        canvas.drawLine(i * scale, j * scale, (i) * scale, (j + 1) * scale, linePaint);
                    }
                }
            }
        }

        for(int j=1 ; j <= yAxis ; j++){
            for(int i=1 ; i<=xAxis ; i++){
                canvas.drawCircle( i * scale , j * scale , 10 , mPaint);
            }
        }

    }

    public void undo (){
//        chance-- ;
//        if (chance < 1) chance = numberOfPlayers;
//        relation[startX[count]][startY[count]][endX[count]][endY[count]][chance] = false ;
//        pointsConnected[a][b][hv][chance] = false;
//
//        for(int j=1 ; j <= yAxis ; j++){
//            for(int i=1 ; i<=xAxis ; i++){
//                if ((pointsConnected2[i][j][0] == false || pointsConnected2[i][j][1] == false) && (pointsConnected2[i][j + 1][0] == false || pointsConnected2[i + 1][j][1] == false) == false) {
//                    for(int k=1 ; k<=10 ; k++){
//                        pointsConnected3[i][j][k] = false ;
//                        chance++ ;
//                        if(chance > numberOfPlayers) chance = 1 ;
//                    }
//                }
//            }
//        }

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
            for(int i=1 ; i<=10 ; i++){
                if(relation[startX[count]][startY[count]][endX[count]][endY[count]][i] == true){ return;}
            }
            pointsConnected[endX[count]][endY[count]][0][chance] = true;
            pointsConnected2[endX[count]][endY[count]][0] = true;
            hv = 0 ;
            a = endX[count] ;
            b = endY[count] ;
            relation[startX[count]][startY[count]][endX[count]][endY[count]][chance] = true ;
            for(int j=1 ; j <= yAxis ; j++){
                for(int i=1 ; i<=xAxis ; i++){
                    int temp = 0 ;
                    if (pointsConnected2[i][j][0] == false || pointsConnected2[i][j][1] == false) {
                    } else if (pointsConnected2[i][j + 1][0] == false || pointsConnected2[i + 1][j][1] == false) {
                    } else {
                        player = chance ;
                        for(int k=1 ; k<=10 ; k++){
                            if(pointsConnected3[i][j][k] == true) {
                                temp = k;
                            }

                        }
                    }
                    if(temp == 0) {
                        if (pointsConnected2[i][j][0] == false || pointsConnected2[i][j][1] == false) {
                        } else if (pointsConnected2[i][j + 1][0] == false || pointsConnected2[i + 1][j][1] == false) {
                        } else {
                            player = chance;
                            if (pointsConnected3[i][j][player] == true) {
                            } else {
                                pointsConnected3[i][j][player] = true;
                                chance--;
                                if (chance < 1) chance = numberOfPlayers;
                            }
                        }
                    }
                }
            }
            colorSet(chance);

        }else if(startX[count] == endX[count] - 1){
            for(int i=1 ; i<=10 ; i++){
                if(relation[startX[count]][startY[count]][endX[count]][endY[count]][i] == true){ return;}
            }
            pointsConnected[startX[count]][endY[count]][0][chance] = true;
            pointsConnected2[startX[count]][endY[count]][0] = true;
            hv=0;
            a= startX[count];
            b = endY [count];
            relation[startX[count]][startY[count]][endX[count]][endY[count]][chance] = true ;
            for(int j=1 ; j <= yAxis ; j++){
                for(int i=1 ; i<=xAxis ; i++){
                    int temp = 0 ;
                    if (pointsConnected2[i][j][0] == false || pointsConnected2[i][j][1] == false) {
                    } else if (pointsConnected2[i][j + 1][0] == false || pointsConnected2[i + 1][j][1] == false) {
                    } else {
                        player = chance ;
                        for(int k=1 ; k<=10 ; k++){
                            if(pointsConnected3[i][j][k] == true) {
                                temp = k;
                            }

                        }
                    }
                    if(temp == 0) {
                        if (pointsConnected2[i][j][0] == false || pointsConnected2[i][j][1] == false) {
                        } else if (pointsConnected2[i][j + 1][0] == false || pointsConnected2[i + 1][j][1] == false) {
                        } else {
                            player = chance;
                            if (pointsConnected3[i][j][player] == true) {
                            } else {
                                pointsConnected3[i][j][player] = true;
                                chance--;
                                if (chance < 1) chance = numberOfPlayers;
                            }
                        }
                    }
                }
            }
            colorSet(chance);

        }else if(startY[count] == endY[count] + 1 ){
            for(int i=1 ; i<=10 ; i++){
                if(relation[startX[count]][startY[count]][endX[count]][endY[count]][i] == true){ return;}
            }
            pointsConnected[endX[count]][endY[count]][1][chance] = true;
            pointsConnected2[endX[count]][endY[count]][1] = true;
            hv=1;
            a= endX[count];
            b = endY[count];
            relation[startX[count]][startY[count]][endX[count]][endY[count]][chance] = true ;
            for(int j=1 ; j <= yAxis ; j++){
                for(int i=1 ; i<=xAxis ; i++){
                    int temp = 0 ;
                    if (pointsConnected2[i][j][0] == false || pointsConnected2[i][j][1] == false) {
                    } else if (pointsConnected2[i][j + 1][0] == false || pointsConnected2[i + 1][j][1] == false) {
                    } else {
                        player = chance ;
                        for(int k=1 ; k<=10 ; k++){
                            if(pointsConnected3[i][j][k] == true) {
                                temp = k;
                            }

                        }
                    }
                    if(temp == 0) {
                        if (pointsConnected2[i][j][0] == false || pointsConnected2[i][j][1] == false) {
                        } else if (pointsConnected2[i][j + 1][0] == false || pointsConnected2[i + 1][j][1] == false) {
                        } else {
                            player = chance;
                            if (pointsConnected3[i][j][player] == true) {
                            } else {
                                pointsConnected3[i][j][player] = true;
                                chance--;
                                if (chance < 1) chance = numberOfPlayers;
                            }
                        }
                    }
                }
            }
            colorSet(chance);

        }else if(startY[count] == endY[count] - 1){
            for(int i=1 ; i<=10 ; i++){
                if(relation[startX[count]][startY[count]][endX[count]][endY[count]][i] == true){ return;}
            }
            pointsConnected[endX[count]][startY[count]][1][chance] = true;
            pointsConnected2[endX[count]][startY[count]][1] = true;
            hv=0;
            a = endX[count];
            b= startY[count];
            relation[startX[count]][startY[count]][endX[count]][endY[count]][chance] = true ;
            for(int j=1 ; j <= yAxis ; j++){
                for(int i=1 ; i<=xAxis ; i++){
                    int temp = 0 ;
                    if (pointsConnected2[i][j][0] == false || pointsConnected2[i][j][1] == false) {
                    } else if (pointsConnected2[i][j + 1][0] == false || pointsConnected2[i + 1][j][1] == false) {
                    } else {
                        player = chance ;
                        for(int k=1 ; k<=10 ; k++){
                            if(pointsConnected3[i][j][k] == true) {
                                temp = k;
                            }

                        }
                    }
                    if(temp == 0) {
                        if (pointsConnected2[i][j][0] == false || pointsConnected2[i][j][1] == false) {
                        } else if (pointsConnected2[i][j + 1][0] == false || pointsConnected2[i + 1][j][1] == false) {
                        } else {
                            player = chance;
                            if (pointsConnected3[i][j][player] == true) {
                            } else {
                                pointsConnected3[i][j][player] = true;
                                chance--;
                                if (chance < 1) chance = numberOfPlayers;
                            }
                        }
                    }
                }
            }
            colorSet(chance);

        }
    }

    public void colorSet (int i){
        color(i);
        chance++ ;
        if(chance > numberOfPlayers) chance = 1 ;
    }

    public void color (int i) {
        if( i == 1){
            linePaint.setColor(getResources().getColor(R.color.c1));
        }
        if( i == 2){
            linePaint.setColor(getResources().getColor(R.color.c2));
        }
        if( i == 3){
            linePaint.setColor(getResources().getColor(R.color.c3));
        }
        if( i == 4){
            linePaint.setColor(getResources().getColor(R.color.c4));
        }
        if( i == 5){
            linePaint.setColor(getResources().getColor(R.color.c5));
        }
        if( i == 6){
            linePaint.setColor(getResources().getColor(R.color.c6));
        }
        if( i == 7){
            linePaint.setColor(getResources().getColor(R.color.c7));
        }
        if( i == 8){
            linePaint.setColor(getResources().getColor(R.color.c8));
        }
        if( i == 9){
            linePaint.setColor(getResources().getColor(R.color.c9));
        }
        if( i == 10){
            linePaint.setColor(getResources().getColor(R.color.c10));
        }

    }

    public void colorBox (int i) {
        if( i == 1){
            boxPaint.setColor(getResources().getColor(R.color.b1));
        }
        if( i == 2){
            boxPaint.setColor(getResources().getColor(R.color.b2));
        }
        if( i == 3){
            boxPaint.setColor(getResources().getColor(R.color.b3));
        }
        if( i == 4){
            boxPaint.setColor(getResources().getColor(R.color.b4));
        }
        if( i == 5){
            boxPaint.setColor(getResources().getColor(R.color.b5));
        }
        if( i == 6){
            boxPaint.setColor(getResources().getColor(R.color.b6));
        }
        if( i == 7){
            boxPaint.setColor(getResources().getColor(R.color.b7));
        }
        if( i == 8){
            boxPaint.setColor(getResources().getColor(R.color.b8));
        }
        if( i == 9){
            boxPaint.setColor(getResources().getColor(R.color.b9));
        }
        if( i == 10){
            boxPaint.setColor(getResources().getColor(R.color.b10));
        }

    }
}

