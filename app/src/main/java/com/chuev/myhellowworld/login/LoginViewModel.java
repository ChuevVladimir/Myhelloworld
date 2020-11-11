package com.chuev.myhellowworld.login;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chuev.myhellowworld.LoftApp;
import com.chuev.myhellowworld.R;
import com.chuev.myhellowworld.remote.AuthAPI;
import com.chuev.myhellowworld.remote.AuthResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<String> authToken  = new MutableLiveData<>("");
    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    void makelogin(AuthAPI authAPI){



        compositeDisposable.add(authAPI.makelogin(LoftApp.AUTH_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authResponse -> {authToken.postValue(authResponse.getAuthToken());},
                        throwable -> {messageString.postValue(throwable.getLocalizedMessage());}));
    }
}
