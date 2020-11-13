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
    List<Orders> arrOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_add_to_cart);

        appDatabase = AppDatabase.getAppDatabase(this);
        addTocart = findViewById(R.id.addToCart);
        goToCart = findViewById(R.id.goToCart);

        int accountID =1 ; //Bien thay doi
        int productID =3 ; //Bien thay doi

        arrCart = appDatabase.orderDetailDAO().getAllCartInOrder();

        addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrCart.size()!=0){
                    double productPrice;
                    productPrice = appDatabase.productDAO().getProduct(productID).price;
                    int ordersID = arrCart.get(0).ordersID;
                    arrCartByProductID = appDatabase.orderDetailDAO().getOrderDetailByOrderIDAndProductID(productID,ordersID);
                    if (!arrCartByProductID.isEmpty()){
                        int quantity = arrCartByProductID.get(0).quantity+1;
                        appDatabase.orderDetailDAO().updateQuantityAndPrice(productID,ordersID,quantity,quantity*productPrice);
                    }else{
                        appDatabase.orderDetailDAO().insertOrderDetail(new OrderDetail(ordersID, productID, 1, productPrice, "", 1));
                    }
                }else{
                    appDatabase.ordersDAO().insertOrder(new Orders(1, accountID, 1, null, null, "1233123", 123, "No description", 0));
                    //arrOrder = appDatabase.ordersDAO().getCurrentOrder();
                    //appDatabase.orderDetail().insertOrderDetail(new OrderDetail(arrOrder.get(0).orderID, productID, 1, price, "", 1));
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