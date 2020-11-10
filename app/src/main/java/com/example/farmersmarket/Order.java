package com.example.farmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.viewadapter.OrderListAdapter;
import com.example.farmersmarket.object.Orders;

import java.sql.Date;
import java.util.ArrayList;

public class Order extends AppCompatActivity {

    RecyclerView recyclerView ;
    OrderListAdapter orderListAdapter;
    ArrayList<Orders> arrOrder;
    Date dateOrder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        builAdapter();
    }

    public void builAdapter(){
        arrOrder = new ArrayList<Orders>();
        arrOrder.add(new Orders( 1, 1, 1, dateOrder, dateOrder, "20 tran hung dao", 1200, "This is description", 1));
        arrOrder.add(new Orders(2, 2, 2, dateOrder, dateOrder, "aaaaadao", 120210, "This is description", 1));
        arrOrder.add(new Orders(3, 3, 3, dateOrder, dateOrder, "nvncbnng dao", 203210, "This is description", 1));


        recyclerView = this.findViewById(R.id.rc1);
        recyclerView.setHasFixedSize(true);
        orderListAdapter = new OrderListAdapter(arrOrder,null);
        recyclerView.setAdapter(orderListAdapter);

        orderListAdapter.setOnItemClickListener(new OrderListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onButtonClick(int position) {

            }

        });
    }
}