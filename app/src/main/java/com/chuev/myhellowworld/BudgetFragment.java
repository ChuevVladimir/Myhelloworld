package com.chuev.myhellowworld;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chuev.myhellowworld.remote.MoneyRemoteItem;
import com.chuev.myhellowworld.remote.MoneyResponse;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BudgetFragment extends Fragment{

   public static final int REQUEST_CODE = 100;
    List<MoneyItem> moneyItems = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView itemsView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MoneyCellAdpter moneyCellAdpter =new MoneyCellAdpter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_udget,container,false);


        generate_content();
        configureRecyclerView(view); //настраиваем ресайклер



        return view;
    }



    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        generate_content();
        super.onResume();
    }


    private void initViews(View view) {

        if (getArguments().getString("test")=="синий")
        {
            AppCompatTextView appCompatTextView = (AppCompatTextView) view.findViewById(R.id.cellmoneycost);
            appCompatTextView.setTextColor( -16776961);

        }
        if (getArguments().getString("test")=="красный")
        {
            ((TextView) view.findViewById(R.id.cellmoneycost)).setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        }
    }

    public static BudgetFragment newInstance(String text) {
        BudgetFragment pageFragment = new BudgetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("test", text);
        pageFragment.setArguments(bundle);

        return pageFragment;
    }
    private void generate_content()
    {

moneyItems.clear();
        SharedPreferences sharedPreferences =getContext().getSharedPreferences(getString(R.string.app_name),0); ;

      Disposable disposable=((LoftApp) getActivity().getApplication()).moneyAPI.getmoneyitems("income",sharedPreferences.getString(LoftApp.AUTH_KEY,""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( moneyRemoteItems -> {


for (MoneyRemoteItem moneyRemoteItem: moneyRemoteItems)
{moneyItems.add(MoneyItem.getInstance(moneyRemoteItem));
mSwipeRefreshLayout.setRefreshing(false);
}
moneyCellAdpter.setData(moneyItems);

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(getActivity().getApplication(),throwable.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
      compositeDisposable.add(disposable);


    }

    private void configureRecyclerView(View view) {
        itemsView = view.findViewById(R.id.itemsView);
        itemsView.setAdapter(moneyCellAdpter);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                generate_content();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false
        );
        itemsView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(itemsView.getContext(),LinearLayoutManager.VERTICAL);
        itemsView.addItemDecoration(dividerItemDecoration);
//        initViews(view);
    }

}
