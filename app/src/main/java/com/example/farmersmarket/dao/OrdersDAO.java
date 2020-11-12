package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.Orders;

import java.util.List;

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
    List<Orders> getOrder(int orderID);

    @Query("select * from orders where orderID = :orderID and (status=1 or status =2)")
    List<Orders> getOrderIsCheckout(int orderID);

    @Query("select * from orders where accountID = :accountID and (status=1 or status =2)")
    List<Orders> getOrdersOfAccount(int accountID);

    @Query("select * from orders where storeHouseID = :storeHouseID and (status=1 or status =2)")
    List<Orders> getOrdersOfStoreHouse(int storeHouseID);

    @Query("select * from orders where shippingID = :shippingID and (status=1 or status =2)")
    List<Orders> getOrdersOfShipping(int shippingID);

    @Query("select storeName from store_house where storeHouseID=:id")
    String getStoreNameByStoreID(int id);

    @Query("select transportFee from shipping_unit where shippingID=:id")
    Double getFeeByShippingID(int id);

    @Query("select sum(totalPrice) from order_detail where ordersID = :orderID")
    double getTotalCostOfOrderDetailByOrderID(int orderID);

    @Query("update orders set total =:total, status=1 where orderID=:orderID")
    void addToOrder(int orderID,double total);

    @Query("select * from orders where status=0")
    List<Orders> getCurrentOrder();
}
