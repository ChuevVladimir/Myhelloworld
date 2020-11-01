package com.chuev.myhellowworld;

import android.app.Application;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddItemActivity extends AppCompatActivity {
    private TextView ItemText;
    private TextView ValueText;
    private String nName;
    private String nValue;
    private Button maddButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);



        ItemText = findViewById(R.id.Item_text);
        ItemText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nName=s.toString();
                checkEditTextHasText();
            }
        });
        ValueText = findViewById(R.id.Value_text);
        ValueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nValue=s.toString();
                checkEditTextHasText();
            }
        });
        maddButton = findViewById(R.id.add_button);
        checkEditTextHasText();

        maddButton.setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                String item = ItemText.getText().toString();
                String value = ValueText.getText().toString();

                if (item.equals("")||value.equals(("")))
                {
                    Toast.makeText(getApplicationContext(),getString(R.string.fill_fields),Toast.LENGTH_LONG).show();
                    return;
                }

               Disposable disposable=((LoftApp) getApplication()).moneyAPI.postMoney(Integer.parseInt(value.toString()),item,"income")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                            @Override
                            public void run() throws Exception {
                        Toast.makeText(getApplicationContext(),getString(R.string.success_added),Toast.LENGTH_LONG).show();
                       finish();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getApplicationContext(),throwable.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            }
                        });


            }
        });


    }
public void checkEditTextHasText()
{
maddButton.setEnabled(!TextUtils.isEmpty(nName)&&!TextUtils.isEmpty(nValue));
}

}

