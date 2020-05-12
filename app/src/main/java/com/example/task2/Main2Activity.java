package com.example.task2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    LinearLayout mLinearLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mLinearLayout = findViewById(R.id.ll);
        Intent myIntent = getIntent();
        int xAxis = myIntent.getIntExtra("xAxis" , 0);
        int yAxis = myIntent.getIntExtra("yAxis" , 0);
        int numberOfPlayers = myIntent.getIntExtra("numberOfPlayers" , 0);
        CustomView v1 = new CustomView(this , xAxis , yAxis );
        mLinearLayout.addView(v1);
        v1.setDrawingCacheEnabled(true);
        v1.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = v1.getDrawingCache();
//        Custom2View v2 = new Custom2View(this, xAxis , yAxis , numberOfPlayers);
//        mLinearLayout.addView(v2);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
