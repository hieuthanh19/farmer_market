package com.example.farmersmarket.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.ProductType;

import java.util.ArrayList;

@Dao
public interface ProductTypeDAO {
    @Insert
    void insertProductType(ProductType productType);

    @Update
    void updateProductType(ProductType productType);

    @Delete
    void deleteProductType(ProductType productType);

    @Query("select * from product_type")
    public ProductType[] getAllProductType();

    @Query("select count(1) from product_type")
    int getProductTypeCount();

    @Query("select * from product_type where productTypeID = :productTypeID")
    ProductType getProductType(int productTypeID);
}
