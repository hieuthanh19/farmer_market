package com.example.farmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.StoreHouse;
import com.example.farmersmarket.viewadapter.WarehouseViewAdapter;

import java.util.List;

public class Warehouse extends AppCompatActivity {

    public static String WAREHOUSE_ID = "warehouseID";

    TextView warehouseEmpty;
    RecyclerView recyclerView;

    AppDatabase appDatabase;
    List<StoreHouse> storeHouseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse);

        warehouseEmpty = findViewById(R.id.warehouse_detail_product_empty);
        recyclerView = findViewById(R.id.list_warehouse);

        appDatabase = AppDatabase.getAppDatabase(this);
        storeHouseList = appDatabase.storeHouseDAO().getStoreHouseByAccountID(App.ACCOUNT_ID);
        // check number of warehouse
        if (storeHouseList.size() > 0) {
            // hide empty message and load data
            warehouseEmpty.setVisibility(View.GONE);
            populateData();
        } else {
            // hide warehouse list
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    private void populateData() {
        WarehouseViewAdapter warehouseViewADapter = new WarehouseViewAdapter(storeHouseList);
        recyclerView.setAdapter(warehouseViewADapter);

    }


    public void back(View view) {
        onBackPressed();
    }

    public void navigateToAddWarehouse(View view) {
        Intent intent = new Intent(this, AddWarehouse.class);
        startActivity(intent);
    }
}