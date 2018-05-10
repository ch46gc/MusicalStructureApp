package com.example.android.musicalstructureapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView ajr=  findViewById(R.id.ajr_music);
        ajr.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent ajrIntent = new Intent(MainActivity.this, AjrActivity.class);
                startActivity(ajrIntent);
            }
        });
        ImageView bebeRexha =  findViewById(R.id.bebe_rexha_music);
         bebeRexha.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent bebeRexhaIntent = new Intent(MainActivity.this, BebeRexhaActivity.class);
                startActivity(bebeRexhaIntent);
            }
        });
        ImageView bts=  findViewById(R.id.bts_music);
        bts.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent btsIntent = new Intent(MainActivity.this, BtsActivity.class);
                startActivity(btsIntent);
            }
        });
        ImageView pink = findViewById(R.id.pink_music);
        pink.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent pinkIntent = new Intent(MainActivity.this, PinkActivity.class);
                startActivity(pinkIntent);
            }
        });

    }

}