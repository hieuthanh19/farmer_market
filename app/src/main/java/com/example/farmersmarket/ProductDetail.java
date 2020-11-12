package com.example.farmersmarket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Account;
import com.example.farmersmarket.object.Product;
import com.example.farmersmarket.object.ProductImage;
import com.example.farmersmarket.object.ProductType;
import com.example.farmersmarket.object.StoreHouse;
import com.example.farmersmarket.viewadapter.ProductHorizontalViewAdapter;
import com.example.farmersmarket.viewadapter.ProductVerticalViewAdapter;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

public class ProductDetail extends AppCompatActivity {

    public static String PRODUCT_ID = "productID";

    private Product product;
    List<ProductImage> productImageList;
    private AppDatabase appDatabase;

    CarouselView carouselView;
    TextView productName;
    TextView productAmount;
    TextView productPrice;
    TextView username;
    TextView warehouseAddress;
    TextView category;
    TextView origin;
    TextView description;
    RecyclerView similarList;
    RecyclerView suggestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // get productID from previous activity or fragment
        Intent intent = getIntent();
        int productID = intent.getIntExtra(PRODUCT_ID, -1);
        // check if productID valid
        if (productID >= 0) {
            // get views
            carouselView = findViewById(R.id.product_detail_carousel);
            productName = findViewById(R.id.product_detail_product_name);
            productAmount = findViewById(R.id.product_detail_amount);
            productPrice = findViewById(R.id.product_detail_price);

            username = findViewById(R.id.product_detail_user_name);
            warehouseAddress = findViewById(R.id.product_detail_warehouse_address);

            category = findViewById(R.id.product_detail_category);
            origin = findViewById(R.id.product_detail_origin);
            description = findViewById(R.id.product_detail_description);

            similarList = findViewById(R.id.product_detail_similar_list);
            suggestList = findViewById(R.id.product_detail_suggestion_list);

            populateDataIntoViews(productID);
        } else {
            Toast.makeText(this, "Error: Can't load product", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateDataIntoViews(int productID) {
        // get data from DB
        appDatabase = AppDatabase.getAppDatabase(this);
        product = appDatabase.productDAO().getProduct(productID);
        StoreHouse storeHouse = appDatabase.storeHouseDAO().getStoreHouse(product.storeHouseID);
        Account account = appDatabase.accountDAO().getAccount(storeHouse.accountID);
        ProductType type = appDatabase.productTypeDAO().getProductType(product.productTypeID);

        // get image list from DB and set to carousel
        productImageList = appDatabase.productImageDAO().getProductImageByProductID(productID);
        if (productImageList.size() != 0) {
            carouselView.setPageCount(productImageList.size());
            carouselView.setImageListener(imageListener());
        } else {
            carouselView.setPageCount(1);
            carouselView.setImageListener(emptyImageListener());
        }

        productName.setText(product.name);
        productAmount.setText(getString(R.string.product_detail_amount, product.amount));
        productPrice.setText(getString(R.string.product_price, product.price));

        username.setText(account.name);
        warehouseAddress.setText(storeHouse.address);

        category.setText(type.productTypeName);
        origin.setText(product.origin);
        description.setText(product.description);

        // load list data
        List<Product> similarProductList = appDatabase.productDAO().getActiveProductByCategory(product.productTypeID);
        List<Product> suggestionProductList = appDatabase.productDAO().getAllActiveProduct();

        ProductHorizontalViewAdapter similarProductViewAdapter = new ProductHorizontalViewAdapter(similarProductList);
        ProductVerticalViewAdapter suggestionProductVerticalViewAdapter =
                new ProductVerticalViewAdapter(suggestionProductList);

        similarList.setAdapter(similarProductViewAdapter);
        suggestList.setAdapter(suggestionProductVerticalViewAdapter);
    }

    private ImageListener imageListener() {
        return new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(getApplicationContext()).load(Uri.parse(productImageList.get(position).URL)).centerCrop().into(imageView);
            }
        };
    }

    private ImageListener emptyImageListener() {
        return new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(getApplicationContext()).load(R.drawable.empty).centerCrop().into(imageView);
            }
        };
    }

    public void addToCart(View view) {
    }

    public void back(View view) {
        onBackPressed();
    }

    public void displayCart(View view) {
    }
}