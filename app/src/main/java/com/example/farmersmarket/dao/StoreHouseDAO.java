package com.example.farmersmarket.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.StoreHouse;

import java.util.ArrayList;

@Dao
public interface StoreHouseDAO {
    @Insert
    void insertStoreHouse(StoreHouse storeHouse);

    @Update
    void updateStoreHouse(StoreHouse storeHouse);

    @Delete
    void deleteStoreHouse(StoreHouse storeHouse);

    @Query("select * from store_house")
    ArrayList<StoreHouse> getAllStoreHouse();

    @Query("select count(1) from store_house")
    int getStoreHouseCount();

    @Query("select * from store_house where storeHouseID = :storeHouseID")
    StoreHouse getStoreHouse(int storeHouseID);

    @Query("select * from store_house where accountID = :accountID")
    StoreHouse getStoreHouseByAccountID(int accountID);
}
