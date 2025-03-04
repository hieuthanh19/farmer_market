package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDAO {
    @Insert
    void insertOrderDetail(OrderDetail orderDetail);

    @Update
    void updateOrderDetail(OrderDetail orderDetail);

    @Delete
    void deleteOrderDetail(OrderDetail orderDetail);

    @Query("select * from order_detail")
    List<OrderDetail> getAllOrderDetail();

    @Query("select * from order_detail where status=1")
    List<OrderDetail> getAllCartInOrder();

    @Query("select count(1) from order_detail")
    int getOrderDetailCount();

    @Query("delete from order_detail WHERE orderID = :orderID AND productID=:productId")
    void deleteOrderCart(int productId, int orderID);

    @Query("update order_detail set quantity =:quantity, totalPrice=:totalPrice where productID=:productId and " +
            "orderID=:orderID")
    void updateQuantityAndPrice(int productId, int orderID, int quantity, double totalPrice);

    @Query("select * from order_detail where orderID = :orderID")
    List<OrderDetail> getAllOrderDetailByOrderID(int orderID);

    @Query("select sum(totalPrice) from order_detail where orderID = :orderID and status=1")
    double getTotalCostOfOrderDetailByOrderID(int orderID);

    @Query("update order_detail set status =:status where status = 1")
    void setStatusOrderDetail(int status);

    @Query("select * from order_detail where productID=:productID and orderID=:ordersID")
    List<OrderDetail> getOrderDetailByOrderIDAndProductID(int productID,int ordersID);

}
