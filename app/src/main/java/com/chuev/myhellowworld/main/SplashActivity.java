package com.chuev.myhellowworld.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chuev.myhellowworld.LoftApp;
import com.chuev.myhellowworld.R;
import com.chuev.myhellowworld.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
      //  SharedPreferences sharedPreferences= getSharedPreferences(getString(R.string.app_name),0);
       // String authtoken = sharedPreferences.getString(LoftApp.AUTH_KEY,"");
        String authtoken="";

    //    Intent intent  = new Intent(getApplicationContext(),LoginActivity.class);
     //   startActivity(intent);

    //    if (TextUtils.isEmpty(authtoken))
      //  {;

         //   Intent intent  = new Intent(getApplicationContext(), LoginActivity.class);
          //  startActivity(intent);
           // finish();

        //}
        //else {;


        //    Intent intent  = new Intent(getApplicationContext(),MainActivity.class);
         //   startActivity(intent);
            //finish();
       // }


    }



}
