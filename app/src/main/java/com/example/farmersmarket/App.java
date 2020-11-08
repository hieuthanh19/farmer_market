package com.example.farmersmarket;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.fragment.AccountFragment;
import com.example.farmersmarket.fragment.CategoryFragment;
import com.example.farmersmarket.fragment.HomeFragment;
import com.example.farmersmarket.fragment.NotificationFragment;
import com.example.farmersmarket.fragment.OrderFragment;
import com.example.farmersmarket.fragment.PageNotFoundFragment;
import com.example.farmersmarket.object.Account;
import com.example.farmersmarket.object.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class App extends AppCompatActivity {

    private int ACCOUNT_ID;
    //    public AppDatabase appDatabase;
//    public List<Product> products;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Intent intent = getIntent();
        ACCOUNT_ID = intent.getIntExtra(Login.ACCOUNT_ID, -1);

        // load default fragment
        loadFragment(new HomeFragment());
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener());

//        RecyclerView recyclerViewProductVertical = findViewById(R.id.product_vertical_list);
//        RecyclerView recyclerViewProductHorizontal = findViewById(R.id.product_horizontal_list);

//        AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
//        appDatabase.accountTypeDAO().insertAccountType(new AccountType("admin", 1));
//        appDatabase.accountDAO().insertAccount(new Account(1, "0348204069", Utils.encryptPassword("admin"), "Admin", 1, "123/12 ABC Street",
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
//        products = appDatabase.productDAO().getAllProduct();
//
//        ProductHorizontalViewAdapter productHorizontalViewAdapter = new ProductHorizontalViewAdapter(products);
//        recyclerViewProductHorizontal.setAdapter(productHorizontalViewAdapter);
//        ProductVerticalViewAdapter productVerticalViewAdapter = new ProductVerticalViewAdapter(products);
//        recyclerViewProductVertical.setAdapter(productVerticalViewAdapter);

    }

    /**
     * Set on navigation item selected listener
     * @return true or false
     */
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                        loadFragment(new HomeFragment());
                        return true;
                    case R.id.category_page:
                        loadFragment(new CategoryFragment());
                        return true;
                    case R.id.order_page:
                        loadFragment(new OrderFragment());
                        return true;
                    case R.id.notification_page:
                        loadFragment(new NotificationFragment());
                        return true;
                    case R.id.account_page:
                        loadFragment(new AccountFragment());
                        return true;
                    default:
                        loadFragment(new PageNotFoundFragment());
                        return false;
                }
            }
        };
    }

    /**
     * Load fragment into current activity
     * @param fragment fragment to display
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}