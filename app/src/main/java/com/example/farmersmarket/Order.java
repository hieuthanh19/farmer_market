package com.example.farmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Orders;
import com.example.farmersmarket.viewadapter.OrderListAdapter;

import java.sql.Date;
import java.util.List;

public class Order extends AppCompatActivity {
    public static int ORDER_ID;

    public AppDatabase appDatabase;
    public int accountID=1;
    RecyclerView recyclerView ;
    OrderListAdapter orderListAdapter;
    List<Orders> arrOrder;
    Date dateOrder = null;
    TextView txtEmpty;

    public void findView(){
        txtEmpty = findViewById(R.id.txtOrderEmpty);
        recyclerView = this.findViewById(R.id.rc1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        appDatabase = AppDatabase.getAppDatabase(this);
        arrOrder = appDatabase.ordersDAO().getOrdersOfAccount(accountID);
        findView();

        if (arrOrder.size()!=0){
            txtEmpty.setVisibility(View.GONE);
            builAdapter();
        }else{
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    public void builAdapter(){
        recyclerView.setHasFixedSize(true);
        orderListAdapter = new OrderListAdapter(arrOrder,getApplicationContext());
        recyclerView.setAdapter(orderListAdapter);

        orderListAdapter.setOnItemClickListener(new OrderListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            @Override
            public void onButtonClick(int position) {
                Intent intent = new Intent(getBaseContext(), OrderDetailAct.class);
                intent.putExtra("ORDER_ID", arrOrder.get(position).orderID);
                startActivity(intent);
            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }
}