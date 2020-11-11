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
    List<OrderDetail> getAllOrderInCart();

    @Query("select count(1) from order_detail")
    int getOrderDetailCount();

    @Query("select * from order_detail where ordersID = :ordersID")
    OrderDetail getOrderDetail(int ordersID);

    @Query("select name from product where productID=:id")
    String getProductNameByProductID(int id);

    @Query("delete from order_detail WHERE ordersID = :orderID AND productID=:productId")
    void deleteOrderCart(int productId, int orderID);

    @Query("update order_detail set quantity =:quantity where productID=:productId and ordersID=:orderID")
    void updateQuantity(int productId, int orderID, int quantity);

    @Query("select * from order_detail where ordersID = :orderID")
    List<OrderDetail> getAllOrderDetailByOrderID(int orderID);

    @Query("select sum(totalPrice*quantity) from order_detail where ordersID = :orderID")
    double getTotalCostOfOrderDetailByOrderID(int orderID);
}
