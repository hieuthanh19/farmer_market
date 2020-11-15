package com.example.farmersmarket;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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
    TextView productOriginalPrice;
    TextView productSale;
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
            productSale = findViewById(R.id.sale_tag);
            productAmount = findViewById(R.id.product_detail_amount);
            productOriginalPrice = findViewById(R.id.product_detail_original_price);
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
                    arrCartByProductID = appDatabase.orderDetailDAO().getOrderDetailByOrderIDAndProductID(productID,
                            Order.ORDER_ID);
                    if (!arrCartByProductID.isEmpty()) {
                        int quantity = arrCartByProductID.get(0).quantity + 1;
                        appDatabase.orderDetailDAO().updateQuantityAndPrice(productID, Order.ORDER_ID, quantity,
                                quantity * productPrice);
                    } else {
                        appDatabase.orderDetailDAO().insertOrderDetail(new OrderDetail(Order.ORDER_ID, productID, 1,
                                productPrice, "", 1));
                    }
                    Toast.makeText(getBaseContext(), "Thêm sản phẩm vào giỏ hàng thành công",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Error: Can't load product", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Populate data from DB into views
     *
     * @param productID product ID
     */
    private void populateDataIntoViews(int productID) {
        // get data from DB
        appDatabase = AppDatabase.getAppDatabase(this);
        product = appDatabase.productDAO().getProduct(productID);
        StoreHouse storeHouse = appDatabase.storeHouseDAO().getActiveStoreHouse(product.storeHouseID);
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

        // If product is on sale
        if (product.price > product.currentPrice) {
            // display sale tag and find sale percentage
            productSale.setVisibility(View.VISIBLE);
            int salePercentage = (int) Math.ceil(((product.price - product.currentPrice) / product.price) * 100);
            productSale.setText(getString(R.string.sale_tag, salePercentage));
            // Set price color to text_sale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                productPrice.setTextColor(getColor(R.color.text_sale));
            }
        } else {
            // Hide sale tag & change price color to normal
            productSale.setVisibility(View.INVISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                productPrice.setTextColor(getColor(R.color.green_600));
            }
        }

        productName.setText(product.name);
        productAmount.setText(getString(R.string.product_detail_amount, product.amount));
        if (product.price != product.currentPrice) {
            productOriginalPrice.setVisibility(View.VISIBLE);
            productOriginalPrice.setText(Html.fromHtml(getString(R.string.product_price_strike_through,
                    product.price)));
        } else {
            productOriginalPrice.setVisibility(View.GONE);
        }
        productPrice.setText(getString(R.string.product_price, product.currentPrice));

        username.setText(account.name);
        warehouseAddress.setText(storeHouse.address);

        category.setText(type.productTypeName);
        origin.setText(product.origin);
        description.setText(product.description);

        // load list data
        List<Product> similarProductList =
                appDatabase.productDAO().getActiveProductByCategoryExcludeThis(product.productID,
                        product.productTypeID);
        List<Product> suggestionProductList = appDatabase.productDAO().getAllActiveProduct();

        ProductHorizontalViewAdapter similarProductViewAdapter = new ProductHorizontalViewAdapter(similarProductList);
        ProductVerticalViewAdapter suggestionProductVerticalViewAdapter =
                new ProductVerticalViewAdapter(suggestionProductList);

        similarList.setAdapter(similarProductViewAdapter);
        suggestList.setAdapter(suggestionProductVerticalViewAdapter);

        //Set account image
        // if account have image -> load first image
        if (!account.image.equals("")) {
            Log.d("PRODUCT", "account has image");
            Log.d("PRODUCT", "URL: " + account.image);
            Glide.with(getApplicationContext()).load(Uri.parse(account.image)).centerCrop().into(product_detail_user_avatar);
        }
        // if not -> load empty image
        else {
            Glide.with(getApplicationContext()).load(R.drawable.empty).centerCrop().into(product_detail_user_avatar);
        }

    }

    /**
     * Load product images to carousel
     *
     * @return a {@link ImageListener}
     */
    private ImageListener imageListener() {
        return new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                if (productImageList.get(position).URL.startsWith("@drawable")) {
                    int resource =
                            getApplicationContext().getResources().getIdentifier(productImageList.get(position).URL,
                                    "drawable", getApplicationContext().getPackageName());
                    Glide.with(getApplicationContext()).load(resource).centerCrop().into(imageView);
                } else
                    Glide.with(getApplicationContext()).load(Uri.parse(productImageList.get(position).URL)).centerCrop().into(imageView);
            }
        };
    }

    /**
     * Load empty image to carousel
     *
     * @return a {@link ImageListener}
     */
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
        Intent intent = new Intent(this, Cart.class);
        startActivity(intent);
    }

}