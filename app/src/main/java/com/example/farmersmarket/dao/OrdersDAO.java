package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.Order;
import com.example.farmersmarket.object.Orders;

import java.util.ArrayList;

@Dao
public interface OrdersDAO {

    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Query("select * from orders")
    ArrayList<Orders> getAllOrders();

    @Query("select count(1) from orders")
    int getOrdersCount();

    @Query("select * from orders where orderID = :orderID")
    Order getOrder(int orderID);

    @Query("select * from orders where accountID = :accountID")
    ArrayList<Orders> getOrdersOfAccount(int accountID);

    @Query("select * from orders where storeHouseID = :storeHouseID")
    ArrayList<Orders> getOrdersOfStoreHouse(int storeHouseID);

    @Query("select * from orders where shippingID = :shippingID")
    ArrayList<Orders> getOrdersOfShipping(int shippingID);
}
