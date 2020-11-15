package com.example.farmersmarket;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.farmersmarket.fragment.LoginPhoneFragment;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadFragment(new LoginPhoneFragment());

//    AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
//
//        appDatabase.accountTypeDAO().insertAccountType(new AccountType("admin", 1));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            appDatabase.accountDAO().insertAccount(new Account(1, "0348204069", Utils.encryptPassword("admin"),
//                    "Admin", 1, "123/12 ABC Street",
//                    "123@gmail.com", "image url", 1));
//        }
//        appDatabase.storeHouseDAO().insertStoreHouse(new StoreHouse(1, "My Store", "ABC/12 DEF street", 1.2, 1.3,
//                "this is my store", 1));
//        appDatabase.productTypeDAO().insertProductType(new ProductType("Fruit", 1));
//        appDatabase.productTypeDAO().insertProductType(new ProductType("Vegetable", 1));
//        appDatabase.productTypeDAO().insertProductType(new ProductType("Fish & Seafood", 1));
//        appDatabase.productTypeDAO().insertProductType(new ProductType("Meat & Poultry", 1));
//
//        appDatabase.productDAO().insertProduct(new Product(1, 1, "Dragon fruit", 1000, 15000, "Binh Thuan", 12000,
//                "This a a fruit", 1));
//        appDatabase.productDAO().insertProduct(new Product(1, 1, "Durian", 1000, 15000, "Binh Thuan", 12000,
//                "This a a fruit", 1));
//        appDatabase.productDAO().insertProduct(new Product(1, 1, "Banana", 1000, 15000, "Binh Thuan", 12000,
//                "This a a fruit", 1));
//        appDatabase.productDAO().insertProduct(new Product(1, 1, "Coconut", 1000, 15000, "Binh Thuan", 12000,
//                "This a a fruit", 1));
//        appDatabase.productDAO().insertProduct(new Product(1, 1, "papaya", 1000, 15000, "Binh Thuan", 12000,
//                "This a a fruit", 1));
    }


    /**
     * Load fragment into current activity
     *
     * @param fragment fragment to display
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_fragment_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}