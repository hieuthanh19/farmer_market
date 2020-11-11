package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.Orders;

@Dao
public interface OrdersDAO {

    @Insert
    void insertOrder(Orders order);

    @Update
    void updateOrder(Orders order);

    @Delete
    void deleteOrder(Orders order);

    @Query("select * from orders")
    Orders[] getAllOrders();

    @Query("select count(1) from orders")
    int getOrdersCount();

    @Query("select * from orders where orderID = :orderID")
    Orders getOrder(int orderID);

    @Query("select * from orders where accountID = :accountID")
    Orders[] getOrdersOfAccount(int accountID);

    @Query("select * from orders where storeHouseID = :storeHouseID")
    Orders[] getOrdersOfStoreHouse(int storeHouseID);

    @Query("select * from orders where shippingID = :shippingID")
    Orders[] getOrdersOfShipping(int shippingID);


}
