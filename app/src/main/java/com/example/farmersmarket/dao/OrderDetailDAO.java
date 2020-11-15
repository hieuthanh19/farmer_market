package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.OrderDetail;

@Dao
public interface OrderDetailDAO {
    @Insert
    void insertOrderDetail(OrderDetail orderDetail);

    @Update
    void updateOrderDetail(OrderDetail orderDetail);

    @Delete
    void deleteOrderDetail(OrderDetail orderDetail);

    @Query("select * from order_detail")
    OrderDetail[] getAllOrderDetail();

    @Query("select count(1) from order_detail")
    int getOrderDetailCount();

    @Query("select * from order_detail where orderID = :orderID")
    OrderDetail getOrderDetail(int orderID);
}
