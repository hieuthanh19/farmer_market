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

    @Query("select * from product where status = 1")
    List<Product> getAllActiveProduct();

    @Query("select count(1) from product")
    int getProductCount();

    @Query("select * from product where productID = :productID")
    Product getProduct(int productID);

    @Query("select * from product where productTypeID = :productTypeID and status = 1")
    List<Product> getActiveProductByCategory(int productTypeID);

    @Query("select * from product where storeHouseID = :storehouseID and status = 1 order by productID desc")
    List<Product> getActiveProductByStoreHouseDesc(int storehouseID);

    @Query("select * from product where storeHouseID = :storehouseID and status = 1")
    List<Product> getActiveProductByStoreHouseAsc(int storehouseID);


}
