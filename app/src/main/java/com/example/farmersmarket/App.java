package com.example.farmersmarket;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
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
import com.example.farmersmarket.object.Orders;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class App extends AppCompatActivity {

    // Store current account's ID
    public static int ACCOUNT_ID;
    private AppDatabase appDatabase;
//    public List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getAppDatabase(this);
        // check user log in
        if (isLoggedIn()) {
            setContentView(R.layout.activity_app);
            // Set account ID
            ACCOUNT_ID = appDatabase.currentAccountDAO().getAllCurrentAccounts().get(0).accountID;

            //Set Order ID
            if (appDatabase.ordersDAO().getCurrentOrder()==null){
                String accountAdress = appDatabase.accountDAO().getAccount(ACCOUNT_ID).address;
                appDatabase.ordersDAO().insertOrder(new Orders(1, ACCOUNT_ID, 1, null, null, accountAdress, 0, "No description", 1));
                Order.ORDER_ID = appDatabase.ordersDAO().getCurrentOrder().orderID;
            }else{
                Order.ORDER_ID = appDatabase.ordersDAO().getCurrentOrder().orderID;
            }

            // load default fragment
            loadFragment(new HomeFragment());
            setStatusBarColor(R.id.home_page);
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener());
        } else {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();


//        RecyclerView recyclerViewProductVertical = findViewById(R.id.product_vertical_list);
//        RecyclerView recyclerViewProductHorizontal = findViewById(R.id.product_horizontal_list);
        }
    }

    /**
     * Check if user already logged in by query Current Account Table
     *
     * @return true if user already logged in, false if not
     */
    public boolean isLoggedIn() {
        return appDatabase.currentAccountDAO().getCurrentAccountsCount() == 1;
    }


    /**
     * Set on navigation item selected listener
     *
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
     *
     * @param fragment fragment to display
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.app_fragment_frame, fragment);
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