package com.example.farmersmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.farmersmarket.fragment.FragmentInfomationCheckout;

public class Cart extends AppCompatActivity {

    Button btnCheckOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        btnCheckOut = findViewById(R.id.btnCheckOut);

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentInfomationCheckout fragmentInfomationCheckout = new FragmentInfomationCheckout();
                fragmentInfomationCheckout.show(getSupportFragmentManager(),
                        "ModalBottomSheet");

            }
        });
    }
}