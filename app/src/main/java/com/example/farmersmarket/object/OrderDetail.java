package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "order_detail",
        primaryKeys = {"orderID", "productID"},
        foreignKeys = {
                @ForeignKey(entity = Product.class, parentColumns = "productID", childColumns = "productID"),
                @ForeignKey(entity = Orders.class, parentColumns = "orderID", childColumns = "orderID")
        },
        indices = {
                @Index("orderID"),
                @Index("productID")
        })
public class OrderDetail {
    public int orderID;
    public int productID;
    public int quantity;
    public double totalPrice;
    public String description;
    public int status;

    @Ignore
    public OrderDetail(int orderID, int productID, int quantity, double totalPrice, String description, int status) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.description = description;
        this.status = status;
    }

    public OrderDetail() {
    }
}
