package com.example.farmersmarket;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Product;
import com.example.farmersmarket.object.StoreHouse;
import com.example.farmersmarket.viewadapter.ProductVerticalManageViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class WarehouseDetail extends AppCompatActivity {

    public static int WAREHOUSE_ID = -1;
    private static final int IMAGE_VIEW_MODE_SEARCH = 1;
    private static final int IMAGE_VIEW_MODE_ADD = 2;
    public static String PRODUCT_ID = "productID";
    public static String PRODUCT_MODE = "productMode";
    public static int MODE_ADD = 1;
    public static int MODE_EDIT = 2;

    ImageView search;
    TextView title;
    TextView productEmptyMsg;
    ImageView addProduct;
    RecyclerView productListView;
    ConstraintLayout productLayout;

    List<Product> productList = new ArrayList<>();
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_detail);

        appDatabase = AppDatabase.getAppDatabase(this);

        search = findViewById(R.id.add_product_add);
        title = findViewById(R.id.add_product_title);
        productEmptyMsg = findViewById(R.id.warehouse_detail_product_empty);
        productListView = findViewById(R.id.home_vertical_list_suggestion);
        addProduct = findViewById(R.id.warehouse_detail_add_product);
        productLayout = findViewById(R.id.warehouse_detail_product_list);

        Intent intent = getIntent();
        loadWarehouseDetail(intent.getIntExtra(Warehouse.WAREHOUSE_ID, -1));

    }

    /**
     * Load warehouse detail
     *
     * @param warehouseID warehouseID
     */
    private void loadWarehouseDetail(int warehouseID) {
        // check if warehouse ID valid
        if (warehouseID >= 0) {
            // Get warehouse info and products of that warehouse
            WAREHOUSE_ID = warehouseID;
            StoreHouse storeHouse = appDatabase.storeHouseDAO().getActiveStoreHouse(warehouseID);
            productList = appDatabase.productDAO().getActiveProductByStoreHouseDesc(storeHouse.storeHouseID);

            // Update activity title
            title.setText(storeHouse.storeName);
            // If warehouse has products
            if (productList.size() > 0) {
                productEmptyMsg.setVisibility(View.GONE);
                ProductVerticalManageViewAdapter productVerticalManageViewAdapter =
                        new ProductVerticalManageViewAdapter(productList);
                productListView.setAdapter(productVerticalManageViewAdapter);
                changeModeImageView(IMAGE_VIEW_MODE_SEARCH);
                addProduct.setOnClickListener(addProductOnClickListener());
            } else {
                productLayout.setVisibility(View.GONE);
                changeModeImageView(IMAGE_VIEW_MODE_ADD);
            }
        } else {
            Toast.makeText(this, "Error: Can't load warehouse", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Update image view base on mode
     *
     * @param mode image view mode
     */
    private void changeModeImageView(int mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mode == IMAGE_VIEW_MODE_SEARCH) {
                // change image view icon to search icon and color to black
                search.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_search_24));
                search.setColorFilter(ContextCompat.getColor(this, R.color.black));
                search.setOnClickListener(searchProductOnClickListener());
            }
            if (mode == IMAGE_VIEW_MODE_ADD) {
                // change image view icon to add icon and color to green
                search.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_round_add_24));
                search.setColorFilter(ContextCompat.getColor(this, R.color.green_600));
                search.setOnClickListener(addProductOnClickListener());
            }
        }

    }

    private View.OnClickListener addProductOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddProduct.class);
                intent.putExtra(WarehouseDetail.PRODUCT_MODE, WarehouseDetail.MODE_ADD);
                startActivity(intent);
                finish();
            }
        };
    }

    private View.OnClickListener searchProductOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    public void back(View view) {
//        Intent intent = new Intent(view.getContext(), Warehouse.class);
//        startActivity(intent);
//        finish();
        onBackPressed();
    }
}