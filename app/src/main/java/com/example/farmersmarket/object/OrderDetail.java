package com.example.farmersmarket.object;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_detail",primaryKeys = {"ordersID","productID"})
public class OrderDetail {
    public int ordersID ;
    public int productID ;
    public int quantity ;
    public double totalPrice ;
    public String description ;
    public int status ;
}
