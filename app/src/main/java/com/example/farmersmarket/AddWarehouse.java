package com.example.farmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.StoreHouse;
import com.google.android.material.textfield.TextInputLayout;

public class AddWarehouse extends AppCompatActivity {

    AppDatabase appDatabase;
    private static int WAREHOUSE_ID;
    private static int MODE;
    private static StoreHouse storeHouse;

    TextInputLayout nameLayout;
    TextInputLayout addressLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);

        // Get mode from intent
        Intent intent = getIntent();
        MODE = intent.getIntExtra(Warehouse.WAREHOUSE_MODE, -1);
        WAREHOUSE_ID = intent.getIntExtra(Warehouse.WAREHOUSE_ID, -1);
        // Get views from layout
        TextView activityTitle = findViewById(R.id.add_warehouse_title);
        nameLayout = findViewById(R.id.add_warehouse_name_layout);
        addressLayout = findViewById(R.id.add_warehouse_address_layout);
        // Initialize data
        appDatabase = AppDatabase.getAppDatabase(this);
        // check mode
        if (MODE == Warehouse.MODE_EDIT) {
            activityTitle.setText(R.string.add_warehouse_title_mode_edit);
            if (WAREHOUSE_ID != -1) {
                storeHouse = appDatabase.storeHouseDAO().getActiveStoreHouse(WAREHOUSE_ID);
                loadData();
            } else {
                Toast.makeText(this, "Error: Cannot load warehouse", Toast.LENGTH_SHORT).show();
            }
        } else {
            activityTitle.setText(R.string.add_warehouse_title_mode_add);
        }
    }

    public void back(View view) {
        onBackPressed();
    }

    private void loadData() {
        nameLayout.getEditText().setText(storeHouse.storeName);
        addressLayout.getEditText().setText(storeHouse.address);
    }

    /**
     * onClick handler. Create new warehouse or Update warehouse
     *
     * @param view Done Button
     */
    public void done(View view) {
        // Check whether all required fields are filled
        if (isFilled()) {
            if (MODE == Warehouse.MODE_ADD) {
                // Insert new warehouse to DB
                appDatabase.storeHouseDAO().insertStoreHouse(new StoreHouse(App.ACCOUNT_ID,
                        nameLayout.getEditText().getText().toString(),
                        addressLayout.getEditText().getText().toString(), 1, 1, "", 1));
                Intent intent = new Intent(this, Warehouse.class);
                Toast.makeText(this, getText(R.string.add_warehouse_create_success), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                // remove activity from backstack
                finish();
            } else {
                storeHouse.storeName = nameLayout.getEditText().getText().toString();
                storeHouse.address = addressLayout.getEditText().getText().toString();
                appDatabase.storeHouseDAO().updateStoreHouse(storeHouse);
                // Return to Warehouse
                Intent intent = new Intent(this, Warehouse.class);
                Toast.makeText(this, getText(R.string.add_warehouse_edit_success), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                // remove activity from backstack
                finish();
            }
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