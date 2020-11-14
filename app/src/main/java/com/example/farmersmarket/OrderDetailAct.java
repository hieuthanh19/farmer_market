package com.example.farmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.Orders;
import com.example.farmersmarket.viewadapter.OrderDetailListAdapter;

import java.util.List;

public class OrderDetailAct extends AppCompatActivity {
    public AppDatabase appDatabase;
    public int accountID = 1;

    RecyclerView recyclerView ; 
    OrderDetailListAdapter orderAdapter;
    List<OrderDetail> arrOrderDetail;
    List<Orders> arrOrder;

    TextView txtOrderDtailOrderIDRS;
    TextView txtOrderDtailOrderDateRS;
    TextView txtOrderDtailStatusRS;
    TextView txtOrderDetailStoreName;
    TextView txtOrderDtailProductTotalRS;
    TextView txtOrderDetailShippingTotalRS;
    TextView txtOrderDetailTotalRS;
    ImageView imageViewOrderDetailStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

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
        txtOrderDetailStoreName.setText(appDatabase.ordersDAO().getStoreNameByStoreID(arrOrder.get(0).orderID));
        txtOrderDtailProductTotalRS.setText(getString(R.string.product_price,appDatabase.ordersDAO().getTotalCostOfOrderDetailByOrderID(arrOrder.get(0).orderID)));
        txtOrderDetailShippingTotalRS.setText(getString(R.string.product_price,appDatabase.ordersDAO().getFeeByShippingID(arrOrder.get(0).shippingID)));
        txtOrderDetailTotalRS.setText(getString(R.string.product_price,arrOrder.get(0).total));

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
        txtOrderDetailStoreName = findViewById(R.id.txtOrderDetailStoreName);
        txtOrderDtailProductTotalRS = findViewById(R.id.txtOrderDtailProductTotalRS);
        txtOrderDetailShippingTotalRS = findViewById(R.id.txtOrderDetailShippingTotalRS);
        txtOrderDetailTotalRS = findViewById(R.id.txtOrderDetailTotalRS);
        imageViewOrderDetailStore = findViewById(R.id.imageViewOrderDetailStore);
    }

    public String getStatus(int status){
        String statusString="";
        switch(status) {
            case 1:
                statusString =  "Đang giao";
                break;
            case 2:
                statusString = "Đã giao";
                break;
        }
        return statusString;
    }
}