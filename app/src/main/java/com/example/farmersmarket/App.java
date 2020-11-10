package com.example.farmersmarket;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.fragment.AccountFragment;
import com.example.farmersmarket.fragment.CategoryFragment;
import com.example.farmersmarket.fragment.HomeFragment;
import com.example.farmersmarket.fragment.NotificationFragment;
import com.example.farmersmarket.fragment.OrderFragment;
import com.example.farmersmarket.fragment.PageNotFoundFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class App extends AppCompatActivity {

    // Store current account's ID
    public static int ACCOUNT_ID;
    //    public AppDatabase appDatabase;
//    public List<Product> products;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        // load default fragment
        loadFragment(new HomeFragment());
        setStatusBarColor(R.id.home_page);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener());

//        RecyclerView recyclerViewProductVertical = findViewById(R.id.product_vertical_list);
//        RecyclerView recyclerViewProductHorizontal = findViewById(R.id.product_horizontal_list);

        AppDatabase appDatabase = AppDatabase.getAppDatabase(this);

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
    }


    /**
     * Set on navigation item selected listener
     * @return true or false
     */
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setStatusBarColor(item.getItemId());
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

    private void setStatusBarColor(int itemID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (itemID == R.id.home_page) {
                window.setStatusBarColor(getColor(R.color.green_600));
            } else {
                window.setStatusBarColor(getColor(R.color.white));
            }

        }

    }

}