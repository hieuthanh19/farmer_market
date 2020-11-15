package com.example.farmersmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.fragment.InfomationCheckoutFragment;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.viewadapter.CarAdapter;

import java.sql.Date;
import java.util.List;

public class Cart extends AppCompatActivity {
    public AppDatabase appDatabase;

    RecyclerView recyclerView ;
    TextView txtEmpty;
    CarAdapter carAdapter;
    List<OrderDetail> arrCart;
    Date c = null;
    Button btnCheckOut;

    //Find view by ID
    public void findView(){
        btnCheckOut = findViewById(R.id.btnCheckOut);
        txtEmpty = findViewById(R.id.txtEmpty);
        recyclerView = findViewById(R.id.rcCart);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        appDatabase = AppDatabase.getAppDatabase(this);

        //Find view
        findView();
        //Load data to array
        arrCart = appDatabase.orderDetailDAO().getAllCartInOrder();
        //Check array and show layout
        if (arrCart.size()!=0){
            txtEmpty.setVisibility(View.GONE);
            builAdapter();
            btnCheckOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InfomationCheckoutFragment infomationCheckoutFragment = new InfomationCheckoutFragment();
                    infomationCheckoutFragment.show(getSupportFragmentManager(),
                            "ModalBottomSheet");
                }
            });
        }else{
            recyclerView.setVisibility(View.INVISIBLE);
            btnCheckOut.setVisibility(View.INVISIBLE);
        }
    }

    //Remove item in recycler view
    public void removeItem(int position) {
        int orderID = carAdapter.getOrderId(position);
        int productID = carAdapter.getProductId(position);

        //Remove item and delete cart in db
        arrCart.remove(position);
        carAdapter.notifyItemRemoved(position);
        appDatabase.orderDetailDAO().deleteOrderCart(productID,orderID);

        if (arrCart.size()!=0){
            txtEmpty.setVisibility(View.GONE);
            builAdapter();
            btnCheckOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InfomationCheckoutFragment infomationCheckoutFragment = new InfomationCheckoutFragment();
                    infomationCheckoutFragment.show(getSupportFragmentManager(),
                            "ModalBottomSheet");
                }
            });
        }else{
            txtEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            btnCheckOut.setVisibility(View.INVISIBLE);
        }
    }

    //Create adapter for recycler view
    public void builAdapter(){
        carAdapter = new CarAdapter(arrCart,getApplicationContext());
        recyclerView.setAdapter(carAdapter);

        //Set event item click
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

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void getDatabase(View view){

    }

    public void back(View view) {
        onBackPressed();
    }
}