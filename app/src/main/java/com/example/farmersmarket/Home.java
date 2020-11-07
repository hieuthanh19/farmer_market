package com.example.farmersmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.viewadapter.ProductHorizontalViewAdapter;
import com.example.farmersmarket.viewadapter.ProductVerticalViewAdapter;
import com.example.farmersmarket.object.Product;

import java.util.List;

public class Home extends AppCompatActivity {

    public AppDatabase appDatabase;
    public List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerViewProductVertical = findViewById(R.id.product_vertical_list);
        RecyclerView recyclerViewProductHorizontal = findViewById(R.id.product_horizontal_list);

        appDatabase = AppDatabase.getAppDatabase(this);
//        appDatabase.accountTypeDAO().insertAccountType(new AccountType("admin", 1));
//        appDatabase.accountDAO().insertAccount(new Account(1, "0989123456", "123", "Admin", 1, "123/12 ABC Street",
//                "123@gmail.com", "image url", 1));
//        appDatabase.storeHouseDAO().insertStoreHouse(new StoreHouse(1, "My Store", "ABC/12 DEF street", 1.2, 1.3,
//                "this is my store", 1));
//        appDatabase.productTypeDAO().insertProductType(new ProductType("fruit", 1));
//
//        appDatabase.productDAO().insertProduct(new Product(1, 1, "Dragon fruit", 1000, 10000, "Binh Thuan", 12000,
//                "This a a fruit", 1));
//        appDatabase.productDAO().insertProduct(new Product(1, 1, "Dragon fruit", 1000, 10000, "Binh Thuan", 12000,
//                "This a a fruit", 1));
//        appDatabase.productDAO().insertProduct(new Product(1, 1, "Dragon fruit", 1000, 10000, "Binh Thuan", 12000,
//                "This a a fruit", 1));
//        appDatabase.productDAO().insertProduct(new Product(1, 1, "Dragon fruit", 1000, 10000, "Binh Thuan", 12000,
//                "This a a fruit", 1));
//        appDatabase.productDAO().insertProduct(new Product(1, 1, "Dragon fruit", 1000, 10000, "Binh Thuan", 12000,
//                "This a a fruit", 1));
        products = appDatabase.productDAO().getAllProduct();

        ProductHorizontalViewAdapter productHorizontalViewAdapter = new ProductHorizontalViewAdapter(products);
        recyclerViewProductHorizontal.setAdapter(productHorizontalViewAdapter);
        ProductVerticalViewAdapter productVerticalViewAdapter = new ProductVerticalViewAdapter(products);
        recyclerViewProductVertical.setAdapter(productVerticalViewAdapter);

    }

}