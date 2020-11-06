package com.example.farmersmarket.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.Product;
import com.example.farmersmarket.object.ProductImage;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProductImageDAO {
    @Insert
    void insertProductImage(ProductImage productImage);

    @Update
    void updateProductImage(ProductImage productImage);

    @Delete
    void deleteProductImage(ProductImage productImage);

    @Query("select * from product_image")
    ProductImage[] getAllProductImage();

    @Query("select count(1) from product_image")
    int getProductImageCount();

    @Query("select * from product_image where productID = :productID")
    List<ProductImage> getProductImageByProductID(int productID);
}
