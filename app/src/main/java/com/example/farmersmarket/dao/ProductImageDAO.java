package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.ProductImage;

import java.util.List;

@Dao
public interface ProductImageDAO {
    @Insert
    void insertProductImage(ProductImage productImage);

    @Insert
    void insertProductImages(List<ProductImage> productImageList);

    @Update
    void updateProductImage(ProductImage productImage);

    @Delete
    void deleteProductImage(ProductImage productImage);

    @Delete
    void deleteProductImages(List<ProductImage> productImages);

    @Query("select * from product_image")
    ProductImage[] getAllProductImage();

    @Query("select count(1) from product_image")
    int getProductImageCount();

    @Query("select * from product_image where productID = :productID")
    List<ProductImage> getProductImageByProductID(int productID);

    @Query("select URL from product_image where productID = :productID LIMIT 1")
    String getOneProductImageByProductID(int productID);
}
