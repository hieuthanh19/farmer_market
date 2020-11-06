package com.example.farmersmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.farmersmarket.fragment.OrderListAdapter;
import com.example.farmersmarket.object.Orders;
import com.example.farmersmarket.object.ProductType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Order extends AppCompatActivity {

    RecyclerView recyclerView ;
    OrderListAdapter orderListAdapter;
    ArrayList<Orders> arrOrder;
    Date c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Create data
        arrOrder = new ArrayList<Orders>();
        arrOrder.add(new Orders( 1, 1, 1, c, c, "20 tran hung dao", 1200, "This is description", 1));
        arrOrder.add(new Orders(2, 2, 2, c, c, "aaaaadao", 120210, "This is description", 1));
        arrOrder.add(new Orders(3, 3, 3, c, c, "nvncbnng dao", 203210, "This is description", 1));

        recyclerView = findViewById(R.id.rc1);
        orderListAdapter = new OrderListAdapter(arrOrder,null);
        recyclerView.setAdapter(orderListAdapter);
    }
}