package com.example.farmersmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.Orders;

import java.util.List;

public class TestAddToCart extends AppCompatActivity {
    public AppDatabase appDatabase;
    Button addTocart;
    Button goToCart;
    List<OrderDetail> arrCart;
    List<OrderDetail> arrCartByProductID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_add_to_cart);

        appDatabase = AppDatabase.getAppDatabase(this);
        addTocart = findViewById(R.id.addToCart);
        goToCart = findViewById(R.id.goToCart);

        int accountID =1 ; //Bien thay doi
        int productID =3 ; //Bien thay doi

        if (appDatabase.ordersDAO().getCurrentOrder()==null){
            String accountAdress = appDatabase.accountDAO().getAccount(accountID).address;
            appDatabase.ordersDAO().insertOrder(new Orders(1, accountID, 1, null, null, accountAdress, 0, "No description", 1));
            Order.ORDER_ID = appDatabase.ordersDAO().getCurrentOrder().orderID;
        }else{
            Order.ORDER_ID = appDatabase.ordersDAO().getCurrentOrder().orderID;
        }

        //arrCart = appDatabase.orderDetailDAO().getAllCartInOrder();
        double productPrice;
        productPrice = appDatabase.productDAO().getProduct(productID).price;

        addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appDatabase.ordersDAO().getCurrentOrder()!=null){
                    arrCartByProductID = appDatabase.orderDetailDAO().getOrderDetailByOrderIDAndProductID(productID,Order.ORDER_ID);
                    if (!arrCartByProductID.isEmpty()){
                        int quantity = arrCartByProductID.get(0).quantity+1;
                        appDatabase.orderDetailDAO().updateQuantityAndPrice(productID,Order.ORDER_ID,quantity,quantity*productPrice);
                    }else{
                        appDatabase.orderDetailDAO().insertOrderDetail(new OrderDetail(Order.ORDER_ID, productID, 1, productPrice, "", 1));
                    }
                }else{
                    appDatabase.orderDetailDAO().insertOrderDetail(new OrderDetail(Order.ORDER_ID, productID, 1, productPrice, "", 1));
                }
            }
        });

        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Cart.class);
                startActivity(intent);
            }
        });
    }

}