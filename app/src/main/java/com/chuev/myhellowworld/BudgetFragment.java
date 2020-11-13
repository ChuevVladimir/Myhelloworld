package com.chuev.myhellowworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.chuev.myhellowworld.main.MainActivity;
import com.chuev.myhellowworld.remote.MoneyAPI;
import com.chuev.myhellowworld.remote.MoneyRemoteItem;
import com.chuev.myhellowworld.remote.MoneyResponse;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BudgetFragment extends Fragment implements ItemsAdapterListener, ActionMode.Callback {

   public static final int REQUEST_CODE = 100;
    private static final String COLOR_ID = "colorId";
    private static final String TYPE = "fragmentType";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView itemsView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MoneyCellAdpter moneyCellAdpter ;
    private ActionMode mActionMode;
    private String type_of_op;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_udget,container,false);



        moneyCellAdpter =new MoneyCellAdpter((getArguments().getInt(COLOR_ID)));
        moneyCellAdpter.setListener(this);

        configureRecyclerView(view); //настраиваем ресайклер

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type_of_op = bundle.getString(TYPE, "2");
        }
        generate_content();
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

    @Override
    public void onItemClick(final MoneyItem item, final int position) {
        moneyCellAdpter.clearItem(position);
        if (mActionMode != null) {
            mActionMode.setTitle(getString(R.string.selected, String.valueOf(moneyCellAdpter.getSelectedSize())));
        }
    }

    @Override
    public void onItemLongClick(final MoneyItem item, final int position) {
        if (mActionMode == null) {
            getActivity().startActionMode(this);
        }
        moneyCellAdpter.toggleItem(position);
        if (mActionMode != null) {
            mActionMode.setTitle(getString(R.string.selected, String.valueOf(moneyCellAdpter.getSelectedSize())));
        }
    }



    public static BudgetFragment newInstance(final int colorId, final String type) {
        BudgetFragment pageFragment = new BudgetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COLOR_ID, colorId);
        bundle.putString(TYPE, type);
        pageFragment.setArguments(bundle);

        return pageFragment;
    }
    public void generate_content()
    {

     get_items(type_of_op);


    }

    private void get_items (String type)
    {
        SharedPreferences sharedPreferences =getContext().getSharedPreferences(getString(R.string.app_name),0); ;



        Disposable disposable=((LoftApp) getActivity().getApplication()).moneyAPI.getmoneyitems(type,sharedPreferences.getString(LoftApp.AUTH_KEY,""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( moneyRemoteItems -> {

                    moneyCellAdpter.clearItems();
                    for (MoneyRemoteItem moneyRemoteItem: moneyRemoteItems)
                    {
                        moneyCellAdpter.addItem(MoneyItem.getInstance(moneyRemoteItem));

                    }

                    if (type_of_op == "income") {
                        MainActivity.income_float = moneyCellAdpter.sum();


                    }
                    else
                    {
                        MainActivity.expense_float = moneyCellAdpter.sum();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity().getApplication(),throwable.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (type_of_op == "income") {
                            MainActivity.income_float = moneyCellAdpter.sum();


                        }
                        else
                        {
                            MainActivity.expense_float = moneyCellAdpter.sum();
                        }
                    }
                });
        compositeDisposable.add(disposable);
        moneyCellAdpter.notifyDataSetChanged();


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

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu)
    {
        mActionMode=mode;
    return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        MenuInflater menuInflater = new MenuInflater(getActivity());
        menuInflater.inflate(R.menu.menu_delete,menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId()==R.id.remove)
        {new AlertDialog.Builder((getContext()))
                .setMessage(R.string.delete_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
removeItems();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
        };
        return true;
    }

    private void removeItems() {
        SharedPreferences sharedPreferences =getContext().getSharedPreferences(getString(R.string.app_name),0); ;
        List<String> selectedItems =moneyCellAdpter.getSelectedItemIds();
    for (String itemId:selectedItems)
    {
        Disposable disposable= ((LoftApp) getActivity().getApplication()).moneyAPI.removeItem(itemId,sharedPreferences.getString(LoftApp.AUTH_KEY,""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        moneyCellAdpter.clearSelections();
                        mActionMode.setTitle(getString(R.string.selected, String.valueOf(moneyCellAdpter.getSelectedSize())));
                        generate_content();
                        moneyCellAdpter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        moneyCellAdpter.clearSelections();
                        mActionMode.setTitle(getString(R.string.selected, String.valueOf(moneyCellAdpter.getSelectedSize())));
                        generate_content();
                        moneyCellAdpter.notifyDataSetChanged();
                    }
                });
        compositeDisposable.add(disposable);


    }

    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode=null;
        moneyCellAdpter.clearSelections();
    }
}
