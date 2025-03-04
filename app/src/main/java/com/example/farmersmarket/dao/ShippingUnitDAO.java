package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.ShippingUnit;

@Dao
public interface ShippingUnitDAO {

    @Insert
    void insertShippingUnit(ShippingUnit shippingUnit);

    @Update
    void updateShippingUnit(ShippingUnit shippingUnit);

    @Delete
    void deleteShippingUnit(ShippingUnit shippingUnit);

    @Query("select * from shipping_unit")
    ShippingUnit[] getAllShippingUnits();

    @Query("select count(1) from shipping_unit")
    int getShippingUnitCount();

    @Query("select * from shipping_unit where shippingID = :shippingUnitID")
    ShippingUnit getShippingUnit(int shippingUnitID);

    @Query("select * from shipping_unit where accountID = :accountID")
    ShippingUnit[] getShippingUnitOfAccount(int accountID);
}
