package com.example.farmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.StoreHouse;
import com.example.farmersmarket.viewadapter.WarehouseViewAdapter;

import java.util.List;

public class Warehouse extends AppCompatActivity {

    public static int REQUEST_CODE = 1;
    public static String WAREHOUSE_ID = "warehouseID";
    public static String WAREHOUSE_MODE = "warehouseMode";
    public static int MODE_ADD = 1;
    public static int MODE_EDIT = 2;

    TextView warehouseEmpty;
    public static RecyclerView recyclerView;

    AppDatabase appDatabase;
    List<StoreHouse> storeHouseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse);

        warehouseEmpty = findViewById(R.id.warehouse_detail_product_empty);
        recyclerView = findViewById(R.id.list_warehouse);

        appDatabase = AppDatabase.getAppDatabase(this);
        storeHouseList = appDatabase.storeHouseDAO().getActiveStoreHousesByAccountID(App.ACCOUNT_ID);
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

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            storeHouseList = appDatabase.storeHouseDAO().getActiveStoreHousesByAccountID(App.ACCOUNT_ID);
            populateData();
        }
    }

    private void populateData() {
        WarehouseViewAdapter warehouseViewADapter = new WarehouseViewAdapter(storeHouseList, this);
        recyclerView.setAdapter(warehouseViewADapter);
    }


    public void back(View view) {
        onBackPressed();
    }

    public void navigateToAddWarehouse(View view) {
        Intent intent = new Intent(this, AddWarehouse.class);
        intent.putExtra(Warehouse.WAREHOUSE_MODE, Warehouse.MODE_ADD);
        startActivityForResult(intent, REQUEST_CODE);
    }
}