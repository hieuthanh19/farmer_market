package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    long insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("select * from product")
    List<Product> getAllProduct();

    @Query("select count(1) from product")
    int getProductCount();

    @Query("select * from product where productID = :productID")
    Product getProduct(int productID);

    @Query("select * from product where productTypeID = :productTypeID")
    List<Product> getProductByCategory(int productTypeID);

    @Query("select * from product where storeHouseID = :storehouseID order by productID desc")
    List<Product> getProductByStoreHouseDesc(int storehouseID);

    @Query("select * from product where storeHouseID = :storehouseID")
    List<Product> getProductByStoreHouseAsc(int storehouseID);


}
