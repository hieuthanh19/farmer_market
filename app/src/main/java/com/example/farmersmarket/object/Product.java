package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "product", foreignKeys = {
        @ForeignKey(entity = StoreHouse.class, parentColumns = "storeHouseID", childColumns = "storeHouseID"),
        @ForeignKey(entity = ProductType.class, parentColumns = "productTypeID", childColumns = "productTypeID")
}
)
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int productID;
    public int storeHouseID;
    public int productTypeID;
    public String name;
    public double amount;
    public double price;
    public String origin;
    public double currentPrice;
    public String description;
    public int status;

    @Ignore
    public Product(int storeHouseID, int productTypeID, String name, double amount, double price, String origin,
                   double currentPrice, String description, int status) {
        this.storeHouseID = storeHouseID;
        this.productTypeID = productTypeID;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.origin = origin;
        this.currentPrice = currentPrice;
        this.description = description;
        this.status = status;
    }

    public Product() {
    }
}
