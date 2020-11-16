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