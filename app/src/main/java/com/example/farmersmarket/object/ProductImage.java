package com.example.farmersmarket.object;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_image", foreignKeys = @ForeignKey(entity = Product.class, parentColumns = "productID", childColumns = "productID"))
public class ProductImage {
    @PrimaryKey
    public int imageID ;

    public int productID ;
    public String URL ;
    public int status;
}
