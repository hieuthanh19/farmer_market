package com.example.farmersmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.farmersmarket.fragment.OrderListAdapter;

public class Order extends AppCompatActivity {

    RecyclerView rc ;
    RecyclerView.Adapter rca ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        rc = findViewById(R.id.rc1);

        rca = new OrderListAdapter();

        rc.setAdapter(rca);
    }
}