package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "order_detail",primaryKeys = {"ordersID","productID"})
public class OrderDetail {
    public int ordersID ;
    public int productID ;
    public int quantity ;
    public double totalPrice ;
    public String description ;
    public int status ;

    @Ignore
    public OrderDetail(int ordersID, int productID, int quantity, double totalPrice, String description, int status) {
        this.ordersID = ordersID;
        this.productID = productID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.description = description;
        this.status = status;
    }

    public OrderDetail() {
    }
}
