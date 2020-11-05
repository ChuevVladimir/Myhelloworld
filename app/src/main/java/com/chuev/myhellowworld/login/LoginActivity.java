package com.chuev.myhellowworld.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.chuev.myhellowworld.LoftApp;
import com.chuev.myhellowworld.R;
import com.chuev.myhellowworld.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.login_activity);

        configureView();
        configureViewModel();
    }
    private void configureView(){
        AppCompatButton loginEnterView = findViewById(R.id.loginbutton);
        loginEnterView.setOnClickListener(v -> {loginViewModel.makelogin(((LoftApp) getApplication()).authAPI );});
    }

    private void configureViewModel(){
        loginViewModel =new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.messageString.observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }});

        loginViewModel.messageString.observe(this, authToken -> {
            if (!TextUtils.isEmpty(authToken)) {
              SharedPreferences sharedPreferences= getSharedPreferences(getString(R.string.app_name),0);
                sharedPreferences.edit().putString(LoftApp.AUTH_KEY,authToken).apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }});
    }
}
