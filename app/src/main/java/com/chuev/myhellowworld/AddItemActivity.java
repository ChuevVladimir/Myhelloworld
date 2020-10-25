package com.chuev.myhellowworld;

import android.content.Intent;
import android.text.TextUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {
    private TextView ItemText;
    private TextView ValueText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);



        ItemText = findViewById(R.id.Item_text);
        ValueText = findViewById(R.id.Value_text);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                String item = ItemText.getText().toString();
                String value = ValueText.getText().toString();

                if (!TextUtils.isEmpty(item) && !TextUtils.isEmpty(value)) {
                    setResult(
                            RESULT_OK,
                            new Intent().putExtra("item", item).putExtra("value", value));
                    finish();
                }


            }
        });
    }


}

