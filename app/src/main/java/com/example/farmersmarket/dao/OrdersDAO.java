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

    @Query("select * from orders where orderID = :orderID and (status=2 or status =3)")
    List<Orders> getOrderIsCheckout(int orderID);

    @Query("select * from orders where accountID = :accountID and (status=2 or status =3)")
    List<Orders> getOrdersOfAccount(int accountID);

    @Query("select * from orders where accountID = :accountID and (status=2 or status =3) order by orderID desc")
    List<Orders> getOrdersOfAccountOrderByDESC(int accountID);

    @Query("select * from orders where storeHouseID = :storeHouseID and (status=2 or status =3)")
    List<Orders> getOrdersOfStoreHouse(int storeHouseID);

    @Query("select * from orders where shippingID = :shippingID and (status=2 or status =3)")
    List<Orders> getOrdersOfShipping(int shippingID);

    @Query("select storeName from store_house where storeHouseID=:id")
    String getStoreNameByStoreID(int id);

    @Query("select transportFee from shipping_unit where shippingID=:id")
    Double getFeeByShippingID(int id);

    @Query("select sum(totalPrice) from order_detail where orderID = :orderID")
    double getTotalCostOfOrderDetailByOrderID(int orderID);

    @Query("update orders set total =:total, status=2 where orderID=:orderID")
    void checkOut(int orderID,double total);

    @Query("select * from orders where status=1 limit 1")
    Orders getCurrentOrder();

    @Query("update orders set orderedDate =:orderDate where orderID=:orderID")
    void updateDate(long orderDate, int orderID);
}
