package com.example.farmersmarket.object;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_type")
public class ProductType {
    @PrimaryKey(autoGenerate = true)
    public int productTypeID ;
    public String productTypeName ;
    public int status ;

    @Ignore
    public ProductType(String productTypeName, int status) {
        this.productTypeName = productTypeName;
        this.status = status;
    }

    public ProductType() {
    }
}
