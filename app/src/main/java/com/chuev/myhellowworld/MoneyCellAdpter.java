package com.chuev.myhellowworld;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoneyCellAdpter extends RecyclerView.Adapter<MoneyCellAdpter.MoneyViewHolder> {

    private List<MoneyItem> moneyItemList = new ArrayList<>();


    public void  setData (List<MoneyItem> moneyItems)
    {
        moneyItemList.clear();
        moneyItemList.addAll(moneyItems);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MoneyViewHolder(layoutInflater.inflate(R.layout.cell_money,parent,  false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
holder.bind(moneyItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return moneyItemList.size();
    }

    static class MoneyViewHolder extends RecyclerView.ViewHolder{
            private TextView titletextview;
            private  TextView costtextview;

        public MoneyViewHolder(@NonNull View itemView) {
            super(itemView);
            titletextview = itemView.findViewById(R.id.cellmoneytitle);
            costtextview = itemView.findViewById(R.id.cellmoneycost);

        }

        public void bind(MoneyItem moneyItem)
        {
              titletextview.setText(moneyItem.getTitle());
              costtextview.setText(moneyItem.getValue());

        }
    }
}
