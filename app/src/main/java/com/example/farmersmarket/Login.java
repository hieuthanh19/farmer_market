package com.example.farmersmarket;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Account;
import com.example.farmersmarket.object.Utils;

public class Login extends AppCompatActivity {

    public static final String ACCOUNT_ID = "account id";

    private AppDatabase appDatabase;
    private EditText inputPhone;
    private EditText inputPassword;
    private TextView loginError;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputPhone = findViewById(R.id.input_phone);
        inputPassword = findViewById(R.id.input_password);
        loginError = findViewById(R.id.login_error);

//        appDatabase.accountDAO().insertAccount(new Account(1, "0348204069", Utils.encryptPassword("admin"),
//        "Admin", 1, "123/12 ABC Street",
//                "123@gmail.com", "image url", 1));
    }

    /**
     * Handle log in
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void login(View view) {
        // get account info using input phone number and password
        appDatabase = AppDatabase.getAppDatabase(this);
        Account account = appDatabase.accountDAO().login(inputPhone.getText().toString(),
                Utils.encryptPassword(inputPassword.getText().toString()));
        // check if account exist
        if (account != null) {
            // account exist
            loginError.setVisibility(View.INVISIBLE);
            // set account ID
            App.ACCOUNT_ID = account.accountID;
            // open App activity
            Intent intent = new Intent(this, App.class);
            startActivity(intent);
        } else {
            // account not exist -> display error message
            loginError.setVisibility(View.VISIBLE);
        }
    }
}