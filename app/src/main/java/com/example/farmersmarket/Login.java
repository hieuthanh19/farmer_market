package com.example.farmersmarket;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Account;
import com.example.farmersmarket.object.CurrentAccount;
import com.example.farmersmarket.object.Utils;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    private AppDatabase appDatabase;
    private TextInputLayout inputPhone;
    private TextInputLayout inputPassword;
    private TextView loginError;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appDatabase = AppDatabase.getAppDatabase(this);
        if (isLoggedIn()) {
            // Set account ID
            App.ACCOUNT_ID = appDatabase.currentAccountDAO().getAllCurrentAccounts().get(0).accountID;
            // open App activity
            Intent intent = new Intent(this, App.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_login);
            inputPhone = findViewById(R.id.input_phone);
            inputPassword = findViewById(R.id.input_password);
            loginError = findViewById(R.id.login_error);

        }

//        appDatabase.accountTypeDAO().insertAccountType(new AccountType("admin", 1));
//        appDatabase.accountDAO().insertAccount(new Account(1, "0348204069", Utils.encryptPassword("admin"),
//                "Admin", 1, "123/12 ABC Street",
//                "123@gmail.com", "image url", 1));
        //appDatabase.storeHouseDAO().insertStoreHouse(new StoreHouse(1, "My Store", "ABC/12 DEF street", 1.2, 1.3,
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
     * Check if user already logged in by query Current Account Table
     *
     * @return true if user already logged in, false if not
     */
    public boolean isLoggedIn() {
        return appDatabase.currentAccountDAO().getCurrentAccountsCount() == 1;
    }

    /**
     * Handle log in
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void login(View view) {
        // if both fields are filled
        if (isFilled()) {
            // get account info using input phone number and password
            Account account = appDatabase.accountDAO().login(inputPhone.getEditText().getText().toString(),
                    Utils.encryptPassword(inputPassword.getEditText().getText().toString()));
            // check if account exist
            if (account != null) {
                // account exist
                loginError.setVisibility(View.INVISIBLE);
                // set account ID
                App.ACCOUNT_ID = account.accountID;
                // remember user
                appDatabase.currentAccountDAO().insertCurrentAccount(new CurrentAccount(account.accountID));
                // open App activity
                Intent intent = new Intent(this, App.class);
                startActivity(intent);
            } else {
                // account not exist -> display error message
                loginError.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * Check whether phone number of password is filled
     *
     * @return true if both are filled, false if not
     */
    private boolean isFilled() {
        boolean isFilled = true;
        if (inputPhone.getEditText().getText().toString().trim().length() == 0) {
            inputPhone.setError(getString(R.string.login_error_phone));
            isFilled = false;
        } else {
            inputPhone.setError(null);
        }

        if (inputPassword.getEditText().getText().toString().trim().length() == 0) {
            inputPassword.setError(getString(R.string.login_error_password));
            isFilled = false;
        } else {
            inputPhone.setError(null);
        }

        return isFilled;

    }
}