package com.example.farmersmarket.object;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_detail", foreignKeys = @ForeignKey(entity = Product.class, parentColumns = "productID", childColumns = "productID"))
public class OrderDetail {
    @PrimaryKey
    public int ordersID ;

    public int productID ;
    public int quantity ;
    public double totalPrice ;
    public String description ;
    public int status ;
}
