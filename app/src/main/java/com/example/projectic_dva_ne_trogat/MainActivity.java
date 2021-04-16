package com.example.projectic_dva_ne_trogat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }


    public void fast(View view) {
            Intent intent = new Intent(this, CatRoomActivity.class);
            startActivity(intent);
        }

    public void kill(View view) {
        System.exit(0);
    }
}