package com.example.projectic_dva_ne_trogat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CatRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CatRoomSurfaceView(this));
        getSupportActionBar().hide();
    }
}