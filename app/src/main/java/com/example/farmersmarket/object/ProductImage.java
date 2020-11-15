package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_image",
        foreignKeys = @ForeignKey(entity = Product.class, parentColumns = "productID", childColumns = "productID"),
        indices = @Index("productID"))
public class ProductImage {
    @PrimaryKey(autoGenerate = true)
    public int imageID;
    public int productID;
    public String URL;
    public int status;

    @Ignore
    public ProductImage(int productID, String URL, int status) {
        this.productID = productID;
        this.URL = URL;
        this.status = status;
    }

    public ProductImage() {
    }
}
