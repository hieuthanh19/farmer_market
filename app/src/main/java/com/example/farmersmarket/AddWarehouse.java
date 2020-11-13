package com.example.farmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.StoreHouse;
import com.google.android.material.textfield.TextInputLayout;

public class AddWarehouse extends AppCompatActivity {

    AppDatabase appDatabase;

    TextInputLayout nameLayout;
    TextInputLayout addressLayout;


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

    /**
     * onClick handler. Create new warehouse
     *
     * @param view Create Warehouse Button
     */
    public void createWarehouse(View view) {
        // Check whether all required fields are filled
        if (isFilled()) {
            // Insert new warehouse to DB
            appDatabase.storeHouseDAO().insertStoreHouse(new StoreHouse(App.ACCOUNT_ID,
                    nameLayout.getEditText().getText().toString(),
                    addressLayout.getEditText().getText().toString(), 1, 1, "", 1));
            Intent intent = new Intent(this, Warehouse.class);
            Toast.makeText(this, getText(R.string.add_warehouse_create_success), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    /**
     * Check if all text fields have been filled
     *
     * @return true if text fields are filled, false if not
     */
    private boolean isFilled() {
        boolean isFilled = true;
        // Check name text field
        if (nameLayout.getEditText().getText().toString().trim().length() == 0) {
            nameLayout.setError("Field can not be empty!");
            isFilled = false;

        } else {
            nameLayout.setError(null);
        }
        // Check address text field
        if (addressLayout.getEditText().getText().toString().trim().length() == 0) {
            addressLayout.requestFocus();
            addressLayout.setError("Field can not be empty!");
            isFilled = false;
        } else {
            addressLayout.setError(null);
        }
        return isFilled;
    }

}