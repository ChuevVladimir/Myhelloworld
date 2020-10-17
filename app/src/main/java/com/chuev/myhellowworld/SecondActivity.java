package com.chuev.myhellowworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        int cost_id= getIntent().getIntExtra("COST_ID",0);
        Log.e("TAG","COST_ID = "+cost_id); // 1!
    }
}