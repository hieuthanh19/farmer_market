package com.example.farmersmarket;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersmarket.database.AppDatabase;
import com.google.android.material.textfield.TextInputLayout;

public class AddWarehouse extends AppCompatActivity {

    TextInputLayout nameLayout;
    TextInputLayout addressLayout;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);

        nameLayout = findViewById(R.id.add_warehouse_name_layout);
        addressLayout = findViewById(R.id.add_warehouse_address_layout);


        appDatabase = AppDatabase.getAppDatabase(this);
    }

    public void back(View view) {
        onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createWarehouse(View view) {
        if (nameLayout.getEditText().getText().toString().trim().length() == 0) {
            nameLayout.setError("Field can not be empty!");

        } else {
            nameLayout.setError(null);
        }
        if (addressLayout.getEditText().getText().toString().trim().length() == 0) {
            addressLayout.requestFocus();
            addressLayout.setError("Field can not be empty!");
        } else {
            addressLayout.setError(null);
        }
    }
}