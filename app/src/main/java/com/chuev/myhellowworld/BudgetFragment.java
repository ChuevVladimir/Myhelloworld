package com.chuev.myhellowworld;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment{

    private static final int REQUEST_CODE = 100;
    List<MoneyItem> moneyItems = new ArrayList<>();
    private RecyclerView itemsView;

    private MoneyCellAdpter moneyCellAdpter =new MoneyCellAdpter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_udget,container,false);


        generate_content();
        configureRecyclerView(view); //настраиваем ресайклер



        view.findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(getActivity(), AddItemActivity.class);
                startActivityForResult(newActivity, REQUEST_CODE);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult( final int requestCode, final int resultCode, @Nullable final Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);

        int value;
        try {
            value = Integer.parseInt(data.getStringExtra("value"));
        } catch (NumberFormatException e) {
           value = 0;
        }

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            moneyItems.add(new MoneyItem(data.getStringExtra("item"), data.getStringExtra("value")));
            moneyCellAdpter.setData(moneyItems);
        }
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

        moneyItems.add(new MoneyItem("Стол","20000"));
        moneyItems.add(new MoneyItem("Не стол","30000"));
        moneyItems.add(new MoneyItem("Очень длиное название, да такое, что никуда не поместится","10000000"));
        moneyItems.add(new MoneyItem("Ещё стол","2000"));
        moneyCellAdpter.setData(moneyItems);
    }

    private void configureRecyclerView(View view) {
        itemsView = view.findViewById(R.id.itemsView);
        itemsView.setAdapter(moneyCellAdpter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false
        );
        itemsView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(itemsView.getContext(),LinearLayoutManager.VERTICAL);
        itemsView.addItemDecoration(dividerItemDecoration);
//        initViews(view);
    }

}
