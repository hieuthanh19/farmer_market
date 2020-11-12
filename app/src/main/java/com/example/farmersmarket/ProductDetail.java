package com.example.farmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Product;

public class ProductDetail extends AppCompatActivity {

    public static String PRODUCT_ID = "productID";

    private Product product;
    private AppDatabase appDatabase;

    TextView productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // get productID from previous activity or fragment
        Intent intent = getIntent();
        int productID = intent.getIntExtra(PRODUCT_ID, -1);
        // check if productID valid
        if (productID >= 0) {
            appDatabase = AppDatabase.getAppDatabase(this);
            product = appDatabase.productDAO().getProduct(productID);

            productName = findViewById(R.id.product_detail_product_name);

            productName.setText(product.name);
        } else {
            Toast.makeText(this, "Error: Can't load product", Toast.LENGTH_SHORT).show();
        }
    }

    public void addToCart(View view) {
    }

    public void back(View view) {
        onBackPressed();
    }

    public void displayCart(View view) {
    }
}