package com.chuev.myhellowworld.main;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.chuev.myhellowworld.MoneyItem;
import com.chuev.myhellowworld.R;
import com.chuev.myhellowworld.remote.MoneyAPI;
import com.chuev.myhellowworld.remote.MoneyRemoteItem;
import com.chuev.myhellowworld.remote.MoneyResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable= new CompositeDisposable();
    public LiveData <List<MoneyItem>> moneylist;
    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void loadincomes (MoneyAPI moneyAPI)
    {
      // пока не стал делать
    }
}
