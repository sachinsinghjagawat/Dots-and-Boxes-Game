package com.example.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText xAxis;
    EditText yAxis;
    EditText players;
    int i1 ,i2, numberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xAxis = (EditText) findViewById(R.id.editText4);
        yAxis = (EditText) findViewById(R.id.editText5);
        players = (EditText) findViewById(R.id.editText2);

        findViewById(R.id.floatingActionButton5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = players.getText().toString();
                numberOfPlayers = Integer.parseInt(temp);
                if(numberOfPlayers<10){
                    numberOfPlayers++ ;
                    players.setText(numberOfPlayers + "");
                }else {
                    Toast.makeText(MainActivity.this , "Number of Players can't be more than 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.floatingActionButton6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = players.getText().toString();
                int numberOfPlayers = Integer.parseInt(temp);

                if (numberOfPlayers > 1){
                    numberOfPlayers-- ;
                    players.setText(numberOfPlayers + "");
                }else{
                    Toast.makeText(MainActivity.this, "number of players can not be less than 1", Toast.LENGTH_SHORT).show();
                }
            }
        });



        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = xAxis.getText().toString();
                String s2 = yAxis.getText().toString();
                i1 = Integer.parseInt(s1);
                i2 = Integer.parseInt(s2);

                if (i1 >= i2){
                    Intent myIntent = new Intent(MainActivity.this , Main2Activity.class);
                    myIntent.putExtra("xAxis" , i1);
                    myIntent.putExtra("yAxis" , i2);
                    myIntent.putExtra("numberOfPlayers" , numberOfPlayers);
                    startActivity(myIntent);
                }else{
                    Toast.makeText(MainActivity.this, "X Axis should be greater than Y axis", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu , menu);
        return true;
    }
}
