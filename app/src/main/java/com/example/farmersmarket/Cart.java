package com.example.farmersmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.farmersmarket.fragment.InfomationCheckoutFragment;
import com.example.farmersmarket.object.Orders;
import com.example.farmersmarket.viewadapter.CarAdapter;
import com.example.farmersmarket.viewadapter.OrderListAdapter;

import java.sql.Date;
import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView ;
    CarAdapter carAdapter;
    ArrayList<Orders> arrCart;
    Date c = null;
    Button btnCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        btnCheckOut = findViewById(R.id.btnCheckOut);

        builAdapter();
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfomationCheckoutFragment infomationCheckoutFragment = new InfomationCheckoutFragment();
                infomationCheckoutFragment.show(getSupportFragmentManager(),
                        "ModalBottomSheet");

            }
        });
    }

    public void removeItem(int position) {
        arrCart.remove(position);
        carAdapter.notifyItemRemoved(position);
    }

    public void builAdapter(){
        arrCart = new ArrayList<Orders>();
        arrCart.add(new Orders( 1, 1, 1, c, c, "20 tran hung dao", 1200, "This is description", 1));
        arrCart.add(new Orders(2, 2, 2, c, c, "aaaaadao", 120210, "This is description", 1));
        arrCart.add(new Orders(3, 3, 3, c, c, "nvncbnng dao", 203210, "This is description", 1));

        recyclerView = findViewById(R.id.rcCart);
        carAdapter = new CarAdapter(arrCart,null);
        recyclerView.setAdapter(carAdapter);

        carAdapter.setOnItemClickListener(new CarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }
}