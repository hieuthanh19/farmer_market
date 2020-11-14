package com.example.farmersmarket;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Account;
import com.example.farmersmarket.object.OrderDetail;
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
    List<OrderDetail> arrCartByProductID;
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
    Button product_detail_add_to_cart_btn;
    ImageView product_detail_user_avatar;

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
            product_detail_user_avatar = findViewById(R.id.product_detail_user_avatar);

            username = findViewById(R.id.product_detail_user_name);
            warehouseAddress = findViewById(R.id.product_detail_warehouse_address);

            category = findViewById(R.id.product_detail_category);
            origin = findViewById(R.id.product_detail_origin);
            description = findViewById(R.id.product_detail_description);

            similarList = findViewById(R.id.product_detail_similar_list);
            suggestList = findViewById(R.id.product_detail_suggestion_list);

            populateDataIntoViews(productID);

            //Button add to cart
            product_detail_add_to_cart_btn = findViewById(R.id.product_detail_add_to_cart_btn);
            double productPrice = appDatabase.productDAO().getProduct(productID).price;
            product_detail_add_to_cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    arrCartByProductID = appDatabase.orderDetailDAO().getOrderDetailByOrderIDAndProductID(productID, Order.ORDER_ID);
                    if (!arrCartByProductID.isEmpty()) {
                        int quantity = arrCartByProductID.get(0).quantity + 1;
                        appDatabase.orderDetailDAO().updateQuantityAndPrice(productID, Order.ORDER_ID, quantity, quantity * productPrice);
                    } else {
                        appDatabase.orderDetailDAO().insertOrderDetail(new OrderDetail(Order.ORDER_ID, productID, 1, productPrice, "", 1));
                    }
                    Toast.makeText(getBaseContext(),"Thêm sản phẩm vào giỏ hàng thành công",
                            Toast.LENGTH_SHORT).show();
                }
            });
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

        //Set image
        // if product have image -> load first image
        if (account.image != "" || account.image != null)
            Glide.with(getApplicationContext()).load(Uri.parse(account.image)).centerCrop().into(product_detail_user_avatar);
            // if not -> load empty image
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Glide.with(getApplicationContext()).load(R.drawable.empty).centerCrop().into(product_detail_user_avatar);
        }

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

    public void back(View view) {
        onBackPressed();
    }

    public void displayCart(View view) {
    }
}