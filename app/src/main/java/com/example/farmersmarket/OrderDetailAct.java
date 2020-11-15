package com.example.farmersmarket;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Account;
import com.example.farmersmarket.object.ConvertDate;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.Orders;
import com.example.farmersmarket.object.ProductImage;
import com.example.farmersmarket.viewadapter.OrderDetailListAdapter;

import java.sql.Date;
import java.util.List;

public class OrderDetailAct extends AppCompatActivity {
    public AppDatabase appDatabase;

    RecyclerView recyclerView ; 
    OrderDetailListAdapter orderAdapter;
    List<OrderDetail> arrOrderDetail;
    List<Orders> arrOrder;

    TextView txtOrderDtailOrderIDRS;
    TextView txtOrderDtailOrderDateRS;
    TextView txtOrderDtailStatusRS;
    TextView txtOrderDtailProductTotalRS;
    TextView txtOrderDetailShippingTotalRS;
    TextView txtOrderDetailTotalRS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_act);

        //Get order Id from ORDER activity
        Intent mIntent = getIntent();
        int orderID = mIntent.getIntExtra("ORDER_ID",1);

        //Connect to db
        appDatabase = AppDatabase.getAppDatabase(this);
        arrOrderDetail = appDatabase.orderDetailDAO().getAllOrderDetailByOrderID(orderID);
        arrOrder = appDatabase.ordersDAO().getOrder(orderID);
        finView();

        //Set text
        txtOrderDtailOrderIDRS.setText(Integer.toString(arrOrder.get(0).orderID));
        txtOrderDtailStatusRS.setText(getStatus(arrOrder.get(0).status));
        txtOrderDtailOrderDateRS.setText((arrOrder.get(0).orderedDate).toString());
        System.out.println(ConvertDate.fromDate(arrOrder.get(0).orderedDate));
        txtOrderDtailProductTotalRS.setText(getString(R.string.cart_price_change,appDatabase.ordersDAO().getTotalCostOfOrderDetailByOrderID(arrOrder.get(0).orderID)));
        txtOrderDetailShippingTotalRS.setText(getString(R.string.product_price,appDatabase.ordersDAO().getFeeByShippingID(arrOrder.get(0).shippingID)));
        txtOrderDetailTotalRS.setText(getString(R.string.cart_price_change,arrOrder.get(0).total));

        //Create recycler view
        builAdapter();
    }

    //Create adapter for recycler view
    public void builAdapter(){
        recyclerView = this.findViewById(R.id.rcOrderDetail);
        recyclerView.setHasFixedSize(true);
        orderAdapter = new OrderDetailListAdapter(arrOrderDetail,getApplicationContext());
        recyclerView.setAdapter(orderAdapter);
    }

    public void finView(){
        txtOrderDtailOrderIDRS = findViewById(R.id.txtOrderDtailOrderIDRS);
        txtOrderDtailOrderDateRS = findViewById(R.id.txtOrderDtailOrderDateRS);
        txtOrderDtailStatusRS = findViewById(R.id.txtOrderDtailStatusRS);
        txtOrderDtailProductTotalRS = findViewById(R.id.txtOrderDtailProductTotalRS);
        txtOrderDetailShippingTotalRS = findViewById(R.id.txtOrderDetailShippingTotalRS);
        txtOrderDetailTotalRS = findViewById(R.id.txtOrderDetailTotalRS);
    }

    public String getStatus(int status){
        String statusString="";
        switch(status) {
            case 2:
                statusString =  "Chờ xác nhận";
                break;
            case 3:
                statusString = "Đang giao giao";
                break;
            case 4:
                statusString = "Đã giao";
                break;
        }
        return statusString;
    }

    public void back(View view) {
        onBackPressed();
    }
}