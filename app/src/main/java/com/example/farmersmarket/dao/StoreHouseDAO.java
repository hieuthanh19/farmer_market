package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.StoreHouse;

import java.util.List;

@Dao
public interface StoreHouseDAO {
    @Insert
    void insertStoreHouse(StoreHouse storeHouse);

    @Update
    void updateStoreHouse(StoreHouse storeHouse);

    @Delete
    void deleteStoreHouse(StoreHouse storeHouse);

    @Query("select * from store_house where status = 1")
    StoreHouse[] getAllActiveStoreHouse();

    @Query("select count(1) from store_house")
    int getStoreHouseCount();

    @Query("select * from store_house where storeHouseID = :storeHouseID and status = 1")
    StoreHouse getActiveStoreHouse(int storeHouseID);

    @Query("select * from store_house where accountID = :accountID and status = 1")
    List<StoreHouse> getActiveStoreHousesByAccountID(int accountID);
}
