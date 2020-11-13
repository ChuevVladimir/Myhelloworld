package com.chuev.myhellowworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chuev.myhellowworld.main.MainActivity;

import java.util.Random;

public class DiagramFragment extends Fragment {

    TextView total;
    TextView income;
    TextView expense;
    BalanceView balanceView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        balanceView = view.findViewById(R.id.balanceView);
        total=view.findViewById(R.id.total);
        income = view.findViewById(R.id.total_income);
        expense =view.findViewById(R.id.total_expense);
        update_data();
    }

    public void update_data ()
    {

        expense.setText(String.valueOf(MainActivity.expense_float));
        income.setText(String.valueOf(MainActivity.income_float));
        total.setText(String.valueOf(MainActivity.income_float-MainActivity.expense_float));
        balanceView.update(MainActivity.expense_float,MainActivity.income_float);
    }

    public static DiagramFragment getInstance() {
        return new DiagramFragment();
    }
}
