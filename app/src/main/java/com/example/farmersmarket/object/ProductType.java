package com.example.farmersmarket.object;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_type")
public class ProductType {
    @PrimaryKey(autoGenerate = true)
    public int productTypeID ;
    public String productTypeName ;
    public int status ;

    public ProductType(int productTypeID, String productTypeName, int status) {
        this.productTypeID = productTypeID;
        this.productTypeName = productTypeName;
        this.status = status;
    }

    public ProductType() {
    }
}
