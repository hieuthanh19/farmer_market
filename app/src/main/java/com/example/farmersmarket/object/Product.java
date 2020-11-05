package com.example.farmersmarket.object;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "product", foreignKeys ={
        @ForeignKey(entity = StoreHouse.class, parentColumns = "storeID", childColumns = "storeID"),
        @ForeignKey(entity = ProductType.class, parentColumns = "productTypeID", childColumns = "productTypeID")
}
)
public class Product {
    @PrimaryKey
    public int productID;

    public int storeID;
    public int productTypeID;
    public String name;
    public double amount;
    public double price;
    public String origin;
    public double currentPrice ;
    public String description;
    public int status;
}
