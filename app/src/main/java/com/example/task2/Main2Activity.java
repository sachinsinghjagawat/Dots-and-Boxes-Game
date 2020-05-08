package com.example.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent myIntent = getIntent();
        int xAxis = myIntent.getIntExtra("xAxis" , 0);
        int yAxis = myIntent.getIntExtra("yAxis" , 0);
        int numberOfPlayers = myIntent.getIntExtra("numberOfPlayers" , 0);
        CustomView v1 = new CustomView(this , xAxis , yAxis );


    }
}
